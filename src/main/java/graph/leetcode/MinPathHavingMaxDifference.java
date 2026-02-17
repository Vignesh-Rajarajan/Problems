package graph.leetcode;

import graph.disjoints.DisjointSetByRank;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/path-with-minimum-effort/
 * <p>
 * https://leetcode.com/problems/swim-in-rising-water/
 * <p>
 * both are almost similar
 */
public class MinPathHavingMaxDifference {
    private static final int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int minimumEffortPath(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        queue.offer(new int[]{0, 0, 0});
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            int row = node[0];
            int col = node[1];
            int cost = node[2];

            if (row == rows - 1 && col == cols - 1) return cost;
            if (dist[row][col] < cost) continue;
            for (int[] dir : directions) {
                int newR = row + dir[0];
                int newC = col + dir[1];
                if (newR < 0 || newC < 0 || newR >= rows || newC >= cols) {
                    continue;
                }
                int newCost = Math.max(cost, Math.abs(heights[row][col] - heights[newR][newC]));
                if (newCost < dist[newR][newC]) {
                    dist[newR][newC] = newCost;
                    queue.offer(new int[]{newR, newC, newCost});
                }
            }

        }

        return 0;

    }

    //1. The "Waiting Room" Analogy
    //Imagine the Priority Queue (PQ) is a waiting room for different potential paths.
    //Path A has encountered a maximum height of 10 so far.
    //Path B has encountered a maximum height of 50 so far.
    //Both are in the PQ. The algorithm asks the PQ:
    // "Give me the easiest path available right now." The PQ gives you Path A. You take one step from Path A.
    //If Path A's neighbor is huge (say, 100), Path A goes back into the waiting room with a cost of 100.
    //Now, when you ask the PQ for the next path, it will switch to Path B (because 50 is better than 100).
    //Why this guarantees the solution: The code explores the grid in order of "effort."
    //It refuses to take a step on a "hard" path (high elevation) until it has exhausted all "easier" paths (low elevation).
    //Therefore, the very first time you touch the destination (n-1, n-1),
    //it is mathematically guaranteed that you arrived there via the path with the lowest possible maximum height.
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        boolean[][] visited = new boolean[n][n];
        int[][] dirs = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

        visited[0][0] = true;
        pq.offer(new int[]{0, 0, grid[0][0]});
        while (!pq.isEmpty()) {
            int[] info = pq.poll();
            int i = info[0], j = info[1], max = info[2];
            for (int[] dir : dirs) {
                int newI = dir[0] + i, newJ = dir[1] + j;
                if (newI < 0 || newI >= n || newJ < 0 || newJ >= n || visited[newI][newJ]) continue;
                visited[newI][newJ] = true;
                int newMax = Math.max(max, grid[newI][newJ]);
                if (newI == n - 1 && newJ == n - 1) return newMax;
                pq.offer(new int[]{newI, newJ, newMax});

            }
        }

        return 0;
    }

    public int swimInWaterUnionFind(int[][] grid) {
        int N = grid.length;
        DisjointSetByRank ds = new DisjointSetByRank(N * N);

        int[] map = new int[N * N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                map[grid[i][j]] = i * N + j;
            }
        }

        for (int time = 0; time < N * N; ++time) {
            int idx = map[time];
            int row = idx / N;
            int col = idx % N;

            for (int[] direction : directions) {
                int rr = row + direction[0], cc = col + direction[1];
                if (!isValid(rr, cc, grid, time)) continue;
                ds.union(idx, rr * N + cc);
            }

            if (ds.isConnected(0, N * N - 1)) return time;
        }

        return -1;
    }

    private boolean isValid(int r, int c, int[][] grid, int time) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid.length && grid[r][c] < time;
    }
}
