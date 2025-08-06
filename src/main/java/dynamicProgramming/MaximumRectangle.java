package dynamicProgramming;

import practiceproblems.stack.MaxHistogram;

import java.util.Arrays;

import static practiceproblems.stack.MaxHistogram.largestRectangleArea;

/**
 * At every row the height and area of rectangle varies, if the last row(base) is having a zero value
 * then that portion have to be avoided, this is the reason we take maxHistogram after every row
 * <p>
 * matrix
 * 0 0 0 1 0 0 0
 * 0 0 1 1 1 0 0
 * 0 1 1 1 1 1 0
 * <p>
 * height
 * 0 0 0 1 0 0 0
 * 0 0 1 2 1 0 0
 * 0 1 2 3 2 1 0
 */
public class MaximumRectangle {

    public static int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] hist = new int[m][n];
        int result = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    // If the current cell is '1', add to the height from the previous row
                    hist[i][j] = (i == 0) ? 1 : hist[i - 1][j] + 1;
                } else {
                    // If the current cell is '0', reset the height to 0
                    hist[i][j] = 0;
                }
            }
            result = Math.max(result,largestRectangleArea(hist[i]));
        }

        return result;
    }

    public static void main(String[] args) {
        maximalRectangle(new char[][]{{'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}});
    }

    /**
     * https://www.youtube.com/watch?v=-FgseNO-6Gk
     */
    public int maximalRectangleBruteForce(char[][] matrix) {
        int rowLength = matrix.length;
        if (rowLength == 0) return 0;
        int colLength = matrix[0].length;
        if (colLength == 0) return 0;

        int maxA = Integer.MIN_VALUE;

        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                if (matrix[row][col] == '0') continue;
                // consider the rectangle whose upper left corner is at [r, c]:

                int rightMostCol = colLength - 1;// the right most column that we should check, initially it's w-1

                // iterate from i,j till row and col end
                for (int r1 = row; r1 < rowLength; r1++) {
                    for (int c1 = col; c1 <= rightMostCol; c1++) {

                        if (matrix[r1][c1] == '0') {
                            rightMostCol = c1 - 1; // update the rightMostCol when we encounter '0', no use in proceeding after
                            break; // go to next row
                        }
                        // r1 - row + 1, c1 - col + 1 are rectangle size relative to i,j where the inner loop started
                        maxA = Math.max(maxA, (r1 - row + 1) * (c1 - col + 1)); //
                    }
                }
            }
        }

        return Math.max(maxA, 0);
    }

}
