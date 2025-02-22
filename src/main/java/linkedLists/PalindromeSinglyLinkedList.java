package linkedLists;


public class PalindromeSinglyLinkedList {

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode s = head;
        ListNode f = head;
        ListNode prev = null;
        while (f != null && f.next != null) {
            prev = s;
            s = s.next;
            f = f.next.next;
        }
        prev.next = null;
        s = reverseList(s);
        ListNode temp = head;
        while (temp != null && s != null) {
            if (temp.val != s.val) return false;
            temp = temp.next;
            s = s.next;
        }
        return true;
    }

}
