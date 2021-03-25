package com.fishercoder.solutions;

import java.util.*;

/**
 * 301. Remove Invalid Parentheses
 * <p>
 * Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
 * <p>
 * Note: The input string may contain letters other than the parentheses ( and ).
 * <p>
 * Examples:
 * "()())()" -> ["()()()", "(())()"]
 * "(a)())()" -> ["(a)()()", "(a())()"]
 * ")(" -> [""]
 */
public class _301 {

    public static class Solution1 {

        public List<String> removeInvalidParentheses(String s) {
            List<String> result = new ArrayList<>();
            if (s == null) {
                return result;
            }

            Set<String> visited = new HashSet();
            Queue<String> q = new LinkedList();

            q.offer(s);
            visited.add(s);

            boolean found = false;

            while (!q.isEmpty()) {
                String curr = q.poll();
                if (isValid(curr)) {
                    found = true;
                    result.add(curr);
                }

                if (found) {
                    continue;//this means if the initial input is already a valid one, we'll just directly return it and there's actually only one valid result
                }

                for (int i = 0; i < curr.length(); i++) {
                    if (curr.charAt(i) != '(' && curr.charAt(i) != ')') {
                        continue;//this is to rule out those non-parentheses characters
                    }

                    String next = curr.substring(0, i) + curr.substring(i + 1);
                    if (!visited.contains(next)) {
                        q.offer(next);
                        visited.add(next);
                    }
                }

            }
            return result;
        }

        private boolean isValid(String str) {
            char[] chars = str.toCharArray();
            int count = 0;
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c == '(') {
                    count++;
                }
                if (c == ')') {
                    count--;
                    if (count == -1) {
                        return false;
                    }
                }
            }
            return count == 0;
        }
    }

    public static class Solution {

        private int len;
        private char[] charArray;
        private Set<String> validExpressions = new HashSet<>();

        public List<String> removeInvalidParentheses(String s) {
            this.len = s.length();
            this.charArray = s.toCharArray();

            // 第 1 步：遍历一次，计算多余的左右括号
            int leftRemove = 0;
            int rightRemove = 0;
            for (int i = 0; i < len; i++) {
                if (charArray[i] == '(') {
                    leftRemove++;
                } else if (charArray[i] == ')') {
                    // 遇到右括号的时候，须要根据已经存在的左括号数量决定
                    if (leftRemove == 0) {
                        rightRemove++;
                    }
                    if (leftRemove > 0) {
                        // 关键：一个右括号出现可以抵销之前遇到的左括号
                        leftRemove--;
                    }
                }
            }

            // 第 2 步：回溯算法，尝试每一种可能的删除操作
            StringBuilder path = new StringBuilder();
            dfs(0, 0, 0, leftRemove, rightRemove, path);
            return new ArrayList<>(this.validExpressions);
        }

        /**
         * @param index       当前遍历到的下标
         * @param leftCount   已经遍历到的左括号的个数
         * @param rightCount  已经遍历到的右括号的个数
         * @param leftRemove  最少应该删除的左括号的个数
         * @param rightRemove 最少应该删除的右括号的个数
         * @param path        一个可能的结果
         */
        private void dfs(int index, int leftCount, int rightCount, int leftRemove, int rightRemove, StringBuilder path) {
            if (index == len) {
                if (leftRemove == 0 && rightRemove == 0) {
                    validExpressions.add(path.toString());
                }
                return;
            }

            char character = charArray[index];
            // 可能的操作 1：删除当前遍历到的字符
            if (character == '(' && leftRemove > 0) {
                // 由于 leftRemove > 0，并且当前遇到的是左括号，因此可以尝试删除当前遇到的左括号
                dfs(index + 1, leftCount, rightCount, leftRemove - 1, rightRemove, path);
            }
            if (character == ')' && rightRemove > 0) {
                // 由于 rightRemove > 0，并且当前遇到的是右括号，因此可以尝试删除当前遇到的右括号
                dfs(index + 1, leftCount, rightCount, leftRemove, rightRemove - 1, path);
            }

            // 可能的操作 2：保留当前遍历到的字符
            path.append(character);
            if (character != '(' && character != ')') {
                // 如果不是括号，继续深度优先遍历
                dfs(index + 1, leftCount, rightCount, leftRemove, rightRemove, path);
            } else if (character == '(') {
                // 考虑左括号
                dfs(index + 1, leftCount + 1, rightCount, leftRemove, rightRemove, path);
            } else if (leftCount > rightCount) {//这里不能是 character == ')'， 还有可能是字母
                // 考虑右括号
                dfs(index + 1, leftCount, rightCount + 1, leftRemove, rightRemove, path);
            }
            path.deleteCharAt(path.length() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        List<String> result = solution.removeInvalidParentheses("(())(()");
        System.out.println(result);
    }

}
