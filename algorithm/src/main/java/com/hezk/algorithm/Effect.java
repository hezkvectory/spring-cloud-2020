package com.hezk.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Effect {

    public static class Solution {
        public boolean effect(String s) {
            Map<Character, Character> map = new HashMap<>();
            map.put('[', ']');
            map.put('(', ')');
            map.put('{', '}');

            char[] chars = s.toCharArray();
            Deque<Character> deque = new ArrayDeque<>();
            for (char c : chars) {
                if (map.keySet().contains(c)) {
                    deque.addLast(c);
                } else if (map.values().contains(c)) {
                    if (map.get(deque.removeLast()) != c) {
                        return false;
                    }
                }
            }
            return deque.isEmpty() ? true : false;
        }
    }

    public static void main(String[] args) {
        String str = "(())[dd{}";
        System.out.println(new Solution().effect(str));
    }

}
