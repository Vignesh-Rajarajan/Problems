package practiceproblems;

import java.util.Arrays;

//https://leetcode.com/problems/unique-paths-ii/
//https://leetcode.com/problems/unique-paths/
public class UniquePath {
    Integer[][] cache;

    private static int uniquePathI(int row, int col) {
        int[][] dp = new int[row][col];
        for (int i = 0; i < col; i++) {
            dp[0][i] = 1;
        }
        for (int j = 0; j < row; j++) {
            dp[j][0] = 1;
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[row - 1][col - 1];
    }

    private static int uniquePathII(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == 1)
            return 0;
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 1) {
                dp[i][0] = 0;
                break;
            }
            dp[i][0] = 1;

        }

        for (int j = 0; j < n; j++) {
            if (obstacleGrid[0][j] == 1) {
                dp[0][j] = 0;
                break;
            }
            dp[0][j] = 1;

        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    continue;
                }
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];

    }

    int uniquePathSpaceOptimised(int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        }
        if (m == 1 || n == 1) {
            return 1;
        }
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[n - 1];
    }

    int uniquePathIRecursion(int row, int col) {
        Integer[][] cache = new Integer[row][col];
        return uniquePathIRecursionHelper(row - 1, col - 1, cache);
    }

    int uniquePathIRecursionHelper(int row, int col, Integer[][] cache) {
        if (row < 0 || col < 0) {
            return 0;
        }
        if (row == 0 || col == 0) {
            return 1;
        }

        if (cache[row][col] != null) {
            return cache[row][col];
        }
        int top = uniquePathIRecursionHelper(row - 1, col, cache);
        int left = uniquePathIRecursionHelper(row, col - 1, cache);
        cache[row][col] = top + left;
        return cache[row][col];

    }

    public int uniquePathsWithObstaclesRecursion(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == 1) return 0;
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        Integer[][] dp = new Integer[m][n];

        return recursionHelper(obstacleGrid, m - 1, n - 1, dp);
    }

    public int recursionHelper(int[][] obstacleGrid, int m, int n, Integer[][] dp) {
        if (m < 0 || n < 0 || obstacleGrid[m][n] == 1) return 0;
        if (m == 0 && n == 0) return 1; // only difference from uniquePathIRecursionHelper

        if (dp[m][n] != null) {
            return dp[m][n];
        }
        int up = recursionHelper(obstacleGrid, m - 1, n, dp);
        int left = recursionHelper(obstacleGrid, m, n - 1, dp);

        dp[m][n] = up + left;
        return up + left;
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        cache = new Integer[obstacleGrid.length][obstacleGrid[0].length];
        return recursionHelper(obstacleGrid, 0, 0);
    }

    public int recursionHelper(int[][] arr, int i, int j) {
        if (i < 0 || j < 0 || i >= arr.length || j >= arr[0].length || arr[i][j] == 1) return 0;
        if (i == arr.length - 1 && j == arr[0].length - 1) return 1;
        if (cache[i][j] != null) return cache[i][j];
        return cache[i][j] = recursionHelper(arr, i, j + 1) + recursionHelper(arr, i + 1, j);

    }

    public int uniquePathsWithObstaclesSpaceOptimised(int[][] obstacleGrid) {
        int width = obstacleGrid[0].length;
        int[] dp = new int[width];
        dp[0] = 1;
        for (int[] row : obstacleGrid) {
            for (int j = 0; j < width; j++) {
                if (row[j] == 1)
                    dp[j] = 0;
                else if (j > 0)
                    dp[j] += dp[j - 1];
            }
        }
        return dp[width - 1];
    }
}
