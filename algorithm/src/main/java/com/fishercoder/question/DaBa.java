package com.fishercoder.question;

/**
 * 一个射击选手打靶，靶一共有10环，连10抢打中90环的可能有多少种？
 * 每粑环数0-10环
 */
public class DaBa {

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.recursive(90, 10));
    }

    public static class Solution {

        int recursive(int total, int num) {
            if (num == 1) {
                if (total >= 0 && total <= 10) {
                    return 1;
                } else {
                    return 0;
                }
            }
            int sum = 0;
            for (int i = 0; i <= 10; i++) {
                sum += recursive(total - i, num - 1);
            }
            return sum;
        }
    }
}
