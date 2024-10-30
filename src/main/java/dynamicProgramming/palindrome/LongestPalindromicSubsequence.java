package dynamicProgramming.palindrome;

// https://leetcode.com/problems/longest-palindromic-subsequence/
// https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome

// We need to find the minimum insertions required to make a string palindrome.
// Let us keep the “minimum” criteria aside and think,
// how can we make any given string palindrome by inserting characters?
// consider s="abcaa" to make it plaindrome we need to append the reverse of the string to it
// s = "abcaa" + "aacba" = "abcaaacba" which is palindrome so max number of insertions is len(s)
// to make it minimum we just need to preserve the original length of longest palindromic subsequence
// and the result will be len(s) - len(lps)
// for ex s = "abcaa" lps = "aaa" , we keep the lps
// and we add the rest of the non palindromic characters in between the lps in reversed fashion
// "bc" is non palindromic se we reverse it and add it in between the lps
// s = "abcacba" which is palindrome
public class LongestPalindromicSubsequence {

    public static void main(String args[]) {
        LongestPalindromicSubsequence lps = new LongestPalindromicSubsequence();
        String str = "agbdba";
        int r2 = lps.longestPalindromeSubseq(str);
        System.out.print(r2);
    }

    public int calculate1(char[] str) {
        int[][] T = new int[str.length][str.length];
        for (int i = 0; i < str.length; i++) {
            T[i][i] = 1;
        }
        for (int l = 2; l < str.length; l++) {
            for (int i = 0; i + l < str.length; i++) {
                int j = i + l;
                if (l == 2 && str[i] == str[j]) {
                    T[i][j] = 2;
                } else if (str[i] == str[j]) {
                    T[i][j] = T[i + 1][j - 1] + 2;
                } else {
                    T[i][j] = Math.max(T[i + 1][j], T[i][j - 1]);
                }
            }
        }
        return T[0][str.length - 1];
    }

    public int longestPalindromeSubseq(String s) {
        int[][] dp = new int[s.length()][s.length()];
        int n = s.length();
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 1;
        }


        // len is the length of subsequence being considered
        for (int len = 2; len <= n; len++) {
            // Consider all starting positions i for current length
            for (int i = 0; i < n - len + 1; i++) {
                // "hello"  (n=5, len=3)
                // i < 5-3+1 = 3
                // i can be 0,1,2
                //i=0: can start "hel"
                //i=1: can start "ell"
                //i=2: can start "llo"

                //j = 0+3-1 = 2
                //So we look at positions 0,1,2 ("hel")
                //
                //If i=1 and len=3:
                //j = 1+3-1 = 3
                //So we look at positions 1,2,3 ("ell")

                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 +  dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][s.length() - 1];
    }

    public int longestPalindromeSubseqRecusive(String s) {
        return helper(s, 0, s.length() - 1, new Integer[s.length()][s.length()]);
    }

    private int helper(String s, int i, int j, Integer[][] memo) {
        if (i >= s.length() || j < 0) {
            return 0;
        }

        if (memo[i][j] != null) {
            return memo[i][j];
        }

        if (s.charAt(i) == s.charAt(j)) {
            return 1 + helper(s, i + 1, j - 1, memo);
        }

        return memo[i][j] = Math.max(helper(s, i + 1, j, memo), helper(s, i, j - 1, memo));
    }

}