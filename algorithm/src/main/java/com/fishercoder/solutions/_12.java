package com.fishercoder.solutions;

/**
 * 12. Integer to Roman
 * <p>
 * Given an integer, convert it to a roman numeral.
 * Input is guaranteed to be within the range from 1 to 3999.
 */
public class _12 {

    public static class Solution1 {
        public String intToRoman(int num) {
            String[] M = new String[]{"", "M", "MM", "MMM"};
            String[] C = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
            String[] X = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
            String[] I = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
            return M[num / 1000] + C[(num % 1000) / 100] + X[(num % 100) / 10] + I[num % 10];
        }
    }

    public static class Solution {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        public String intToRoman(int num) {
            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < values.length && num > 0; i++) {
                while (values[i] < num) {
                    num -= values[i];
                    buffer.append(symbols[i]);
                }
            }
            return buffer.toString();
        }
    }

}
