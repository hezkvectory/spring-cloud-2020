package com.hezk.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class Bubble {

    public static void main(String[] args) {
//        int[] arr = {3, 1, 2, 4, 5, 9, 7, 8, 6};
        int[] arr = {-30, 11, -10, 0, 0, 8, 89, -3, -4, 9, 7, 0, 0, -1, -7, 4, 0, 6, 3, -9, 10, 22, -20};

        Solution1 solution1 = new Solution1();
        solution1.bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static class Solution1 {
        public void bubbleSort(int[] nums) {
            int len = nums.length;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len - i - 1; j++) {
                    if (nums[j] > nums[j + 1]) {
                        int temp = nums[j];
                        nums[j] = nums[j + 1];
                        nums[j + 1] = temp;
                    }
                }
            }
        }
    }

    public static class Solution2 {
        public void bubbleSort(int[] arr) {
            for (int n = arr.length - 1; n >= 1; n--) {
                for (int j = 0; j < n; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int tmp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = tmp;
                    }
                }
            }
        }
    }
}
