package practiceproblems.recursion;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/generate-binary-strings-without-adjacent-zeros

//If problem involves fixed size/length → Use state-based completion
//If problem can lead to invalid states → Use explicit termination
//Some problems need both:
public class ValidBinaryStrings {

    // every substring of length 2 contains at least one "1".
    //111 (allowed because 1's can be consecutive)
    //110 (allowed because 0 follows 1)
    //101 (allowed because 0 follows and is followed by 1)
    public List<String> validStrings(int n) {
        List<String> result = new ArrayList<>();
        dfs(result, new StringBuilder(), n);
        return result;
    }

    public void dfs(List<String> result, StringBuilder sb, int n) {
        if (n == sb.length()) {
            result.add(sb.toString());
            return;
        }

        if (sb.length() == 0 || sb.charAt(sb.length() - 1) == '1') {
            sb.append("0");  // We can only append 0 if previous char was 1
            dfs(result, sb, n);
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append("1");  // We can always append 1 regardless of previous char
        dfs(result, sb, n);
        sb.deleteCharAt(sb.length() - 1);

    }

}
