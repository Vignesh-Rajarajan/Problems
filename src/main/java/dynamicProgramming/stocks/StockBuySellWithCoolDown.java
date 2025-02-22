package dynamicProgramming.stocks;

public class StockBuySellWithCoolDown {

    public int maxProfit(int[] prices) {

        if (prices == null || prices.length == 0)
            return 0;

        int n = prices.length;
        int[] buy = new int[n];
        int[] sold = new int[n];
        int[] rest = new int[n];

        // Initial conditions
        buy[0] = -prices[0]; // Buying the stock on the first day
        sold[0] = 0; // No profit as we haven't sold anything yet
        rest[0] = 0; // No profit as we haven't done anything

        for (int i = 1; i < n; i++) {
            buy[i] = Math.max(buy[i - 1], rest[i - 1] - prices[i]);
            sold[i] = buy[i - 1] + prices[i];
            rest[i] = Math.max(rest[i - 1], sold[i - 1]);
        }

        return Math.max(sold[n - 1], rest[n - 1]);
    }

    public int maxProfitExtraSpace(int[] prices) {
        int n = prices.length;
        if (n < 2) return 0;

        int[][] dp = new int[n][2];

        // Base cases
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[1][0] = Math.max(dp[0][0], dp[0][1] + prices[1]);
        dp[1][1] = Math.max(dp[0][1], dp[0][0] - prices[1]);

        for (int i = 2; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // (buy) state now considers the previous two days' state (dp[i - 2][0])
            // instead of just the previous day.
            // This is to account for the cool-down period,
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }

        return dp[n - 1][0];
    }

    public int maxProfitRecursive(int[] prices) {
        Integer[][] dp = new Integer[prices.length][2];
        return recursionHelper(prices, 0, 0, dp);
    }

    public int recursionHelper(int[] prices, int idx, int canSell, Integer[][] dp) {
        if (idx >= prices.length) return 0;
        if (dp[idx][canSell] != null) return dp[idx][canSell];
        if (canSell == 0) {
            int buy = -prices[idx] + recursionHelper(prices, idx + 1, 1, dp);
            int notBuy = recursionHelper(prices, idx + 1, 0, dp);
            return dp[idx][canSell] = Math.max(buy, notBuy);
        } else {
            int sell = prices[idx] + recursionHelper(prices, idx + 2, 0, dp);
            int notSell = recursionHelper(prices, idx + 1, 1, dp);
            return dp[idx][canSell] = Math.max(sell, notSell);
        }
    }
}
