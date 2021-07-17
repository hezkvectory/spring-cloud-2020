package com.fishercoder.solutions;

import java.util.*;

/**
 * 1190. Reverse Substrings Between Each Pair of Parentheses
 * <p>
 * You are given a string s that consists of lower case English letters and brackets.
 * Reverse the strings in each pair of matching parentheses, starting from the innermost one.
 * Your result should not contain any brackets.
 * <p>
 * Example 1:
 * Input: s = "(abcd)"
 * Output: "dcba"
 * <p>
 * Example 2:
 * Input: s = "(u(love)i)"
 * Output: "iloveu"
 * Explanation: The substring "love" is reversed first, then the whole string is reversed.
 * <p>
 * Example 3:
 * Input: s = "(ed(et(oc))el)"
 * Output: "leetcode"
 * Explanation: First, we reverse the substring "oc", then "etco", and finally, the whole string.
 * <p>
 * Example 4:
 * Input: s = "a(bcdefghijkl(mno)p)q"
 * Output: "apmnolkjihgfedcbq"
 * <p>
 * Constraints:
 * 0 <= s.length <= 2000
 * s only contains lower case English characters and parentheses.
 * It's guaranteed that all parentheses are balanced.
 */
public class _1190 {
    public static class Solution1 {
        public String reverseParentheses(String s) {
            Stack<Character> stack = new Stack<>();
            Queue<Character> queue = new LinkedList<>();
            for (char c : s.toCharArray()) {
                if (c != ')') {
                    stack.push(c);
                } else {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        queue.offer(stack.pop());
                    }
                    if (!stack.isEmpty()) {
                        stack.pop();//pop off the open paren
                    }
                    while (!queue.isEmpty()) {
                        stack.push(queue.poll());
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty()) {
                sb.append(stack.pop());
            }
            return sb.reverse().toString();
        }
    }


    public static class Solution {
        public String reverseParentheses(String str) {
            Deque<String> deque = new ArrayDeque<>();
            StringBuilder builder = new StringBuilder();
            for (char c : str.toCharArray()) {
                if (c == '(') {
                    deque.add(builder.toString());
                    builder.setLength(0);
                } else if (c == ')') {
                    builder.reverse();
                    builder.insert(0, deque.removeLast());
                } else {
                    builder.append(c);
                }
            }
            return builder.toString();
        }
    }

    public static class Solution3 {
        public String reverseParentheses(String s) {
            Stack<Character> stack = new Stack<>();
            for (char c : s.toCharArray()) {
                if (c == ')') {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (stack.peek() != '(') {
                        stringBuilder.append(stack.pop());
                    }
                    stack.pop();
                    for (char ch : stringBuilder.toString().toCharArray()) {
                        stack.push(ch);
                    }
                } else {
                    stack.push(c);
                }
            }
            StringBuilder result = new StringBuilder();
            while (!stack.isEmpty()) {
                result.append(stack.pop());
            }
            return result.reverse().toString();
        }
    }

}
