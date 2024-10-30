package dynamicProgramming.subsequence;


//https://leetcode.com/problems/coin-change

import java.util.Arrays;

public class MinCoinsRequired {

    public int coinChange(int[] coins, int amount) {
        Integer[][] cache = new Integer[coins.length][amount + 1];
        int result = recursionUtil(coins, coins.length - 1, amount, cache);
        return result == Integer.MAX_VALUE - 1 ? -1 : result;
    }

    int recursionUtil(int[] coins, int idx, int amount, Integer[][] cache) {
        if (amount == 0) {
            return 0; // Need 0 coins to make amount 0
        }
        if (amount < 0 || idx < 0) {
            return Integer.MAX_VALUE - 1; // Invalid/impossible case
        }

        if (cache[idx][amount] != null) {
            return cache[idx][amount];
        }
        // Use current coin and stay at same index (since we can reuse)
        int include = 1 + recursionUtil(coins, idx, amount - coins[idx], cache); // Add 1 for the coin we used
        // Don't use current coin at all - move to next coin
        int exclude = recursionUtil(coins, idx - 1, amount, cache);

        return cache[idx][amount] = Math.min(include, exclude);
    }

    //Initial dp array: coins = [1,2,5], amount = 5:
    //     0  1  2  3  4  5
    //0: [ 0, 6, 6, 6, 6, 6]
    //1: [ 0, 0, 0, 0, 0, 0]
    //2: [ 0, 0, 0, 0, 0, 0]
    //3: [ 0, 0, 0, 0, 0, 0]
    public int coinChangeTabulation(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];

        // Initialize first row (except dp[0][0]) with impossible value
        // This represents the case when we have no coins but need some amount
        for (int j = 1; j <= amount; j++) {
            dp[0][j] = amount + 1;  // Using amount+1 as impossible value
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {

                // Don't take current coin
                int without = dp[i - 1][j];
                // Take current coin if possible
                int with = amount + 1;
                if (j - coins[i - 1] >= 0) {
                    with = 1 + dp[i][j - coins[i - 1]];
                }
                dp[i][j] = Math.min(with, without);
            }
        }

        return dp[coins.length][amount] == amount + 1 ? -1 : dp[coins.length][amount];

    }

    //coins = [1,2,5], amount = 11
    //Initial: [0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12]
    //After processing amount = 1:
    //[0, 1, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12]
    //(used coin 1)
    //After amount = 2:
    //[0, 1, 1, 12, 12, 12, 12, 12, 12, 12, 12, 12]
    //(used coin 2)
    //After amount = 3:
    //[0, 1, 1, 2, 12, 12, 12, 12, 12, 12, 12, 12]
    //(used coin 1)
    // ...
    // Final array:
    //[0, 1, 1, 2, 2, 1, 2, 2, 3, 3, 2, 3]
    public int coinChangeSpaceOptimised(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // Initialize with amount + 1 (impossible value as we need minimum)
        Arrays.fill(dp, amount + 1);
        // Base case: 0 amount needs 0 coins
        dp[0] = 0;
        // Try each coin
        for (int coin : coins) {
            // For each amount from 1 to target
            for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
                if (coin <= currentAmount) {
                    dp[currentAmount] = Math.min(dp[currentAmount],
                            1 + dp[currentAmount - coin]);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

}
