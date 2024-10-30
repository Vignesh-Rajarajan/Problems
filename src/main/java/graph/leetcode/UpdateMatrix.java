package graph.leetcode;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * tricky
 * https://leetcode.com/problems/01-matrix/
 */
public class UpdateMatrix {

    /**
     * the reason we add 0 to queue is consider this example
     * If we started with 1s, we'd have no way to know that the 0 in the center is the reference point for all other distances.
     * We might start updating distances from the edge 1s, which would be incorrect.
     * 1 1 1 1 1
     * 1 1 1 1 1
     * 1 1 0 1 1
     * 1 1 1 1 1
     * 1 1 1 1 1
     * if neighbour cell has not been visited --> then it must bea land cell
     * If we start from 1s, what initial distance would we assign them? We don't know their true distances yet.
     * 0s have a clear initial state - their distance to the nearest 0 is 0.
     */
    public int[][] updateMatrix(int[][] matrix) {
        Queue<int[]> queue = new ArrayDeque<>();
        int m = matrix.length, n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }
        int[][] dir = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int row = cur[0] + dir[i][0];
                int col = cur[1] + dir[i][1];
                if (row < 0 || row >= m || col < 0 || col >= n || visited[row][col]) {
                    continue;
                }
                visited[row][col] = true;
                /**
                 * since, we popped out the cell (cell(0),cell(1)) and now looking at all its four adjacent cells
                 * and since we're sure that the cell has its minimum distance from zero ,
                 * so, in case any of its four cells value(calling it child cell)
                 * (which is child's distance from zero cell except when its Max Integer value)
                 * has value more than the {value in cell} + 1 ,
                 * we update the child cell to {value in cell} + 1 .. + 1 is used because
                 * when add +1 as going from a cell to next adjacent cell increases path by 1
                 */
                matrix[row][col] = matrix[cur[0]][cur[1]] + 1;
                queue.offer(new int[]{row, col});
            }
        }
        return matrix;
    }

    /**
     * In this problem, a cell has at most 4 neighbors that are left, top, right, bottom.
     * If we use dynamic programming to compute the distance of the current cell based on 4 neighbors simultaneously,
     * it's impossible because we are not sure if distance of neighboring cells is already computed or not
     * <p>
     * For those who are asking why DP is done in two passes, in DP we can only use the values which are previously calculated.
     * When we are parsing from top left and coming down to bottom right,
     * we can only use the values of above and left because only those two values are precomputed,
     * if we take right and down, those values are not yet computed,
     * if we work with those values we will get suboptimal answer.
     */
    public int[][] updateMatrixDP(int[][] matrix) {
        int maxValue = matrix.length * matrix[0].length;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) continue;

                int top = i - 1 >= 0 ? matrix[i - 1][j] : maxValue;
                int left = j - 1 >= 0 ? matrix[i][j - 1] : maxValue;

                matrix[i][j] = Math.min(top, left) + 1;
            }
        }

        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j >= 0; j--) {
                int bottom = i + 1 < matrix.length ? matrix[i + 1][j] : maxValue;
                int right = j + 1 < matrix[0].length ? matrix[i][j + 1] : maxValue;

                matrix[i][j] = Math.min(matrix[i][j], Math.min(bottom, right) + 1);
            }
        }

        return matrix;

    }
}
