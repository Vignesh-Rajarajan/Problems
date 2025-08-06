package strings.parentheses;

public class MinAddToMakeValid {
    public int minAddToMakeValid(String S) {
        if (S == null || S.length() == 0) return 0;

        int open = 0; // counts unclosed '('
        int close = 0; // counts unmatched ')'

        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == '(') {
                open++;
            } else if (c == ')') {
                if (open > 0) {
                    open--; // matches with a previous '('
                } else {
                    close++; // no '(' to match with
                }
            }
        }

        return open + close;
    }
}
