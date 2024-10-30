package graph.leetcode.shortestPath;

import java.util.Comparator;
import java.util.PriorityQueue;

//https://leetcode.com/problems/path-with-minimum-effort/
public class MinimumEffortPath {

    // [[1,2,2],
    //  [3,8,2],
    //  [5,3,5]]
    // queue = [(0, 0, 0)]
    // Up: (0, 1) with effort max(1, |2 - 1|) = 2
    // Right: (1, 0) with effort max(1, |3 - 1|) = 3
    // queue = [(1, 0, 3), (0, 1, 2)]
    // Up: (0, 2) with effort max(2, |2 - 2|) = 2
    // Right: (1, 1) with effort max(2, |8 - 2|) = 6
    // Down: (1, 0) with effort max(2, |3 - 2|) = 2
    // queue = [(1, 1, 6), (1, 0, 2), (0, 2, 2)]
    // Up: (1, 1) with effort max(2, |8 - 3|) = 5
    // Right: (2, 0) with effort max(2, |5 - 3|) = 5
    // Down: (2, 0) with effort max(2, |5 - 3|) = 5
    // Left: (0, 0) with effort max(2, |1 - 3|) = 2
    // queue = [(2, 0, 5), (1, 1, 5), (0, 0, 2)]
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        int[][] dirs = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        queue.offer(new int[]{0, 0, 0});
        boolean[][] visited = new boolean[m][n];
        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int weight = curNode[2];
            int x = curNode[0];
            int y = curNode[1];
            if (x == m - 1 && y == n - 1) {
                return weight;
            }
            if (visited[x][y]) {
                continue;
            }
            visited[x][y] = true;
            for (int[] dir : dirs) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX < 0 || newX >= m || newY < 0 || newY >= n) {
                    continue;
                }

                int newWeight = Math.max(weight, Math.abs(heights[newX][newY] - heights[x][y]));

                queue.offer(new int[]{newX, newY, newWeight});
            }
        }

        return -1;
    }
}
