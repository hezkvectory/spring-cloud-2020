package com.fishercoder.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Q51 {

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] nums = {8, 7, 6, 5, 4, 3, 2, 1, 0};
//        int[] nums = {7, 5, 6, 4};
        int[] nums = {2, 3, 5, 7, 1, 4, 6, 8};
        System.out.println(solution.reversePairs(nums));
    }

    public static class Solution {
        public int reversePairs(int[] nums) {
            int len = nums.length;
            if (len < 2) {
                return 0;
            }
            int[] copy = new int[len];
            for (int i = 0; i < len; i++) {
                copy[i] = nums[i];
            }
            int[] temp = new int[len];
            int num = reversePairs(copy, 0, len - 1, temp);
            return num;
        }

        private int reversePairs(int[] nums, int left, int right, int[] temp) {
            if (left == right) {
                return 0;
            }
            int mid = left + (right - left) / 2;
            int leftPairs = reversePairs(nums, left, mid, temp);
            int rightPairs = reversePairs(nums, mid + 1, right, temp);

            if (nums[mid] <= nums[mid + 1]) {
                return leftPairs + rightPairs;
            }

            int crossPairs = mergeAndCount1(nums, left, mid, right, temp);
            return leftPairs + rightPairs + crossPairs;
        }

        private int mergeAndCount1(int[] nums, int left, int mid, int right, int[] temp) {
            int i = left;
            int j = mid + 1;
            int t = 0;
            int count = 0;
            while (i <= mid && j <= right) {
                if (nums[i] <= nums[j]) {
                    temp[t++] = nums[i];
                    i++;
                } else {
                    temp[t++] = nums[j];
                    count += (mid - i + 1);
                    j++;
                }
            }
            while (i <= mid) {
                temp[t++] = nums[i++];
                count += (mid - i + 1);
            }
            while (j <= right) {
                temp[t++] = nums[j++];
            }
            t = 0;
            while (left <= right) {
                nums[left++] = temp[t++];
            }
            return count;
        }


        /**
         * nums[left..mid]有序，nums[mid+1..right]有序
         *
         * @param nums
         * @param left
         * @param mid
         * @param right
         * @param temp
         * @return
         */
        private int mergeAndCount(int[] nums, int left, int mid, int right, int[] temp) {
            for (int i = left; i <= right; i++) {
                temp[i] = nums[i];
            }
            int i = left;
            int j = mid + 1;

            int count = 0;
            for (int k = left; k <= right; k++) {
                if (i == mid + 1) {
                    nums[k] = temp[j];
                    j++;
                } else if (j == right + 1) {
                    nums[k] = temp[i];
                    i++;
                } else if (temp[i] <= temp[j]) {
                    nums[k] = temp[i];
                    i++;
                } else {
                    nums[k] = temp[j];
                    j++;
                    count += (mid - i + 1);
                }
            }
            return count;
        }
    }


    public class Solution2 {

        public int reversePairs(int[] nums) {
            int len = nums.length;

            if (len < 2) {
                return 0;
            }

            // 离散化：使得数字更紧凑，节约树状数组的空间
            // 1、使用二分搜索树是为了去掉重复元素
            Set<Integer> treeSet = new TreeSet<>();
            for (int i = 0; i < len; i++) {
                treeSet.add(nums[i]);
            }

            // 2、把排名存在哈希表里方便查询
            Map<Integer, Integer> rankMap = new HashMap<>();
            int rankIndex = 1;
            for (Integer num : treeSet) {
                rankMap.put(num, rankIndex);
                rankIndex++;
            }

            int count = 0;
            // 在树状数组内部完成前缀和的计算
            // 规则是：从后向前，先给对应的排名 + 1，再查询前缀和
            FenwickTree fenwickTree = new FenwickTree(rankMap.size());

            for (int i = len - 1; i >= 0; i--) {
                int rank = rankMap.get(nums[i]);
                fenwickTree.update(rank, 1);
                count += fenwickTree.query(rank - 1);
            }
            return count;
        }

        private class FenwickTree {
            private int[] tree;
            private int len;

            public FenwickTree(int n) {
                this.len = n;
                tree = new int[n + 1];
            }

            /**
             * 单点更新：将 index 这个位置 + delta
             *
             * @param i
             * @param delta
             */
            public void update(int i, int delta) {
                // 从下到上，最多到 size，可以等于 size
                while (i <= this.len) {
                    tree[i] += delta;
                    i += lowbit(i);
                }
            }


            // 区间查询：查询小于等于 tree[index] 的元素个数
            // 查询的语义是「前缀和」
            public int query(int i) {
                // 从右到左查询
                int sum = 0;
                while (i > 0) {
                    sum += tree[i];
                    i -= lowbit(i);
                }
                return sum;
            }

            public int lowbit(int x) {
                return x & (-x);
            }
        }
    }

}
