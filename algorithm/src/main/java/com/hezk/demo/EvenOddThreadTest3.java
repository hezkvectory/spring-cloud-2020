package com.hezk.demo;

public class EvenOddThreadTest3 {

    public class Num {
        volatile int num = 1;
    }

    public class OddEvenThreadClass implements Runnable {
        private Num num;

        public OddEvenThreadClass(Num num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (this.num.num <= 100) {
                synchronized (num) {
                    this.num.notifyAll();
                    System.out.println(Thread.currentThread().getName() + "----" + this.num.num++);
                    try {
                        this.num.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.num.notifyAll();
                }
            }
        }
    }

    /**
     * even 偶数
     * odd 奇数
     */
    public static void main(String[] args) {
        EvenOddThreadTest3 tt = new EvenOddThreadTest3();
        Num num = tt.new Num();
        OddEvenThreadClass oddClass = tt.new OddEvenThreadClass(num);
        new Thread(oddClass, "odd").start();
        new Thread(oddClass, "even").start();
    }
}
