package com.hezk.algorithm.sort;

/**
 * Created by hezhengkui on 29/11/2018.
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {8, 5, 7, 6, 3, 1, 2, 4, 9};

        heapSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            insertHeap(arr, i);
        }

        int r = arr.length - 1;
        while (r > 0) {
            swap(arr, 0, r);
            heapify(arr, 0, r--);
        }
    }

    public static void insertHeap(int[] arr, int i) {
        while (arr[i] > arr[(i - 1) / 2]) {
            swap(arr, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    public static void heapify(int[] arr, int index, int size) {
        int left = index * 2 + 1;
        while (left < size) {
            int largest = left + 1 < size && arr[left] < arr[left + 1] ? left + 1 : left;
            if (arr[index] > arr[largest]) {
                return;
            }
            swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    //交换位置
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
