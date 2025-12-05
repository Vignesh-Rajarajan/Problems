package graph.leetcode;

import java.util.LinkedList;
import java.util.Queue;

//https://leetcode.com/problems/shortest-bridge/
public class IslandConnectBridge {
    private int[][] direct = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int shortestBridge(int[][] grid) {
        int N = grid.length;
        Queue<int[]> q = new LinkedList<>();

        //The method starts by iterating through the grid to locate the first island.
        // Once a cell with a value of 1 is found, the dfs method is called to mark all cells of this island
        // as 2 and add their coordinates to a queue for further processing:
        outer:
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] == 1) {
                    dfs(grid, r, c, q);
                    break outer;
                }
            }
        }

        int res = 0;
        while (!q.isEmpty()) {
            for (int i = q.size(); i > 0; i--) {
                int[] cell = q.poll();
                int r = cell[0], c = cell[1];

                for (int[] d : direct) {
                    int nr = r + d[0], nc = c + d[1];

                    if (nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
                    if (grid[nr][nc] == 1) return res;

                    if (grid[nr][nc] == 0) {
                        grid[nr][nc] = 2;
                        q.offer(new int[]{nr, nc});
                    }
                }
            }
            res++;
        }
        return res;
    }

    private void dfs(int[][] grid, int r, int c, Queue<int[]> q) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid.length || grid[r][c] != 1)
            return;

        grid[r][c] = 2;
        q.offer(new int[]{r, c});
        for (int[] d : direct) {
            dfs(grid, r + d[0], c + d[1], q);
        }
    }
}
