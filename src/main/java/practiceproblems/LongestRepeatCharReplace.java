package practiceproblems;

/**
 * https://leetcode.com/problems/longest-repeating-character-replacement/
 *
 * revise
 */
public class LongestRepeatCharReplace {
    public int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) return 0;

        int[] cache = new int[26];
        int left = 0;
        int right = 0;
        int result = 0;
        int maxOccurred = 0;
        while (right < s.length()) {
            char temp = s.charAt(right);
            ++cache[temp - 'A'];
            maxOccurred = Math.max(maxOccurred, cache[temp - 'A']);

            // end-start+1 = size of the current window
            // maxCount = largest count of a single, unique character in the current window
            // The main equation is: end-start+1-maxCount
            // When end-start+1-maxCount == 0,  then the window is filled with only one character
            // When end-start+1-maxCount > 0, then we have characters in the window that are NOT the character that occurs the most.
            // end-start+1-maxCount is equal to exactly the # of characters that are NOT the character that occurs the most in that window. 
            //Example: For a window "xxxyz", end-start+1-maxCount would equal 2. (maxCount is 3 and there are 2 characters here, "y" and "z" that are not "x" in the window.)
            // We are allowed to have at most k replacements in the window, so when end-start+1-maxCount > k, 
            //then there are more characters in the window than we can replace, and we need to shrink the window.
            // If we have window with "xxxy" and k = 1, that's fine because end-start+1-maxCount = 1, which is not > k. maxLength gets updated to 4.
            // But if we then find a "z" after, like "xxxyz", then we need to shrink the window because now end-start+1-maxCount = 2, and 2 > 1. The window becomes "xxyz".
            while (right - left + 1 - maxOccurred > k) {
                char leftChr = s.charAt(left);
                --cache[leftChr - 'A'];
                left++;
            }
            result = Math.max(result, right - left + 1);
            right++;
        }

        return result;
    }
}