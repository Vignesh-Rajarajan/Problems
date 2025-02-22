package linkedLists;

// https://leetcode.com/problems/rotate-list/
public class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        ListNode fake = new ListNode(-1), slow = fake, fast = fake;
        fake.next = head;

        int len = 0;
        while (fast.next != null) {   // fast REACH tail && Count len
            fast = fast.next;
            len++;
        }
        if (len == 0) return null;   // CHECK null

        k %= len;
        for (int i = 0; i < len - k; i++)  // slow REACH before the rotated point
            slow = slow.next;

        fast.next = fake.next;      // CONNECT
        fake.next = slow.next;
        slow.next = null;

        return fake.next;
    }

    public ListNode rotateLeft(ListNode head, int k) {
        // Handle edge cases
        if (head == null || k == 0) return head;
        // Calculate the length of the list
        ListNode temp = head;
        int len = 1;
        while (temp.next != null) {
            temp = temp.next;
            len++;
        }

        k = k % len;
        if (k == 0) return head;
        temp.next = head;

        // Find the new head and break point
        // For left rotation, we move k steps
        temp = head;
        for (int i = 0; i < k - 1; i++) {
            temp = temp.next;
        }
        // Set the new head
        ListNode newHead = temp.next;
        // Break the circular list
        temp.next = null;
        return newHead;
    }
}

