package com.hezk.algorithm.sort;

/**
 * 归并排序
 * Created by hezhengkui on 28/11/2018.
 */
public class MergeSortMe1 {


    public static void main(String[] args) {
//        int[] arr = {3, 4, 5, 2, 7, 8, 6, 1};
        int[] arr = {11, 3, 6, 7, 8, 4, 5, 9, 10};

        mergeSort(arr);

        for (int i = 0; i <= arr.length - 1; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

    public static void mergeSort(int[] A) {
        //归并排序，递归做法，分而治之
        mSort(A, 0, A.length - 1);
    }

    public static void mSort(int[] A, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;//中点
            mSort(A, low, mid);
            mSort(A, mid + 1, high);
            merge(A, low, mid, high);
        }
    }

    public static void merge(int[] A, int low, int mid, int high) {
        //辅助数组
        int[] temp = new int[A.length];
        //左子序列的下标
        int i = low;
        //右子序列的下标
        int j = mid + 1;
        //辅助数组的下标
        int tempIndex = low;
        while (i <= mid && j <= high) {
            if (A[i] < A[j]) {
                temp[tempIndex++] = A[i++];
            } else {
                temp[tempIndex++] = A[j++];
            }
        }
        for (; i <= mid; i++) {
            temp[tempIndex++] = A[i];
        }
        for (; j <= high; j++) {
            temp[tempIndex++] = A[j];
        }
        //复制已排好序的数组至原数组
        for (int v = low; v <= high; v++) {
            A[v] = temp[v];
        }
    }
}
