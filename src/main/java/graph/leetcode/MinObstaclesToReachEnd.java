package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/minimum-obstacle-removal-to-reach-corner/
public class MinObstaclesToReachEnd {
    public int minimumObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 1. Distance array to track the best cost found so far
        int[][] minObstacles = new int[m][n];
        for (int[] row : minObstacles) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        minHeap.offer(new int[]{0, 0, 0});
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!minHeap.isEmpty()) {
            int[] node = minHeap.poll();
            int r = node[0];
            int c = node[1];
            int cost = node[2];
            if (r == m - 1 && c == n - 1) {
                return cost;
            }
            if (cost > minObstacles[r][c]) {
                continue;
            }

            for (int[] d : dirs) {
                int newRow = r + d[0];
                int newCol = c + d[1];

                if (newRow < 0 || newCol < 0 || newRow >= m || newCol >= n) {
                    continue;
                }
                int newCost = cost + grid[newRow][newCol]; // 0 or 1

                // 3. Relaxation Step: Only add if this is a strictly better path
                if (newCost < minObstacles[newRow][newCol]) {
                    minObstacles[newRow][newCol] = newCost;
                    minHeap.offer(new int[]{newRow, newCol, newCost});
                }
            }

        }

        return -1;
    }


    public int minimumObstaclesOptimised(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // 1. Distance array to keep track of the minimum cost to reach each cell
        int[][] minObstacles = new int[m][n];
        for (int[] row : minObstacles) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        minObstacles[0][0] = 0;

        // 2. Use Deque (ArrayDeque) instead of PriorityQueue
        Deque<int[]> deque = new ArrayDeque<>();
        // Store: {row, col, current_cost}
        deque.offerFirst(new int[]{0, 0, 0});

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!deque.isEmpty()) {
            // Always take from the FRONT
            int[] node = deque.pollFirst();
            int r = node[0];
            int c = node[1];
            int cost = node[2];

            // Since 0-1 BFS guarantees we process nodes in increasing order of cost,
            // the first time we hit the target, it is the optimal answer.
            if (r == m - 1 && c == n - 1) {
                return cost;
            }

            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nc >= 0 && nr < m && nc < n) {
                    // The weight is simply the value in the grid (0 or 1)
                    int weight = grid[nr][nc];
                    int newCost = cost + weight;

                    // Relaxation Step: Only process if we found a shorter path
                    if (newCost < minObstacles[nr][nc]) {
                        minObstacles[nr][nc] = newCost;

                        // THE CORE OPTIMIZATION:
                        if (weight == 0) {
                            // Zero cost edge: "High Priority" -> Push to FRONT
                            // This ensures we explore all free paths before increasing cost
                            deque.offerFirst(new int[]{nr, nc, newCost});
                        } else {
                            // Cost + 1 edge: "Normal Priority" -> Push to BACK
                            // Process these only after current free moves are exhausted
                            deque.offerLast(new int[]{nr, nc, newCost});
                        }
                    }
                }
            }
        }
        return -1;
    }
}
