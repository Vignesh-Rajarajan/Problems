package practiceproblems;

import java.util.PriorityQueue;

//https://leetcode.com/problems/minimum-deviation-in-array/
public class MinimumDeviation {

    /**
     * Here is why your two-heap approach is tricky, and why this specific solution is so clever.
     * The "Ping-Pong" Effect: Without fixing the odd numbers first, you would constantly fluctuate between doubling and halving the same numbers.
     * The Core Problem: Too Many Directions
     * In this problem, you have two operations:
     * If a number is even, you can divide it by 2.
     * If a number is odd, you can multiply it by 2.
     * If you use both heaps, you might find yourself in a loop.
     * For example, if you have the number 3, you might double it to 6.
     * Then, seeing 6 is large, your max-heap logic might divide it back to 3.
     * You'd be stuck in an infinite cycle of transformations.
     * The Solution's Strategy: "One-Way Traffic"
     * To solve this, we need to eliminate one of the directions.
     * Step 1: We make every number as large as it can possibly be.
     * We multiply all odd numbers by 2 (now they are even). Even numbers stay even.
     * Step 2: Now, the only thing we are allowed to do is divide even numbers by 2.
     * The reason we can be sure this works is that:
     * Odd numbers can only be doubled once. (Once you double 3 to 6, it's even. You can't double it again).
     * Even numbers can be halved multiple times until they become odd.
     * By starting at the "max" (doubling all odds), we are at the top of a slide. We just slide down by halving the largest element
     */
    public int minimumDeviation(int[] nums) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        int globalMin = Integer.MAX_VALUE;

        //We put everything into a PriorityQueue (a Max-Heap), which always keeps the biggest number at the top.
        //We also keep track of the minVal because the "deviation" is just Max - Min.
        for (int n : nums) {
            if (n % 2 == 1) {
                n = n * 2;
            }
            globalMin = Math.min(globalMin, n);
            maxHeap.offer(n);
        }

        int res = Integer.MAX_VALUE;
        while (!maxHeap.isEmpty()) {
            //We repeatedly take the biggest number and divide it by 2.
            // This is the only way to potentially decrease the gap (deviation).
            //Why the break? If the largest number in our set is odd, we are stuck.
            // We can't divide an odd number by 2 (per the rules),
            // and we've already established that multiplying it by 2 would just make the gap larger.
            // So, the moment the "king of the hill" is an odd number, we've found the best we can do.
            int maxVal = maxHeap.poll();
            res = Math.min(res, maxVal - globalMin);

            if (maxVal % 2 == 1) break;
            int nextVal = maxVal / 2;
            maxHeap.offer(nextVal);
            //When using a max-heap, you only have direct access to the maximum element.
            // The deviation requires knowing both the max and min values.
            // A common error is trying to find the minimum by scanning the heap, which defeats the purpose of the data structure.
            // Track the minimum separately and update it when pushing new values.
            globalMin = Math.min(globalMin, nextVal);
        }

        return res;
    }
}
