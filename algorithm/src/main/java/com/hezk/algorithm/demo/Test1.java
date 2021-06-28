package com.hezk.algorithm.demo;

import java.io.IOException;
import java.util.Stack;

public class Test1 {
    public static int[] findMax(int[] arry) {
        int len = arry.length;
        int i = 0;
        int[] res = new int[len];
        Stack<Integer> sk = new Stack<Integer>();
        while (i < len) {
            if (sk.isEmpty() || arry[i] < arry[sk.peek()]) {
                sk.push(i);
                i++;
            } else {
                res[sk.pop()] = arry[i];
            }
        }
        while (!sk.isEmpty()) {
            res[sk.pop()] = -1;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        int array[] = new int[]{1, 5, 3, 6, 4, 8, 9, 10};
        int res[] = findMax(array);
        for (int num : res) {
            System.out.println(num);
        }
    }
}
