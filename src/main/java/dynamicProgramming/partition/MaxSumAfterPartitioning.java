package dynamicProgramming.partition;

public class MaxSumAfterPartitioning {

    public int maxSumAfterPartitioning(int[] arr, int k) {
        Integer[] cache = new Integer[arr.length + 1];
        return recursionHelper(arr, 0, k, cache);
    }

    int recursionHelper(int[] arr, int idx, int k, Integer[] cache) {
        if (idx >= arr.length) {
            return 0;
        }
        if (cache[idx] != null) {
            return cache[idx];
        }
        int len = 0;
        int max = Integer.MIN_VALUE;
        int maxSum = Integer.MIN_VALUE;
        for (int i = idx; i < Math.min(arr.length, idx + k); i++) {
            len++;
            max = Math.max(arr[i], max);
            maxSum = Math.max(maxSum, max * len + recursionHelper(arr, i + 1, k, cache));
        }

        return cache[idx] = maxSum;
    }

    public int maxSumAfterPartitioningTabulation(int[] arr, int k) {
        int[] dp = new int[arr.length + 1];

        for (int idx = arr.length - 1; idx >= 0; idx--) {
            int len = 0;
            int max = Integer.MIN_VALUE;
            int maxSum = Integer.MIN_VALUE;
            for (int i = idx; i < Math.min(arr.length, idx + k); i++) {
                len++;
                max = Math.max(arr[i], max);
                maxSum = Math.max(maxSum, max * len + dp[i+1]);
            }
            dp[idx]=maxSum;
        }

        return dp[0];
    }
}
