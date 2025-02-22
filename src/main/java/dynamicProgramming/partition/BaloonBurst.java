package dynamicProgramming.partition;

// https://leetcode.com/problems/burst-balloons/
public class BaloonBurst {
    public int maxCoins(int[] nums) {
        int len = nums.length + 2;
        int[] newNums = new int[len];
        newNums[0] = 1;
        for (int i = 1; i < len - 1; i++) {
            newNums[i] = nums[i - 1];
        }
        newNums[len - 1] = 1;
        Integer[][] cache = new Integer[len][len];
        return recursionHelper(newNums, 1, nums.length, cache);
    }

    public int recursionHelper(int[] nums, int i, int j, Integer[][] cache) {
        if (i > j) {
            return 0;
        }

        if (cache[i][j] != null) {
            return cache[i][j];
        }

        int result = 0;
        for (int k = i; k <= j; k++) {
            int computation = nums[i - 1] * nums[k] * nums[j + 1] +
                    recursionHelper(nums, i, k - 1, cache) +
                    recursionHelper(nums, k + 1, j, cache);

            result = Math.max(result, computation);

        }

        return cache[i][j] = result;
    }

    public int maxCoinsTabulation(int[] num) {
        int n = num.length;
        int[] nums = new int[n + 2];
        System.arraycopy(num, 0, nums, 1, n);
        nums[0] = 1;
        nums[n + 1] = 1;
        int[][] dp = new int[n + 2][n + 2];
        for (int i = n; i >= 1; i--) {
            for (int j = i; j <= n; j++) {
                int cost = Integer.MIN_VALUE;
                for (int k = i; k <= j; k++) {
                    int prev = nums[i - 1], next = nums[j + 1];
                    cost = Math.max(
                            cost, dp[i][k - 1] +
                                    dp[k + 1][j] + (nums[k] * prev * next));
                }
                dp[i][j] = cost;
            }
        }
        return dp[1][n];
    }
}
