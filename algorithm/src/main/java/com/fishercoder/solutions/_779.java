package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 779. K-th Symbol in Grammar
 * <p>
 * On the first row, we write a 0. Now in every subsequent row,
 * we look at the previous row and replace each occurrence of 0 with 01, and each occurrence of 1 with 10.
 * Given row N and index K, return the K-th indexed symbol in row N. (The values of K are 1-indexed.) (1 indexed).
 * <p>
 * Examples:
 * Input: N = 1, K = 1
 * Output: 0
 * <p>
 * Input: N = 2, K = 1
 * Output: 0
 * <p>
 * Input: N = 2, K = 2
 * Output: 1
 * <p>
 * Input: N = 4, K = 5
 * Output: 1
 * <p>
 * Explanation:
 * row 1: 0
 * row 2: 01
 * row 3: 0110
 * row 4: 01101001
 * <p>
 * Note:
 * <p>
 * N will be an integer in the range [1, 30].
 * K will be an integer in the range [1, 2^(N-1)].
 */

public class _779 {
    public static class Solution1 {
        /**
         * Time: O(2^n)
         * Space: O(2^n)
         * This will result int TLE.
         */
        public int kthGrammar(int N, int K) {
            List<List<Integer>> lists = new ArrayList<>();
            lists.add(Arrays.asList(0));
            for (int i = 1; i <= N; i++) {
                List<Integer> curr = new ArrayList<>();
                List<Integer> prev = lists.get(i - 1);
                for (int j = 0; j < prev.size(); j++) {
                    if (prev.get(j) == 0) {
                        curr.add(0);
                        curr.add(1);
                    } else {
                        curr.add(1);
                        curr.add(0);
                    }
                }
                lists.add(curr);
            }
            return lists.get(N).get(K - 1);
        }
    }

    public static class Solution2 {
        /**
         * Time: O(logn)
         * Space: O(1)
         */
        public int kthGrammar(int N, int K) {
            return Integer.bitCount(K - 1) % 2;
        }

    }

}
