package practiceproblems;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/
public class MaxDistinctElementAfterKRemoval {
    // after removing k elements

    /**
     * Create a hash table to store the frequency of each element.
     * Insert frequency of each element in a max heap.
     * Now, perform the following operation k times.
     * Remove an element from the max heap. Decrement its value by 1. After this if element is not equal to 0, then again push the element in the max heap.
     */
    static int maxDistinctNum(int[] arr, int n, int k) {
        // hash map to store
        // frequency of each element
        HashMap<Integer, Integer> map = new HashMap<>();

        // priority_queue 'pq' implemented as
        // max heap
        PriorityQueue<Integer> pq =
                new PriorityQueue<>(Collections.reverseOrder());

        // storing frequency of each element in map
        for (int i = 0; i < n; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }

        // inserting frequency of each element in 'pq'
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            pq.add(entry.getValue());
        }

        while (k > 0) {
            // get the top element of 'pq'
            int temp = pq.poll();

            // decrement the popped element by 1
            temp--;

            // if true, then push the element in 'pq'
            if (temp > 0)
                pq.add(temp);
            k--;
        }

        // Count all those elements that appear
        // once after above operations.
        int res = 0;
        while (!pq.isEmpty()) {
            pq.poll();
            res++;
        }

        return res;
    }


    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : arr) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> minQueue = new PriorityQueue<>(freqMap.values());
        while (!minQueue.isEmpty() && k >= minQueue.peek()) {
            k -= minQueue.poll();
        }
        return minQueue.size();
    }
}