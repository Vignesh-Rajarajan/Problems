package dynamicProgramming;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// https://leetcode.com/problems/word-break/
class WordBreak {

/**
|T| | | |T| | | |T|
 0 1 2 3 4 5 6 7 8
*/
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null) {
            return false;
        }
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        Set<String> set = new HashSet<>(wordDict);

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                dp[i] = dp[j] && set.contains(s.substring(j, i));
                if (dp[i]) {
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    public boolean wordBreakRec(String s, List<String> wordDict) {
        // create the memoization array to save results and avoid repeat computations
        Boolean[] canBreak = new Boolean[s.length()];

        // convert the list into set for faster lookup
        Set<String> wordSet = new HashSet<>(wordDict);

        // recursion with memoization
        return helper(s, 0, wordSet, canBreak);
    }

    private boolean helper(String s, int startIdx, Set<String> wordSet, Boolean[] canBreak) {
        // in case we've reached the end of string, return true
        if (startIdx == s.length()) return true;
        // else if we've already computed on current substring before
        if (canBreak[startIdx] != null) return canBreak[startIdx]; // auto-unboxing

        boolean res = false;
        // iterate through all indices after startIdx, explore every possible word
        for (int i = startIdx + 1; i <= s.length(); i++) {
            // We need to check substrings up to and including the last character
            //The substring() method's endIndex is exclusive,
            // so to include the last character we need to go up to length
            String currWord = s.substring(startIdx, i);
            // skip if this is not a word in the input dictionary
            // recursively call upon the rest of string
            if (wordSet.contains(currWord) && helper(s, i, wordSet, canBreak)) {
                res = true;
                break; // we don't need to check other words from wordDict for that position
            }
        }
        // add result to memo and return the result
        canBreak[startIdx] = res;
        return res;
    }
}