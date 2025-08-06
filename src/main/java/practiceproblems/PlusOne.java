package practiceproblems;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/plus-one/
 */
class PlusOne {

    public static int[] plusOne(int[] digits) {
        int n = digits.length;
        for (int idx = n - 1; idx >= 0; --idx) {
            if (digits[idx] < 9) {
                digits[idx]++;
                return digits;
            }
            digits[idx] = 0;
        }
        int[] newDigits = new int[n + 1];
        newDigits[0] = 1;
        return newDigits;
    }

    public static void main(String[] args) {
        int[] digits = {1, 2, 9};
        int[] endpointUrl = plusOne(digits);
        Arrays.stream(endpointUrl).forEach(System.out::println);
    }
}