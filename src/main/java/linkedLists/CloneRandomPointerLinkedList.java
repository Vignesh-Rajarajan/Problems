package linkedLists;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/copy-list-with-random-pointer/
 */
class CloneRandomPointerLinkedList {

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Node dummy = new Node(-1);
        dummy.next = head;

        while (head != null) {
            Node newNode = new Node(head.data);
            newNode.next = head.next;
            head.next = newNode;
            head = newNode.next;
        }

        head = dummy.next;

        while (head != null && head.next != null) {
            head.next.random = head.random != null ? head.random.next : null;
            head = head.next.next;
        }

        Node oldHead = dummy.next;
        Node newHead = dummy.next.next;
        Node newTemp = newHead;

        while (oldHead != null) {
            oldHead.next = oldHead.next.next;
            newHead.next = newHead.next == null ? null : newHead.next.next;

            oldHead = oldHead.next;
            newHead = newHead.next;
        }

        return newTemp;
    }

    public Node copyRandomListExtraSpace(Node head) {
        if (head == null) {
            return null;
        }

        final Map<Node, Node> map = new HashMap<>();

        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.data));
            cur = cur.next;
        }

        for (Map.Entry<Node, Node> entry : map.entrySet()) {
            final Node newNode = entry.getValue();
            newNode.next = map.get(entry.getKey().next);
            newNode.random = map.get(entry.getKey().random);
        }

        return map.get(head);
    }

    // Structure of linked list Node
    static class Node {
        int data;
        Node next, random;

        Node(int x) {
            data = x;
            next = random = null;
        }
    }
}
