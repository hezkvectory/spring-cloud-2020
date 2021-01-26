package com.hezk.zk.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class NIOServerCnxn {
    private static final Logger log = LoggerFactory.getLogger(NIOServerCnxn.class);

    private final SelectionKey selectionKey;
    private final SocketChannel socketChannel;
    private final NioServerFactory.SelectorThread selectorThread;
    private final NioServerFactory factory;
    private final AtomicBoolean selectable = new AtomicBoolean(true);
    private volatile boolean stale = false;
    private long sessionId;
    protected DisconnectReason disconnectReason = DisconnectReason.UNKNOWN;

    private int sessionTimeout = 100000000;

    private final AtomicBoolean throttled = new AtomicBoolean(false);

    private final Queue<ByteBuffer> outgoingBuffers = new LinkedBlockingQueue<ByteBuffer>();

    public NIOServerCnxn(SocketChannel socketChannel, SelectionKey selectionKey, NioServerFactory factory,
                         NioServerFactory.SelectorThread selectorThread) throws IOException {
        this.socketChannel = socketChannel;
        this.selectionKey = selectionKey;
        this.factory = factory;
        this.selectorThread = selectorThread;
        socketChannel.socket().setTcpNoDelay(true);
        socketChannel.socket().setSoLinger(false, -1);
        InetAddress addr = ((InetSocketAddress) socketChannel.socket().getRemoteSocketAddress()).getAddress();
    }

    public void enableSelectable() {
        selectable.set(true);
    }

    public boolean isSelectable() {
        return selectionKey.isValid() && selectable.get();
    }

    public void close(DisconnectReason reason) {
        disconnectReason = reason;
        close();
    }

    public InetAddress getSocketAddress() {
        if (!socketChannel.isOpen()) {
            return null;
        }
        return socketChannel.socket().getInetAddress();
    }

    public int getInterestOps() {
        if (!isSelectable()) {
            return 0;
        }
        int interestOps = 0;
        if (getReadInterest()) {
            interestOps |= SelectionKey.OP_READ;
        }
        if (getWriteInterest()) {
            interestOps |= SelectionKey.OP_WRITE;
        }
        return interestOps;
    }

    private boolean getWriteInterest() {
        return !outgoingBuffers.isEmpty();
    }

    private boolean getReadInterest() {
        return !throttled.get();
    }

    public void setStale() {
        stale = true;
    }

    public long getSessionId() {
        return sessionId;
    }

    private void close() {
        setStale();
        if (!factory.removeCnxn(this)) {
            return;
        }

        if (selectionKey != null) {
            try {
                // need to cancel this selection key from the selector
                selectionKey.cancel();
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("ignoring exception during selectionkey cancel", e);
                }
            }
        }

        closeSock();
    }

    public void disableSelectable() {
        selectable.set(false);
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }


    private void closeSock() {
        if (socketChannel.isOpen() == false) {
            return;
        }

        log.debug("Closed socket connection for client "
                + socketChannel.socket().getRemoteSocketAddress()
                + (sessionId != 0 ?
                " which had sessionid 0x" + Long.toHexString(sessionId) :
                " (no session established for client)"));
        closeSock(socketChannel);
    }


    public static void closeSock(SocketChannel sock) {
        if (!sock.isOpen()) {
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

    protected boolean isSocketOpen() {
        return socketChannel.isOpen();
    }


    /**
     * 具体的read、write事件在这里处理，进行数据包的拆分
     *
     * @param k
     * @throws InterruptedException
     */
    void doIO(SelectionKey k) throws InterruptedException {
        try {
            if (!isSocketOpen()) {
                log.warn("trying to do i/o on a null socket for session:0x" + Long.toHexString(sessionId));
                return;
            }
            if (k.isReadable()) {//只有客户端发送数据，才会触发这里方法的执行
                ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                int rc = socketChannel.read(byteBuffer);
                byteBuffer.flip();
                log.debug("------r--{}, {}, {}", k.interestOps(), rc, new String(byteBuffer.array()));
            }
            if (k.isWritable()) {
                log.debug("------w--", k.interestOps());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CancelledKeyException e) {
            e.printStackTrace();
            log.warn("CancelledKeyException causing close of session 0x" + Long.toHexString(sessionId));
            if (log.isDebugEnabled()) {
                log.debug("CancelledKeyException stack trace", e);
            }
            close(DisconnectReason.CANCELLED_KEY_EXCEPTION);
        }
    }

    public enum DisconnectReason {
        UNKNOWN("unknown"),
        SERVER_SHUTDOWN("server_shutdown"),
        CONNECTION_EXPIRED("connection_expired"),
        CANCELLED_KEY_EXCEPTION("cancelled_key_exception"),
        CLEAN_UP("clean_up"),
        CONNECTION_MODE_CHANGED("connection_mode_changed");
        String disconnectReason;

        DisconnectReason(String reason) {
            this.disconnectReason = reason;
        }

        public String toDisconnectReasonString() {
            return disconnectReason;
        }
    }

}
