package dynamicProgramming.matrix;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-falling-path-sum/
 */
public class MinimumFallingPathMatrix {

    public static int minFallingPathSum(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        for (int i = 1; i < m; i += 1) {
            for (int j = 0; j < n; j += 1) {
                int min = matrix[i - 1][j];
                if (j > 0) {
                    min = Math.min(min, matrix[i - 1][j - 1]);
                }

                if (j < n - 1) {
                    min = Math.min(min, matrix[i - 1][j + 1]);
                }

                matrix[i][j] += min;
            }
        }

        return Arrays.stream(matrix[m - 1]).min().orElse(0);
    }

    public static void main(String[] args) {
        minFallingPathSum(new int[][]{{2, 1, 3},
                {6, 5, 4},
                {7, 8, 9}});
    }

    public int minFallingPathSumRecursion(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        Integer[][] cache = new Integer[m][n];
        int result = Integer.MAX_VALUE;
        // for each column in the last row, we compute the minimum falling path sum
        for (int j = 0; j < n; j++) {
            result = Math.min(result, recursionUtil(matrix, m - 1, j, cache));
        }

        return result;

    }

    int recursionUtil(int[][] matrix, int i, int j, Integer[][] cache) {
        if (j >= matrix[0].length || j < 0) {
            return Integer.MAX_VALUE;
        }

        if (i == 0) {
            return matrix[0][j];
        }

        if (cache[i][j] != null) {
            return cache[i][j];
        }

        int one = recursionUtil(matrix, i - 1, j, cache);
        int tow = recursionUtil(matrix, i - 1, j - 1, cache);
        int three = recursionUtil(matrix, i - 1, j + 1, cache);

        return cache[i][j] = Math.min(one, Math.min(tow, three)) + matrix[i][j];
    }
}
