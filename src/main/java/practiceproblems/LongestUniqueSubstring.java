package practiceproblems;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 */

public class LongestUniqueSubstring {

    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int begin = 0;
        int end = 0;
        int counter = 0;
        int result = 0;

        while (end < s.length()) {
            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1);
            if (map.get(c) > 1) {
                counter++;
            }

            while (counter > 0) {
                char charTemp = s.charAt(begin);
                if (map.get(charTemp) > 1) {
                    counter--;
                }
                map.put(charTemp, map.get(charTemp) - 1);
                begin++;
            }
            result = Math.max(result, end - begin + 1);
            end++;
        }
        return result;
    }

    // tricky
    public static int lengthOfLongestSubstringOpt(String s) {
        int res = 0, n = s.length();
        //it's an array that acts as a character position map.
        int[] arr = new int[256];
        int startIndex = 0;
        for (int curr = 0; curr < n; curr++) {
            //This is where we check for duplicates.
            //If this is greater than 0, it means we've seen s.charAt(curr) before.
            //The value will be the index +1 where the previous occurrence was seen
            //If we have already seen a character we will update the startindex
            //to the next index of that character that was previously seen
            startIndex = Math.max(startIndex, arr[s.charAt(curr)]);

            res = Math.max(res, curr - startIndex + 1);
            // store curr+1=> next index, so that we can start from here
            //"If I see this character again,
            // start checking for uniqueness after this index."
            arr[s.charAt(curr)] = curr + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstringOpt("pwwkew"));
    }
}