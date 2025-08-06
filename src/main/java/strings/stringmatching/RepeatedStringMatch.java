package strings.stringmatching;

//https://leetcode.com/problems/repeated-string-match/
public class RepeatedStringMatch {
    //The logic is based on the idea that for a string B to be composed from repeated A,
    // we may need to repeat A several times. The maximum number of repetitions needed to check is when:
    //We build a repeated A such that its length ≥ B.length()
    //Then do one more repetition (A appended one more time), and check again
    //This is because the start of B could overlap the end of one repetition and the beginning of another.
    public int repeatedStringMatch(String base, String target) {
        // Start with one repetition
        int repeatCount = 1;

        // Build a string by repeating 'base' until it is at least as long as 'target'
        StringBuilder repeated = new StringBuilder(base);
        while (repeated.length() < target.length()) {
            repeated.append(base);
            repeatCount++;
        }

        // Check if 'target' is a substring of the current repeated string
        if (repeated.indexOf(target) >= 0) {
            return repeatCount;
        }

        // Try one more repetition in case the match wraps around to the next copy
        repeated.append(base);
        if (repeated.indexOf(target) >= 0) {
            return repeatCount + 1;
        }

        // 'target' is not found even after adding one more repetition
        return -1;
    }
}
