package dynamicProgramming.matrix;

// https://leetcode.com/problems/cherry-pickup-ii/
// https://youtu.be/QGfn7JeXK54
// Tricky 3D DP Revise
public class CherryPickUpII {

    public int cherryPickup(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Integer[][][] cache = new Integer[m][n][n];
        return recursiveUtil(grid, 0, 0, n - 1, cache);
    }

    public int recursiveUtil(int[][] grid, int row, int roboPos1, int roboPos2, Integer[][][] cache) {
        if (roboPos1 < 0 || roboPos1 >= grid[0].length || roboPos2 < 0 || roboPos2 >= grid[0].length) {
            return -10000; // return a very small number
        }

        if (row == grid.length - 1) {
            if (roboPos1 == roboPos2) {
                return grid[row][roboPos1];
            }

            return grid[row][roboPos2] + grid[row][roboPos1];
        }

        if (cache[row][roboPos1][roboPos2] != null) {
            return cache[row][roboPos1][roboPos2];
        }

        int max = -100000;
        // when they go into next row they will traverse all 3 possible column ( j-1, j, j+1 )
        // and will make sure they are in boundary as well as robot don't colide
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int value = grid[row][roboPos2] + grid[row][roboPos1];
                if (roboPos1 == roboPos2) {
                    value = grid[row][roboPos2];
                }
                value = value + recursiveUtil(grid, row + 1, roboPos1 + i, roboPos2 + j, cache);
                max = Math.max(max, value);
            }
        }

        return cache[row][roboPos1][roboPos2] = max;
    }

    public int cherryPickupTabulation(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][][] dp = new int[m][n][n];

        // Initialize the dp array with values from the last row of the grid
        for (int j1 = 0; j1 < n; j1++) {
            for (int j2 = 0; j2 < n; j2++) {
                if (j1 == j2)
                    dp[m - 1][j1][j2] = grid[m - 1][j1];
                else
                    dp[m - 1][j1][j2] = grid[m - 1][j1] + grid[m - 1][j2];
            }
        }

        // Outer nested loops to traverse the DP array from the second last row to the
        // first row
        for (int i = m - 2; i >= 0; i--) {
            for (int j1 = 0; j1 < n; j1++) {
                for (int j2 = 0; j2 < n; j2++) {
                    int maxi = Integer.MIN_VALUE;

                    // Inner nested loops to try out 9 options
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            int ans = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];

                            // Check if the indices are valid
                            if ((j1 + di >= 0 && j1 + di < n) && (j2 + dj >= 0 && j2 + dj < n)) {
                                ans += dp[i + 1][j1 + di][j2 + dj];
                            } else {
                                ans += (int) Math.pow(-10, 9);
                            }

                            // Update maxi with the maximum result
                            maxi = Math.max(ans, maxi);
                        }
                    }
                    // Store the result in the dp array
                    dp[i][j1][j2] = maxi;
                }
            }
        }

        // The final result is stored at the top row (first row) of the dp array
        return dp[0][0][n - 1];

    }
}
