package dynamicProgramming.stocks;

// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
public class BuySell2 {

    public int maxProfit(int[] prices) {
        Integer[][] cache = new Integer[prices.length][2];
        return helper(prices, 0, 1, cache); // Added totalProfit parameter
    }

    private int helper(int[] prices, int idx, int canBuy, Integer[][] cache) {
        if (idx >= prices.length) {
            return 0;
        }

        if (cache[idx][canBuy] != null) {
            return cache[idx][canBuy];
        }

        if (canBuy == 1) { // Not holding stock
            // Either buy at current price or skip
            return cache[idx][canBuy] = Math.max(
                    helper(prices, idx + 1, 0, cache) - prices[idx], // buy, you need to subtract the price
                    helper(prices, idx + 1, 1, cache) // skip
            );
        } else { // Holding stock

            return cache[idx][canBuy] = Math.max(
                    helper(prices, idx + 1, 1, cache) + prices[idx], // sell and add price to profit
                    helper(prices, idx + 1, 0, cache) // keep holding
            );
        }
    }

    public int maxProfitTabulation(int[] prices) {
        int[][] dp = new int[prices.length][2];

        // Base case: on day 0
        dp[0][0] = 0;           // Not holding any stock
        dp[0][1] = -prices[0];  // Buy stock on day 0

        for (int i = 1; i < prices.length; i++) {
            // If not holding stock (j=0)
            dp[i][0] = Math.max(
                    dp[i - 1][0],         // Continue not holding
                    dp[i - 1][1] + prices[i]  // Sell stock
            );

            // If holding stock (j=1)
            dp[i][1] = Math.max(
                    dp[i - 1][1],         // Continue holding
                    dp[i - 1][0] - prices[i]  // Buy stock
            );
        }

        return Math.max(dp[prices.length - 1][0], dp[prices.length - 1][1]);
    }
}
