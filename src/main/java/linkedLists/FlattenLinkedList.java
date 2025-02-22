package linkedLists;

class FlattenLinkedList {
    Node flatten(Node root) {
        // Base case
        if (root == null) return null;

        // Sort and merge
        return mergeList(root);
    }

    Node mergeList(Node root) {
        // If no more nodes to process
        if (root == null) return null;

        // Recursively flatten the bottom and next list
        Node bottomFlattened = mergeList(root.bottom);
        Node nextFlattened = mergeList(root.next);

        // Detach bottom and next pointers
        root.bottom = null;
        root.next = null;

        // Merge current node with flattened bottom list
        Node mergedBottom = mergeTwoLists(root, bottomFlattened);

        // Merge the result with flattened next list
        return mergeTwoLists(mergedBottom, nextFlattened);
    }

    Node mergeTwoLists(Node a, Node b) {
        // If one list is empty, return the other
        if (a == null) return b;
        if (b == null) return a;

        Node result;

        // Choose the smaller value as the head
        if (a.data <= b.data) {
            result = a;
            result.bottom = mergeTwoLists(a.bottom, b);
        } else {
            result = b;
            result.bottom = mergeTwoLists(a, b.bottom);
        }

        return result;
    }

    class Node {
        int data;
        Node next;
        Node bottom;

        Node(int x) {
            data = x;
            next = null;
            bottom = null;
        }
    }
}