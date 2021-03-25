package com.fishercoder.solutions;

import java.util.*;

/**
 * 327. Count of Range Sum
 * <p>
 * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.
 * <p>
 * Note:
 * A naive algorithm of O(n2) is trivial. You MUST do better than that.
 * <p>
 * Example:
 * Given nums = [-2, 5, -1], lower = -2, upper = 2, Return 3.
 * The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.
 */
public class _327 {

    public static class Solution1 {
        /**
         * Time: O(n^2)
         * This results in TLE on Leetcode by the last test case.
         */
        public int countRangeSum(int[] nums, int lower, int upper) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            long[] sums = new long[nums.length];
            sums[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sums[i] = sums[i - 1] + nums[i];
            }
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] <= upper && nums[i] >= lower) {
                    count++;
                }
                for (int j = i + 1; j < nums.length; j++) {
                    long sum = sums[j] - (i > 0 ? sums[i - 1] : 0);
                    if (sum <= upper && sum >= lower) {
                        count++;
                    }
                }
            }
            return count;
        }
    }

    public static class Solution2 {
        public int countRangeSum(int[] nums, int lower, int upper) {
            int n = nums.length;
            long[] sums = new long[n + 1];
            for (int i = 0; i < n; i++) {
                sums[i + 1] = sums[i] + nums[i];
            }
            return countWhileMergeSort(sums, 0, n + 1, lower, upper);
        }

        private int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
            if (end - start <= 1) {
                return 0;
            }
            int mid = (start + end) / 2;
            int count = countWhileMergeSort(sums, start, mid, lower, upper) + countWhileMergeSort(sums, mid, end, lower, upper);
            int j = mid;
            int k = mid;
            int t = mid;
            long[] cache = new long[end - start];
            for (int i = start, r = 0; i < mid; i++, r++) {
                while (k < end && sums[k] - sums[i] < lower) {
                    k++;
                }
                while (j < end && sums[j] - sums[i] <= upper) {
                    j++;
                }
                while (t < end && sums[t] < sums[i]) {
                    cache[r++] = sums[t++];
                }
                cache[r] = sums[i];
                count += j - k;
            }
            System.arraycopy(cache, 0, sums, start, t - start);
            return count;
        }
    }

    public static class Solution3 {
        public int countRangeSum(int[] nums, int lower, int upper) {
            long sum = 0;
            long[] preSum = new long[nums.length + 1];
            for (int i = 0; i < nums.length; ++i) {
                sum += nums[i];
                preSum[i + 1] = sum;
            }

            Set<Long> allNumbers = new TreeSet<Long>();
            for (long x : preSum) {
                allNumbers.add(x);
                allNumbers.add(x - lower);
                allNumbers.add(x - upper);
            }
            // 利用哈希表进行离散化
            Map<Long, Integer> values = new HashMap<Long, Integer>();
            int idx = 0;
            for (long x : allNumbers) {
                values.put(x, idx++);
            }

            SegNode root = build(0, values.size() - 1);
            int ret = 0;
            for (long x : preSum) {
                int left = values.get(x - upper), right = values.get(x - lower);
                ret += count(root, left, right);
                insert(root, values.get(x));
            }
            return ret;
        }

        public SegNode build(int left, int right) {
            SegNode node = new SegNode(left, right);
            if (left == right) {
                return node;
            }
            int mid = (left + right) / 2;
            node.lchild = build(left, mid);
            node.rchild = build(mid + 1, right);
            return node;
        }

        public int count(SegNode root, int left, int right) {
            if (left > root.hi || right < root.lo) {
                return 0;
            }
            if (left <= root.lo && root.hi <= right) {
                return root.add;
            }
            return count(root.lchild, left, right) + count(root.rchild, left, right);
        }

        public void insert(SegNode root, int val) {
            root.add++;
            if (root.lo == root.hi) {
                return;
            }
            int mid = (root.lo + root.hi) / 2;
            if (val <= mid) {
                insert(root.lchild, val);
            } else {
                insert(root.rchild, val);
            }
        }

        class SegNode {
            int lo, hi, add;
            SegNode lchild, rchild;

            public SegNode(int left, int right) {
                lo = left;
                hi = right;
                add = 0;
                lchild = null;
                rchild = null;
            }
        }
    }


    /**
     * 整体思路（面向结果型，即不提供想到算法的思路，只描述代码实现的思路）：
     * 满足题目要求的连续数组通过数学公式表达如下：
     * lower <= sum[i, j] <= upper
     * 第一层转换：
     * 实际计算过程中需要遍历所有0 <= i <= j < input.length的组合且存在大量的重复运算，因此通过前缀和进行转换
     * preSum[i] = sum[0, i - 1]
     * 第二层转换：
     * 通过前缀和数组变形之后的满足题目要求的公式表达变为：
     * lower <= preSum[j] - preSum[i - 1] <= upper
     * 也就是说preSum[i - 1]数值在[preSum[j] - upper, preSum[j] - lower]范围内表示input[i, j]满足题目要求
     * 至此，我们将原始输入转换成了各个前缀和数组之间的【范围】关系，将范围关系保存在helper数组中
     * 第三层转换：
     * 由于input[]、lower和upper内容离散导致helper数组内容也离散，为便于查找，将helper[]的内容进行hash映射
     * 核心算法：
     * 将helper数组转换为线段树保存，利用线段树可以快速查询连续数组数据特征的功能统计每个叶子节点的【范围】
     */
    public static class Solution4 {

        class SegNode {
            int lo, hi, add;
            SegNode lchild, rchild;

            public SegNode(int left, int right) {
                lo = left;
                hi = right;
                add = 0;
                lchild = null;
                rchild = null;
            }

            public void printf() {
                if (this.lchild != null) {
                    this.lchild.printf();
                }

                System.out.println("lo=" + this.lo + ", hi=" + hi + ", add=" + add);

                if (this.rchild != null) {
                    this.rchild.printf();
                }
            }
        }

        public int countRangeSumMergeSegmentTree(int[] nums, int lower, int upper) {
            long sum = 0;
            int tempCount = 0;
            long[] preSum = new long[nums.length + 1];
            for (int i = 0; i < nums.length; ++i) {
                sum += nums[i];
                preSum[i + 1] = sum;
            }

            Set<Long> helper = new TreeSet();
            for (long x : preSum) {
                helper.add(x);
                helper.add(x - lower);
                helper.add(x - upper);
            }

            /**
             * 利用哈希表进行离散化，通常构建线段树时，需要严格划分所有叶子节点，也就是每个叶子节点表示的数据范围为1
             * 因此，通过hashMap将原始数据映射到数据范围较小的数值上，以代码中输入[-2, 5, 1]为例
             * sum=[0, -2, 3, 4]
             * helper=[-4, -2, 0, 1, 2, 3, 4, 5, 6]
             * map=[
             *      key    : -4 -2  0  1  2  3  4  5  6
             *      value  :  0  1  2  3  4  5  6  7  8
             * ]
             * 实现了将范围较大的value映射比较聚合的“连续”的“小”数组上
             */

            Map<Long, Integer> values = new HashMap();
            int idx = 0;
            for (long x : helper) {
                values.put(x, idx++);
            }

            System.out.println("preSum:");
            System.out.println(Arrays.toString(preSum));
            System.out.println("\nhelper:");
            System.out.println(helper);
            System.out.println("\nhelper map:");
            System.out.println(values);

            /* 按照helper[]的大小构建空的线段树，此时线段树只有标注map的key的位置的功能，还不具备统计【范围】的能力
             * 本例的线段树如下([left, right]):
             * [0,8]---------------------------+
             *   |                             |
             * [0,4]---------------+         [5,8]---------+
             *   |                 |           |           |
             * [0,2]---------+   [3,4]---+   [5,6]---+   [7,8]---+
             *   |           |     |     |     |     |     |     |
             * [0,1]---+     |     |     |     |     |     |     |
             *   |     |     |     |     |     |     |     |     |
             * [0,0] [1,1] [2,2] [3,3] [4,4] [5,5] [6,6] [7,7] [8,8]
             */
            SegNode root = build(0, values.size() - 1);

            int ret = 0;
            for (long x : preSum) {
                /*
                 * left和right表示preSum[x]的范围，举例来说：
                 * preSum[0]=0，不需要判断
                 * preSum[1]=-2，它满足题目的要求就是[-4, 0]
                 */
                int left = values.get(x - upper), right = values.get(x - lower);
                System.out.println("\nx=" + x + ", x.idx=" + values.get(x) + ", left.idx=" + left + ", right.idx=" + right);

                /*
                 * 这里的代码实现不全是线段树统计的算法，比较顺畅的思路应该是先将preSum的范围插入线段树，也就是更新线段树的内容
                 * 然后再依次统计所有preSum的结果，由于上述说法需要两次for循环，从效率的角度以上两个操作可以合并在一个for循环中，
                 * 从结果正确性的角度来说，preSum[0]其实不是一个有效的结果
                 * 因此这里实际操作的是preSum[1]开始的数据，因为第一轮循环时，preSum[0]的数据还没有更新到线段树中
                 * 对于preSum[1]来说，它的范围通过前文的left、right保存，只需要在当前的线段树中统计节点【范围】在left、right之间即可
                 */
                tempCount = count(root, left, right);
                ret += tempCount;
                System.out.println("count=" + tempCount);
                root.printf();

                /*
                 * 将preSum[i]的数据更新到线段树中，这里更新的内容是preSum[i]对应的hash值
                 * 在for循环时这样考虑
                 * preSum[0]=0 实际没有进行过统计
                 * preSum[1]=-2 insert之后，本例的线段树如下({[left, right]=add}):
                 * {[0,8]=1}--------------------------------------------+
                 *   |                                                  |
                 * {[0,4]=1}------------------------+                {[5,8]=1}-------------+
                 *   |                              |                   |                  |
                 * {[0,2]=1}-------------+        {[3,4]=0}--+       {[5,6]=0}---+       {[7,8]=0}---+
                 *   |                   |          |        |         |         |         |         |
                 * {[0,1]=0}---+         |          |        |         |         |         |         |
                 *   |         |         |          |        |         |         |         |         |
                 * {[0,0]=0} {[1,1]=0} {[2,2]=1} {[3,3]=0} {[4,4]=0} {[5,5]=0} {[6,6]=0} {[7,7]=0} {[8,8]=0}
                 * 此时进行count，统计的是从input开始到input[0]为在满足要求的连续子数组的数量，对于preSum[1]=-2而言，满足要求的范围是[-4,-2]，
                 * 对应树形hash结构的[0,2], count=1
                 *
                 * preSum[2]=3 insert之后
                 * {[0,8]=2}--------------------------------------------+
                 *   |                                                  |
                 * {[0,4]=2}------------------------+                {[5,8]=1}-------------+
                 *   |                              |                   |                  |
                 * {[0,2]=2}-------------+        {[3,4]=0}--+       {[5,6]=0}---+       {[7,8]=0}---+
                 *   |                   |          |        |         |         |         |         |
                 * {[0,1]=0}---+         |          |        |         |         |         |         |
                 *   |         |         |          |        |         |         |         |         |
                 * {[0,0]=0} {[1,1]=1} {[2,2]=1} {[3,3]=0} {[4,4]=0} {[5,5]=0} {[6,6]=0} {[7,7]=0} {[8,8]=0}
                 * 此时进行count，统计的是input开始input[2]为止满足要求的连续子数组的数量，对于preSum[2]=3而言，满足要求的范围是[1,5],
                 * 对应树形hash结构的[3,7], count=0
                 *
                 * preSum[3]=3 insert之后
                 * {[0,8]=3}--------------------------------------------+
                 *   |                                                  |
                 * {[0,4]=2}------------------------+                {[5,8]=1}-------------+
                 *   |                              |                   |                  |
                 * {[0,2]=2}-------------+        {[3,4]=0}--+       {[5,6]=1}---+       {[7,8]=0}---+
                 *   |                   |          |        |         |         |         |         |
                 * {[0,1]=0}---+         |          |        |         |         |         |         |
                 *   |         |         |          |        |         |         |         |         |
                 * {[0,0]=0} {[1,1]=1} {[2,2]=1} {[3,3]=0} {[4,4]=0} {[5,5]=1} {[6,6]=0} {[7,7]=0} {[8,8]=0}
                 * 此时进行count，统计的是input开始input[3]为止满足要求的连续子数组的数量，对于preSum[3]=3而言，满足要求的范围是[3,7],
                 * 对应树形hash结构的[3,7], count=1
                 * 后续的依次类推
                 */
                insert(root, values.get(x));
                System.out.println("\nsegment tree after insert: ");
                root.printf();
            }

            System.out.println("\nsegment tree: ");
            root.printf();

            return ret;
        }

        public SegNode build(int left, int right) {
            SegNode node = new SegNode(left, right);
            if (left == right) {
                return node;
            }
            int mid = (left + right) / 2;
            node.lchild = build(left, mid);
            node.rchild = build(mid + 1, right);
            return node;
        }

        public int count(SegNode root, int left, int right) {
            if (left > root.hi || right < root.lo) {
                return 0;
            }
            if (left <= root.lo && root.hi <= right) {
                return root.add;
            }
            return count(root.lchild, left, right) + count(root.rchild, left, right);
        }

        public void insert(SegNode root, int val) {
            root.add++;
            if (root.lo == root.hi) {
                return;
            }
            int mid = (root.lo + root.hi) / 2;
            if (val <= mid) {
                insert(root.lchild, val);
            } else {
                insert(root.rchild, val);
            }
        }

    }

    public static void main(String[] args) {
        int[] input = new int[]{-2, 5, 1};
        Solution4 s = new Solution4();
        System.out.println(s.countRangeSumMergeSegmentTree(input, -2, 2));
    }


}
