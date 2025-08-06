package practiceproblems;

import java.util.Map;
// https://leetcode.com/problems/roman-to-integer/

public class RomanToInteger {
    public static void main(String[] args) {
        new RomanToInteger().romanToInt("LIX");
    }

    public int romanToInt(String roman) {
        // Map to store Roman numeral values
        Map<Character, Integer> values = Map.of(
                'I', 1,
                'V', 5,
                'X', 10,
                'L', 50,
                'C', 100,
                'D', 500,
                'M', 1000
        );

        int total = 0;

        for (int i = 0; i < roman.length(); i++) {
            int current = values.get(roman.charAt(i));

            // If the next value is greater, subtract current value (special cases like IV, IX, etc.)
            //If current (e.g., 'I' = 1) is less than the next numeral (e.g., 'V' = 5),
            // then we subtract the current value (1) from the total.
            //Example: "IV" → 1 < 5 → total = 0 - 1 = -1 → then add 5 → total = 4.
            if (i < roman.length() - 1 && current < values.get(roman.charAt(i + 1))) {
                total -= current;
            } else {
                total += current;
            }
        }

        return total;
    }

    public String intToRoman(int num) {

        String[] keys = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            if (num - values[i] >= 0) {
                while (num - values[i] >= 0) {
                    sb.append(keys[i]);
                    num -= values[i];
                }
            }
        }

        return sb.toString();
    }

}