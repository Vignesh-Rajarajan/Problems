package dynamicProgramming.matrix;

/**
 * https://leetcode.com/problems/minimum-path-sum/
 * <p>
 * tricky recursion part
 */
public class MinCostPath {

    public static void main(String args[]) {
        MinCostPath mcp = new MinCostPath();
        int[][] cost = {{1, 2, 3}, {4, 8, 2}, {1, 5, 3}, {6, 2, 9}};
        int result = mcp.minPathSum1(cost);
        System.out.println(result);
    }

    public int minPathSum1(int[][] grid) {

        for (int i = 1; i < grid.length; i++) {
            grid[i][0] += grid[i - 1][0];
        }

        for (int j = 1; j < grid[0].length; j++) {
            grid[0][j] += grid[0][j - 1];
        }
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                grid[i][j] = Math.min(grid[i][j] + grid[i - 1][j], grid[i][j] + grid[i][j - 1]);
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        Integer[][] dp = new Integer[m][n];
        return recursionUtil(grid, m - 1, n - 1, dp);
    }

    private int recursionUtil(int[][] grid, int row, int col, Integer[][] dp) {
        if (row < 0 || col < 0) return Integer.MAX_VALUE;
        if (row == 0 && col == 0) return grid[0][0];
        if (dp[row][col] != null) return dp[row][col];

        int minSum = grid[row][col] + Math.min(
                recursionUtil(grid, row - 1, col, dp),
                recursionUtil(grid, row, col - 1, dp)
        );

        return dp[row][col] = minSum;
    }
}