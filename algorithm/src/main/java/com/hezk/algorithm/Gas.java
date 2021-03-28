package com.hezk.algorithm;

/**
 * 在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
 * 如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。
 * 说明:
 * 如果题目有解，该答案即为唯一答案。
 * 输入数组均为非空数组，且长度相同。
 * 输入数组中的元素均为非负数。
 * 示例 1:
 * 输入:
 * gas  = [1,2,3,4,5]
 * cost = [3,4,5,1,2]
 */
public class Gas {

    public static class Solution {
        public int gas(int[] gas, int[] costs) {
            int len = gas.length;
            int sum = 0;
            int tempSum = 0;
            int start = 0;
            for (int i = 0; i < len; i++) {
                sum += gas[i] - costs[i];
                tempSum += gas[i] - costs[i];
                if (tempSum < 0) {
                    start = i + 1;
                    tempSum = 0;
                }
            }
            if (sum < 0) {
                return -1;
            }
            return start;
        }
    }

    public static class Solution1 {
        public int canCompleteCircuit(int[] gas, int[] cost) {
            int sum = 0;
            int min = 0;
            for (int i = 0; i < gas.length; i++) {
                // 统计加油量和耗油量
                sum += gas[i] - cost[i];
                min = Math.min(min, sum);
            }
            // 如果小于0，则不能环绕一周
            if (sum < 0)
                return -1;
            // 此时从起点出发能够环绕一周
            if (min >= 0)
                return 0;
            for (int i = gas.length - 1; i >= 0; i--) {
                int diff = gas[i] - cost[i];
                min += diff;
                // 如果该点能够将min补充到大于等于0，则该点为起点
                if (min >= 0)
                    return i;
            }
            return -1;
        }
    }
}
