package dynamicProgramming.lcs;

// https://leetcode.com/problems/delete-operation-for-two-strings/
// tricky
public class MinDeleteToMakeStringEqual {

    public int minDistanceRecursion(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        Integer[][] cache = new Integer[n1][n2];
        return recursionUtil(word1, word2, word1.length() - 1, word2.length() - 1, cache);
    }

    public int recursionUtil(String word1, String word2, int idx1, int idx2, Integer[][] cache) {
        if (idx1 < 0) {
            return idx2 + 1;
        }
        // If second string is empty, return length of first string
        if (idx2 < 0) {
            return idx1 + 1;
        }

        if (cache[idx1][idx2] != null) {
            return cache[idx1][idx2];
        }

        if (word1.charAt(idx1) == word2.charAt(idx2)) {
            return recursionUtil(word1, word2, idx1 - 1, idx2 - 1, cache);
        }

        return cache[idx1][idx2] = 1 + Math.min(recursionUtil(word1, word2, idx1 - 1, idx2, cache), recursionUtil(word1, word2, idx1, idx2 - 1, cache));
    }

    public int minDistance(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        int[][] dp = new int[n1 + 1][n2 + 1];

        // Initialize first column - transforming word1 into empty string
        for (int i = 0; i <= n1; i++) {
            dp[i][0] = i;
        }
        // Initialize first row - transforming empty string into word2
        for (int j = 0; j <= n2; j++) {
            dp[0][j] = j;
        }

        //DP table would look like:
        //    ""  h   i   t
        //""  0   1   2   3
        //h   1   0   1   2
        //e   2   1   2   3
        //a   3   2   3   4
        //t   4   3   4   3

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n1][n2];

    }
}
