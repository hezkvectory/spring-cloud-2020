package cn.itcast.algorithm.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

class InsertSort {
    public static void main(String[] args) {
        int[] arr = {7, 3, 6, 4, 1, 8};
        InsertSort insertSort = new InsertSort();
        insertSort.sort(arr);
        System.out.println(Arrays.stream(arr).boxed().collect(Collectors.toList()));
    }

    public void sort(int[] arr) {
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            for (int j = i; j > 0; j--) {
                if (greater(arr, j, j - 1)) {
                    swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private boolean greater(int[] arr, int i, int j) {
        return arr[i] > arr[j];
    }
}