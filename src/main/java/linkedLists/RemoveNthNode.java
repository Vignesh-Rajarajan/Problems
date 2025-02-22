package linkedLists;

// https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/
// https://leetcode.com/problems/remove-nth-node-from-end-of-list/
public class RemoveNthNode {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        //start is a just a dummy node ,appended to the front of LL just to make the deletion process uniform ,
        // otherwise you may have to handle the case where n=length_of(linked_list) separately
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        ListNode fast = newHead;
        ListNode slow = newHead;

        for (int i = 1; i <= n + 1; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return newHead.next;
    }

    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return null; // Handle edge cases of empty list or single node
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = slow.next;
        return head;
    }
}
