package dynamicProgramming.subsequence;

import java.util.Arrays;

// https://leetcode.com/problems/target-sum/description/
// tricky tabulation edge case: if first element is 0, we have two ways to make sum 0
public class CountPartitionsWithGivenDifference {
    public static int findTargetSumWays(int[] arr, int difference) {
        int n = arr.length;
        // subset1 + subset2 = sum(arr)
        // subset1 - subset2 = difference
        // subset1 = sum(arr) - subset2
        // sum(arr) - subset2 - subset2 = difference
        // sum(arr) - 2 * subset2 = difference
        // subset2 = (sum(arr) - difference) / 2
        int sum = Arrays.stream(arr).sum();
        if (sum - difference < 0 || (sum - difference) % 2 == 1) return 0;
        int target = (sum - difference) / 2;
        Integer[][] dp = new Integer[n][target + 1];
        return solve(arr, n - 1, target, dp);
    }

    private static int solve(int[] arr, int index, int target, Integer[][] dp) {
        final int MOD = 1000000007;
        if (index < 0) { // remember this condition tricky
            // Return 1 only if target is 0, meaning we found a valid subset
            return target == 0 ? 1 : 0;
        }

        // If already calculated, return from memo
        if (dp[index][target] != null) {
            return dp[index][target];
        }

        // Don't take current element
        long notTake = solve(arr, index - 1, target, dp);

        // Take current element if it doesn't exceed target
        long take = 0;
        if (arr[index] <= target) {
            take = solve(arr, index - 1, target - arr[index], dp);
        }
        return dp[index][target] = (int) ((take + notTake) % MOD);
    }

    public int findTargetSumWaysIterative(int[] arr, int target) {
        int n = arr.length;
        int sum = Arrays.stream(arr).sum();
        if (sum - target < 0 || (sum - target) % 2 == 1)
            return 0;
        target = (sum - target) / 2;
        int[][] dp = new int[n][target + 1];

        // Handle first element edge case
        dp[0][0] = arr[0] == 0 ? 2 : 1;
        for (int i = 1; i < n; i++) {
            dp[i][0] = 1;
        }
        if (arr[0] != 0 && arr[0] <= target) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                int without = dp[i - 1][j];
                int with = j - arr[i] >= 0 ? dp[i - 1][j - arr[i]] : 0;

                dp[i][j] = with + without;
            }
        }

        return dp[n - 1][target];
    }
}
