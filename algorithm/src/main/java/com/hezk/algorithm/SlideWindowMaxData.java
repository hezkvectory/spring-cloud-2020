package com.hezk.algorithm;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class SlideWindowMaxData {

    public static void main(String[] args) {
        int[] nums = {5, 4, 3, 8, 4, 3, 2, 1};
//        int[] nums = {1, 2, 3, 4, 4, 5, 5, 6, 7, 8, 9};

        Solution1 solution = new Solution1();
        int[] result = solution.slide(nums, 3);
        Arrays.stream(result).boxed().map(i -> i + ",").forEach(System.out::print);
        System.out.println();
    }

    public static class Solution {
        public int[] slide(int[] nums, int k) {
            Deque<Integer> deque = new LinkedList<>();
            int[] result = new int[nums.length - k + 1];
            for (int i = 0; i < nums.length; i++) {
                while (!deque.isEmpty() && deque.peekLast() < nums[i]) {
                    deque.removeLast();
                }
                deque.addLast(nums[i]);
                if (i >= k && deque.peek() == nums[i - k]) {
                    deque.removeFirst();
                }
                if (i >= k - 1) {
                    result[i - k + 1] = deque.peekFirst();
                }
            }
            return result;
        }
    }

    public static class Solution1 {
        public int[] slide(int[] nums, int k) {
            PriorityQueue<int[]> deque = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o2[1] - o1[1]);
            int[] result = new int[nums.length - k + 1];

            for (int i = 0; i < nums.length; i++) {
                deque.offer(new int[]{nums[i], i});
                while (deque.peek()[1] <= i - k) {
                    deque.poll();
                }
                if (i >= k - 1) {
                    result[i - k + 1] = deque.peek()[0];
                }
            }
            return result;
        }
    }
}
