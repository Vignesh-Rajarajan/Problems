package practiceproblems.stack;

import java.util.*;

public class PostfixToPrefix {
    // Same as PostfixToInfix, just iterate from end to start
    // add operands to stack
    // when operator is encountered, pop two operands from stack
    // create a string by concatenating the operator and two operands
    // push the resultant string back to stack
    // top of stack will contain prefix expression
    static String postToPre(String postExp) {
        if(postExp==null || postExp.isEmpty()) return postExp;
        Deque<String> stack = new ArrayDeque<>();
        for(int i=0;i<postExp.length();i++){
            char ch = postExp.charAt(i);
            if((ch>='A' && ch<='Z') || (ch>='a' && ch<='z') || (ch>='0' && ch<='9')){
                stack.push(ch+"");
            }else{
                String second = stack.pop();
                String first = stack.pop();
                String exp = ch+first+second;
                stack.push(exp);
            }
        }

        return stack.peek();
    }
}
