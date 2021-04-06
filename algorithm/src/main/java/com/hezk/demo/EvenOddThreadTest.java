package com.hezk.demo;

public class EvenOddThreadTest {

    public volatile boolean flag = false;

    public class OddClass implements Runnable {
        public EvenOddThreadTest t;

        public OddClass(EvenOddThreadTest t) {
            this.t = t;
        }

        @Override
        public void run() {
            int i = 1;  //本线程打印奇数,则从1开始
            while (i < 100) {
                //两个线程的锁的对象只能是同一个object
                synchronized (t) {
                    if (!t.flag) {
                        System.out.println("-----" + i);

                        i += 2;
                        t.flag = true;
                        t.notify();
                    } else {
                        try {
                            t.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    public class EvenClass implements Runnable {
        public EvenOddThreadTest t;

        public EvenClass(EvenOddThreadTest t) {
            this.t = t;
        }

        @Override
        public void run() {
            int i = 2;//本线程打印偶数,则从2开始
            while (i <= 100)
                //两个线程的锁的对象只能是同一个object
                synchronized (t) {
                    if (t.flag) {
                        System.out.println("-----------" + i);
                        i += 2;
                        t.flag = false;
                        t.notify();
                    } else {
                        try {
                            t.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }


    public static void main(String[] args) {
        EvenOddThreadTest tt = new EvenOddThreadTest();
        OddClass oddClass = tt.new OddClass(tt);
        EvenClass evenClass = tt.new EvenClass(tt);
        new Thread(oddClass).start();
        new Thread(evenClass).start();
    }
}
