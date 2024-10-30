package dynamicProgramming.subsequence;

public class UnboundedKanpsack {
    public int unboundedKanpscakTabulation(int N, int W, int val[], int wt[]) {
        int[][] dp = new int[N][W + 1];

        //First item weight wt[0] = 2
        //First item value val[0] = 3
        //Total capacity W = 7
        //w = 0:
        //  - wt[0] = 2 is NOT <= 0
        //  - dp[0][0] = 0 (default)
        //w = 1:
        //  - wt[0] = 2 is NOT <= 1
        //  - dp[0][1] = 0 (default)
        //w = 2:
        //  - wt[0] = 2 is <= 2
        //  - dp[0][2] = (2/2) * 3 = 1 * 3 = 3
        //  - Means we can use the first item exactly once
        //w = 3:
        //  - wt[0] = 2 is <= 3
        //  - dp[0][3] = (3/2) * 3 = 1 * 3 = 3
        //  - Integer division results in 1
        //  - Means we can still use first item only once
        //w = 4:
        //  - wt[0] = 2 is <= 4
        //  - dp[0][4] = (4/2) * 3 = 2 * 3 = 6
        //  - Can use first item twice now!
        //w = 5:
        //  - wt[0] = 2 is <= 5
        //  - dp[0][5] = (5/2) * 3 = 2 * 3 = 6
        //  - Integer division still results in 2
        //w = 6:
        //  - wt[0] = 2 is <= 6
        //  - dp[0][6] = (6/2) * 3 = 3 * 3 = 9
        //  - Can use first item three times!
        //w = 7:
        //  - wt[0] = 2 is <= 7
        //  - dp[0][7] = (7/2) * 3 = 3 * 3 = 9
        //  - Integer division still results in 3
        for (int w = 0; w <= W; w++) {
            if (wt[0] <= w) {
                dp[0][w] = (w / wt[0]) * val[0];
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

    public int unboundedKnapsackSpace(int W, int n, int[] val, int[] wt) {

        // dp[i] is going to store maximum value
        // with knapsack capacity i.
        int[] dp = new int[W + 1];

        // Fill dp[] using above recursive formula
        for (int i = 0; i <= W; i++) {
            for (int j = 0; j < n; j++) {
                if (wt[j] <= i) {
                    dp[i] = Math.max(dp[i], dp[i - wt[j]] + val[j]);
                }
            }
        }
        return dp[W];
    }

    public int unboundedKanpscakRecursion(int N, int W, int[] val, int[] wt) {
        Integer[][] cache = new Integer[N][W + 1];
        return recursionUtil(val, wt, W, N - 1, cache);
    }

    public int recursionUtil(int[] val, int[] wt, int weight, int idx, Integer[][] cache) {
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
            with = val[idx] + recursionUtil(val, wt, weight - wt[idx], idx, cache); // only difference with bounded vs unbouded
        }
        int without = recursionUtil(val, wt, weight, idx - 1, cache);
        return cache[idx][weight] = Math.max(with, without);
    }
}
