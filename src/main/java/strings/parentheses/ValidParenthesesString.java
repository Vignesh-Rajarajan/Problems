package strings.parentheses;

/**
 * tricky braces
 * https://leetcode.com/problems/valid-parenthesis-string/discuss/543521/Java-Count-Open-Parenthesis-O(n)-time-O(1)-space-Picture-Explain
 */
public class ValidParenthesesString {
    //A single pass treating * as ( or ) isn't enough. The two passes ensure:
    //We don't have too many ) at any point (left-to-right)
    //We don't have too many ( at any point (right-to-left)
    //First Pass (Left to Right)
    //Treat all * as (
    //Track balance (increment for ( or *, decrement for ))
    //If balance ever goes negative, it's invalid
    //Second Pass (Right to Left)
    //Treat all * as )
    //Track balance (increment for ) or *, decrement for ()
    //If balance ever goes negative, it's invalid
    //If both passes succeed, the string is valid.
    public boolean checkValidStringAnother(String s) {
        if (s.isEmpty()) return true;

        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')') balance--;
            else balance++;

            if (balance < 0) return false;
        }

        if (balance == 0) return true;
        balance = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') balance--;
            else balance++;
            if (balance < 0) return false;

        }
        //After both passes succeed (didn't return false early),
        // we can be certain the string is valid regardless of the final balance value
        // since this * characters give us flexibility to adjust the balance as needed in positive way
        // What matters is that both passes never hit a negative balance (which would make it impossible to balance)
        //Consider s = "(*)":
        //First Pass (L→R, * as ():
        //'(': balance = 1
        //'*': balance = 2
        //')': balance = 1 → Final balance = 1 (not 0)
        //Second Pass (R→L, * as )):
        //')': balance = 1
        //'*': balance = 2
        //'(': balance = 1 → Final balance = 1 (not 0)
        //Yet this string is valid (we could treat * as empty). Both passes succeeded (never went negative), so we return true despite non-zero balances.
        return true;
    }
}
