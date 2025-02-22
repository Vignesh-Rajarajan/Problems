package practiceproblems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class PostfixToInfix {
    // Add operands (characters) to stack
    // When an operator is encountered, pop two operands from stack
    // Create a string by concatenating the two operands and the operator between them.
    // string = (operand1 + operator + operand2)
    // And push the resultant string back to stack
    // Top of stack will contain infix expression.
    static String postToInfix(String preExp) {
        if(preExp==null || preExp.isEmpty()) return preExp;

        Deque<String> stack = new ArrayDeque<>();
        for(int i=0;i<preExp.length();i++){
            char ch = preExp.charAt(i);

            if((ch>='A' && ch<='Z') || (ch>='a' && ch<='z') || (ch>='0' && ch<='9')){
                stack.push(ch+"");
            }else{
                String second = stack.pop();
                String first = stack.pop();

                String exp = "("+first+ch+second+")";
                stack.push(exp);
            }
        }
        return stack.peek();
    }
}
