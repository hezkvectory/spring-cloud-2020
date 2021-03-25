package com.fishercoder.solutions;

/**
 * 122. Best Time to Buy and Sell Stock II
 * <p>
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
 * (ie, buy one and sell one share of the stock multiple times).
 * However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 */

public class _122 {
    public static class Solution1 {
        //peak and valley approach
        public int maxProfit(int[] prices) {
            int pro = 0;
            int i = 0;
            while (i < prices.length - 1) {
                while (i < prices.length - 1 && prices[i] >= prices[i + 1]) {
                    i++;
                }
                int valley = prices[i];
                while (i < prices.length - 1 && prices[i] <= prices[i + 1]) {
                    i++;
                }
                int peak = prices[i];
                pro += peak - valley;
            }
            return pro;
        }
    }

    public static class Solution2 {
        //simple one pass approach: the above solution could be simplied as below
        public int maxProfit(int[] prices) {
            int pro = 0;
            for (int i = 0; i < prices.length - 1; i++) {
                if (prices[i + 1] > prices[i]) {
                    pro += prices[i + 1] - prices[i];
                }
            }
            return pro;
        }
    }

    public static class Solution3 {
        public int maxProfix(int[] prices) {
            if (prices == null || prices.length == 0) {
                return 0;
            }

            int[] buys = new int[prices.length];//持有股票的最优结果
            int[] sells = new int[prices.length];//不持有股票的最优结果

            buys[0] = -prices[0];
            sells[0] = 0;

            for (int i = 1; i < prices.length; i++) {
                buys[i] = Math.max(buys[i - 1], sells[i - 1] - prices[i]);
                sells[i] = Math.max(sells[i - 1], prices[i] + buys[i - 1]);
            }
            return sells[prices.length - 1];
        }
    }
}
