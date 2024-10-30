package graph.leetcode;

import graph.disjoints.DisjointSetByRank;

import java.util.*;

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

        int m = heights.length;
        int n = heights[0].length;

        int[][] dist = new int[m][n];

        for (int[] d : dist) {
            Arrays.fill(d, Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        queue.offer(new int[]{0, 0, 0});
        dist[0][0] = 0;
        Set<String> set = new HashSet<>();
        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int result = 0;
        while (!queue.isEmpty()) {

            int[] node = queue.poll();

            int x = node[0];
            int y = node[1];
            int distance = node[2];

            if (distance > dist[x][y]) continue;

            result = Math.max(result, distance);
            if (x == m - 1 && y == n - 1) return result;

            if (!set.add(x + "-" + y)) continue;
            for (int[] dir : dirs) {

                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX < 0 || newY < 0 || newX >= m || newY >= n) continue;

                int diff = Math.abs(heights[x][y] - heights[newX][newY]);
                dist[newX][newY] = diff;
                queue.offer(new int[]{newX, newY, diff});
            }
        }

        return -1;
    }

    // This works because at each step, we're always
    // choosing the option that keeps our highest point as low as possible
    //     0 1 2
    //     3 5 6
    //     4 8 7
    // Start at (0,0), value 0. Add to PQ: [(0,0,0)]
    //Explore neighbors of (0,0):
    //Right (0,1): value 1. Add to PQ: [(0,1,1), (0,0,0)]
    //Down (1,0): value 3. Add to PQ: [(0,1,1), (1,0,3), (0,0,0)]
    //Process (0,1) as it has the lowest max (1):
    //Explore (0,2): value 2. Add to PQ: [(0,2,2), (1,0,3), (0,0,0)]
    //Explore (1,1): value 5. Add to PQ: [(0,2,2), (1,0,3), (1,1,5), (0,0,0)]
    //Process (0,2) as it has the lowest max (2):
    //Explore (1,2): value 6. Add to PQ: [(1,0,3), (1,1,5), (1,2,6), (0,0,0)]
    //Process (1,0) as it has the next lowest max (3):
    //Explore (2,0): value 4. Add to PQ: [(2,0,4), (1,1,5), (1,2,6), (0,0,0)]
    //Process (2,0) as it has the next lowest max (4):
    //Explore (2,1): value 8. Add to PQ: [(1,1,5), (1,2,6), (2,1,8), (0,0,0)]
    //Process (1,1) as it has the next lowest max (5):
    //No new unexplored neighbors.
    //Process (1,2) as it has the next lowest max (6):
    //Explore (2,2): value 7. We've reached the target!
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
