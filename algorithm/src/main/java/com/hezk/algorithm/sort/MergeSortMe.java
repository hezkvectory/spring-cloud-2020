package com.hezk.algorithm.sort;

/**
 * Created by hezhengkui on 28/11/2018.
 */
public class MergeSortMe {


    public static void main(String[] args) {
//        int[] arr = {3, 4, 5, 2, 7, 8, 6, 1};
        int[] arr = {11, 3, 6, 7, 8, 4, 5, 9, 10};

        mergeSort(arr, 0, arr.length - 1);

        for (int i = 0; i <= arr.length - 1; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

    private static void mergeSort(int[] arr, int L, int R) {
        if (L == R) {
            return;
        } else {
            int M = (L + R) / 2;
            mergeSort(arr, L, M);
            mergeSort(arr, M + 1, R);
            merge(arr, L, M + 1, R);
        }

    }

    private static void merge(int[] arr, int L, int M, int R) {
        int left_size = M - L;
        int right_size = R - M + 1;

        int[] left = new int[left_size];
        int[] right = new int[right_size];
        for (int i = L; i < M; i++) {
            left[i - L] = arr[i];
        }

        for (int i = M; i <= R; i++) {
            right[i - M] = arr[i];
        }

        int a = 0, b = 0, k = L;

        while (a < left_size && b < right_size) {
            if (left[a] < right[b]) {
                arr[k] = left[a];
                k++;
                a++;
            } else {
                arr[k] = right[b];
                k++;
                b++;
            }
        }
        while (a < left_size) {
            arr[k++] = left[a++];
        }
        while (b < right_size) {
            arr[k++] = right[b++];
        }

    }
}
