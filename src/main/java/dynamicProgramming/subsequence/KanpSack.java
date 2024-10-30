package dynamicProgramming.subsequence;

class KanpSack {

    public int generalKanpscakTabulation(int N, int W, int[] val, int[] wt) {
        int[][] dp = new int[N][W + 1];
        for (int w = 0; w <= W; w++) {
            if (wt[0] <= w) {
                dp[0][w] = val[0];
            }
        }

        // Fill the dp table
        for (int i = 1; i < N; i++) {
            for (int w = 0; w <= W; w++) {
                // Don't include current item (copy value from previous item)
                dp[i][w] = dp[i - 1][w];

                // Include current item if possible
                if (wt[i] <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                            val[i] + dp[i][w - wt[i]]);
                }
            }
        }

        return dp[N - 1][W];
    }


    public int generalKnapSackRecursion(int N, int W, int[] val, int[] wt) {
        Integer[][] cache = new Integer[N][W + 1];
        return recursionUtilGeneralKanpsack(val, wt, W, N - 1, cache);
    }

    public int recursionUtilGeneralKanpsack(int[] val, int[] wt, int weight, int idx, Integer[][] cache) {
        // Base cases
        if (idx < 0 || weight <= 0) {
            return 0;
        }

        // Check cache
        if (cache[idx][weight] != null) {
            return cache[idx][weight];
        }
        int with = 0;
        if (wt[idx] <= weight) {
            with = val[idx] + recursionUtilGeneralKanpsack(val, wt, weight - wt[idx], idx - 1, cache); // only difference with bounded vs unbouded
        }
        int without = recursionUtilGeneralKanpsack(val, wt, weight, idx - 1, cache);
        return cache[idx][weight] = Math.max(with, without);
    }

    public int findProfitSpaceOptimised(int[] val, int[] wt, int W) {
        int[] profits = new int[W + 1];
        // if we have only one weight we take if it's not more than the capacity
        for (int c = 0; c <= W; c++) {
            if (wt[0] < c) profits[c] = profits[0];
        }

        for (int i = 1; i <= val.length; i++) {
            for (int j = W; j >= 0; j--) {
                int profit1 = 0, profit2 = 0;
                if (wt[i - 1] <= j) {
                    profit1 = val[i - 1] + profits[j - wt[i - 1]];
                }
                profit2 = profits[j];
                profits[j] = Math.max(profit1, profit2);
            }
        }
        return profits[W];
    }

    public int backPackII(int m, int[] A, int[] V) {
        int[][] dp = new int[A.length + 1][m + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= m; j++) {
                if (A[i - 1] > m) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    int prevVal = j >= m ? dp[i - 1][j - m] : 0;
                    dp[i][j] = Math.max(dp[i - 1][j],
                            V[i - 1] + prevVal);
                }
            }
        }
        return dp[A.length][m];

    }

}