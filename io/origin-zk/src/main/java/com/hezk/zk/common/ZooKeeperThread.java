package com.hezk.zk.common;


import com.hezk.zk.server.NIOServerCnxn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooKeeperThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(NIOServerCnxn.class);

    private UncaughtExceptionHandler uncaughtExceptionalHandler = (t, e) -> handleException(t.getName(), e);

    public ZooKeeperThread(String threadName) {
        super(threadName);
        setUncaughtExceptionHandler(uncaughtExceptionalHandler);
    }

    /**
     * This will be used by the uncaught exception handler and just log a
     * warning message and return.
     *
     * @param thName - thread name
     * @param e      - exception object
     */
    protected void handleException(String thName, Throwable e) {
        log.warn("Exception occurred from thread {}", thName, e);
    }
}
