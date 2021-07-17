package com.fishercoder.question;

import java.util.Arrays;

/**
 * 一个射击选手打靶，靶一共有10环，连10抢打中90环的可能有多少种？
 * 每粑环数0-10环
 */
public class DaBa {

    public static void main(String[] args) {
        Solution solution = new Solution();
        long startTime = System.currentTimeMillis();
        System.out.println(solution.recursive(90, 10));
        System.out.println(System.currentTimeMillis() - startTime);
    }

    public static class Solution {

        int recursive(int total, int num) {
            if (num == 1) {
                if (total >= 0 && total <= 10) {
                    return 1;
                } else {
                    return 0;
                }
            }
            if(total > num * 10)
                return 0;
            int sum = 0;
            for (int i = 0; i <= 10; i++) {
                sum += recursive(total - i, num - 1);
            }
            return sum;
        }
    }


    public static class Solution2 {
        static int count = 0;
        static int numberOfShooting = 10;
        static int totalScore = 90;

        public static void main(String[] args) {
            //score数组表示一个射击计划，score[i]表示第i枪的环数
            int[] score = new int[numberOfShooting];
            Arrays.fill(score, 0);

            long beginTime = System.currentTimeMillis();
            shootplan(numberOfShooting, totalScore, score);
            long endTime = System.currentTimeMillis();

            System.out.println("Executed in " + (endTime - beginTime) + " milliseconds");
        }

        /**
         * 计算一个射击计划
         *
         * @param number 射击次数
         * @param total  总环数
         * @param score  记录成绩的数组
         */
        private static void shootplan(int number, int total, int[] score) {
            // 边界条件，如果仅剩一枪且剩余环数符合条件，那剩余环数即为最后一枪的成绩，同时打印输出10枪成绩
            if (number == 1 && total <= 10 && total >= 0) {
                score[numberOfShooting - number] = total; // 输出一个射击计划
                System.out.print(++count + ":");
                for (int i : score)
                    System.out.print(i + " ");

                System.out.println();
            }

            if (number > 1) {
                for (int i = 0; i <= 10; i++) {
                    score[numberOfShooting - number] = i;
                    shootplan(number - 1, total - i, score);
                }
            }
        }

    }
}
