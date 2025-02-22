package dynamicProgramming.palindrome;

/**
 * revise
 * https://leetcode.com/problems/palindrome-partitioning-ii
 */
public class PalindromePartitioningII {


    public static int minCutPalindromicSubstringVariant(String s) {
        int[] cutsDp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            cutsDp[i] = i;
        }

        for (int mid = 0; mid < s.length(); mid++) {
            findMin(mid, mid, cutsDp, s);
            findMin(mid, mid + 1, cutsDp, s);
        }

        return cutsDp[s.length() - 1];
    }

    public static void findMin(int start, int end, int[] cutsDp, String s) {
        for (int i = start, j = end; i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j); i--, j++) {
            int newCut = i == 0 ? 0 : cutsDp[i - 1] + 1;
            cutsDp[j] = Math.min(cutsDp[j], newCut);
        }
    }

    public static void main(String[] args) {
        minCutPalindromicSubstringVariant("nooradars");
    }

    public int minCut(String s) {
        int[] dp = new int[s.length() + 1];
        for (int i = s.length() - 1; i >= 0; i--) {
            int minCost = Integer.MAX_VALUE;

            for (int j = i; j < s.length(); j++) {
                if (isPalindrome(s, i, j)) {
                    int cost = 1 + dp[j + 1];
                    minCost = Math.min(minCost, cost);
                }
            }

            dp[i] = minCost;
        }

        return dp[0] - 1;
    }

    public int minCutRecursive(String s) {
        Integer[] cache = new Integer[s.length() + 1];
        return recursionHelper(s, 0, cache) - 1;
    }

    int recursionHelper(String s, int idx, Integer[] cache) {
        if (idx >= s.length()) {
            return 0;
        }
        if (cache[idx] != null) {
            return cache[idx];
        }
        int result = Integer.MAX_VALUE;
        for (int i = idx; i < s.length(); i++) {

            if (isPalindrome(s, idx, i)) {
                int cost = 1 + recursionHelper(s, i + 1, cache);
                result = Math.min(result, cost);
            }
        }

        return cache[idx] = result;
    }

    public boolean isPalindrome(String s, int start, int end) {
        if (start > end) return false;

        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) return false;

            start++;
            end--;
        }

        return true;

    }
}
