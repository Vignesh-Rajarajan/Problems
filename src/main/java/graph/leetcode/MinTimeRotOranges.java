package graph.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
  https://leetcode.com/problems/rotting-oranges/
 */

public class MinTimeRotOranges {

    public static void main(String[] args) {
        int[][] grid = {{2, 1, 0, 1, 1}, {1, 0, 2, 1, 1}, {1, 1, 1, 1, 1}};
        System.out.println(new MinTimeRotOranges().orangesRotting(grid));
    }

    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int countFresh = 0;
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Deque<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    countFresh++;
                }
            }
        }
        if (countFresh == 0) return 0;
        int result = -1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] temp = queue.poll();
                for (int[] dir : dirs) {
                    int newX = temp[0] + dir[0];
                    int newY = temp[1] + dir[1];
                    if (newX < 0 || newX >= grid.length || newY < 0 || newY >= grid[0].length || grid[newX][newY] == 0 || grid[newX][newY] == 2) {
                        continue;
                    }
                    queue.offer(new int[]{newX, newY});
                    grid[newX][newY] = 2;
                    countFresh--;
                }
            }
            result++;
        }
        return countFresh != 0 ? -1 : result;
    }

    public int orangesRottingDFS(int[][] grid) {
        if (grid == null || grid.length == 0) return -1;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) rotAdjacent(grid, i, j, 2);
            }
        }

        //That is because it's counting the minutes elapsed
        // with an offset of 2 (since 0 and 1 are reserved for empty cell and fresh oranges)
        int minutes = 2;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 1) return -1;
                minutes = Math.max(minutes, cell);
            }
        }

        return minutes - 2;
    }

    private void rotAdjacent(int[][] grid, int i, int j, int minutes) {
        if (i < 0 || i >= grid.length /* out of bounds */
                || j < 0 || j >= grid[0].length /* out of bounds */
                || grid[i][j] == 0 /* empty cell */
                || (1 < grid[i][j] && grid[i][j] < minutes) /* this orange is already rotten by another rotten orange */
        ) return;
        else {
            grid[i][j] = minutes;
            rotAdjacent(grid, i - 1, j, minutes + 1);
            rotAdjacent(grid, i + 1, j, minutes + 1);
            rotAdjacent(grid, i, j - 1, minutes + 1);
            rotAdjacent(grid, i, j + 1, minutes + 1);
        }
    }

}