package practiceproblems.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

// https://leetcode.com/problems/parsing-a-boolean-expression/
public class ParseBooleanExpression {
    public boolean parseBoolExpr(String expression) {
        Deque<Character> stk = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if (c == ')') {
                Set<Character> seen = new HashSet<>();
                while (stk.peek() != '(')
                    seen.add(stk.pop());
                stk.pop();// pop out '('.
                char operator = stk.pop(); // get operator for current expression.
                if (operator == '&') {
                    stk.push(seen.contains('f') ? 'f' : 't'); // if there is any 'f', & expression results to 'f'
                } else if (operator == '|') {
                    stk.push(seen.contains('t') ? 't' : 'f'); // if there is any 't', | expression results to 't'
                } else { // ! expression.
                    stk.push(seen.contains('t') ? 'f' : 't'); // Logical NOT flips the expression.
                }
            } else if (c != ',') {
                stk.push(c);
            }
        }
        return stk.pop() == 't';
    }
}
