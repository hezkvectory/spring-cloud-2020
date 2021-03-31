package com.hezk.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hezhengkui on 2019/3/20.
 */
public class AuctionTest {

    /**
     * 创建N条线程，代表N个人，每个人的名称分别为：（“P1”，“P2”，“P3”…“Pn”）
     * 每个人在出价之后必须等其他人出价后才能再次出价；第一次出价不受此限制。
     * 所有人的心理上线价位都为 M ，不会出价比M高
     * 每次出价必须比上一次出价高
     * 经过 k 次竞拍后，打印出所有k次竞拍的人及其出价。
     */
    static int N = 10; //竞拍人数
    static int M = 100;//所有人的心理上线价位都为 M ，不会出价比M高
    static int AUCTION_NUM = 5; //经过 k 次竞拍后

    static ConcurrentHashMap<String, List<Integer>> ACTION_HIST = new ConcurrentHashMap<>();

    static ReentrantLock lock = new ReentrantLock();//这个锁是为了保证出价比上一个人出价高

    static volatile int CUR_MAX_PRICE = 0;

    public static void main(String[] args) throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(N);//每个人在出价之后必须等其他人出价后才能再次出价；第一次出价不受此限制。
        CountDownLatch countDownLatch = new CountDownLatch(N);//这里应该是N
        ThreadPoolExecutor executor = new ThreadPoolExecutor(N, N, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Auction");
            }
        });


        int step = M / (AUCTION_NUM * N); //每次出价阶梯

        for (int i = 1; i <= N; i++) {
            executor.execute(new Auction(barrier, countDownLatch, "P" + i, step));
        }

        countDownLatch.await();

        String personName = null;
        int maxPrice = 0;
        int personId = 0;
        for (int num = 1; num <= AUCTION_NUM; num++) {
            for (int i = 1; i <= N; i++) {
                personName = "P" + i;
                Integer price = ACTION_HIST.get(personName).get(num - 1);
                if (num == AUCTION_NUM) {
                    if (maxPrice < price) {
                        maxPrice = price;
                        personId = i;
                    }
                }
                System.out.println("第" + num + "次出价人:P" + i + " 出价:" + price);
            }
            System.out.println("-----------------");
        }
        System.out.println("最终竞价成功人:P" + personId + ",最终出价:" + maxPrice);
        executor.shutdown();
    }

    static class Auction implements Runnable {
        private String name;
        private CyclicBarrier barrier;
        private int step;
        private CountDownLatch countDownLatch;

        public Auction(CyclicBarrier barrier, CountDownLatch countDownLatch, String name, int step) {
            this.name = name;
            this.barrier = barrier;
            this.step = step;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            if (!ACTION_HIST.containsKey(name)) {
                ACTION_HIST.put(name, new ArrayList<>());
            }
            lock.lock();
            int currentPrice = 0;
            try {
                currentPrice = getRandPrice(step);
                CUR_MAX_PRICE = CUR_MAX_PRICE < currentPrice ? currentPrice : CUR_MAX_PRICE;
            } finally {
                lock.unlock();
            }

            ACTION_HIST.get(name).add(currentPrice);
            int tmp = 1;
            try {
                while (tmp < AUCTION_NUM) {
                    barrier.await();
                    currentPrice = getRandPrice(step);
                    currentPrice = currentPrice > M ? M : currentPrice;
                    CUR_MAX_PRICE = CUR_MAX_PRICE < currentPrice ? currentPrice : CUR_MAX_PRICE;
                    ACTION_HIST.get(name).add(currentPrice);
                    tmp++;
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * 当前心里价比上次多多少
         *
         * @param step
         * @return
         */
        int getRandPrice(int step) {
            return CUR_MAX_PRICE + new Random().nextInt(step) + 1;
        }
    }
}
