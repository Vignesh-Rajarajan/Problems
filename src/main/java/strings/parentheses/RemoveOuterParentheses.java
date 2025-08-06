package strings.parentheses;

// https://leetcode.com/problems/remove-outermost-parentheses/
public class RemoveOuterParentheses {

    public String removeOuterParentheses(String s) {
        int depth = 0;              // Tracks how many levels deep we are in parentheses
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            if (current == '(') {
                // Only add '(' if it's not the outermost one
                if (depth > 0) {
                    result.append(current);
                }
                depth++;  // Go one level deeper
            } else {
                depth--;  // Come one level up
                // Only add ')' if it's not the outermost one
                if (depth > 0) {
                    result.append(current);
                }
            }
        }

        //Example with "( ( ) ( ) )":
        //First ( - Outer layer (depth=0) → Don't keep
        //Second ( - Inner layer (depth=1) → Keep
        //First ) - Still inside (depth=1) → Keep
        //Third ( - Inner layer (depth=1) → Keep
        //Second ) - Still inside (depth=1) → Keep
        //Final ) - Outer layer (depth=0) → Don't keep
        return result.toString();
    }
}
