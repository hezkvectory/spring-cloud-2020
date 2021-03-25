package com.fishercoder.solutions;

import java.util.Stack;

/**
 * 20. Valid Parentheses
 * <p>
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * <p>
 * An input string is valid if:
 * <p>
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 * <p>
 * Note that an empty string is also considered valid.
 * <p>
 * Example 1:
 * Input: "()"
 * Output: true
 * <p>
 * Example 2:
 * Input: "()[]{}"
 * Output: true
 * <p>
 * Example 3:
 * Input: "(]"
 * Output: false
 * <p>
 * Example 4:
 * Input: "([)]"
 * Output: false
 * <p>
 * Example 5:
 * Input: "{[]}"
 * Output: true
 */
public class _20 {
    public static void main(String[] args) {
        String str = "{(({}))}";
        Solution1 solution = new Solution1();
        System.out.println(solution.isValid(str));
    }

    public static class Solution1 {
        public boolean isValid(String s) {
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                    stack.push(s.charAt(i));
                } else {
                    if (stack.isEmpty()) {
                        return false;
                    } else {
                        if (stack.peek() == '(' && s.charAt(i) != ')') {
                            return false;
                        } else if (stack.peek() == '{' && s.charAt(i) != '}') {
                            return false;
                        } else if (stack.peek() == '[' && s.charAt(i) != ']') {
                            return false;
                        }
                        stack.pop();
                    }
                }
            }
            return stack.isEmpty();
        }
    }

    public static class Solution2 {
        /**
         * A more concise solution:
         * credit: https://leetcode.com/problems/valid-parentheses/discuss/9178/Short-java-solution
         */
        public boolean isValid(String s) {
            Stack<Character> stack = new Stack<>();
            for (char c : s.toCharArray()) {
                if (c == '(') {
                    stack.push(')');
                } else if (c == '{') {
                    stack.push('}');
                } else if (c == '[') {
                    stack.push(']');
                } else if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
            return stack.isEmpty();
        }
    }
}
