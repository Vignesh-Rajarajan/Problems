package dynamicProgramming.subsequence;

// https://www.naukri.com/code360/problems/rod-cutting-problem
public class RodCuttingMaximise {
    public static int cutRod(int price[], int n) {
        Integer[][] cache = new Integer[price.length][n + 1];
        return recursionUtil(price, price.length - 1, n, cache);
    }

    static int recursionUtil(int[] price, int idx, int totalLen, Integer[][] cache) {
        if (totalLen <= 0) {
            return 0;
        }
        if (idx < 0) {
            return 0;
        }
        if (cache[idx][totalLen] != null) {
            return cache[idx][totalLen];
        }
        int without = recursionUtil(price, idx - 1, totalLen, cache);
        int with = Integer.MIN_VALUE;
        int rodLen = idx + 1;
        if (rodLen <= totalLen) {
            with = price[idx] + recursionUtil(price, idx, totalLen - rodLen, cache);
        }

        return cache[idx][totalLen] = Math.max(with, without);
    }

    public static int cutRodTabulation(int price[], int n) {
        int[][] dp = new int[price.length][n + 1];

        // What's the maximum value we can get for a rod of length j when we can only use rods of length 1
        // price[0] is the price of a rod of length 1
        // price[0] = 2 (meaning a rod of length 1 costs 2 units)
        // n = 4 (we want to cut a rod of length 4)
        // dp[0][0] = 0 * 2 = 0 (for length 0, no rods used)
        // dp[0][1] = 1 * 2 = 2 (for length 1, one rod of length 1)
        // dp[0][2] = 2 * 2 = 4 (for length 2, two rods of length 1)
        // dp[0][3] = 3 * 2 = 6 (for length 3, three rods of length 1)
        // dp[0][4] = 4 * 2 = 8 (for length 4, four rods of length 1)
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j * price[0];
        }

        for (int i = 1; i < price.length; i++) {
            for (int target = 0; target <= n; target++) {
                int without = dp[i - 1][target];
                int with = Integer.MIN_VALUE;
                int rodLen = i + 1;
                if (rodLen <= target) {
                    with = price[i] + dp[i][target - rodLen];
                }

                dp[i][target] = Math.max(with, without);
            }
        }

        return dp[price.length - 1][n];

    }
}
