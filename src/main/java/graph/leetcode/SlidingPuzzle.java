package graph.leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

//https://leetcode.com/problems/sliding-puzzle/submissions/1855228317/
public class SlidingPuzzle {

    public int slidingPuzzle(int[][] board) {
        String target = "123450";
        StringBuilder start = new StringBuilder();

        // 1. Convert initial board to string
        for (int[] row : board) {
            for (int val : row) {
                start.append(val);
            }
        }
        String startState = start.toString();

        // Optimization: Hardcode neighbor indices for a 2x3 board (indices 0 to 5)
        // 0 1 2
        // 3 4 5
        //Deep Copy Avoidance: If you stored int[][] arrays in the queue,
        // you would have to manually deep copy the 2D array for every single neighbor move
        // to avoid mutating the state for other paths. String operations handle this immutability naturally.
        // HashSet Lookup: Checking visited.contains(string) is O(1) (average).
        // Checking if a 2D array has been visited requires a custom hash function or nested loops, which is messy
        int[][] neighbors = {
                {1, 3},       // 0 can swap with 1, 3
                {0, 2, 4},    // 1 can swap with 0, 2, 4
                {1, 5},       // 2 can swap with 1, 5
                {0, 4},       // 3 can swap with 0, 4
                {1, 3, 5},    // 4 can swap with 1, 3, 5
                {2, 4}        // 5 can swap with 2, 4
        };

        // BFS Setup
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(startState);
        visited.add(startState);

        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                if (current.equals(target)) {
                    return moves;
                }

                // Find position of '0'
                int zeroIdx = current.indexOf('0');

                // Try swapping '0' with all valid neighbors
                for (int neighborIdx : neighbors[zeroIdx]) {
                    String nextState = swap(current, zeroIdx, neighborIdx);

                    if (!visited.contains(nextState)) {
                        visited.add(nextState);
                        queue.offer(nextState);
                    }
                }
            }
            moves++;
        }

        return -1;
    }

    // Helper to swap characters in a string
    private String swap(String str, int i, int j) {
        char[] chars = str.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }
}
