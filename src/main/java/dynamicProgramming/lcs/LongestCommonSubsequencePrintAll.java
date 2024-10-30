package dynamicProgramming.lcs;

import java.util.*;

//  The problem is that your DP approach only keeps one possible LCS string at each cell,
//  but for all longest common subsequences, we need to maintain all possible strings
public class LongestCommonSubsequencePrintAll {
    public List<String> all_longest_common_subsequences(String str1, String str2) {
        Set<String>[][] dp = new HashSet[str1.length() + 1][str2.length() + 1];

        // Initialize with empty sets
        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                dp[i][j] = new HashSet<>();
                if (i == 0 || j == 0) dp[i][j].add(""); // base case
            }
        }

        // Fill the dp table
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    // Add current char to all sequences from dp[i-1][j-1]
                    for (String prev : dp[i - 1][j - 1]) {
                        dp[i][j].add(prev + str1.charAt(i - 1));
                    }
                    continue;
                }
                // Take the longer sequences
                if (getLengthOfAnyString(dp[i - 1][j]) > getLengthOfAnyString(dp[i][j - 1])) {
                    dp[i][j].addAll(dp[i - 1][j]);
                } else if (getLengthOfAnyString(dp[i][j - 1]) > getLengthOfAnyString(dp[i - 1][j])) {
                    dp[i][j].addAll(dp[i][j - 1]);
                } else {
                    // If lengths are equal, take both
                    dp[i][j].addAll(dp[i - 1][j]);
                    dp[i][j].addAll(dp[i][j - 1]);
                }

            }
        }
        List<String> result = new ArrayList<>(dp[str1.length()][str2.length()]);
        Collections.sort(result);
        return result;
    }

    private int getLengthOfAnyString(Set<String> set) {
        if (set.isEmpty()) return 0;
        return set.iterator().next().length(); // All strings in a cell have same length
    }

}
