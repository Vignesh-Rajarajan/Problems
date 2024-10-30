package graph.leetcode.shortestPath;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

//https://leetcode.com/problems/shortest-path-in-binary-matrix/
public class ShortestPathBinaryMat {

    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0 || grid[0][0] == 1) {
            return -1;
        }

        int m = grid.length;
        int n = grid[0].length;
        if (m == 1 && n == 1) {
            return 1;
        }

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 1});
        int[][] dirs = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int x = curNode[0];
            int y = curNode[1];
            int weight = curNode[2];
            for (int[] dir : dirs) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX < 0 || newX >= m || newY < 0 || newY >= n || grid[newX][newY] != 0) {
                    continue;
                }
                if (newX == m - 1 && newY == n - 1 && grid[newX][newY] == 0) {
                    return weight + 1;
                }
                grid[newX][newY] = -1;
                queue.offer(new int[]{newX, newY, weight + 1});
            }
        }

        return -1;
    }
}
