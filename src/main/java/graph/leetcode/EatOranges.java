package graph.leetcode;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

//https://leetcode.com/problems/minimum-number-of-days-to-eat-n-oranges
public class EatOranges {

    public int minDays(int n) {
        // BFS initialization
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        queue.offer(n);
        visited.add(n);
        int days = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            // Process all states reachable in the current number of 'days'
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();

                // If we have 0 oranges, we are done
                if (curr == 0) {
                    return days;
                }

                // Try all 3 possible moves

                // 1. Eat one orange (Current - 1)
                // We assume this is always a valid move to reach a number divisible by 2 or 3
                if (!visited.contains(curr - 1)) {
                    visited.add(curr - 1);
                    queue.offer(curr - 1);
                }

                // 2. If divisible by 2, eat n/2 (Remaining: Current / 2)
                if (curr % 2 == 0 && !visited.contains(curr / 2)) {
                    visited.add(curr / 2);
                    queue.offer(curr / 2);
                }

                // 3. If divisible by 3, eat 2*(n/3) (Remaining: Current / 3)
                if (curr % 3 == 0 && !visited.contains(curr / 3)) {
                    visited.add(curr / 3);
                    queue.offer(curr / 3);
                }
            }
            // Increment days after finishing the current level
            days++;
        }
        return -1; // Should not reach here
    }
}
