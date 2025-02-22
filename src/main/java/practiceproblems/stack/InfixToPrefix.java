package practiceproblems.stack;

public class InfixToPrefix {
    public static void main(String[] args) {
        System.out.println(infixToPrefix("(a-b/c)*(a/k-l)"));
    }
    //Reverse the Input String: The input string is reversed to process it from right to left.
    //
    //Swap Parentheses: ( and ) are swapped because the order of parentheses is reversed.
    //
    //Convert to Postfix: The reversed string is converted to postfix using the same logic as before.
    //
    //Reverse the Result: The postfix result is reversed to get the prefix expression.
    public static String infixToPrefix(String s) {
        if (s == null || s.isEmpty()) return s;

        String  infix = new StringBuilder(s).reverse().toString();
        char[] chars = infix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                chars[i] = ')';
            } else if (chars[i] == ')') {
                chars[i] = '(';
            }
        }
        String postfix = InfixToPostFix.infixToPostfix(new String(chars));
        return new StringBuilder(postfix).reverse().toString();
    }
}
