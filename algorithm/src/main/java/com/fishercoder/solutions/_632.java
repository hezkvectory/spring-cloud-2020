package com.fishercoder.solutions;

import java.util.*;

/**
 * 632. Smallest Range
 * <p>
 * You have k lists of sorted integers in ascending order.
 * Find the smallest range that includes at least one number from each of the k lists.
 * <p>
 * We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.
 * <p>
 * Example 1:
 * Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
 * Output: [20,24]
 * Explanation:
 * List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
 * List 2: [0, 9, 12, 20], 20 is in range [20,24].
 * List 3: [5, 18, 22, 30], 22 is in range [20,24].
 * <p>
 * Note:
 * The given list may contain duplicates, so ascending order means >= here.
 * 1 <= k <= 3500
 * -105 <= value of elements <= 105.
 * For Java users, please note that the input type has been changed to List<List<Integer>>.
 * And after you reset the code template, you'll see this point.
 */
public class _632 {
    public static class Solution1 {
        /**
         * reference: https://discuss.leetcode.com/topic/94445/java-code-using-priorityqueue-similar-to-merge-k-array/2
         */
        public int[] smallestRange(List<List<Integer>> nums) {
            PriorityQueue<int[]> minHeap = new PriorityQueue<>(nums.size(), (a, b) -> a[0] - b[0]);
            /**int[] array consists of three numbers: value; which list in nums; index of value in this list*/

            int max = nums.get(0).get(0);
            for (int i = 0; i < nums.size(); i++) {
                minHeap.offer(new int[]{nums.get(i).get(0), i, 0});
                max = Math.max(max, nums.get(i).get(0));
            }
            int minRange = Integer.MAX_VALUE;
            int start = -1;
            while (minHeap.size() == nums.size()) {
                int[] curr = minHeap.poll();
                if (max - curr[0] < minRange) {
                    minRange = max - curr[0];
                    start = curr[0];
                }
                if (curr[2] + 1 < nums.get(curr[1]).size()) {
                    curr[0] = nums.get(curr[1]).get(curr[2] + 1);
                    curr[2]++;
                    minHeap.offer(curr);
                    max = Math.max(max, curr[0]);
                }
            }
            return new int[]{start, start + minRange};
        }
    }

    public static class Solution {

        public int[] smallestRange(List<List<Integer>> nums) {
            int n = nums.size();
            int inf = 0x3f3f3f;
            int max = -inf; // 当前最大值
            int st = -inf;  // 起点
            int ed = inf;   // 终点

            PriorityQueue<Node> pq = new PriorityQueue<>(((o1, o2) -> o1.val - o2.val));
            PriorityQueue<Node> pq1 = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.val, o2.val));
            PriorityQueue<Node> pq2 = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));

            // 相当于合并k个有序链表，把 head 放进去
            for (int i = 0; i < n; i++) {
                int val = nums.get(i).get(0);
                pq.offer(new Node(i, 0, val));
                max = Math.max(max, val);
            }

            // 必须包含 k 个元素
            while (pq.size() == n) {
                Node node = pq.poll();
                int i = node.i;
                int j = node.j;
                int val = node.val;

                // 更新区间长度
                if (max - val < ed - st) {
                    st = val;
                    ed = max;
                }

                // 为堆中填充元素
                if (j + 1 < nums.get(i).size()) {
                    int nVal = nums.get(i).get(j + 1);
                    pq.offer(new Node(i, j + 1, nVal));
                    max = Math.max(max, nVal);
                }
            }
            return new int[]{st, ed};
        }

        class Node {
            int i, j, val;

            public Node(int i, int j, int val) {
                this.i = i;
                this.j = j;
                this.val = val;
            }
        }
    }

    public static class Solution3 {
        public int[] smallestRange(List<List<Integer>> nums) {
            int size = nums.size();
            Map<Integer, List<Integer>> indices = new HashMap<>();
            int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                for (int x : nums.get(i)) {
                    List<Integer> list = indices.getOrDefault(x, new ArrayList<>());
                    list.add(i);
                    indices.put(x, list);
                    xMin = Math.min(xMin, x);
                    xMax = Math.max(xMax, x);
                }
            }

            int[] freq = new int[size];
            int inside = 0;
            int left = xMin, right = xMin - 1;
            int bestLeft = xMin, bestRight = xMax;

            while (right < xMax) {
                right++;
                if (indices.containsKey(right)) {
                    for (int x : indices.get(right)) {
                        freq[x]++;
                        if (freq[x] == 1) {
                            inside++;
                        }
                    }
                    while (inside == size) {
                        if (right - left < bestRight - bestLeft) {
                            bestLeft = left;
                            bestRight = right;
                        }
                        if (indices.containsKey(left)) {
                            for (int x: indices.get(left)) {
                                freq[x]--;
                                if (freq[x] == 0) {
                                    inside--;
                                }
                            }
                        }
                        left++;
                    }
                }
            }

            return new int[]{bestLeft, bestRight};
        }
    }


    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
//        nums.add(Arrays.asList(4, 10, 15, 24, 26));
//        nums.add(Arrays.asList(0, 9, 12, 20));
//        nums.add(Arrays.asList(5, 18, 22, 30));
        nums.add(Arrays.asList(4, 10, 15, 24, 26));
        nums.add(Arrays.asList(0));
        nums.add(Arrays.asList(5, 18, 22, 30));
        Solution3 solution = new Solution3();
        int[] result = solution.smallestRange(nums);
        System.out.print(result[0] + "\t");
        System.out.println(result[1]);
    }

}
