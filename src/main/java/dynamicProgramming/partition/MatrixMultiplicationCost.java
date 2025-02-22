package dynamicProgramming.partition;

/**
 * http://www.geeksforgeeks.org/dynamic-programming-set-8-matrix-chain-multiplication/
 * https://www.youtube.com/watch?v=vgLJZMUfnsU&t=316s
 */
public class MatrixMultiplicationCost {

    public static int matrixMultiplication(int[] arr, int N) {
        Integer[][] dp = new Integer[N + 1][N + 1];
        //If A is 2×3, B is 3×4, and C is 4×2
        //We represent this as array [2,3,4,2]
        // so we start from 1 to N-1
        //i starts at 1 (not 0) because we need access to (i-1) for dimensions
        //j ends at N-1 because that's the last element's index
        return recursionHelper(arr, 1, N - 1, dp);
    }

    public static int recursionHelper(int[] arr, int i, int j, Integer[][] dp) {
        if (i == j) return 0;
        if (dp[i][j] != null) return dp[i][j];
        int min = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            // arr[i-1] gives left matrix's rows
            // arr[k] gives columns of left/rows of right
            // arr[j] gives right matrix's columns
            //So when you see arr[i-1] * arr[k] * arr[j], think:
            //"Cost of multiplying a matrix of size (arr[i-1] × arr[k]) with another of size (arr[k] × arr[j])"
            min = Math.min(min, arr[i - 1] * arr[k] * arr[j] + recursionHelper(arr, i, k, dp) + recursionHelper(arr, k + 1, j, dp));
        }

        return dp[i][j] = min;
    }

    public static int matrixMultiplicationTabulation(int[] arr, int N) {
        int[][] dp = new int[N][N];

        for (int i = N - 1; i > 0; i--) {
            for (int j = i + 1; j < N; j++) {

                int min = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    min = Math.min(min, arr[i - 1] * arr[k] * arr[j] + dp[i][k] + dp[k + 1][j]);
                }
                dp[i][j] = min;
            }
        }

        // this is basically recursion call where we start from 1 to N-1
        return dp[1][N - 1];
    }
}