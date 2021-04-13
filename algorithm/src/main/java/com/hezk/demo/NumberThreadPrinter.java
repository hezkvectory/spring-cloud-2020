package com.hezk.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hezhengkui on 07/03/2019.
 */
public class NumberThreadPrinter implements Runnable {

    private static final int PRINT_NUM = 10;
    ReentrantLock reentrantLock;
    Condition thisCondition;
    Condition nextCondition;
    char printChar;

    public NumberThreadPrinter(ReentrantLock reentrantLock, Condition thisCondition, Condition nextCondition, char printChar) {
        this.reentrantLock = reentrantLock;
        this.thisCondition = thisCondition;
        this.nextCondition = nextCondition;
        this.printChar = printChar;
    }

    @Override
    public void run() {

        reentrantLock.lock();
        try {
            for (int i = 0; i < PRINT_NUM; i++) {
                System.out.print(printChar);
                nextCondition.signal();
                if (i < PRINT_NUM - 1) {
                    thisCondition.await();
                }
            }

        } catch (Exception e) {

        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition aCondtion = reentrantLock.newCondition();
        Condition bCondtion = reentrantLock.newCondition();
        Condition cCondtion = reentrantLock.newCondition();

        NumberThreadPrinter aprinter = new NumberThreadPrinter(reentrantLock, aCondtion, bCondtion, 'A');
        NumberThreadPrinter bprinter = new NumberThreadPrinter(reentrantLock, bCondtion, cCondtion, 'B');
        NumberThreadPrinter cprinter = new NumberThreadPrinter(reentrantLock, cCondtion, aCondtion, 'C');

        new Thread(aprinter).start();
        new Thread(bprinter).start();
        new Thread(cprinter).start();
    }
}
