package dynamicProgramming;

import java.util.Arrays;

/**
 * position i in array refers to the cost of shirt on ith day.
 * return total cost of shirts and the order in which they should be bought so that total cost is minimum.
 * do not buy two shirts of same color on consecutive days.
 *
 * <p>
 * For inputs
 * • blueCosts = [18, 12, 1, 9],
 * • greenCosts = [13, 15,7,9],
 * • redCosts = [12, 16,4,8]
 * <p>
 * output: ['r', 'g' b', 'r'] (total cost of 36).
 */
public class MinimiseShirtCost {

    public static void main(String[] args) {
        MinimiseShirtCost msc = new MinimiseShirtCost();
        int[] blueCosts = {18, 12, 1, 9};
        int[] greenCosts = {13, 15, 7, 9};
        int[] redCosts = {12, 16, 4, 8};
        System.out.println(msc.LowestCostRecursion(blueCosts, greenCosts, redCosts));

        int[] blueCosts1 = {100, 1, 76, 14};
        int[] greenCosts1 = {22, 20, 1, 2};
        int[] redCosts1 = {99, 99, 5, 12};
        System.out.println(msc.LowestCostRecursion(blueCosts1, greenCosts1, redCosts1));
    }

    public int LowestCost(int[] blueCosts, int[] greenCosts, int[] redCosts) {
        int n = blueCosts.length;

        // 2D DP array to store minimum costs
        // dp[i][j] represents minimum cost up to day i, where shirt on day i is color j
        // j: 0 = blue, 1 = green, 2 = red
        int[][] dp = new int[n][3];

        // Tracks the color chosen on each day for backtracking
        // Initialize first day costs
        dp[0][0] = blueCosts[0];   // blue
        dp[0][1] = greenCosts[0];  // green
        dp[0][2] = redCosts[0];    // red

        for (int day = 1; day < n; day++) {
            // Blue shirt on current day
            dp[day][0] = Math.min(dp[day - 1][1] + blueCosts[day],
                    dp[day - 1][2] + blueCosts[day]);
            // Green shirt on current day
            dp[day][1] = Math.min(dp[day - 1][0] + greenCosts[day],
                    dp[day - 1][2] + greenCosts[day]);

            // Red shirt on current day
            dp[day][2] = Math.min(dp[day - 1][0] + redCosts[day],
                    dp[day - 1][1] + redCosts[day]);

        }

        // Find minimum total cost
        return Math.min(dp[n - 1][0],
                Math.min(dp[n - 1][1], dp[n - 1][2]));
    }

    public int LowestCostRecursion(int[] blueCosts, int[] greenCosts, int[] redCosts) {
        // Memoization array to cache results
        // memo[day][color] represents minimum cost from this day onwards
        // color: 0 = blue, 1 = green, 2 = red, 3 = no previous color
        int[][] memo = new int[blueCosts.length][4];

        // Initialize memoization array with -1 to indicate uncalculated states
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // Find minimum cost by calling recursive method
        return findMinimumCost(blueCosts, greenCosts, redCosts, 0, 3, memo);
    }

    private int findMinimumCost(
            int[] blueCosts,
            int[] greenCosts,
            int[] redCosts,
            int day,
            int prevColor,
            int[][] memo
    ) {
        // Base case: if we've processed all days
        if (day == blueCosts.length) {
            return 0;
        }

        // Check if result is already memoized
        if (memo[day][prevColor] != -1) {
            return memo[day][prevColor];
        }

        // Initialize minimum cost to a large value
        int minCost = Integer.MAX_VALUE;

        // Try buying a blue shirt
        if (prevColor != 0) {
            int blueCost = blueCosts[day] + findMinimumCost(
                    blueCosts, greenCosts, redCosts,
                    day + 1, 0, memo
            );
            minCost = Math.min(minCost, blueCost);
        }

        // Try buying a green shirt
        if (prevColor != 1) {
            int greenCost = greenCosts[day] + findMinimumCost(
                    blueCosts, greenCosts, redCosts,
                    day + 1, 1, memo
            );
            minCost = Math.min(minCost, greenCost);
        }

        // Try buying a red shirt
        if (prevColor != 2) {
            int redCost = redCosts[day] + findMinimumCost(
                    blueCosts, greenCosts, redCosts,
                    day + 1, 2, memo
            );
            minCost = Math.min(minCost, redCost);
        }

        // Memoize and return the result
        memo[day][prevColor] = minCost;
        return minCost;
    }
}
