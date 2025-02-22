package practiceproblems.stack;

class Node {
    int min;
    int data;
    Node next;

    public Node(int data, int min) {
        this.data = data;
        this.min = min;
    }

    public Node(int data, int min, Node next) {
        this.data = data;
        this.min = min;
        this.next = next;
    }
}

public class MinStack {

    Node head;

    public MinStack() {

    }

    public void push(int x) {
        if (head == null) {
            head = new Node(x, x);
        } else {
            head = new Node(x, Math.min(x, head.min), head);
        }
    }

    public void pop() {
        if (head == null) return;
        head = head.next;
    }

    public int top() {
        return head.data;
    }

    public int getMin() {
        return head.min;
    }
}
