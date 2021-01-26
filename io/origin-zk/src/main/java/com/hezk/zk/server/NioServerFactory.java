package com.hezk.zk.server;

import com.hezk.zk.common.ExpiryQueue;
import com.hezk.zk.common.WorkerService;
import com.hezk.zk.common.ZooKeeperThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class NioServerFactory {
    private static final Logger log = LoggerFactory.getLogger(NIOServerCnxn.class);

    protected final Set<NIOServerCnxn> cnxns = Collections.newSetFromMap(new ConcurrentHashMap<>());
    protected int maxClientCnxns = 60;
    private volatile boolean stopped = true;
    private AcceptThread acceptThread;
    private ConnectionExpirerThread expirerThread;
    private final ConcurrentHashMap<InetAddress, Set<NIOServerCnxn>> ipMap = new ConcurrentHashMap<>();
    private ExpiryQueue<NIOServerCnxn> cnxnExpiryQueue;
    private final Set<SelectorThread> selectorThreads = new HashSet<>();
    protected WorkerService workerPool;
    final ConcurrentHashMap<Long, NIOServerCnxn> sessionMap = new ConcurrentHashMap<Long, NIOServerCnxn>();
    private int numSelectorThreads;
    private int numWorkerThreads;
    private long workerShutdownTimeoutMS;
    public static final String ZOOKEEPER_NIO_NUM_SELECTOR_THREADS = "zookeeper.nio.numSelectorThreads";
    public static final String ZOOKEEPER_NIO_NUM_WORKER_THREADS = "zookeeper.nio.numWorkerThreads";
    ServerSocketChannel serverSocketChannel;
    /**
     * Default worker pool shutdown timeout in ms: 5000 (5s)
     */
    public static final String ZOOKEEPER_NIO_SHUTDOWN_TIMEOUT = "zookeeper.nio.shutdownTimeout";

    static {
        try {
            Selector.open().close();
        } catch (IOException ie) {
            log.error("Selector failed to open", ie);
        }
    }

    private abstract class AbstractSelectThread extends ZooKeeperThread {
        protected final Selector selector;

        public AbstractSelectThread(String name) throws IOException {
            super(name);
            // Allows the JVM to shutdown even if this thread is still running.
            setDaemon(true);
            this.selector = Selector.open();
        }

        public void wakeupSelector() {
            selector.wakeup();
        }

        /**
         * Close the selector. This should be called when the thread is about to
         * exit and no operation is going to be performed on the Selector or
         * SelectionKey
         */
        protected void closeSelector() {
            try {
                selector.close();
            } catch (IOException e) {
                log.warn("ignored exception during selector close "
                        + e.getMessage());
            }
        }

        protected void cleanupSelectionKey(SelectionKey key) {
            if (key != null) {
                try {
                    key.cancel();
                } catch (Exception ex) {
                    if (log.isDebugEnabled()) {
                        log.debug("ignoring exception during selectionkey cancel", ex);
                    }
                }
            }
        }

        protected void fastCloseSock(SocketChannel sc) {
            if (sc != null) {
                try {
                    // Hard close immediately, discarding buffers
                    sc.socket().setSoLinger(true, 0);
                } catch (SocketException e) {
                    log.warn("Unable to set socket linger to 0, socket close"
                            + " may stall in CLOSE_WAIT", e);
                }
                closeSock(sc);
            }
        }
    }


    public static void closeSock(SocketChannel sock) {
        if (sock.isOpen() == false) {
            return;
        }

        try {
            /*
             * The following sequence of code is stupid! You would think that
             * only sock.close() is needed, but alas, it doesn't work that way.
             * If you just do sock.close() there are cases where the socket
             * doesn't actually close...
             */
            sock.socket().shutdownOutput();
        } catch (IOException e) {
            // This is a relatively common exception that we can't avoid
            if (log.isDebugEnabled()) {
                log.debug("ignoring exception during output shutdown", e);
            }
        }
        try {
            sock.socket().shutdownInput();
        } catch (IOException e) {
            // This is a relatively common exception that we can't avoid
            if (log.isDebugEnabled()) {
                log.debug("ignoring exception during input shutdown", e);
            }
        }
        try {
            sock.socket().close();
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("ignoring exception during socket close", e);
            }
        }
        try {
            sock.close();
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("ignoring exception during socketchannel close", e);
            }
        }
    }


    private class AcceptThread extends AbstractSelectThread {
        private final ServerSocketChannel serverSocketChannel;
        private final SelectionKey acceptKey;
        private final Collection<SelectorThread> selectorThreads;
        private Iterator<SelectorThread> selectorIterator;
        private volatile boolean reconfiguring = false;

        public AcceptThread(ServerSocketChannel ss, InetSocketAddress addr,
                            Set<SelectorThread> selectorThreads) throws IOException {
            super("NIOServerCxnFactory.AcceptThread:" + addr);
            this.serverSocketChannel = ss;
            this.acceptKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            this.selectorThreads = Collections.unmodifiableList(new ArrayList<>(selectorThreads));
            selectorIterator = this.selectorThreads.iterator();
        }

        public void run() {
            try {
                while (!stopped && !serverSocketChannel.socket().isClosed()) {
                    try {
                        select();
                    } catch (RuntimeException e) {
                        log.warn("Ignoring unexpected runtime exception", e);
                    } catch (Exception e) {
                        log.warn("Ignoring unexpected exception", e);
                    }
                }
            } finally {
                closeSelector();
                // This will wake up the selector threads, and tell the
                // worker thread pool to begin shutdown.
                if (!reconfiguring) {
                    NioServerFactory.this.stop();
                }
                log.info("accept thread exitted run method");
            }
        }

        public void setReconfiguring() {
            reconfiguring = true;
        }

        private void select() {
            try {
                int select = selector.select();

                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (!stopped && selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        if (!doAccept()) {
                            // If unable to pull a new connection off the accept
                            // queue, pause accepting to give us time to free
                            // up file descriptors and so the accept thread
                            // doesn't spin in a tight loop.
                            pauseAccept(10);
                        }
                    } else {
                        log.warn("Unexpected ops in accept select "
                                + key.readyOps());
                    }
                }
            } catch (IOException e) {
                log.warn("Ignoring IOException while selecting", e);
            }
        }

        /**
         * Mask off the listen socket interest ops and use select() to sleep
         * so that other threads can wake us up by calling wakeup() on the
         * selector.
         */
        private void pauseAccept(long millisecs) {
            acceptKey.interestOps(0);
            try {
                selector.select(millisecs);
            } catch (IOException e) {
                // ignore
            } finally {
                acceptKey.interestOps(SelectionKey.OP_ACCEPT);
            }
        }

        /**
         * Accept new socket connections. Enforces maximum number of connections
         * per client IP address. Round-robin assigns to selector thread for
         * handling. Returns whether pulled a connection off the accept queue
         * or not. If encounters an error attempts to fast close the socket.
         *
         * @return whether was able to accept a connection or not
         */
        private boolean doAccept() {
            boolean accepted = false;
            SocketChannel sc = null;
            try {
                sc = serverSocketChannel.accept();
                accepted = true;
                InetAddress ia = sc.socket().getInetAddress();
                int cnxncount = getClientCnxnCount(ia);

                if (maxClientCnxns > 0 && cnxncount >= maxClientCnxns) {
                    throw new IOException("Too many connections from " + ia + " - max is " + maxClientCnxns);
                }

                log.debug("Accepted socket connection from " + sc.socket().getRemoteSocketAddress());
                sc.configureBlocking(false);

                // Round-robin assign this connection to a selector thread
                if (!selectorIterator.hasNext()) {
                    selectorIterator = selectorThreads.iterator();
                }
                SelectorThread selectorThread = selectorIterator.next();
                //交给selector 线程执行
                if (!selectorThread.addAcceptedConnection(sc)) {
                    throw new IOException(  "Unable to add connection to selector queue"  + (stopped ? " (shutdown in progress)" : ""));
                }
            } catch (IOException e) {
                e.printStackTrace();
                // accept, maxClientCnxns, configureBlocking
                fastCloseSock(sc);
            }
            return accepted;
        }
    }

    public void stop() {
        stopped = true;

        // Stop queuing connection attempts
        try {
            serverSocketChannel.close();
        } catch (IOException e) {
            log.warn("Error closing listen socket", e);
        }

        if (acceptThread != null) {
            if (acceptThread.isAlive()) {
                acceptThread.wakeupSelector();
            } else {
                acceptThread.closeSelector();
            }
        }
        if (expirerThread != null) {
            expirerThread.interrupt();
        }
        for (SelectorThread thread : selectorThreads) {
            if (thread.isAlive()) {
                thread.wakeupSelector();
            } else {
                thread.closeSelector();
            }
        }
        if (workerPool != null) {
            workerPool.stop();
        }
    }


    class SelectorThread extends AbstractSelectThread {
        private final int id;
        private final Queue<SocketChannel> acceptedQueue;
        private final Queue<SelectionKey> updateQueue;

        public SelectorThread(int id) throws IOException {
            super("NIOServerCxnFactory.SelectorThread-" + id);
            this.id = id;
            acceptedQueue = new LinkedBlockingQueue<>();
            updateQueue = new LinkedBlockingQueue<>();
        }

        /**
         * Place new accepted connection onto a queue for adding. Do this
         * so only the selector thread modifies what keys are registered
         * with the selector.
         */
        public boolean addAcceptedConnection(SocketChannel accepted) {
            if (stopped || !acceptedQueue.offer(accepted)) {
                return false;
            }
            wakeupSelector();
            return true;
        }

        /**
         * Place interest op update requests onto a queue so that only the
         * selector thread modifies interest ops, because interest ops
         * reads/sets are potentially blocking operations if other select
         * operations are happening.
         */
        public boolean addInterestOpsUpdateRequest(SelectionKey sk) {
            if (stopped || !updateQueue.offer(sk)) {
                return false;
            }
            wakeupSelector();
            return true;
        }

        /**
         * The main loop for the thread selects() on the connections and
         * dispatches ready I/O work requests, then registers all pending
         * newly accepted connections and updates any interest ops on the
         * queue.
         */
        public void run() {
            try {
                while (!stopped) {
                    try {
                        //accept事件第一次select()方法不会有任何事件触发，通过seletor.wakeUp（）唤起，
                        select();
                        //这个方法会触发registor(SelectorKey.OP_READ)事件
                        processAcceptedConnections();
                        processInterestOpsUpdateRequests();
                    } catch (RuntimeException e) {
                        log.warn("Ignoring unexpected runtime exception", e);
                    } catch (Exception e) {
                        log.warn("Ignoring unexpected exception", e);
                    }
                }

                // Close connections still pending on the selector. Any others
                // with in-flight work, let drain out of the work queue.
                for (SelectionKey key : selector.keys()) {
                    NIOServerCnxn cnxn = (NIOServerCnxn) key.attachment();
                    if (cnxn.isSelectable()) {
                        cnxn.close(NIOServerCnxn.DisconnectReason.SERVER_SHUTDOWN);
                    }
                    cleanupSelectionKey(key);
                }
                SocketChannel accepted;
                while ((accepted = acceptedQueue.poll()) != null) {
                    fastCloseSock(accepted);
                }
                updateQueue.clear();
            } finally {
                closeSelector();
                // This will wake up the accept thread and the other selector
                // threads, and tell the worker thread pool to begin shutdown.
                NioServerFactory.this.stop();
                log.info("selector thread exitted run method");
            }
        }

        private void select() {
            try {
                //selector.wakeUp()方法会唤起select()方法执行
                //registor()方法同样能唤起select()方法执行
                int select = selector.select();

                Set<SelectionKey> selected = selector.selectedKeys();
                ArrayList<SelectionKey> selectedList = new ArrayList<SelectionKey>(selected);
                Collections.shuffle(selectedList);
                Iterator<SelectionKey> selectedKeys = selectedList.iterator();
                while (!stopped && selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selected.remove(key);

                    if (!key.isValid()) {
                        cleanupSelectionKey(key);
                        continue;
                    }
                    if (key.isReadable() || key.isWritable()) {
                        handleIO(key);
                    } else {
                        log.warn("Unexpected ops in select " + key.readyOps());
                    }
                }
            } catch (IOException e) {
                log.warn("Ignoring IOException while selecting", e);
            }
        }

        /**
         * Schedule I/O for processing on the connection associated with
         * the given SelectionKey. If a worker thread pool is not being used,
         * I/O is run directly by this thread.
         */
        private void handleIO(SelectionKey key) {
            IOWorkRequest workRequest = new IOWorkRequest(this, key);
            NIOServerCnxn cnxn = (NIOServerCnxn) key.attachment();

            // Stop selecting this key while processing on its
            // connection
            cnxn.disableSelectable();
            key.interestOps(0);
            touchCnxn(cnxn);
            workerPool.schedule(workRequest);
        }

        /**
         * Iterate over the queue of accepted connections that have been
         * assigned to this thread but not yet placed on the selector.
         */
        private void processAcceptedConnections() {
            SocketChannel accepted;
            while (!stopped && (accepted = acceptedQueue.poll()) != null) {
                SelectionKey key = null;
                try {
                    key = accepted.register(selector, SelectionKey.OP_READ);
                    NIOServerCnxn cnxn = createConnection(accepted, key, this);
                    key.attach(cnxn);
                    addCnxn(cnxn);
                } catch (IOException e) {
                    e.printStackTrace();
                    // register, createConnection
                    cleanupSelectionKey(key);
                    fastCloseSock(accepted);
                }
            }
        }

        /**
         * Iterate over the queue of connections ready to resume selection,
         * and restore their interest ops selection mask.
         */
        private void processInterestOpsUpdateRequests() {
            SelectionKey key;
            while (!stopped && (key = updateQueue.poll()) != null) {
                if (!key.isValid()) {
                    cleanupSelectionKey(key);
                }
                NIOServerCnxn cnxn = (NIOServerCnxn) key.attachment();
                if (cnxn.isSelectable()) {
                    key.interestOps(cnxn.getInterestOps());
                }
            }
        }
    }


    private void addCnxn(NIOServerCnxn cnxn) throws IOException {
        InetAddress addr = cnxn.getSocketAddress();
        if (addr == null) {
            throw new IOException("Socket of " + cnxn + " has been closed");
        }
        Set<NIOServerCnxn> set = ipMap.get(addr);
        if (set == null) {
            // in general we will see 1 connection from each
            // host, setting the initial cap to 2 allows us
            // to minimize mem usage in the common case
            // of 1 entry --  we need to set the initial cap
            // to 2 to avoid rehash when the first entry is added
            // Construct a ConcurrentHashSet using a ConcurrentHashMap
            set = Collections.newSetFromMap(new ConcurrentHashMap<NIOServerCnxn, Boolean>(2));
            // Put the new set in the map, but only if another thread
            // hasn't beaten us to it
            Set<NIOServerCnxn> existingSet = ipMap.putIfAbsent(addr, set);
            if (existingSet != null) {
                set = existingSet;
            }
        }
        set.add(cnxn);

        cnxns.add(cnxn);
        touchCnxn(cnxn);
    }

    public void touchCnxn(NIOServerCnxn cnxn) {
        cnxnExpiryQueue.update(cnxn, cnxn.getSessionTimeout());
    }

    public boolean removeCnxn(NIOServerCnxn cnxn) {
        // If the connection is not in the master list it's already been closed
        if (!cnxns.remove(cnxn)) {
            return false;
        }
        cnxnExpiryQueue.remove(cnxn);

        removeCnxnFromSessionMap(cnxn);

        InetAddress addr = cnxn.getSocketAddress();
        if (addr != null) {
            Set<NIOServerCnxn> set = ipMap.get(addr);
            if (set != null) {
                set.remove(cnxn);
                // Note that we make no effort here to remove empty mappings
                // from ipMap.
            }
        }

        return true;
    }

    protected NIOServerCnxn createConnection(SocketChannel sock,
                                             SelectionKey sk, SelectorThread selectorThread) throws IOException {
        return new NIOServerCnxn(sock, sk, this, selectorThread);
    }

    public void removeCnxnFromSessionMap(NIOServerCnxn cnxn) {
        long sessionId = cnxn.getSessionId();
        if (sessionId != 0) {
            sessionMap.remove(sessionId);
        }
    }

    private class ConnectionExpirerThread extends ZooKeeperThread {
        ConnectionExpirerThread() {
            super("ConnnectionExpirer");
        }

        public void run() {
            try {
                while (!stopped) {
                    long waitTime = cnxnExpiryQueue.getWaitTime();
                    if (waitTime > 0) {
                        Thread.sleep(waitTime);
                        continue;
                    }
                    for (NIOServerCnxn conn : cnxnExpiryQueue.poll()) {
//                        ServerMetrics.getMetrics().SESSIONLESS_CONNECTIONS_EXPIRED.add(1);
                        conn.close(NIOServerCnxn.DisconnectReason.CONNECTION_EXPIRED);
                    }
                }

            } catch (InterruptedException e) {
                log.info("ConnnectionExpirerThread interrupted");
            }
        }
    }

    private int getClientCnxnCount(InetAddress cl) {
        Set<NIOServerCnxn> s = ipMap.get(cl);
        if (s == null){
            return 0;
        }
        return s.size();
    }


    private class IOWorkRequest extends WorkerService.WorkRequest {
        private final SelectorThread selectorThread;
        private final SelectionKey key;
        private final NIOServerCnxn cnxn;

        IOWorkRequest(SelectorThread selectorThread, SelectionKey key) {
            this.selectorThread = selectorThread;
            this.key = key;
            this.cnxn = (NIOServerCnxn) key.attachment();
        }

        public void doWork() throws InterruptedException {
            if (!key.isValid()) {
                selectorThread.cleanupSelectionKey(key);
                return;
            }

            if (key.isReadable() || key.isWritable()) {
                cnxn.doIO(key);

                // Check if we shutdown or doIO() closed this connection
                if (stopped) {
                    cnxn.close(NIOServerCnxn.DisconnectReason.SERVER_SHUTDOWN);
                    return;
                }
                if (!key.isValid()) {
                    selectorThread.cleanupSelectionKey(key);
                    return;
                }
                touchCnxn(cnxn);
            }

            // Mark this connection as once again ready for selection
            cnxn.enableSelectable();
            // Push an update request on the queue to resume selecting
            // on the current set of interest ops, which may have changed
            // as a result of the I/O operations we just performed.
            if (!selectorThread.addInterestOpsUpdateRequest(key)) {
                cnxn.close(NIOServerCnxn.DisconnectReason.CONNECTION_MODE_CHANGED);
            }
        }

        @Override
        public void cleanup() {
            cnxn.close(NIOServerCnxn.DisconnectReason.CLEAN_UP);
        }
    }

    public void configure(InetSocketAddress addr, int maxcc) throws IOException {

        maxClientCnxns = maxcc;
        cnxnExpiryQueue = new ExpiryQueue<>(10000);
        expirerThread = new ConnectionExpirerThread();

        int numCores = Runtime.getRuntime().availableProcessors();
        // 32 cores sweet spot seems to be 4 selector threads
        numSelectorThreads = Integer.getInteger(ZOOKEEPER_NIO_NUM_SELECTOR_THREADS,  Math.max((int) Math.sqrt((float) numCores / 2), 1));
        if (numSelectorThreads < 1) {
            throw new IOException("numSelectorThreads must be at least 1");
        }

        numWorkerThreads = Integer.getInteger(ZOOKEEPER_NIO_NUM_WORKER_THREADS, 2 * numCores);
        workerShutdownTimeoutMS = Long.getLong(ZOOKEEPER_NIO_SHUTDOWN_TIMEOUT, 5000);

        for (int i = 0; i < numSelectorThreads; ++i) {
            selectorThreads.add(new SelectorThread(i));
        }

        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        log.info("binding to port " + addr);
        serverSocketChannel.socket().bind(addr);

        serverSocketChannel.configureBlocking(false);
        acceptThread = new AcceptThread(serverSocketChannel, addr, selectorThreads);
    }

    public void start() {
        stopped = false;
        if (workerPool == null) {
            workerPool = new WorkerService("NIOWorker", numWorkerThreads, false);
        }
        for (SelectorThread thread : selectorThreads) {
            if (thread.getState() == Thread.State.NEW) {
                thread.start();
            }
        }
        // ensure thread is started once and only once
        if (acceptThread.getState() == Thread.State.NEW) {
            acceptThread.start();
        }
        if (expirerThread.getState() == Thread.State.NEW) {
            expirerThread.start();
        }
    }

    public static void main(String[] args) throws IOException {
        NioServerFactory factory = new NioServerFactory();
        InetSocketAddress address = new InetSocketAddress(9999);
        factory.configure(address, 3);
        factory.start();
    }
}
