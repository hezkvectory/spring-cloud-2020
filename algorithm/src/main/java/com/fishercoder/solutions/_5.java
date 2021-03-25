package com.fishercoder.solutions;

/**
 * 5. Longest Palindromic Substring
 * <p>
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 * <p>
 * Example:
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * <p>
 * Example:
 * Input: "cbbd"
 * Output: "bb"
 */
public class _5 {

    public static void main(String[] args) {
//        Solution1 solution1 = new Solution1();
//        System.out.println(solution1.longestPalindrome("babad"));


        Solution2 solution2 = new Solution2();
        System.out.println(solution2.longestPalindrome("babad"));
    }

    public static class Solution1 {
        private int low;
        private int maxLen;

        public String longestPalindrome(String s) {
            int len = s.length();
            if (len < 2) {
                return s;
            }

            for (int i = 0; i < len - 1; i++) {
                extendPalindrome(s, i, i); // assume odd length, try to extend Palindrome as possible
                extendPalindrome(s, i, i + 1); // assume even length.
            }
            return s.substring(low, low + maxLen);
        }

        private void extendPalindrome(String s, int left, int right) {
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            if (maxLen < right - left - 1) {
                low = left + 1;
                maxLen = right - left - 1;
            }
        }
    }

    public static class Solution2 {
        public static String longestPalindrome(String s) {
            if (s.length() <= 1)
                return s;
            for (int i = s.length(); i > 0; i--) {//子串长度
                for (int j = 0; j <= s.length() - i; j++) {
                    String sub = s.substring(j, i + j);//子串位置
                    int count = 0;//计数，用来判断是否对称
                    for (int k = 0; k < sub.length() / 2; k++) {//左右对称判断
                        if (sub.charAt(k) == sub.charAt(sub.length() - k - 1))
                            count++;
                    }
                    if (count == sub.length() / 2)
                        return sub;
                }
            }
            return "";//表示字符串中无回文子串

        }
    }
}
