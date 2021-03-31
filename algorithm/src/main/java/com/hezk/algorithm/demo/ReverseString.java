package com.hezk.algorithm.demo;

/**
 * Created by hezhengkui on 30/11/2018.
 */
public class ReverseString {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        leftRotateString(arr, 2, 6);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
    }


    private static void leftRotateString(int[] arr, int m, int n) {
        m %= n;
        reverseString(arr, 0, m - 1);
        reverseString(arr, m, n - 1);
        reverseString(arr, 0, n - 1);
    }


    private static void reverseString(int[] arr, int from, int to) {
        while (from < to) {
            int tmp = arr[from];
            arr[from++] = arr[to];
            arr[to--] = tmp;
        }
    }
}
