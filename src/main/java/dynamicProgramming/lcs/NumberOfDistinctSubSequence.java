package dynamicProgramming.lcs;

// https://leetcode.com/problems/distinct-subsequences/
// tricky
public class NumberOfDistinctSubSequence {
    public int numDistinct(String s, String t) {
        Integer[][] cache = new Integer[s.length()][t.length()];
        return recursionHelper(s, t, s.length() - 1, t.length() - 1, cache);
    }

    public int recursionHelper(String s, String t, int idx1, int idx2, Integer[][] cache) {
        if (idx2 < 0) {
            return 1;
        }
        if (idx1 < 0) {
            return 0;
        }
        if (cache[idx1][idx2] != null) {
            return cache[idx1][idx2];
        }

        // if both chars are same we skip both, and also we explore by skipping the source index to find the target char elsewhere
        if (s.charAt(idx1) == t.charAt(idx2)) {
            return cache[idx1][idx2] = recursionHelper(s, t, idx1 - 1, idx2 - 1, cache)
                    + recursionHelper(s, t, idx1 - 1, idx2, cache);
        }
        return cache[idx1][idx2] = recursionHelper(s, t, idx1 - 1, idx2, cache);
    }

    /**
     * Copy the recurrence relation into tabulation that's all is the trick
     */
    public int numDistinctTopDown(String s, String t) {

        int[][] dp = new int[s.length() + 1][t.length() + 1];

        // the first row must be filled with 1.
        // That's because the empty string is a subsequence of any string but only 1 time.
        // which is nothing but if (idx2 < 0) in the above recursion
        for (int i = 0; i <= s.length(); i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[s.length()][t.length()];
    }
}
