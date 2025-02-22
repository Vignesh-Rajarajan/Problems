package practiceproblems.stack;

import java.util.*;

public class InfixToPostFix {
    public static String infixToPostfix(String s) {
        if(s==null || s.isEmpty()) return s;

        Map<Character,Integer> priorityMap = new HashMap<>();
        priorityMap.put('^',3);
        priorityMap.put('*',2);
        priorityMap.put('/',2);
        priorityMap.put('+',1);
        priorityMap.put('-',1);
        priorityMap.put('(',-1);
        priorityMap.put(')',-1);

        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();i++){
            char ch= s.charAt(i);

            if((ch>='A' && ch<='Z') || (ch>='a' && ch<='z') || (ch>='0' && ch<='9')){
                sb.append(ch);
            }else if(ch == '('){
                stack.push(ch);
            }else if(ch==')'){
                while(!stack.isEmpty() && stack.peek()!='('){
                    sb.append(stack.pop());
                }
                stack.pop();
            }else{
                while (!stack.isEmpty() && priorityMap.get(stack.peek()) >= priorityMap.get(ch)) {
                    sb.append(stack.pop());
                }
                stack.push(ch);
            }

        }

        while(!stack.isEmpty()){
            sb.append(stack.pop());
        }

        return sb.toString();


    }
}
