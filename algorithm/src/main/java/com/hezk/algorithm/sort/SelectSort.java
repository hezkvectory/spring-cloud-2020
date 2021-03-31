package com.hezk.algorithm.sort;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectSort {


    public static void main(String[] args) {
        int[] arr = {3, 4, 5, 2, 9, 6, 8, 7, 1};

        Solution2 solution1 = new Solution2();
        solution1.selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static class Solution1 {
        public void selectSort(int[] arr) {
            int len = arr.length;
            while (len > 1) {
                int pos = findMaxPos(arr, len);
                int tmp = arr[len - 1];
                arr[len - 1] = arr[pos];
                arr[pos] = tmp;
                len--;
            }
        }

        private int findMaxPos(int[] arr, int n) {
            int max = arr[0];
            int pos = 0;
            for (int i = 1; i < n; i++) {
                if (max < arr[i]) {
                    max = arr[i];
                    pos = i;
                }
            }
            return pos;
        }
    }

    public static class Solution2 {
        public void selectSort(int[] nums) {
            int len = nums.length;
            for (int i = 0; i < len - 1; i++) {
                int min = i;
                for (int j = i + 1; j < len; j++) {
                    if (nums[min] > nums[j]) {
                        min = j;
                    }
                }
                int tmp = nums[min];
                nums[min] = nums[i];
                nums[i] = tmp;
            }
        }
    }

}
