package strings.stringProblems;

//https://leetcode.com/problems/shortest-palindrome/
public class ShortestPalindrome {
    //https://youtu.be/niOT-FK1RH8
    //
    public String shortestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        long prefix = 0;
        long suffix = 0;
        long base = 29;
        long power = 1;
        // Use a large prime to minimize collisions
        long mod = 1_000_000_007;
        int lastIndex = 0;

        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a' + 1;

            // Rolling hash from Left -> Right
            prefix = (prefix * base + c) % mod;

            // Rolling hash from Right -> Left (simulated)
            // This effectively calculates the hash of the reverse of s[0...i]
            suffix = (suffix + c * power) % mod;

            power = (power * base) % mod;

            // If the forward hash equals the reverse hash,
            // the substring s[0...i] is a palindrome.
            if (prefix == suffix) {
                lastIndex = i;
            }
        }

        // 1. Get the part of the string strictly AFTER the longest palindromic prefix
        String nonPalindromeSuffix = s.substring(lastIndex + 1);

        // 2. Reverse that part
        String reversedSuffix = new StringBuilder(nonPalindromeSuffix).reverse().toString();

        // 3. Prepend it to the original string
        return reversedSuffix + s;
    }
}
