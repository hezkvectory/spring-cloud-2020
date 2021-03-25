package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * 131. Palindrome Partitioning
 * <p>
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * <p>
 * Return all possible palindrome partitioning of s.
 * <p>
 * For example, given s = "aab",
 * Return
 * <p>
 * [
 * ["aa","b"],
 * ["a","a","b"]
 * ]
 */
public class _131 {

    public static void main(String[] args) {
        Solution3 solution2 = new Solution3();
        System.out.println(solution2.partition("aab"));
    }

    public static class Solution3 {
        /*
        用来保存从任意一位置至任意一位置的子串是否是回文串，类似于动态规划中保存之前的状态来减小时间复杂度
        不同之处在于这里的状态并没有发生转移，所以不算是动态规划与回溯算法的结合
        这一步是优化时间复杂度的关键
         */
        int[][] dp;

        public List<List<String>> partition(String s) {
            List<List<String>> res = new ArrayList<>();
            if (null == s || s.length() == 0) {
                return res;
            }
            int length = s.length();
        /*
        它是一个二维矩阵，有三种状态值：
        0表示之前还没有计算过
        1表示从下表i到j的子串是回文串
        2表示不是回文串
        我们只用到了数组的右上半部分
        当然这里也可以使用char数组，空间复杂度更低
         */
            dp = new int[length][length];
            //初始化，单个字符的肯定是回文串
            for (int i = 0; i < length; i++) {
                dp[i][i] = 1;
            }
            helper(res, new ArrayList<>(), s, length, 0);
            return res;
        }

        /**
         * 回溯算法
         *
         * @param res      结果集
         * @param templist 中间list
         * @param s        字符串
         * @param length   字符串长度
         * @param index    从当前位置向后组合判断
         */
        private void helper(List<List<String>> res, ArrayList<String> templist, String s, int length, int index) {
            //走到这一步就表示走到了最后，添加到结果集
            if (index == length) {
                res.add(new ArrayList<>(templist));//一定要重新new一个对象，templist可以得到重用
                return;
            }
            //走到某一步有若干的选择继续走下一步
            for (int i = index; i < length; i++) {
                if (isPalindrome(s, index, i)) {
                    templist.add(s.substring(index, i + 1));
                    helper(res, templist, s, length, i + 1);
                    templist.remove(templist.size() - 1);//回溯算法中回退一定要记得这一步
                }
            }
        }

        //判断是否是回文串，这里首先需要到保存的状态中查看是否之前已经有了，优化时间复杂度
        private boolean isPalindrome(String s, int from, int to) {
            if (dp[from][to] == 1) {
                return true;
            } else if (dp[from][to] == 2) {
                return false;
            } else {
                for (int i = from, j = to; i < j; i++, j--) {
                    if (s.charAt(i) != s.charAt(j)) {
                        dp[from][to] = 2;
                        return false;
                    }
                }
                dp[from][to] = 1;
                return true;
            }
        }

    }

    static class Solution2 {
        public List<List<String>> partition(String s) {
            List<List<String>> result = new ArrayList<>();
            if (s == null || "".equals(s)) {
                return result;
            }
            dfs(s, 0, s.length(), new ArrayList<>(), result);
            return result;
        }

        private void dfs(String s, int index, int len, List<String> path, List<List<String>> result) {
            if (index == s.length()) {
                result.add(new ArrayList<>(path));
                return;
            }
            for (int i = index; i < len; i++) {
                String temp = s.substring(index, i + 1);
                if (!valid(temp)) {
                    continue;
                }
                path.add(temp);
                dfs(s, i + 1, len, path, result);
                path.remove(path.size() - 1);
            }
        }

        private boolean valid(String temp) {
            if ("".equals(temp)) {
                return false;
            }
            return temp.equals(new StringBuffer(temp).reverse().toString());
        }
    }

    public static class Solution1 {
        public List<List<String>> partition(String s) {
            List<List<String>> result = new ArrayList();
            int n = s.length();
            boolean[][] dp = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= i; j++) {
                    if (s.charAt(j) == s.charAt(i) && (j + 1 >= i - 1 || dp[j + 1][i - 1])) {
                        // j+1 >= i-1 means j and i are adjance to each other or only one char apart from each other
                        //dp[j+1][i-1] means its inner substring is a palindrome, so as long as s.charAt(j) == s.charAt(i), then dp[j][i] must be a palindrome.
                        dp[j][i] = true;
                    }
                }
            }

            for (boolean[] list : dp) {
                for (boolean b : list) {
                    System.out.print(b + ", ");
                }
                System.out.println();
            }
            System.out.println();

            backtracking(s, 0, dp, new ArrayList(), result);

            return result;
        }

        void backtracking(String s, int start, boolean[][] dp, List<String> temp, List<List<String>> result) {
            if (start == s.length()) {
                List<String> newTemp = new ArrayList(temp);
                result.add(newTemp);
            }
            for (int i = start; i < s.length(); i++) {
                if (dp[start][i]) {
                    temp.add(s.substring(start, i + 1));
                    backtracking(s, i + 1, dp, temp, result);
                    temp.remove(temp.size() - 1);
                }
            }
        }
    }
}
