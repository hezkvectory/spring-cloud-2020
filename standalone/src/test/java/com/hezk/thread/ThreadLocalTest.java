package com.hezk.thread;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.*;

public class ThreadLocalTest {
    public static final Integer SIZE = 500;
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 5, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    static class LocalVariable {//总共有5M
        private byte[] locla = new byte[1024 * 1024 * 5];
    }

    static ThreadLocal<LocalVariable> local = new ThreadLocal<>();

    public static void main(String[] args) {
        try {
            for (int i = 0; i < SIZE; i++) {
                executor.execute(() -> {
                    local.set(new LocalVariable());
                    System.out.println("开始执行");
//                    local.remove();
                });
                Thread.sleep(100);
            }
            local = null;//这里设置为null，依旧会造成内存泄漏
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟ThreadLocal内存泄露导致OOM
     * JVM启动参数 -Xms20M -Xmx20M -Xmn10M
     *
     * @param args
     */
    @Test
    public void test1(String[] args) {
        // 是否调用remove方法
        boolean doRemove = false;
        // 加锁，让多个线程串行执行，避免多个线程同时占用内存导致的内存溢出问题
        Object lockObj = new Object();
        // 开启20个线程
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 为了不重复使用线程，用Map标记一下已经已使用过的线程，
        Map<Long, Integer> threadIdMap = new ConcurrentHashMap<>();
        // 循环向线程变量中设置数据 1024 * 1024 = 1M
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                synchronized (lockObj) {
                    Integer num = threadIdMap.putIfAbsent(Thread.currentThread().getId(), 1);
                    if (num == null) {
                        ThreadLocal<Byte[]> threadLocal = new ThreadLocal<>();
                        threadLocal.set(new Byte[1024 * 1024]);
                        if (doRemove) {
                            // 解决内存泄露关键
                            threadLocal.remove();
                        }
                        // 将threadLocal置为空引用，利于回收
                        threadLocal = null;
                        // 手工回收
                        System.gc();
                        try {
                            // 调用GC后不一定会马上回收
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName());
                }
            });
            // System.out.println(i);
        }
    }

}
