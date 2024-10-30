package dynamicProgramming.fibonacci;

import java.util.HashMap;
import java.util.Map;

public class ClimbStairsWithKSteps {
    private Map<Integer, Integer> cache = new HashMap<>();

    /**
     * public int minimizeCost(int k, int cost[]) {
     * return minCostWithKStepsRecursion(cost, k, 0);
     * }
     * <p>
     * private int minCostWithKStepsRecursion(int[] cost, int k, int idx) {
     * if (idx >= cost.length-1) return 0;
     * if (cache.containsKey(idx)) return cache.get(idx);
     * int minCost = Integer.MAX_VALUE;
     * <p>
     * for (int i = 1; i <= k && idx + i < cost.length; i++) {
     * int nextCost = minCostWithKStepsRecursion(cost, k, idx + i) + Math.abs(cost[idx + i] - cost[idx]);
     * minCost = Math.min(minCost, nextCost);
     * }
     * cache.put(idx, minCost);
     * return minCost;
     * }
     */
    public int minimizeCost(int k, int[] cost) {
        return minCostWithKStepsRecursion(cost, k, cost.length - 1);
    }


    private int minCostWithKStepsRecursion(int[] cost, int k, int idx) {
        if (idx == 0) return 0;
        if (cache.containsKey(idx)) return cache.get(idx);
        int minCost = Integer.MAX_VALUE;

        for (int i = 1; i <= k && idx - i >= 0; i++) {
            int cost1 = minCostWithKStepsRecursion(cost, k, idx - i) + Math.abs(cost[idx - i] - cost[idx]);
            minCost = Math.min(minCost, cost1);
        }
        cache.put(idx, minCost);
        return minCost;
    }


    public int minCostTabulation(int[] cost, int k) {
        int n = cost.length;
        int[] dp = new int[n];
        for (int i = 1; i < n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 1; j <= k && i - j >= 0; j++) {
                dp[i] = Math.min(dp[i], dp[i - j] + Math.abs(cost[i] - cost[i - j]));
            }
        }
        return dp[n - 1];
    }
}
