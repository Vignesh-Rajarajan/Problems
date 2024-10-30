package dynamicProgramming.subsequence;

/**
 * Given a set of positive numbers, partition the set into two subsets with minimum difference between their subset sums.
 * Input: {1, 2, 3, 9}
 * Output: 3
 * Explanation: We can partition the given set into two subsets where minimum absolute difference
 * between the sum of numbers is '3'. Following are the two subsets: {1, 2, 3} & {9}.
 * <p>
 * Input: {1, 3, 100, 4}
 * Output: 92
 * Explanation: We can partition the given set into two subsets where minimum absolute difference
 * between the sum of numbers is '92'. Here are the two subsets: {1, 3, 4} & {100}.
 */
public class MinimumSubsetSum {

    int minSubsetSumDifference(int[] arr, int n) {
        int totSum = 0;
        // Calculate the total sum of the array elements
        for (int i = 0; i < n; i++) {
            totSum += arr[i];
        }

        // Create a DP table to store subset sum information
        boolean[][] dp = new boolean[n][totSum + 1];

        // Initialize the DP table for the first row
        for (int i = 0; i <= totSum; i++) {
            dp[0][i] = (i == arr[0]);
        }

        // Fill in the DP table using bottom-up dynamic programming
        for (int ind = 1; ind < n; ind++) {
            for (int target = 0; target <= totSum; target++) {
                // Calculate if the current element is not taken
                boolean notTaken = dp[ind - 1][target];

                // Calculate if the current element is taken
                boolean taken = false;
                if (arr[ind] <= target) {
                    taken = dp[ind - 1][target - arr[ind]];
                }

                // Update the DP table for the current element and target sum
                dp[ind][target] = notTaken || taken;
            }
        }

        int mini = Integer.MAX_VALUE;

        // Find the minimum absolute difference between two subsets
        for (int i = 0; i <= totSum; i++) {
            if (dp[n - 1][i]) {
                int diff = Math.abs(i - (totSum - i));
                mini = Math.min(mini, diff);
            }
        }
        return mini;
    }
}
