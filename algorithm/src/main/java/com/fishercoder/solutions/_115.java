package com.fishercoder.solutions;

/**
 * 115. Distinct Subsequences
 * <p>
 * Given a string S and a string T, count the number of distinct subsequences of S which equals T. A
 * subsequence of a string is a new string which is formed from the original string by deleting some
 * (can be none) of the characters without disturbing the relative positions of the remaining
 * characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
 * <p>
 * Here is an example: S = "rabbbit", T = "rabbit" Return 3.
 */

/*
  Ø r a b b b i t
Ø 1 1 1 1 1 1 1 1
r 0 1 1 1 1 1 1 1
a 0 0 1 1 1 1 1 1
b 0 0 0 1 2 3 3 3
b 0 0 0 0 1 3 3 3
i 0 0 0 0 0 0 3 3
t 0 0 0 0 0 0 0 3
 */

/*
首先，若原字符串和子序列都为空时，返回1，因为空串也是空串的一个子序列。
若原字符串不为空，而子序列为空，也返回1，因为空串也是任意字符串的一个子序列。
而当原字符串为空，子序列不为空时，返回0，因为非空字符串不能当空字符串的子序列。
理清这些，二维数组 dp 的边缘便可以初始化了，下面只要找出状态转移方程，就可以更新整个 dp 数组了。
我们通过观察上面的二维数组可以发现，当更新到 dp[i][j] 时，dp[i][j] >= dp[i][j - 1] 总是成立，再进一步观察发现，当 T[i - 1] == S[j - 1] 时，dp[i][j] = dp[i][j - 1] + dp[i - 1][j - 1]，若不等， dp[i][j] = dp[i][j - 1]，所以，综合以上，递推式为：

dp[i][j] = dp[i][j - 1] + (T[i - 1] == S[j - 1] ? dp[i - 1][j - 1] : 0)
 */

public class _115 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        String s = "rabbbit";
        String t = "abbit";
        System.out.println(solution.numDistinct(s, t));
    }


    public static class Solution1 {
        public int numDistinct(String s, String t) {
            int m = s.length();
            int n = t.length();
            int[][] dp = new int[m + 1][n + 1];

            char[] schar = s.toCharArray();
            char[] tchar = t.toCharArray();

            for (int i = 0; i <= m; i++) {
                dp[i][0] = 1;
            }

            for (int j = 1; j <= n; j++) {
                dp[0][j] = 0;
            }

            print(dp);

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (schar[i - 1] == tchar[j - 1]) {
                        dp[i][j] = dp[i - 1][j] + dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }
                    print(dp);
                }
            }
            return dp[m][n];
        }

        private void print(int dp[][]) {
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[0].length; j++) {
                    System.out.print(dp[i][j] + "\t");
                }
                System.out.println();
            }

            System.out.println("------------------");
        }
    }
}
