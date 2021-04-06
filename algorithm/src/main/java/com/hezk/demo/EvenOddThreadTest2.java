package com.hezk.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EvenOddThreadTest2 {

    public volatile boolean flag = false;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public class OddClass implements Runnable {
        public EvenOddThreadTest2 t;

        public OddClass(EvenOddThreadTest2 t) {
            this.t = t;
        }

        @Override
        public void run() {
            int i = 1;  //本线程打印奇数,则从1开始
            while (i < 100) {
                try {
                    lock.lock();
                    if (!t.flag) {
                        System.out.println("-----" + i);
                        i += 2;
                        t.flag = true;
                        condition.signalAll();
                    } else {
                        condition.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public class EvenClass implements Runnable {
        public EvenOddThreadTest2 t;

        public EvenClass(EvenOddThreadTest2 t) {
            this.t = t;
        }

        @Override
        public void run() {
            int i = 2;//本线程打印偶数,则从2开始
            while (i <= 100) {
                try {
                    lock.lock();
                    if (t.flag) {
                        System.out.println("---------" + i);
                        i += 2;
                        t.flag = false;
                        condition.signalAll();
                    } else {
                        condition.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    public static void main(String[] args) {
        EvenOddThreadTest2 tt = new EvenOddThreadTest2();
        OddClass oddClass = tt.new OddClass(tt);
        EvenClass evenClass = tt.new EvenClass(tt);
        new Thread(oddClass).start();
        new Thread(evenClass).start();
    }
}
