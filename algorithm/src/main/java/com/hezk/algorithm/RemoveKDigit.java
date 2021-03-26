package com.hezk.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;

public class RemoveKDigit {

    public static class Solution {
        public String removeKdigits(String str, int k) {
            Deque<Character> deque = new ArrayDeque<>();
            int len = str.length();
            for (int i = 0; i < len; i++) {
                char digit = str.charAt(i);
                while (!deque.isEmpty() && deque.peekLast() > digit && k > 0) {
                    deque.removeLast();
                    k--;
                }
                deque.addLast(digit);
            }
            while (k-- > 0) {
                deque.removeLast();
            }
            StringBuilder builder = new StringBuilder();
            boolean firstZero = true;
            for (char c : deque) {
                if (c == '0' && firstZero) {
                    continue;
                }
                firstZero = false;
                builder.append(c);
            }
            return builder.toString();
        }
    }

    public static void main(String[] args) {
        String str = "4329768";
        Solution solution = new Solution();
        System.out.println(solution.removeKdigits(str, 4));
    }

}
