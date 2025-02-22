package practiceproblems.recursion;

// https://leetcode.com/problems/string-to-integer-atoi/
public class AtoI {

    public int myAtoi(String s) {
        int index = 0;
        int n = s.length();
        int number = 0;
        if (n == 0) return number;
        int sign = 1;
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }

        if (index < n && (s.charAt(index) == '-' || s.charAt(index) == '+')) {
            if (s.charAt(index) == '-') {
                sign = -1;
            }
            index++;
        }


        return recursion(s, index, number, sign);
    }

    public int recursion(String s, int index, int number, int sign) {
        if (index >= s.length() || !Character.isDigit(s.charAt(index))) {
            return sign * number;
        }

        int val = s.charAt(index) - '0';

        if (number > Integer.MAX_VALUE / 10) {
            return sign == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }

        if (number == Integer.MAX_VALUE / 10 && val > 7) {
            return sign == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }

        number = (number * 10) + val;

        return recursion(s, index + 1, number, sign);

    }
}
