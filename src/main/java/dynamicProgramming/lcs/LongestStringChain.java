package dynamicProgramming.lcs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/longest-string-chain/
 */
public class LongestStringChain {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length)); // sort by length
        Map<String, Integer> dp = new HashMap<>();

        int maxPath = 1;
        // same idea behind the previous approach but performed iteratively.
        for (String word : words) {
            int currLength = 1;
            StringBuilder sb = new StringBuilder(word);
            for (int i = 0; i < word.length(); i++) {
                sb.deleteCharAt(i);
                String prevWord = sb.toString();
                currLength = Math.max(currLength, dp.getOrDefault(prevWord, 0) + 1);
                sb.insert(i, word.charAt(i));
            }
            dp.put(word, currLength);
            maxPath = Math.max(maxPath, currLength);
        }

        return maxPath;
    }
}
