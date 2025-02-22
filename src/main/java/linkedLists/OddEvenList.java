package linkedLists;

// https://leetcode.com/problems/odd-even-linked-list/
public class OddEvenList {

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode odd = head;
        ListNode evenHead = head.next;
        ListNode even = head.next;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}
