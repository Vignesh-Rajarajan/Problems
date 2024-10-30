package dynamicProgramming.subsequence;

// https://www.naukri.com/code360/problems/count-subsets-with-sum-k
public class CountSubsetSumEqualToK {

    public static int findWays(int[] arr, int sum) {
        int n = arr.length;
        final int MOD = 1000000007;

        // dp[i][j] represents number of subsets using elements from index 0 to i that sum to j
        int[][] dp = new int[n][sum + 1];

        // Initialize base case for first element (index 0)
        // If arr[0] is 0, there are two ways to make sum 0 - take it or leave it
        dp[0][0] = 1;
        if (arr[0] <= sum) {
            dp[0][arr[0]] = 1;
        }
        // Special case: if first element is 0, we have two ways to make sum 0
        if (arr[0] == 0) {
            dp[0][0] = 2;
        }

        // Fill the dp table
        for (int index = 1; index < n; index++) {
            for (int target = 0; target <= sum; target++) {
                // Don't take current element
                long notTake = dp[index - 1][target];

                // Take current element if possible
                long take = 0;
                if (arr[index] <= target) {
                    take = dp[index - 1][target - arr[index]];
                }

                // Store result with modulo
                dp[index][target] = (int) ((take + notTake) % MOD);
            }
        }

        return dp[n - 1][sum];
    }

    public static int countSubsets(int[] arr, int sum) {
        int n = arr.length;
        Integer[][] dp = new Integer[n][sum + 1];
        return solve(arr, n - 1, sum, dp);
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

    public static void main(String[] args) {
        int[] num = {0, 1, 3};
        int k = 4;
        System.out.println(countSubsets(num, k));
    }
}
