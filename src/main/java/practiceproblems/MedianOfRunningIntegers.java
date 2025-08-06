package practiceproblems;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/find-median-from-data-stream/
 */
public class MedianOfRunningIntegers {

    PriorityQueue<Integer> minQueue; // holds the larger half
    PriorityQueue<Integer> maxQueue; // holds the smaller half

    public MedianOfRunningIntegers() {
        minQueue = new PriorityQueue<>(); // min heap
        maxQueue = new PriorityQueue<>((a, b) -> b - a); // max heap
    }

    //Since maxQueue holds the smaller half, we tentatively place the new number here.
    //Why? Because the new number might belong in the smaller half, or it might need to move to the larger half. We'll fix this in the next step.
    //Move the largest from maxQueue to minQueue:
    //After adding to maxQueue, we move its largest element (the new number or an existing one) to minQueue.
    //Why? This ensures that the ordering invariant is maintained: everything in maxQueue ≤ everything in minQueue.
    //Rebalance the sizes:
    //If minQueue has more elements than maxQueue, move the smallest element from minQueue back to maxQueue.
    //Why? This ensures the balancing invariant: maxQueue is always equal or one larger than minQueue.

    //Adding 1, 2, 3:
    //Add 1:
    //maxQueue: [1]
    //minQueue: []
    //After rebalancing: maxQueue: [1], minQueue: [].
    //Add 2:
    //maxQueue: [2, 1] (after adding 2), then [1] (after polling 2)
    //minQueue: [2] (after adding the polled 2)
    //Now maxQueue.size() (1) < minQueue.size() (1) is false, so no rebalancing.
    //Final state:
    //maxQueue: [1]
    //minQueue: [2]
    //Add 3:
    //maxQueue: [3, 1] (after adding 3), then [1] (after polling 3)
    //minQueue: [2, 3] (after adding the polled 3)
    //Now maxQueue.size() (1) < minQueue.size() (2), so move 2 back to maxQueue:
    //maxQueue: [2, 1]
    //minQueue: [3]
    //Final state:
    //maxQueue: [2, 1]
    //minQueue: [3]

    public void addNum(int num) {
        maxQueue.offer(num);
        minQueue.offer(maxQueue.poll());

        if (maxQueue.size() < minQueue.size()) {
            maxQueue.offer(minQueue.poll());
        }
    }

    public double findMedian() {
        if (maxQueue.size() > minQueue.size()) {
            return maxQueue.peek();
        } else {
            return (maxQueue.peek() + minQueue.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        MedianOfRunningIntegers median = new MedianOfRunningIntegers();
        int []A = {5, 15, 1, 3, 2, 8, 7, 9, 10, 6, 11, 4};
        for (int num : A) {
            median.addNum(num);
            System.out.println(median.findMedian());
        }
    }

}
