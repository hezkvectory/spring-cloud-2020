package com.fishercoder.solutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 30. Substring with Concatenation of All Words
 * <p>
 * You are given a string, s, and a list of words, words, that are all of the same length.
 * Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.
 * <p>
 * For example, given:
 * s: "barfoothefoobarman"
 * words: ["foo", "bar"]
 * <p>
 * You should return the indices: [0,9].
 * (order does not matter).
 */
public class _30 {

    public static class Solution1 {

        public List<Integer> findSubstring(String s, String[] words) {
            Map<String, Integer> map = new HashMap<>();
            for (String word : words) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }

            List<Integer> result = new ArrayList<>();
            int startIndex = 0;
            int wordLen = words.length;
            for (int i = 0; i < s.length(); i++) {
                startIndex = i;
                Map<String, Integer> clone = new HashMap<>(map);
                int matchedWord = 0;
                for (int j = i + wordLen; j < s.length(); j += wordLen) {
                    String word = s.substring(i, j);
                    if (clone.containsKey(word)) {
                        clone.put(word, clone.get(word) - 1);
//                        i = j;
                        matchedWord++;
                    }
                    if (matchedWord == wordLen) {
                        boolean all = true;
                        for (String key : clone.keySet()) {
                            if (clone.get(key) != 0) {
                                all = false;
                                break;
                            }
                        }
                        if (all) {
                            result.add(startIndex);
                        }
                        matchedWord = 0;
                    }
                }
            }
            return result;
        }
    }


    public static class Solution2 {
        public List<Integer> findSubstring(String str, String[] arr) {
            List<Integer> result = new ArrayList<>();
            if (str == null || str.length() == 0 || arr.length == 0) {
                return result;
            }
            int wordLen = arr[0].length();
            int arrLen = arr.length;
            Map<String, Integer> count = new HashMap<>();
            for (String temp : arr) {
                count.put(temp, count.getOrDefault(temp, 0) + 1);
            }

            for (int i = 0; i < str.length() - wordLen * arrLen + 1; i++) {
                String substr = str.substring(i, i + wordLen * arrLen);
                Map<String, Integer> clone = new HashMap<>();
                for (int j = 0; j < arrLen * wordLen; j += wordLen) {
                    String temp = substr.substring(j, j + wordLen);
                    if (!count.containsKey(temp)) {
                        break;
                    }
                    clone.put(temp, clone.getOrDefault(temp, 0) + 1);
                    if (clone.get(temp) > count.get(temp)) {
                        break;
                    }
                }
                if (clone.equals(count)) {
                    result.add(i);
                }
            }
            return result;
        }
    }


    public static void main(String[] args) {
        String str = "barfoothefoobarman";
        String[] arr = {"foo", "bar"};
        Solution2 solution = new Solution2();
        System.out.println(solution.findSubstring(str, arr));
    }

}
