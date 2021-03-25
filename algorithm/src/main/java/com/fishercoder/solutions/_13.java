package com.fishercoder.solutions;

import java.util.HashMap;
import java.util.Map;

/**
 * 13. Roman to Integer
 * <p>
 * Given a roman numeral, convert it to an integer.
 * Input is guaranteed to be within the range from 1 to 3999.
 */

public class _13 {

    public static class Solution1 {
        public int romanToInt(String s) {
            Map<Character, Integer> map = new HashMap();
            map.put('I', 1);
            map.put('V', 5);
            map.put('X', 10);
            map.put('L', 50);
            map.put('C', 100);
            map.put('D', 500);
            map.put('M', 1000);

            int result = 0;
            for (int i = 0; i < s.length(); i++) {
                if (i > 0 && map.get(s.charAt(i)) > map.get(s.charAt(i - 1))) {
                    result += map.get(s.charAt(i)) - 2 * map.get(s.charAt(i - 1));
                } else {
                    result += map.get(s.charAt(i));
                }
            }
            return result;
        }
    }


    public static class Solution {
        public int romanToInt(String str) {
            Map<String, Integer> map = new HashMap<>();
            map.put("I", 1);
            map.put("IV", 4);
            map.put("V", 5);
            map.put("IX", 9);
            map.put("X", 10);
            map.put("XL", 40);
            map.put("L", 50);
            map.put("XC", 90);
            map.put("C", 100);
            map.put("CD", 400);
            map.put("D", 500);
            map.put("CM", 900);
            map.put("M", 1000);

            int result = 0;
            for (int i = 0; i < str.length(); ) {
                if (i + 1 < str.length() && map.containsKey(str.substring(i, i + 2))) {
                    result += map.get(str.substring(i, i + 2));
                    i += 2;
                } else {
                    result += map.get(str.substring(i, i + 1));
                    i++;
                }
            }
            return result;
        }
    }
}
