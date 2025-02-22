package linkedLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class DLLFindSumPairs {
    public static ArrayList<ArrayList<Integer>> findPairsWithGivenSum(int target, Node head) {
        Node ptr1 = head;
        Node ptr2 = head;

        while (ptr2.next != null) {
            ptr2 = ptr2.next;
        }

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();


        //ptr1 != ptr2: Prevents continuing when pointers meet or cross
        //ptr2.next != ptr1: Handles cases where pointers are adjacent
        // 1 <-> 2 <-> 3 <-> 4 <-> 5 <-> 6
        // If target is 7
        //ptr1 at 2 (value 2)
        //ptr2 at 5 (value 5)
        //Sum = 7, but algorithm shouldn't stop here
        // ptr1 at 3 (value 3)
        // ptr2 at 4 (value 4)
        //Sum = 7, but algorithm needs to stop here
        // so ptr2.next != ptr1 stops the algorithm
        while (ptr1 != ptr2 && ptr2.next != ptr1) {
            int sum = ptr1.data + ptr2.data;

            if (sum == target) {
                result.add(new ArrayList<>(Arrays.asList(ptr1.data, ptr2.data)));
                ptr1 = ptr1.next;
                ptr2 = ptr2.prev;
            } else if (sum < target) {
                ptr1 = ptr1.next;
            } else {
                ptr2 = ptr2.prev;
            }

        }

        return result;

    }

    public Node removeDuplicates(Node head) {
        HashSet<Integer> hashSet = new HashSet<>();
        Node curr = head;

        while (curr != null) {
            if (hashSet.contains(curr.data)) {
                if (curr.prev != null)
                    curr.prev.next = curr.next;

                if (curr.next != null)
                    curr.next.prev = curr.prev;
                curr = curr.next;
            } else {
                hashSet.add(curr.data);
                curr = curr.next;
            }
        }
        return head;

    }
}
