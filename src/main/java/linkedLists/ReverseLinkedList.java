package linkedLists;

public class ReverseLinkedList {

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        new ReverseLinkedList().reverseList(node);
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        // for 1 -> 2 -> 3 -> 4 -> null
        // reverseList(4): The function returns 4
        // 3.next.next = 3 (i.e., 4.next = 3) and 3.next = null.
        // We return the new head, which is 4.
        // reverseList(3): The function returns 4.
        //We update the pointers: 2.next.next = 2 (i.e., 3.next = 2) and 2.next = null.
        //We return the new head, which is 4
        ListNode temp = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return temp;

    }

    public ListNode reverseList1(ListNode head) {
        if (head == null) return null;
        ListNode root = head;
        ListNode prev = null;
        while (root != null) {
            ListNode temp = root.next;
            root.next = prev;
            prev = root;
            root = temp;
        }
        return prev;

    }

    public DLLNode reverseDLL(DLLNode head) {
        DLLNode root = head;
        DLLNode prev = null;

        while (root != null) {
            DLLNode next = root.next; // We store the next node in next
            root.next = prev; // We update the next and prev pointers of the current node to reverse the links
            root.prev = next;
            prev = root;
            root = next;
        }

        return prev;
    }
}

class DLLNode {
    int val;
    DLLNode prev;
    DLLNode next;

    public DLLNode(int key, int val) {
        this.val = val;
    }
}
