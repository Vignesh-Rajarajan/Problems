package dynamicProgramming.lcs;

/**
 * General Idea: Credit: https://leetcode.com/problems/wildcard-matching/discuss/370736/Detailed-Intuition-From-Brute-force-to-Bottom-up-DP
 * <p>
 * Case 1:
 * When the first character of P is a lowercase letter different from 'c', return False.
 * <p>
 * Case 2:
 * If the first character of P is 'c' or '?', we move both pointers one step to the right.
 * <p>
 * Case 3:
 * If the first character of P is '*', we have 2 possibilities:
 * <p>
 * - '*' matches 0 character : in this case we move the pointer in P one step, ie will ignore the whole pattern
 * - '*' matches 1 or more characters : in this case we move the pointer in S one step, ie we consider pattern
 * And we continue like this for each two positions taken by the two pointers.
 * <p>
 * - If we reach the end of P but there is still characters from S, simply return .. False !
 * - If we reach the end of S and there is still characters from P, the only case when there is a match is that all the remaining characters in P are '*',
 * in this case these stars will be matched with the empty string.
 **/
public class WildCardMatching {

    public static void main(String args[]) {
        WildCardMatching wcm = new WildCardMatching();
        System.out.println(wcm.isMatch("xbylmz", "x?y***z"));

    }

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        Boolean[][] cache = new Boolean[m][n];
        return recursionHelper(s, p, m - 1, n - 1, cache);
    }

    boolean recursionHelper(String s, String p, int idx1, int idx2, Boolean[][] cache) {
        // reached the end of P check if S is also empty
        if (idx2 < 0) {
            return idx1 < 0;
        }

        // if we reached end of text and pattern is still left.
        // Try to see if p at or after this stage is only * or ** or *** etc.
        if (idx1 < 0) {
            while (idx2 >= 0) {
                if (p.charAt(idx2) != '*')
                    return false;
                idx2--;
            }
            return true;
        }

        if (cache[idx1][idx2] != null) {
            return cache[idx1][idx2];
        }

        if (s.charAt(idx1) == p.charAt(idx2) || p.charAt(idx2) == '?') {
            return cache[idx1][idx2] = recursionHelper(s, p, idx1 - 1, idx2 - 1, cache);
        }

        // 1: When * matches an empty seq, it's work is done. Hence, the next stage to check match for is w/o *.
        // 	  Also, there could be *s in line. So, consuming this *, could exhibit new p with next fresh *.
        // 2: '*' can match seq of chars. Hence, * kept. Further rec stages could use it to match more chars/empty.
        if (p.charAt(idx2) == '*') {
            return cache[idx1][idx2] = recursionHelper(s, p, idx1, idx2 - 1, cache) || recursionHelper(s, p, idx1 - 1, idx2, cache);
        }

        // if we reached here, then the characters are not equal
        return cache[idx1][idx2] = false;
    }

    public boolean isMatchBottomUp(String s, String t) {
        //First, we need to create a 2d dp table dp. The size of this table is (s.size() + 1) * (p.size() + 1).
        // We introduce +1 here to better handle the edge cases where we have an empty string or an empty pattern.
        boolean[][] dp = new boolean[s.length() + 1][t.length() + 1];

        //When both the string and the pattern are empty.
        //Always match. dp[0][0] = true
        dp[0][0] = true;

        //It ensures that patterns starting with '*' can match empty strings,
        // which is essential for wildcard matching
        //It directly corresponds to the base case in the recursive solution where we check
        // if remaining pattern only contains '*' characters when the string is exhausted
        for (int i = 1; i <= t.length(); i++) {
            if (t.charAt(i - 1) == '*')
                dp[0][i] = dp[0][i - 1];
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {

                if (t.charAt(j - 1) == '?' || s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];

                } else if (t.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }
        return dp[s.length()][t.length()];
    }

}