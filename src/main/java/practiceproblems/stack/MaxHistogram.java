package practiceproblems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 */

public class MaxHistogram {

    /**
     * Let's start by thinking of a brute force, naive solution.
     * Pick two bars and find the maxArea between them and compare that to your global maxArea.
     * To do that, you’ll need to find the bar that “restricts” the height of the forming rectangle to its own height -
     * i.e; the bar with the minimum height between two bars.
     */
    public int largestRectangleAreaBruteForce(int[] heights) {
        if (heights.length == 1) return heights[0];
        int area = 0;
        for (int i = 0; i < heights.length; i++) {
            int min = heights[i];
            for (int j = i; j < heights.length; j++) {
                min = Math.min(min, heights[j]);
                area = Math.max(area, ((j - i + 1) * min));
            }
        }

        return area;
    }

    /**
     * https://abhinandandubey.github.io/posts/2019/12/15/Largest-Rectangle-In-Histogram.html
     * Because if the length of the array is n, the largest possible rectangle has to have a height one of the elements of the array,
     * that is to say, there are only n possible largest rectangles.
     * So we don't really need to go through every pair of bars, but should rather search by the height of the bar.
     *
     * Why Stack?
     * At each step we need the information of previously seen "candidate" bars - bars which give us hope.
     * These are the bars of increasing heights.
     * And since they'll need to be put in the order of their occurrence, stack should come to your mind.
     *
     * Lets take the example [2, 1, 5, 6, 2, 3]
     *
     * The first bar we see is the bar at position 0 of height 2.
     * It is definitely as "candidate bar" as it gives us hope of finding a larger rectangle, so we just add it to the stack.
     * The next one we see is the bar at position 1 with height 1.
     * At this point, we look at the stack and see that the "candidate bar" at the top of the stack is of height 2,
     * and because of this 1, we definitely can't do a rectangle of height 2 now,
     * so the only natural thing to do at this point is to pop it out of the stack,
     * and see what area it could have given us.
     *
     * After adding 1,5,6 to the stack hoping to find bigger rectangle we hit at 2 which is smaller than 6
     * At this point it should be clear that we only pop from the stack when
     * height of the current bar is lower than the height of the bar at the top of the stack.
     * so lets see what it can give us and pop it out.
     * The height of this rectangle is 6, and the width is i−stack[peek()]−1=> 4−2−1 => 1.
     */
    public static int largestRectangleArea(int[] heights) {
        int result = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            // When we reach the end of the array, we use 0 as the height to force all remaining bars to be processed
            // into area calculation
            int h = (i == n) ? 0 : heights[i];

            // While the stack is not empty and the current bar is shorter than the bar at the top of the stack
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()]; // Height of the bar at the top of the stack
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1); // Width of the rectangle
                result = Math.max(result, height * width); // Update the maximum area
            }

            // Push the current index onto the stack
            stack.push(i);
        }

        return result;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{2, 1, 5, 6, 2, 3};
        System.out.println(largestRectangleArea(arr));
    }
}