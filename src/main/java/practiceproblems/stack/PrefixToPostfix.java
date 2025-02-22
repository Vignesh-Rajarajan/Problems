package practiceproblems.stack;

import java.util.*;

public class PrefixToPostfix {
    // Same as PostfixToPrefix, just iterate from end to start
    // add operands to stack
    // when operator is encountered, pop two operands from stack
    // create a string by concatenating the two operands and the operator at last.
    // push the resultant string back to stack
    // top of stack will contain postfix expression
    static String preToPost(String preExp) {
        if(preExp==null || preExp.isEmpty()) return preExp;
        Deque<String> stack = new ArrayDeque<>();
        for(int i=preExp.length()-1;i>=0;i--){
            char ch = preExp.charAt(i);
            if((ch>='A' && ch<='Z') || (ch>='a' && ch<='z') || (ch>='0' && ch<='9')){
                stack.push(ch+"");
            }else{
                String first = stack.pop();
                String second = stack.pop();
                String exp = first+second+ch;
                stack.push(exp);
            }
        }
        return stack.peek();
    }
}
