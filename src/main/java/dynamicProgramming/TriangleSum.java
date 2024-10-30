package dynamicProgramming;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/triangle/
 */
public class TriangleSum {
    static Integer[][] cache = null;

    public static int minimumTotal(List<List<Integer>> triangle) {
        cache = new Integer[triangle.size()][triangle.size()];

        return recursionUtil(triangle, 0, 0);
    }

    public static int recursionUtil(List<List<Integer>> triangle, int triangleIndex, int subIndex) {
        if (triangleIndex >= triangle.size()) return 0;

        if (cache[triangleIndex][subIndex] != null) return cache[triangleIndex][subIndex];

        // recursively take from next rom same column
        int left = recursionUtil(triangle, triangleIndex + 1, subIndex);

        // recursively take for next row, next column
        int right = recursionUtil(triangle, triangleIndex + 1, subIndex + 1);

        cache[triangleIndex][subIndex] = Math.min(left, right) + triangle.get(triangleIndex).get(subIndex);

        return cache[triangleIndex][subIndex];
    }

    //We start from the bottom row and work our way up.
    //This is often preferred because:
    //The base case (bottom row) is straightforward - just the values themselves.
    //For each element, we know we'll always have two choices in the row below.
    // we can also move from top but the code is cumbersome like from line 63
    public static int minimumTotalBottomUp(List<List<Integer>> triangle) {

        int[][] dp = new int[triangle.size()][triangle.size()];
        // the commented code is to optimise the O(N^2) space
        //  int[]dp = new int[triangle.size()];
        //    int[]dp1 = new int[triangle.size()];

        //Remember base case is just returning leaf nodes
        for (int i = 0; i < triangle.size(); i++) {
            dp[triangle.size() - 1][i] = triangle.get(triangle.size() - 1).get(i);
        }

        for (int row = triangle.size() - 2; row >= 0; row--) {
            for (int pos = 0; pos < triangle.get(row).size(); pos++) {
                //dp1[pos] = triangle.get(row).get(pos) + Math.min(dp[pos+1], dp[pos]);
                dp[row][pos] = triangle.get(row).get(pos) + Math.min(dp[row + 1][pos], dp[row + 1][pos + 1]);
            }
            //dp = dp1;
        }

        return dp[0][0];
    }

    public static int minimumTotalTopDown(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];

        // Initialize the top of the triangle
        dp[0][0] = triangle.get(0).get(0);

        // Fill the dp table
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    // Leftmost element
                    dp[i][j] = dp[i - 1][j] + triangle.get(i).get(j);
                } else if (j == i) {
                    // Rightmost element
                    dp[i][j] = dp[i - 1][j - 1] + triangle.get(i).get(j);
                } else {
                    // Middle elements
                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j);
                }
            }
        }

        // Find the minimum in the last row
        int minPath = dp[n - 1][0];
        for (int j = 1; j < n; j++) {
            minPath = Math.min(minPath, dp[n - 1][j]);
        }
        return minPath;
    }

    public static void main(String[] args) {
        List<List<Integer>> triangle = Arrays.stream(new Integer[][]{{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}})
                .map(Arrays::asList)
                .collect(Collectors.toList());
        minimumTotalBottomUp(triangle);
    }
}
