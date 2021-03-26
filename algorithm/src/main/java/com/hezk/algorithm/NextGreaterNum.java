package com.hezk.algorithm;

import java.util.*;

public class NextGreaterNum {

    public static class Solution {
        public int[] nextGreaterNum(int[] num1, int[] num2) {
            int len2 = num2.length;
            int len1 = num1.length;
            Map<Integer, Integer> map = new HashMap<>();
            Deque<Integer> deque = new ArrayDeque<>();
            for (int i = 0; i < len2; i++) {
                while (!deque.isEmpty() && deque.peekLast() < num2[i]) {
                    int num = deque.removeLast();
                    map.put(num, num2[i]);
                }
                deque.addLast(num2[i]);
            }

            int[] result = new int[len1];
            for (int i = 0; i < len1; i++) {
                result[i] = map.getOrDefault(num1[i], -1);
            }
            return result;
        }

    }

    public static void main(String[] args) {
        int[] num1 = {4, 1, 2};
        int[] num2 = {1, 3, 4, 2};
        int[] result = new Solution().nextGreaterNum(num1, num2);
        Arrays.stream(result).boxed().map(o -> o + ",").forEach(System.out::print);
        System.out.println();
    }
}
