package com.hezk.algorithm.sort;

import java.util.Arrays;

/**
 * Created by hezhengkui on 29/11/2018.
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {8, 5, 7, 6, 3, 1, 2, 4, 9};

//        shellSort(arr);

        shellSortMe(arr);
        System.out.println(Arrays.toString(arr));
    }


    private static void shellSortMe(int[] arr) {
        int step = arr.length;
        while (true) {
            step = step / 2;
            for (int i = 0; i < step; i++) {
                for (int a = i + step; a < arr.length; a = a + step) {
                    for (int b = a - step; b >= 0 && arr[b] > arr[b + step]; b = b - step) {
                        int tmp = arr[b];
                        arr[b] = arr[b + step];
                        arr[b + step] = tmp;
                    }
                }
            }
            if (step == 1) {
                break;
            }
        }
    }

    /**
     * 希尔排序
     *
     * @param num
     */
    public static void shellSort(int num[]) {
        int temp;
        //默认步长为数组长度除以2
        int step = num.length;
        while (true) {
            step = step / 2;
            //确定分组数
            for (int i = 0; i < step; i++) {
                //对分组数据进行直接插入排序
                for (int j = i + step; j < num.length; j = j + step) {
                    temp = num[j];
                    int k;
                    for (k = j - step; k >= 0; k = k - step) {
                        if (num[k] > temp) {
                            num[k + step] = num[k];
                        } else {
                            break;
                        }
                    }
                    num[k + step] = temp;
                }
            }
            if (step == 1) {
                break;
            }
        }
    }

}
