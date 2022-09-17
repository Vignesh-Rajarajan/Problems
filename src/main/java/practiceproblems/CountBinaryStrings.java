package practiceproblems;

/**
 * https://leetcode.com/problems/count-binary-substrings
 *  tricky
 */
public class CountBinaryStrings {

    /**
     * The problem can be solved by observing the examples carefully -
     * 1. 0011
     * In this string, consecutive count of binary characters are [2, 2]. We can form a total of 2 substrings.
     * 2. 00011
     * In this string, consecutive count of binary characters are [3, 2]. Still, we can only form 2 substrings.
     * 3. 000111
     * Here, consecutive count of binary characters are as - [3,3]. Now, we can form 3 substrings.
     * 4. 00011100
     * Consecutive count of binary characters are - [3,3,2]. We can form 3 substrings with the first 2 groups of zeros and ones.
     * Then we can form 2 substrings with the latter 2 groups. So, a total of 5 substrings.
     * 5. 00011100111
     * Consecutive count - [3,3,2,3]. Substrings formed - 3 + 2 + 2 = 7
     * We can observe from the above examples that our final count will only depend on the consecutive counts of binary characters.
     * With each two groups of consecutive characters, the number of substrings that can be formed
     * will be minimum of count among the two groups.
     */
    public int countBinarySubstrings(String s) {

        int i = 1;
        int prevCount = 0;
        int currCount = 1;
        int result = 0;

        while (i < s.length()) {

            if (s.charAt(i - 1) != s.charAt(i)) {
                /**
                 * s[i] != s[i - 1] :Whenever current character is not equal to previous -
                 * We know that we atleast have group of 2 different characters having consecutiveCount >= 1.
                 * The number of substrings that can be formed from these would be equal to minimum of currentConsecutiveCount & prevConsecutiveCount.
                 * So just add that amount to ans. Now prevConsecutiveCount will become equal to
                 * currentConsecutiveCount and reset the currentConsecutiveCount to 1.
                 */
                result += Math.min(prevCount, currCount);
                prevCount = currCount;
                currCount = 1;
            } else {
                //s[i] == s[i - 1] : When current character is equal to previous - just increment the current consecutive count.
                currCount++;
            }
            i++;
        }

        result += Math.min(prevCount, currCount);
        return result;
    }
}
