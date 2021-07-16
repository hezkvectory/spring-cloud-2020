package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * 784. Letter Case Permutation
 * <p>
 * Given a string S, we can transform every letter individually to be lowercase or uppercase to create another string.
 * Return a list of all possible strings we could create.
 * <p>
 * Examples:
 * Input: S = "a1b2"
 * Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
 * <p>
 * Input: S = "3z4"
 * Output: ["3z4", "3Z4"]
 * <p>
 * Input: S = "12345"
 * Output: ["12345"]
 * <p>
 * Note:
 * <p>
 * S will be a string with length at most 12.
 * S will consist only of letters or digits.
 */

public class _784 {

    /**
     * 回溯算法
     */
    public static class Solution1 {

        public static List<String> letterCasePermutation(String S) {
            List<String> res = new ArrayList<>();
            char[] charArray = S.toCharArray();
            dfs(charArray, 0, res);
            return res;
        }

        private static void dfs(char[] charArray, int index, List<String> res) {
            if (index == charArray.length) {
                res.add(new String(charArray));
                return;
            }

            dfs(charArray, index + 1, res);
            if (Character.isLetter(charArray[index])) {
                charArray[index] ^= 1 << 5;
                dfs(charArray, index + 1, res);
            }
        }
    }

}
