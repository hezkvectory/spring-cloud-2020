package com.fishercoder.solutions;

import java.util.LinkedList;
import java.util.List;

/**
 * 241. Different Ways to Add Parentheses
 * <p>
 * Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. The valid operators are +, - and *.
 * <p>
 * <p>
 * Example 1
 * Input: "2-1-1".
 * <p>
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 * Output: [0, 2]
 * <p>
 * <p>
 * Example 2
 * Input: "2*3-4*5"
 * <p>
 * (2*(3-(4*5))) = -34
 * ((2*(3-4))*5) = -10
 * ((2*3)-(4*5)) = -14
 * (2*((3-4)*5)) = -10
 * (((2*3)-4)*5) = 10
 * Output: [-34, -14, -10, -10, 10]
 */
public class _241 {

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        System.out.println(solution1.diffWaysToCompute("2*3-4*5"));
    }

    public static class Solution1 {
        /**Time:  O(n * 4^n / n^(3/2)) ~= n * Catalan numbers = n * (C(2n, n) - C(2n, n - 1)),
         due to the size of the results is Catalan numbers,
         and every way of evaluation is the length of the string,
         so the time complexity is at most n * Catalan numbers.
         Space: O(n * 4^n / n^(3/2)), the cache size of lookup is at most n * Catalan numbers.*/

        /**
         * Credit: https://discuss.leetcode.com/topic/19901/a-recursive-java-solution-284-ms
         */
        public List<Integer> diffWaysToCompute(String input) {
            List<Integer> answer = new LinkedList<>();
            int len = input.length();
            for (int i = 0; i < len; i++) {
                if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*') {
                    String part1 = input.substring(0, i);
                    String part2 = input.substring(i + 1);
                    List<Integer> answer1 = diffWaysToCompute(part1);
                    List<Integer> answer2 = diffWaysToCompute(part2);
                    for (int a1 : answer1) {
                        for (int a2 : answer2) {
                            int result = 0;
                            switch (input.charAt(i)) {
                                case '+':
                                    result = a1 + a2;
                                    break;
                                case '-':
                                    result = a1 - a2;
                                    break;
                                case '*':
                                    result = a1 * a2;
                                    break;
                                default:
                                    break;
                            }
                            answer.add(result);
                        }
                    }
                }
            }
            if (answer.size() == 0) {
                answer.add(Integer.valueOf(input));
            }
            return answer;
        }
    }
}
