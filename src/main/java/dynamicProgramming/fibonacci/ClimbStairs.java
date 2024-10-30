package dynamicProgramming.fibonacci;

import java.util.HashMap;

// https://leetcode.com/problems/climbing-stairs/
// https://leetcode.com/problems/min-cost-climbing-stairs/
public class ClimbStairs {
    private HashMap<Integer, Integer> cache = new HashMap<>();

    public int minCostClimbingStairs(int[] cost) {
        if (cost.length == 2) return Math.min(cost[0], cost[1]);
        int[] dp = new int[cost.length + 1];
        dp[0] = cost[0];
        dp[1] = cost[1];

        for (int i = 2; i < cost.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }

        return Math.min(dp[cost.length - 1], dp[cost.length - 2]);
    }

    public int minCostClimbingStairsSpaceOptimised(int[] cost) {
        int n = cost.length;
        int twoStepBehind = cost[0];
        int oneStepBehind = cost[1];
        if (n == 2)
            return Math.min(twoStepBehind, oneStepBehind);
        for (int i = 2; i < n; i++) {
            int curr = cost[i] + Math.min(twoStepBehind, oneStepBehind);
            //we're "sliding" our window forward:
            //What was the current step (stored in oneStepBehind) becomes the previous step (now stored in twoStepBehind).
            //The newly calculated cost for the next step (stored in curr)
            // becomes the current step (now stored in oneStepBehind).
            twoStepBehind = oneStepBehind;
            oneStepBehind = curr;
        }
        return Math.min(twoStepBehind, oneStepBehind);
    }

    public int minCostClimbingStairsRecursion(int[] cost) {
        if (cost.length == 2) {
            return Math.min(cost[0], cost[1]);
        }

        return Math.min(recursionHelper(cost, 0), recursionHelper(cost, 1));
    }

    public int recursionHelper(int[] cost, int idx) {
        //You pay the cost only when you land on a step, not when you reach the top.
        if (idx >= cost.length) {
            return 0;
        }
        if (cache.containsKey(idx)) {
            return cache.get(idx);
        }
        int cost1 = recursionHelper(cost, idx + 1) + cost[idx];
        int cost2 = recursionHelper(cost, idx + 2) + cost[idx];
        cache.put(idx, Math.min(cost1, cost2));
        return Math.min(cost1, cost2);
    }

    // this can be used if we call recursionHelper2(cost, cost.length)
    public int recursionHelper2(int[] cost, int idx) {
        if (idx == 0) return 0;

        int jump2 = Integer.MAX_VALUE;
        int jump1 = recursionHelper(cost, idx - 1) + Math.min(cost[idx - 1], cost[idx]);
        if (idx - 2 >= 0) {
            jump2 = recursionHelper(cost, idx - 2) + Math.min(cost[idx - 2], cost[idx]);
        }
        return Math.min(jump1, jump2);
    }
}
