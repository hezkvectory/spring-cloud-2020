package com.fishercoder.solutions;

/**
 * 121. Best Time to Buy and Sell Stock
 * <p>
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * <p>
 * If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock),
 * design an algorithm to find the maximum profit.
 * <p>
 * Example 1:
 * Input: [7, 1, 5, 3, 6, 4]
 * Output: 5
 * <p>
 * max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
 * <p>
 * Example 2:
 * Input: [7, 6, 4, 3, 1]
 * Output: 0
 * <p>
 * In this case, no transaction is done, i.e. max profit = 0.
 */

public class _121 {

    public static class Solution1 {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length == 0) {
                return 0;
            }
            int minBuy = prices[0];
            int maxProfit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] < minBuy) {
                    minBuy = prices[i];
                } else {
                    maxProfit = Math.max(maxProfit, prices[i] - minBuy);
                }
            }
            return maxProfit;
        }
    }

    public static class Solution2 {
        public int maxProfix(int[] prices) {
            if (prices == null || prices.length == 0) {
                return 0;
            }

            int[] buys = new int[prices.length];//买入最大利润
            int[] sells = new int[prices.length];//卖出最大利润

            buys[0] = -prices[0];
            sells[0] = 0;

            for (int i = 1; i < prices.length; i++) {
                buys[i] = Math.max(buys[i - 1], -prices[i]);
                sells[i] = Math.max(sells[i - 1], prices[i] + buys[i - 1]);
            }
            return sells[prices.length - 1];
        }
    }
}
