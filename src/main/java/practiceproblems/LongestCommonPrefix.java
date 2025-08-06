package practiceproblems;

import java.util.Arrays;

//https://leetcode.com/problems/longest-common-prefix/
public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            String curr = strs[i];
            int minLen = Math.min(prefix.length(), curr.length());
            int j = 0;
            while (j < minLen && prefix.charAt(j) == curr.charAt(j)) {
                j++;
            }

            prefix = prefix.substring(0, j);
        }

        return prefix;
    }

    //When we sort the array ["flower","flow","flight"] lexicographically (dictionary order), it becomes:
    //["flight", "flow", "flower"]
    public String longestCommonPrefixSort(String[] strs) {
        if (strs.length == 1) {
            return strs[0];
        }
        Arrays.sort(strs);
        int n = Math.min(strs[0].length(), strs[strs.length - 1].length());
        for (int i = 0; i < n; i++) {
            if (strs[0].charAt(i) != strs[strs.length - 1].charAt(i)) {
                return strs[0].substring(0, i);
            }
        }

        return strs[0];
    }
}
