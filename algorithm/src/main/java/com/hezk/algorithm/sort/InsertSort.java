package com.hezk.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序
 * Created by hezhengkui on 29/11/2018.
 */
public class InsertSort {


    public static void main(String[] args) {
        int[] arr = {8, 5, 7, 6, 3, 1, 2, 4, 9};

//        insertSort(arr, arr.length);
//        insertSort(arr);
        Solution1 solution1 = new Solution1();
        solution1.insertSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public static class Solution1 {
        public void insertSort(int[] nums) {
            int len = nums.length;
            for (int i = 1; i < len; i++) {
                int temp = nums[i];
                int k = i - 1;
                while (k >= 0 && nums[k] > temp) {
                    k--;
                }
                for (int j = i; j > k + 1; j--) {
                    nums[j] = nums[j - 1];
                }
                nums[k + 1] = temp;
            }
        }
    }

    private static void insertSort(int[] num) {
        int temp;
        for (int i = 0; i < num.length; i++) {
            //对分组数据进行直接插入排序
            for (int j = i + 1; j < num.length; j = j + 1) {
                temp = num[j];
                int k;
                for (k = j - 1; k >= 0; k = k - 1) {
                    if (num[k] > temp) {
                        num[k + 1] = num[k];
                    } else {
                        break;
                    }
                }
                num[k + 1] = temp;
            }
        }
    }


    //Insertion Sort
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    //交换位置
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void insertSort(int[] arr, int len) {
        for (int i = 1; i < len; i++) {
            insert(arr, i);
        }
    }


    private static void insert(int[] arr, int n) {

        int key = arr[n];
        while (arr[n - 1] > key) {
            arr[n] = arr[n - 1];
            n--;
            if (n == 0) {
                break;
            }
        }
        arr[n] = key;
    }
}
