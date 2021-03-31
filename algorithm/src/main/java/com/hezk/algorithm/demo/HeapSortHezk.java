package com.hezk.algorithm.demo;

public class HeapSortHezk {
    public static void main(String[] args) {
        int[] arr = {3, 1, 6, 4, 8, 5, 9};
        for (int i = 1; i < arr.length; i++) {
            insertSort(arr, i);
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();

        int n = arr.length - 1;
        while (n > 0) {
            swap(arr, 0, n);
            adjustHeap(arr, 0, n--);
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

    public static void insertSort(int[] arr, int i) {
        while (arr[i] > arr[(i - 1) / 2]) {
            swap(arr, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    public static void adjustHeap(int[] arr, int index, int size) {
        int left = index * 2 + 1;
        while (left < size) {
            int larget = left + 1 < size && arr[left] < arr[left + 1] ? left + 1 : left;
            if (arr[index] > arr[larget]) {
                break;
            }
            swap(arr, index, larget);
            index = larget;
            left = index * 2 + 1;
        }

    }

    public static void swap(int[] arr, int l, int r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }
}