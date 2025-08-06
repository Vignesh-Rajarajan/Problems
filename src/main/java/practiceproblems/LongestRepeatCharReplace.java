package practiceproblems;

/**
 * https://leetcode.com/problems/longest-repeating-character-replacement/
 * <p>
 * revise
 */
public class LongestRepeatCharReplace {

    public int characterReplacementBruteForce(String s, int k){
        int result =0;
        for(int i=0; i<s.length();i++){
            int[] cache = new int[26];
            int maxCharCount = 0 ;
            for (int j=i;j<s.length();j++){
                char currentChar = s.charAt(j);
                cache[currentChar-'A']++;
                 maxCharCount = Math.max(maxCharCount, cache[currentChar-'A']);
                if(j-i+1-maxCharCount<=k) {
                    result = Math.max(result, j - i + 1);
                }
            }
        }
        return result;
    }

    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int mostFreqLetter = 0;
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            freq[currentChar - 'A']++;
            mostFreqLetter = Math.max(mostFreqLetter, freq[currentChar - 'A']);
            int lettersToChange = (right - left + 1) - mostFreqLetter;

            if (lettersToChange > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
                // Recalculate mostFreqLetter after adjusting the window
                mostFreqLetter = 0;
                for (int i = 0; i < 26; i++) {
                    mostFreqLetter = Math.max(mostFreqLetter, freq[i]);
                }
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}