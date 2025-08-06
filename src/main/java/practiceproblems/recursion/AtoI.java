package practiceproblems.recursion;

// https://leetcode.com/problems/string-to-integer-atoi/
public class AtoI {
    public int myAtoi(String s) {
        if (s == null || s.isEmpty()) return 0;

        int index = 0;
        int n = s.length();
        int sign = 1;
        int result = 0;

        // Skip leading whitespace
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }

        // Handle sign
        if (index < n && (s.charAt(index) == '-' || s.charAt(index) == '+')) {
            sign = (s.charAt(index) == '-') ? -1 : 1;
            index++;
        }

        // Process digits
        while (index < n && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';

            // Check for overflow before actually multiplying and adding
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return sign * result;
    }
}
