package practiceproblems.recursion;

import java.util.Stack;

public class SortStack {
    public Stack<Integer> sort(Stack<Integer> s) {
        Stack<Integer> tempStack = new Stack<>();
        while (!s.isEmpty()) {
            int currentElem = s.pop();
            while (!tempStack.isEmpty() && tempStack.peek() > currentElem) {
                s.push(tempStack.pop());
            }
            tempStack.push(currentElem);
        }
        return tempStack;
    }

    // example stack: [34, 3, 31]
    // First Iteration (Removing 31)
    //Remove 31 from the stack
    //Recursively sort [34, 3]
    //Remove 3 from the stack
    //Recursively sort [34]
    //Stack is now [34]
    public Stack<Integer> sortRecursion(Stack<Integer> s) {
        if (!s.isEmpty()) {
            int temp = s.pop();
            sortRecursion(s);
            rec(s, temp);

        }
        return s;
    }

    private void rec(Stack<Integer> stack, int n) {
        if (stack.isEmpty() || stack.peek() <= n) {
            stack.push(n);
        } else {
            // If top element is > current element
            int temp = stack.pop();     // Remove top element
            rec(stack, n);              // Recursively find right position
            stack.push(temp);           // Put back the removed element
        }
    }
}
