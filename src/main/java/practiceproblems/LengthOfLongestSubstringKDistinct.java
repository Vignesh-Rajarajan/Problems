package practiceproblems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters
 */
public class LengthOfLongestSubstringKDistinct {

    public static final int CHAR_RANGE = 128;

    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || k == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();

        int result = 0;
        int left = 0;
        int right = 0;

        while (left < s.length()) {
            char ch = s.charAt(left);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
            while (map.size() > k) {
                char chright = s.charAt(right);
                map.put(chright, map.get(chright) - 1);
                if (map.get(chright) <= 0) {
                    map.remove(chright);
                }
                right++;
            }
            left++;
            result = Math.max(result, left - right);
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstringKDistinct("aaaaaa", 2));
    }

    public static String findLongestSubstring(String str, int k) {
        // stores the longest substring boundaries
        int end = 0, begin = 0;

        // set to store distinct characters in a window
        Set<Character> window = new HashSet<>();

        // Count array `freq` stores the frequency of characters present in the
        // current window. We can also use a map instead of a count array.
        int[] freq = new int[CHAR_RANGE];

        for (int low = 0, high = 0; high < str.length(); high++) {
            window.add(str.charAt(high));
            freq[str.charAt(high)]++;

            while (window.size() > k) {
                if (--freq[str.charAt(low)] == 0) {
                    window.remove(str.charAt(low));
                }

                low++;
            }

            if (end - begin < high - low) {
                end = high;
                begin = low;
            }
        }

        return str.substring(begin, end + 1);
    }

    public static int totalElements(Integer[] arr) {
        int left=0, right=0;
        Map<Integer,Integer> cache = new HashMap<>();
        int result =0;
        while(right<arr.length){
            cache.put(arr[right],cache.getOrDefault(arr[right],0)+1);
            while(cache.size()>2){
                cache.put(arr[left],cache.getOrDefault(arr[left],0)-1);
                if(cache.get(arr[left])<=0){
                    cache.remove(arr[left]);
                }
                left++;
            }

            result = Math.max(result,right-left+1);
            right++;

        }

        return result;
    }

}