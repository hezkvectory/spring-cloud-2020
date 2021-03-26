package com.hezk.algorithm;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 去除重复字母 316
 * 使用栈
 * 使用visited统计是否已经使用，和图遍历类似
 */
public class RemoveDuplicateLetter {

    public static class Solution {
        public String removeDuplicateLetters(String str) {
            if (str == null || str.isEmpty()) {
                return str;
            }
            char[] chars = str.toCharArray();
            int len = chars.length;
            int[] pos = new int[26];
            boolean[] used = new boolean[26];

            for (int i = 0; i < len; i++) {
                pos[chars[i] - 'a'] = i;
            }

            Deque<Character> deque = new LinkedList<>();
            for (int i = 0; i < len; i++) {
                char ch = chars[i];
                if (used[ch - 'a']) {
                    continue;
                }
                while (!deque.isEmpty() && deque.peekLast() > ch && pos[deque.peekLast() - 'a'] > i) {
                    used[deque.peekLast() - 'a'] = false;
                    deque.removeLast();
                }
                deque.addLast(ch);
                used[ch - 'a'] = true;
            }
            StringBuilder builder = new StringBuilder();
            for (Character ch : deque) {
                builder.append(ch);
            }
            return builder.toString();
        }
    }

    public static class Solution1 {
        public String removeDuplicateLetters(String str) {
            if (str.length() < 2) {
                return str;
            }
            char[] chars = str.toCharArray();
            int len = chars.length;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < len; i++) {
                while (builder.length() > 0 && builder.charAt(builder.length() - 1) > chars[i] && str.indexOf(builder.charAt(builder.length() - 1), i) > i) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                builder.append(chars[i]);
            }
            return builder.toString();
        }
    }


    public static void main(String[] args) {
//        String str = "babac";
        String str = "cbacdcbc";
        Solution solution = new Solution();
        System.out.println(solution.removeDuplicateLetters(str));
    }
}
