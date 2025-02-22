package practiceproblems.recursion;

import java.util.*;

public class AddOperators {

    /**
     * When we use dfs to do this question, the most tricky part is that how to deal with multiplication. For every
     * addition and subtraction, we're just directly adding or subtracting the new number. However, for multiplication,
     * we should multiply current number and previous number firstly, and then add previous number.
     * So we can use a variable preNum to record every previous number in each recursion step. If current recursive
     * call is trying multiplication, we should use previous calculation value subtract previous number, and then
     * adding multiplication result between previous number and current number.
     * */
    public List<String> addOperators(String num, int target) {
        if (num.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        dfs(result, num, target, new StringBuilder(), 0, 0, 0);
        return result;
    }

    /**
     * @param result: result list to store valid expressions
     * @param num: input num candidates
     * @param target: input target number
     * @param expr: current expression string
     * @param calcVal: current calculation value
     * @param preNum: previous number, in order to multiply current number if we want to put * between preNum and curNum
     * @param pos: current index in the input num array
     * */
    public void dfs(List<String> result, String num, int target, StringBuilder expr, long calcVal, long preNum, int pos) {
        if (pos == num.length()) {
            if (calcVal == target) {
                result.add(expr.toString());
            }
            return;
        }

        int len = expr.length();
        for (int i = pos; i < num.length(); i++) {
            //To avoid cases where we have 1 + 05 or 1 * 05 since 05 won't be a
            //valid operand.
            if (i != pos && num.charAt(pos) == '0') {
                break;
            }
            long curNum = Long.parseLong(num.substring(pos, i + 1));

            if (pos == 0) {
                expr.append(curNum);
                dfs(result, num, target, expr, curNum, curNum, i + 1);
                expr.setLength(len);
            } else {
                expr.append('+').append(curNum);
                dfs(result, num, target, expr, calcVal + curNum, curNum, i + 1);
                expr.setLength(len);

                expr.append('-').append(curNum);
                dfs(result, num, target, expr, calcVal - curNum, -curNum, i + 1);
                expr.setLength(len);

                expr.append('*').append(curNum);
                dfs(result, num, target, expr, calcVal - preNum + preNum * curNum, preNum * curNum, i + 1);
                expr.setLength(len);
            }
        }
    }
}
