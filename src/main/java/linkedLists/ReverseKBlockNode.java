package linkedLists;

/**
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 * <p>
 * tricky
 */
public class ReverseKBlockNode {

    static ListNode head;
    static int k = 3;

    public static void main(String[] args) {
        head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(6);
        head.next.next.next.next.next.next = new ListNode(7);
        head.next.next.next.next.next.next.next = new ListNode(8);
        head.next.next.next.next.next.next.next.next = new ListNode(9);
        head.next.next.next.next.next.next.next.next.next = new ListNode(10);

        reverseKNodes(head, 2);
        print(head);
    }


    private static void print(ListNode current) {
        ListNode node = current;
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }

    public static ListNode reverseKNodes(ListNode head, int k) {
        if (head == null) return null;
        ListNode root = head;
        int count = 0;
        //1. test weather we have more then k node left, if less then k node left we just return head
        while (count < k) { // && root!=null add this condition to reverse remaining elements
            //base case: head listnode contains less than k nodes,
            // in this case, return the original listnode(aka head)
            if (root == null) return head;
            root = root.next;
            count++;
        }
        // 2.reverse k node at current level
        ListNode prev = reverseKNodes(root, k); //prev node point to the answer of sub-problem
        while (count > 0) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
            count--;

        }

        return prev;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pointer = dummy;
        while (pointer != null) {
            ListNode node = pointer;
            // first check whether there are k nodes to reverse
            for (int i = 0; i < k && node != null; i++) {
                node = node.next;
            }
            if (node == null) break;

            // now we know that we have k nodes, we will start from the first node
            ListNode prev = null, curr = pointer.next, next = null;
            //step1: 0 (pointer) -> 1      2 -> 3 -> 4 -> 5 -> 6 -> 7
            //step2: 0 (pointer) -> 1 <- 2      3 -> 4 -> 5 -> 6 -> 7
            //step3: 0 (pointer) -> 1 <- 2 <- 3      4 -> 5 -> 6 -> 7
            // link from 3 to 4 will be cut (as shown in step3).
            for (int i = 0; i < k; i++) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            // You will figure out that at step3, the 3 is the prev node, 4 is the curr node.
            //	step3: 0 (pointer) -> 1 <- 2 <- 3 (prev)    4 (curr) -> 5 -> 6 -> 7
            //	after first line:   0 (pointer) -> 1 (tail) <- 2 <- 3 (prev)    4 (curr) -> 5 -> 6 -> 7
            //	after second line:  0 (pointer) -> 1 (tail) <- 2 <- 3 (prev)    4 (curr) -> 5 -> 6 -> 7
            //								       |____________________________↑
            //	after third line:
            //								|-----------------------↓
            //						0 (pointer)    1 (tail) <- 2 <- 3 (prev)    4 (curr) -> 5 -> 6 -> 7
            //									   |____________________________↑
            //
            //	after forth line:	0 -> 3 -> 2 -> 1 (pointer) -> 4 -> 5 -> 6 -> 7
            ListNode tail = pointer.next;
            tail.next = curr;
            pointer.next = prev;
            pointer = tail;
        }
        return dummy.next;
    }

}
