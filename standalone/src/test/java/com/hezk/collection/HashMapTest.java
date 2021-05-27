package com.hezk.collection;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HashMapTest {

    @Test
    public void test1() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                while (true) {
                    int num = atomicInteger.getAndIncrement();
                    map.put(num, "num-" + num);
                    System.out.println(num);
                }
            });
        }
        System.in.read();
    }

    static class HashThreadTest extends Thread {
        static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(1);
        static AtomicInteger aInteger = new AtomicInteger();

        public void run() {
            while (aInteger.get() < 10000) {
                map.put(aInteger.get(), aInteger.get());
                //sysout操作非常耗时，加入将很难遇到冲突。
                //System.out.println(Thread.currentThread().getName()+"put了: "+aInteger.get());
                aInteger.incrementAndGet();
            }
        }
    }

    @Test
    public void test3() throws Exception {
        HashThreadTest t = new HashThreadTest();
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(t, "线程" + i);
            threads[i].start();
        }
        TimeUnit.SECONDS.sleep(5);
        //默认还有个守护线程
        System.out.println(Thread.activeCount());
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(t.map.size());
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            if (!t.map.containsKey(i)) {
                count++;
                //System.out.println("key: "+i+" is not contains");
            }
        }
        System.out.println(t.map.size());
        System.out.println(count);
    }

    @Test
    public void test2() throws Exception {
        // HashMap初始容量16
        Map<String, String> map = new HashMap<>(16);

        Class<?> mapType = map.getClass();
        Method capacity = mapType.getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        // 这里打印的初始 capacity = 16
        System.out.println("capacity :" + capacity.invoke(map));

        // 向HashMap中put12个元素
        map.put("222", "222");
        map.put("223", "222");
        map.put("224", "222");
        map.put("225", "222");
        map.put("226", "222");
        map.put("227", "222");
        map.put("228", "222");
        map.put("229", "222");
        map.put("230", "222");
        map.put("231", "222");
        map.put("232", "222");
        map.put("233", "222");

        // 当向HashMap中put12个元素时，容量capacity仍为16
        Method capacity2 = mapType.getDeclaredMethod("capacity");
        capacity2.setAccessible(true);
        System.out.println("capacity2 : " + capacity2.invoke(map));

        // 当向HashMap中put第13个元素时，会触发扩容，容量加倍，变成32
        map.put("234", "222");
        Method capacity3 = mapType.getDeclaredMethod("capacity");
        capacity3.setAccessible(true);
        System.out.println("capacity3 : " + capacity3.invoke(map));
    }

    @Test
    public void testHashEquals() {
        Map<String, String> m1 = new HashMap<>();
        m1.put("222", "222");
        m1.put("223", "222");

        Map<String, String> m2 = new HashMap<>();
        m2.put("222", "222");
        m2.put("223", "222");
        m2.put("2235", "222");

        System.out.println(m1.equals(m2));
    }
}
