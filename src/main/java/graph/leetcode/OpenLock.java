package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/open-the-lock/
public class OpenLock {
    public int openLock(String[] deadends, String target) {
        // Convert deadends to a Set for O(1) lookup
        Set<String> dead = new HashSet<>(Arrays.asList(deadends));

        // If start is blocked, impossible
        if (dead.contains("0000")) {
            return -1;
        }
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.offer("0000");
        visited.add("0000");

        int steps = 0;
        int[] dirs = new int[]{-1, 1};
        while (!queue.isEmpty()) {
            int size = queue.size();

            // Process all nodes at current level
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();

                // Check if we reached the target
                if (curr.equals(target)) {
                    return steps;
                }

                // Try all 8 possible moves (each of 4 wheels ±1)
                char[] arr = curr.toCharArray();
                for (int j = 0; j < 4; j++) {
                    // Two directions: +1 and -1
                    for (int dir : dirs) {
                        char[] copy = arr.clone(); // Work on a copy
                        // Rotate digit: handle wrap-around (0 -> 9, 9 -> 0)
                        // the +10 is for adding -1, if we add -1 to 0 and take %10 it will
                        // result in -1, but we need 9
                        copy[j] = (char) (((copy[j] - '0' + dir + 10) % 10) + '0');
                        String next = new String(copy);

                        // If not visited and not a deadend, add to queue
                        if (!visited.contains(next) && !dead.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                }
            }
            steps++; // After finishing the level, increment step count
        }

        return -1; // Target not reachable
    }
}
