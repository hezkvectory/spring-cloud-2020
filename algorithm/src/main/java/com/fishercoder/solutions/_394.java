package com.fishercoder.solutions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * 394. Decode String
 * <p>
 * Given an encoded string, return it's decoded string.
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times.
 * Note that k is guaranteed to be a positive integer.
 * You may assume that the input string is always valid;
 * No extra white spaces, square brackets are well-formed, etc.
 * Furthermore, you may assume that the original data does not contain any digits and
 * that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
 * <p>
 * Examples:
 * s = "3[a]2[bc]", return "aaabcbc".
 * s = "3[a2[c]]", return "accaccacc".
 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 */

public class _394 {

    public static class Solution1 {
        public String decodeString(String s) {
            Stack<Integer> count = new Stack<>();
            Stack<String> str = new Stack<>();

            int idx = 0;
            str.push("");

            while (idx < s.length()) {
                if (s.charAt(idx) >= '0' && s.charAt(idx) <= '9') {
                    int start = idx;
                    while (s.charAt(idx + 1) >= '0' && s.charAt(idx + 1) <= '9') {
                        idx++;
                    }

                    count.push(Integer.parseInt(s.substring(start, idx + 1)));
                } else if (s.charAt(idx) == '[') {
                    str.push("");
                } else if (s.charAt(idx) == ']') {
                    String st = str.pop();
                    StringBuilder sb = new StringBuilder();
                    int n = count.pop();

                    for (int j = 0; j < n; j++) {
                        sb.append(st);
                    }

                    str.push(str.pop() + sb.toString());
                } else {
                    str.push(str.pop() + s.charAt(idx));
                }

                idx++;
            }

            return str.pop();
        }
    }


    /**
     * 辅助栈法
     */
    public static class Solution {
        public String decodeString(String s) {
            Deque<Integer> stack_multi = new ArrayDeque<>();
            Deque<String> stack_res = new ArrayDeque<>();
            StringBuilder res = new StringBuilder();

            int multi = 0;
            for (char c : s.toCharArray()) {
                if (c == '[') {
                    stack_multi.add(multi);
                    stack_res.add(res.toString());
                    multi = 0;
                    res = new StringBuilder();
                } else if (c == ']') {
                    int mul = stack_multi.removeLast();
                    StringBuilder tmp = new StringBuilder();
                    for (int i = 0; i < mul; i++) {
                        tmp.append(res.toString());
                    }
                    res = new StringBuilder(stack_res.removeLast()).append(tmp.toString());
                } else if (c >= '1' && c <= '9') {
                    multi = multi * 10 + Integer.parseInt("" + c);
                } else {
                    res.append(c);
                }
            }
            return res.toString();
        }
    }


    /**
     * 递归
     */
    public static class Solution3 {
        public String decodeString(String str) {
            return dfs(str, 0)[0];
        }

        private String[] dfs(String str, int i) {
            StringBuilder builder = new StringBuilder();

            int len = str.length();
            int multi = 0;
            while (i < len) {
                if (str.charAt(i) == '[') {
                    String[] tmp = dfs(str, i + 1);
                    i = Integer.parseInt(tmp[0]);
                    while (multi > 0) {
                        builder.append(tmp[1]);
                        multi--;
                    }
                } else if (str.charAt(i) == ']') {
                    return new String[]{String.valueOf(i), builder.toString()};
                } else if (str.charAt(i) >= '1' && str.charAt(i) <= '9') {
                    multi = multi * 10 + Integer.parseInt("" + str.charAt(i));
                } else {
                    builder.append(str.charAt(i));
                }
                i++;
            }

            return new String[]{builder.toString()};
        }
    }
}
