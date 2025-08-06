package practiceproblems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/remove-k-digits
 */

public class RemoveKDigits {
    //Core Idea: Monotonically Increasing Stack
    // Notice that removing a larger digit that comes before a
    // smaller digit seems to reduce the number most effectively.
    //  num = "4321", k = 2 the smallest is "21."
    //  How did we get there? "By removing 4 and 3."
    // 14232191
    public static String removeKdigits(String num, int k) {
        if (k == num.length()) return "0"; // Edge case: remove all digits

        Deque<Character> stack = new ArrayDeque<>();

        for (char digit : num.toCharArray()) {
            while (!stack.isEmpty() && k > 0 && stack.peek() > digit) {
                stack.pop();
                k--;
            }
            stack.push(digit);
        }

        // Remove extra digits if k is still greater than 0
        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }

        // Build the final number string
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        result.reverse();

        // Remove leading zeros
        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }

        return result.length() == 0 ? "0" : result.toString();
    }

    public static void main(String[] args) {
        System.out.println(removeKdigits("14232191", 3));
    }
}