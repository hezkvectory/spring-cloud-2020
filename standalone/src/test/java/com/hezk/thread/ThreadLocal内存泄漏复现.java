package com.hezk.thread;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

public class ThreadLocal内存泄漏复现 {

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class MyWeakEntry extends WeakReference<String> {
        String value;

        public MyWeakEntry(String thread, String value, ReferenceQueue<String> q) {
            super(thread, q);
            this.value = value;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ReferenceQueue<String> q = new ReferenceQueue<>();//引用队列，当被回收时会进入该队列
        MyWeakEntry[] entries = new MyWeakEntry[10];//模拟ThreadLocal中的Map table
        String key = new String("我是key");
        String value = "我是value";
        entries[2] = new MyWeakEntry(key, value, q);//将其放入到对应的table中
        key = null;//取消掉其引用，目前没有指向key的强引用
        new Thread(() -> {
            try {
                Object k;
                System.out.println("开始监听");
                while ((k = q.remove()) != null) {//判断队列中是否有该key，有说明已经被回收了
                    System.out.println(k + " 被回收了");
                    System.out.println(entries[2].value);
                    break;
                }
            } catch (Exception e) {

            } finally {
                System.out.println("结束监听");
            }
        }).start();
        sleep(2);
        System.gc();//触发gc
    }

}
 