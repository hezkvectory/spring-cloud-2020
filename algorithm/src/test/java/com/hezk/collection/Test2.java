package com.hezk.collection;

/**
 * Created by hezhengkui on 2019/3/26.
 */
public class Test2 {
    public static void main(String[] args) {
        int[] arr = {-30, 11, -10, 0, 0, 8, 89, -3, -4, 9, 7, 0, 0, -1, -7, 4, 0, 6, 3, -9, 10, 22, -20};
        int len = arr.length;
        System.out.println("数组长度：" + len);

        int total = 0;

        int zeroStart = 0;
        for (int i = 0; i < len; i++) {
            total++;
            if (arr[i] >= 0) {
                for (int j = len - 1; j > i; j--) {
                    total++;
                    if (arr[j] < 0) {
                        swap(arr, i, j);
                        zeroStart = i;
                    }
                }
            }
        }

        for (int i = len - 1; i > zeroStart + 1; i--) {
            total++;
            if (arr[i] == 0) {
                for (int j = zeroStart + 1; j < i; j++) {
                    total++;
                    if (arr[j] > 0) {
                        swap(arr, i, j);
                    }
                }
            }
        }

        for (int i = 0; i < len; i++) {
            System.out.print(arr[i] + ",");
        }

        System.out.println();
        System.out.println("总共循环次数：" + total);
    }

    private static void swap(int[] arr, int start, int end) {

        int tmp = arr[start];
        arr[start] = arr[end];
        arr[end] = tmp;

    }
}
