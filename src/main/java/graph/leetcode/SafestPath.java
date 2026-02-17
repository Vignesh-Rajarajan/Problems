package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/find-the-safest-path-in-a-grid/
//https://neetcode.io/solutions/find-the-safest-path-in-a-grid
public class SafestPath {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        int[][] dist = new int[n][n];
        for (int[] a : dist) {
            Arrays.fill(a, -1);
        }
        Deque<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    queue.offer(new int[]{i, j});
                    dist[i][j] = 0;
                }
            }
        }

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size(); // Capture size snapshot
            for (int i = 0; i < size; i++) {
                int[] co = queue.poll();
                int r = co[0];
                int c = co[1];

                for (int[] dir : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                    int r2 = r + dir[0], c2 = c + dir[1];
                    if (inBounds(r2, c2, n) && dist[r2][c2] == -1) {
                        dist[r2][c2] = level + 1;
                        queue.offer(new int[]{r2, c2});
                    }
                }

            }
            level++;
        }

        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        maxHeap.offer(new int[]{dist[0][0], 0, 0});
        boolean[][] visit = new boolean[n][n];

        while (!maxHeap.isEmpty()) {
            int[] curr = maxHeap.poll();
            int distance = curr[0], r = curr[1], c = curr[2];
            if (r == n - 1 && c == n - 1) {
                return distance;
            }

            for (int[] dir : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                int r2 = r + dir[0], c2 = c + dir[1];
                if (inBounds(r2, c2, n) && !visit[r2][c2]) {
                    visit[r2][c2] = true;
                    int dist2 = Math.min(distance, dist[r2][c2]);
                    maxHeap.offer(new int[]{dist2, r2, c2});
                }
            }

        }

        return 0;
    }

    private boolean inBounds(int r, int c, int N) {
        return r >= 0 && c >= 0 && r < N && c < N;
    }
}
