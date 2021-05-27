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

    public static class Solution {
        public List<Integer> findSubstring(String s, String[] words) {
            List<Integer> ans = new ArrayList<>();
            if (words.length == 0) {
                return ans;
            }

            int n = s.length(), m = words.length, w = words[0].length();

            // 统计 words 中「每个目标单词」的出现次数
            Map<String, Integer> map = new HashMap<>();
            for (String word : words) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }

            for (int i = 0; i < w; i++) {
                // 构建一个当前子串对应 map，统计当前子串中「每个目标单词」的出现次数
                Map<String, Integer> curMap = new HashMap<>();
                // 滑动窗口的大小固定是 m * w
                // 每次将下一个单词添加进 cur，上一个单词移出 cur
                for (int j = i; j + w <= n; j += w) {
                    String cur = s.substring(j, j + w);
                    if (j >= i + (m * w)) {
                        int idx = j - m * w;
                        String prev = s.substring(idx, idx + w);
                        if (curMap.get(prev) == 1) {
                            curMap.remove(prev);
                        } else {
                            curMap.put(prev, curMap.get(prev) - 1);
                        }
                    }
                    curMap.put(cur, curMap.getOrDefault(cur, 0) + 1);
                    // 如果当前子串对应 map 和 words 中对应的 map 相同，说明当前子串包含了「所有的目标单词」，将起始下标假如结果集
                    if (map.containsKey(cur) && curMap.get(cur).equals(map.get(cur)) && cmp(map, curMap)) {
                        ans.add(j - (m - 1) * w);
                    }
                }
            }
            return ans;
        }

        // 比较两个 map 是否相同
        boolean cmp(Map<String, Integer> m1, Map<String, Integer> m2) {
            if (m1.size() != m2.size()){
                return false;
            }
            for (String k1 : m1.keySet()) {
                if (!m2.containsKey(k1) || !m1.get(k1).equals(m2.get(k1))) {
                    return false;
                }
            }
            for (String k2 : m2.keySet()) {
                if (!m1.containsKey(k2) || !m1.get(k2).equals(m2.get(k2))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        String str = "barfoothefoobarman";
        String[] arr = {"foo", "bar"};
        Solution solution = new Solution();
        System.out.println(solution.findSubstring(str, arr));
    }

}
