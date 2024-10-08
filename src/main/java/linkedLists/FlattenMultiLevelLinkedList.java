package linkedLists;

/**
 * https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/discuss/150321/Easy-Understanding-Java-beat-95.7-with-Explanation
 */
class FlattenMultiLevelLinkedList {

    public Node flatten(Node head) {
        if (head == null) {
            return head;
        }
        // Pointer
        Node p = head;
        while (p != null) {
            if (p.child == null) {
                p = p.next;
                continue;
            }
            /* CASE 2: got child, find the tail of the child and link it to p.next */
            Node temp = p.child;

            while (temp.next != null) {
                temp = temp.next;
            }
            // Connect tail with p.next, if it is not null
            temp.next = p.next;
            if (p.next != null) {
                p.next.prev = temp;
            }
            // Connect p with p.child, and remove p.child
            p.next = p.child;
            p.child.prev = p;
            p.child = null;
        }
        return head;
    }

    /**
     * tricky recursion
     *
     */
    public Node flattenRecur(Node head) {
        if (head == null) return head;

        Node root = head;
        while (root != null && root.child == null) {
            root = root.next;
        }
        if (root == null) return head;

        Node transformedList = flattenRecur(root.child);

        Node next = root.next;

        root.next = transformedList;
        transformedList.prev = root;

        while (transformedList.next != null) {
            transformedList = transformedList.next;
        }
        transformedList.next = next;

        if (next != null) next.prev = transformedList;
        root.child = null;

        return head;
    }

    static class Node {
        long data;
        Node next;
        Node prev;
        Node child;

        public Node(long id) {
            this.data = id;
        }
    }
}