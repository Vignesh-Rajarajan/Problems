package dynamicProgramming.subsequence;

// https://leetcode.com/problems/coin-change-2/
public class CoinChangeII {
    public int change(int amount, int[] coins) {
        Integer[][] cache = new Integer[coins.length][amount + 1];
        return recursionUtil(coins, coins.length - 1, amount, cache);
    }

    int recursionUtil(int[] coins, int idx, int amount, Integer[][] cache) {
        if (amount < 0) {
            return 0; // Need 0 coins to make amount 0
        }
        if (idx < 0) {
            return amount == 0 ? 1 : 0;
        }

        if (cache[idx][amount] != null) {
            return cache[idx][amount];
        }
        // Use current coin and stay at same index (since we can reuse)
        int include = recursionUtil(coins, idx, amount - coins[idx], cache);
        // Don't use current coin at all - move to next coin
        int exclude = recursionUtil(coins, idx - 1, amount, cache);

        return cache[idx][amount] = include + exclude;
    }

    public int changeOptimised(int amount, int[] coins) {
        int[] dp = new int[amount + 1];

        // Base case: 0 amount needs 0 coins
        dp[0] = 1;

        // we need to avoid counting the same combination multiple times. For example:
        //If amount = 5 and coins = [1,2]
        //We don't want to count [1,2,2] and [2,1,2] as different combinations
        //By processing one coin at a time completely, we ensure each combination is counted exactly once
        // Let's see a concrete example with amount = 3, coins = [1,2]:
        // wrong order:
        //amount = 1: check coin 1 → [1]
        //amount = 1: check coin 2 → can't use
        //amount = 2: check coin 1 → [1,1]
        //amount = 2: check coin 2 → [2]
        //amount = 3: check coin 1 → [1,1,1], [1,2]
        //amount = 3: check coin 2 → [2,1]  // Problem! [2,1] is same as [1,2]

        // correct order:
        // coin = 1:
        //  amount = 1 → [1]
        //  amount = 2 → [1,1]
        //  amount = 3 → [1,1,1]
        //
        //coin = 2:
        //  amount = 1 → can't use
        //  amount = 2 → [2]
        //  amount = 3 → [2,1]
        for (int coin : coins) {
            for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
                if (coin <= currentAmount) {
                    dp[currentAmount] += dp[currentAmount - coin];
                }
            }
        }

        return dp[amount];
    }
}
