package practiceproblems;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 */
public class SlidingWindow {

    public static void main(String[] args) {
        int arr[] = {8, 5, 10, 7, 9, 4, 15, 12, 90, 13};
        int k = 3;
        maxSlidingWindow(arr, k);
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {

        List<Integer> result = new ArrayList<>();

        Deque<Integer> queue = new ArrayDeque<>();
    // order to follow
    // 1. Remove the elements which are out of this window
    // 2. Remove the elements which are smaller than the current element
    // 3. Add the current element
    // 4. Add the maximum element to the result
        for (int i = 0; i < nums.length; i++) {
            // This step ensures that the queue only contains indices of elements
            // that are within the current window of size k.
            while (!queue.isEmpty() && queue.peekFirst() < i - k + 1) {
                queue.removeFirst();
            }
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }

            queue.addLast(i);

            if (!queue.isEmpty() && i >= k - 1) {
                result.add(nums[queue.peekFirst()]);
            }
        }
        int[] res = new int[nums.length - k + 1];

        for (int j = 0; j < result.size(); j++) {
            res[j] = result.get(j);
        }
        return res;
    }
}