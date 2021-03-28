package com.hezk.algorithm;

public class StockDfsCommon {

    public static class One {
        public static class Solution {
            public int maxProfit(int[] prices) {
                int profit = 0;
                int len = prices.length;
                for (int i = 1; i < len; i++) {
                    profit += Math.max(0, prices[i] - prices[i - 1]);
                }
                return profit;
            }
        }
    }

    public static class N {


        public static class Solution {
            private int res = 0;

            public int maxProfit(int[] prices) {
                int len = prices.length;
                dfs(prices, 0, len, 0, this.res);
                return this.res;
            }

            /**
             * @param prices 股票价格
             * @param index  第几天
             * @param len    总交易几天
             * @param status 是否持有股票，0：不持有股票， 1：持有股票
             * @param res
             */
            private void dfs(int[] prices, int index, int len, int status, int res) {
                if (index == len) {
                    this.res = Math.max(this.res, res);
                    return;
                }
                dfs(prices, index + 1, len, status, res);
                if (status == 0) {
                    dfs(prices, index + 1, len, 1, res - prices[index]);
                } else {
                    dfs(prices, index + 1, len, 0, res + prices[index]);
                }
            }
        }

        public static class Solution1 {
            public int maxProfit(int[] prices) {
                int len = prices.length;
                int[][] res = new int[len][2];
                res[0][0] = 0; //不持有股票
                res[0][1] = -prices[0];//持有股票
                for (int i = 1; i < len; i++) {
                    res[i][0] = Math.max(res[i - 1][0], res[i - 1][1] + prices[i]);
                    res[i][1] = Math.max(res[i - 1][1], res[i - 1][0] - prices[i]);
                }
                return res[len - 1][0];
            }
        }

        public static class Solution2 {

            public int maxProfit(int[] prices) {
                int len = prices.length;
                if (len < 2) {
                    return 0;
                }

                int res = 0;
                for (int i = 1; i < len; i++) {
                    int diff = prices[i] - prices[i - 1];
                    if (diff > 0) {
                        res += diff;
                    }
                }
                return res;
            }
        }
    }

    public static class Two {
        public int maxProfit(int[] prices) {
            int len = prices.length;
            int buy1 = -prices[0];
            int buy2 = -prices[0];
            int sell1 = 0;
            int sell2 = 0;
            for (int i = 1; i < len; i++) {
                buy1 = Math.max(buy1, -prices[i]);
                sell1 = Math.max(sell1, buy1 + prices[i]);
                buy2 = Math.max(buy2, sell1 - prices[i]);
                sell2 = Math.max(sell2, buy2 + prices[i]);
            }
            return sell2;
        }
    }

    public static class Freeze {
        public static class Solution {
            public int maxProfit(int[] prices) {
                int len = prices.length;
                int[][] res = new int[len][3];
                res[0][0] = -prices[0];//当前只有股票
                res[0][1] = 0;//当前不持有股票，并且在冷冻期
                res[0][2] = 0;//当前不只有股票，并且不再冷冻期
                for (int i = 0; i < len; i++) {
                    res[i][0] = Math.max(res[i - 1][0], res[i - 1][2] - prices[i]);
                    res[i][1] = res[i - 1][0] + prices[i];
                    res[i][2] = Math.max(res[i - 1][1], res[i - 1][2]);
                }
                return Math.max(res[len - 1][1], res[len - 1][2]);
            }
        }
    }

    public static class ServiceChargeN {
        public static class Solution {
            public int maxProfit(int[] prices, int serviceCharge) {
                int len = prices.length;
                int[][] res = new int[len][0];
                res[0][0] = -prices[0];//持有股票
                res[0][1] = 0;//不持有股票
                for (int i = 1; i < len; i++) {
                    res[i][0] = Math.max(res[i - 1][0], res[i - 1][1] - prices[i]);
                    res[i][1] = Math.max(res[i - 1][1], res[i - 1][0] + prices[i] - serviceCharge);
                }
                return res[len - 1][0];
            }
        }
    }

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(new N.Solution().maxProfit(prices));
    }
}
