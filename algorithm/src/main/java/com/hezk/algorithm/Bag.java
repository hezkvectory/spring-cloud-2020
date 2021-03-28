package com.hezk.algorithm;

/**
 * 01背包问题
 * https://leetcode-cn.com/problems/partition-equal-subset-sum/solution/bang-ni-ba-0-1bei-bao-xue-ge-tong-tou-by-px33/
 * <p>
 * #416 分割等和子集
 * #698.划分为k个相等的子集
 * #473.火柴拼正方形
 */
public class Bag {

    public static class Solution {
        public int dp(int[] weights, int[] prices, int bagWeight) {

            int x = weights.length;

            /**
             * 行：代表物品0..X
             * 列： 代表背包重量
             */
            int[][] res = new int[x][bagWeight + 1];
            for (int i = 0; i < x; i++) {
                res[i][0] = 0;
            }
            for (int i = bagWeight; i >= weights[0]; i--) {
                res[0][i] = res[0][i - weights[0]] + prices[0];
            }
            for (int i = 1; i < x; i++) {
                for (int j = 0; j <= bagWeight; j++) {
                    if (j < weights[i]) {
                        res[i][j] = res[i - 1][j];
                    } else {
                        res[i][j] = Math.max(res[i - 1][j], res[i - 1][j - weights[i]] + prices[i]);
                    }
                }
            }
            for (int i = 0; i < x; i++) {
                for (int j = 0; j <= bagWeight; j++) {
                    System.out.print(res[i][j] + "\t");
                }
                System.out.println();
            }
            return res[x - 1][bagWeight];
        }
    }

    public static class Solution1 {
        public int dp(int[] weights, int[] prices, int bagWeight) {
            int[] dp = new int[bagWeight + 1];
            int x = weights.length;
            for (int i = 0; i < x; i++) {
                for (int j = bagWeight; j >= weights[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - weights[i]] + prices[i]);
                }
            }
            return dp[bagWeight];
        }
    }

    public static class Solution416 {
        public boolean cal(int[] nums) {
            int len = nums.length;
            int total = 0;
            for (int i = 0; i < nums.length; i++) {
                total += nums[i];
            }
            if (total % 2 == 1) {
                return false;
            }
            total = total / 2;
            int[] dp = new int[total + 1];
            for (int i = 0; i < len; i++) {
                for (int j = total; j >= nums[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
                }
            }
            return dp[total] == total;
        }
    }

    public class Solution3 {
        public int findMaxForm(String[] strs, int m, int n) {
            int[][] dp = new int[m + 1][n + 1];
            for (String s : strs) {
                int[] count = countzeroesones(s);
                for (int zeroes = m; zeroes >= count[0]; zeroes--)
                    for (int ones = n; ones >= count[1]; ones--)
                        dp[zeroes][ones] = Math.max(1 + dp[zeroes - count[0]][ones - count[1]], dp[zeroes][ones]);
            }
            return dp[m][n];
        }

        public int[] countzeroesones(String s) {
            int[] c = new int[2];
            for (int i = 0; i < s.length(); i++) {
                c[s.charAt(i) - '0']++;
            }
            return c;
        }
    }


    public static void main(String[] args) {
//        int[] weights = {1, 3, 4};
//        int[] prices = {15, 20, 30};
//        System.out.println(new Solution1().dp(weights, prices, 4));

        int[] nums = {1, 5, 11, 5};
        System.out.println(new Solution416().cal(nums));
    }

}
