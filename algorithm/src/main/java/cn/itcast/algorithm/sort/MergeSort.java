package cn.itcast.algorithm.sort;

import java.util.Arrays;

public class MergeSort {

    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            a[k2 + low] = temp[k2];
        }
    }

    public static void mergeSort(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            mergeSort(a, low, mid);
            // 右边
            mergeSort(a, mid + 1, high);
            // 左右归并
            merge(a, low, mid, high);
            System.out.println(Arrays.toString(a));
        }

    }

    public static void main(String[] args) {
        int a[] = {51, 46, 20, 18, 65, 97, 82, 30, 77, 50};
//        mergeSort(a, 0, a.length - 1);
        new Solution().mergeSort(a, 0, a.length - 1);
        System.out.println("排序结果：" + Arrays.toString(a));
        //排序结果：[18, 20, 30, 46, 50, 51, 65, 77, 82, 97]
        //排序结果：[18, 20, 30, 46, 50, 51, 65, 77, 82, 97]
        //排序结果：[18, 20, 30, 46, 50, 51, 65, 77, 82, 97]
        //排序结果：[18, 20, 30, 46, 50, 51, 65, 77, 82, 97]
        //排序结果：[18, 20, 30, 46, 50, 51, 65, 77, 82, 97]
    }


    public static class Solution {

        private void mergeSort(int[] nums, int start, int end) {
            if (start < end) {
                int mid = start + (end - start) / 2;
                mergeSort(nums, start, mid);
                mergeSort(nums, mid + 1, end);
                merge(nums, start, mid, end);
            }
        }

        private void merge(int[] nums, int start, int mid, int end) {
            int len = end - start + 1;
            int[] temp = new int[len];
            int i = start;
            int j = mid + 1;
            int k = 0;
            while (i <= mid && j <= end) {
                if (nums[i] < nums[j]) {
                    temp[k++] = nums[i++];
                } else {
                    temp[k++] = nums[j++];
                }
            }
            while (i <= mid) {
                temp[k++] = nums[i++];
            }
            while (j <= end) {
                temp[k++] = nums[j++];
            }
            k = 0;
            while (k < len) {
                nums[start++] = temp[k++];
            }
        }

    }
}