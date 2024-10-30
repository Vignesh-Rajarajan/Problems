package dynamicProgramming.lcs;

/**
 * https://leetcode.com/problems/shortest-common-supersequence/
 * tricky
 * The idea is very simple. The result string should contain all characters of s1 and s2 discarding the common ones.
 * -> S1+S2-LCS
 * because characters appearing in LCS are coming twice in the result. So count them only once.
 *  O(MN)
 */
public class ShortestCommonSupersequence {
    public String shortestCommonSupersequence(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Fill the dp array to find the length of the longest common subsequence (LCS)
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        //When characters match (str1[i-1] == str2[j-1]):
        //We add the character once (since it's common to both strings)
        //Move diagonally up-left (i-- and j--)

        //When characters don't match:
        //We compare dp[i-1][j] and dp[i][j-1]
        //If dp[i-1][j] > dp[i][j-1]: take character from str1 (move up)
        //Otherwise: take character from str2 (move left)
        //    W O R L D
        //  0 0 0 0 0 0
        //H 0 0 0 0 0 0
        //E 0 0 0 0 0 0
        //L 0 0 0 0 1 1
        //L 0 0 0 0 2 2
        //O 0 0 1 1 2 2
        //1. i=5, j=5: dp[4][4] > dp[5][4], append 'D', move left
        //   Result: "D"
        //
        //2. i=5, j=4: dp[4][4] > dp[5][3], append 'L', move left
        //   Result: "LD"
        //
        //3. i=5, j=3: append 'R', move left
        //   Result: "LDR"
        //
        //4. i=5, j=2: Found matching 'O', append 'O', move diagonally
        //   Result: "LDRO"
        //
        //5. i=5, j=1: append 'W', move left
        //   Result: "LDROW"
        //
        //6. Append remaining characters from str1 (HELLO)
        //   Result: "LDROWH"

        //7. Reverse the final string
        //   Final Result: "WHORLDLO"

        // Construct the shortest common supersequence
        StringBuilder ans = new StringBuilder();
        int i = m, j = n;

        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                // If characters match, add them to the result and move diagonally
                ans.append(str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // If the value in dp[i-1][j] is greater, add str1's character
                ans.append(str1.charAt(i - 1));
                i--;
            } else {
                // Otherwise, add str2's character
                ans.append(str2.charAt(j - 1));
                j--;
            }
        }

        // If any characters remain in str1, append them
        while (i > 0) {
            ans.append(str1.charAt(i - 1));
            i--;
        }

        // If any characters remain in str2, append them
        while (j > 0) {
            ans.append(str2.charAt(j - 1));
            j--;
        }

        // Since we constructed the result backwards, reverse the string
        return ans.reverse().toString();
    }
}
