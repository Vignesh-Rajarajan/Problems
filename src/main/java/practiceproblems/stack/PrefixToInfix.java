package practiceproblems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class PrefixToInfix {

    // Same as PostfixToInfix, just iterate from end to start
    static String preToInfix(String preExp) {
        if(preExp==null || preExp.isEmpty()) return preExp;

        Deque<String> stack = new ArrayDeque<>();
        for(int i=preExp.length()-1;i>=0;i--){
            char ch = preExp.charAt(i);

            if((ch>='A' && ch<='Z') || (ch>='a' && ch<='z') || (ch>='0' && ch<='9')){
                stack.push(ch+"");
            }else{
                String first = stack.pop();
                String second = stack.pop();

                String exp = "("+first+ch+second+")";
                stack.push(exp);
            }
        }
        return stack.peek();
    }
}
