package com.fishercoder.solutions;

/**
 * 409. Longest Palindrome
 * <p>
 * Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.
 * This is case sensitive, for example "Aa" is not considered a palindrome here.
 * <p>
 * Note:
 * Assume the length of given string will not exceed 1,010.
 * <p>
 * Example:
 * Input:
 * "abccccdd"
 * <p>
 * Output:
 * 7
 * <p>
 * Explanation:
 * One longest palindrome that can be built is "dccaccd", whose length is 7.
 */
public class _409 {
    public static class Solution1 {
        public int longestPalindrome(String s) {
            int[] counts = new int[56];
            for (char c : s.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    counts[c - 'A' + 33]++;
                } else {
                    counts[c - 'a']++;
                }
            }
            boolean hasOdd = false;
            int len = 0;
            for (int i = 0; i < 56; i++) {
                if (counts[i] % 2 != 0) {
                    hasOdd = true;
                    if (counts[i] > 1) {
                        len += counts[i] - 1;
                    }
                } else {
                    len += counts[i];
                }
            }
            return hasOdd ? len + 1 : len;
        }
    }
//
//    public static class Solution {
//        public int longestPalindrome(String str) {
//            int len = str.length();
//            int ans = 0;
//            for (int i = 0; i < len; i++) {
//                int left = i / 2;
//                int right = left + i % 2;
//                while (left >= 0 && right < len && str.charAt(left) == str.charAt(right)) {
//                    if (right - left + 1 > ans) {
//                        ans = right - left + 1;
//                    }
//                    left--;
//                    right++;
//                }
//            }
//            return ans;
//        }
//    }
}
