package dynamicProgramming.subsequence;

// https://www.geeksforgeeks.org/problems/subset-sum-problem-1611555638
// tricky tabulation condition
public class SubsetEqualSum {

    static boolean isSubsetSum(int[] arr, int sum) {
        Integer[][] cache = new Integer[arr.length][sum + 1];
        return recursionUtil(arr, 0, sum, cache);
    }

    static boolean recursionUtil(int[] arr, int idx, int sum, Integer[][] cache) {
        if (idx >= arr.length || sum <= 0) {
            return sum == 0;
        }

        if (cache[idx][sum] != null) {
            return cache[idx][sum] == 1;
        }

        boolean with = recursionUtil(arr, idx + 1, sum - arr[idx], cache);
        boolean without = recursionUtil(arr, idx + 1, sum, cache);
        cache[idx][sum] = with || without ? 1 : 0;
        return with || without;
    }

    //arr = [3, 4, 5]
    //sum = 9
    //    0  1  2  3  4  5  6  7  8  9   <- possible sums
    //0   T  F  F  ?  F  F  F  F  F  F   <- only using arr[0]=3
    //1   T  F  F  F  F  F  F  F  F  F   <- using up to arr[1]=4
    //2   T  F  F  F  F  F  F  F  F  F   <- using up to arr[2]=5
    //dp[0][0] = true  // Can we make sum=0 using just [3]? Yes, don't pick anything
    //dp[1][0] = true  // Can we make sum=0 using [3,4]? Yes, don't pick anything
    //dp[2][0] = true  // Can we make sum=0 using [3,4,5]? Yes, don't pick anything
    // When we only look at the first element (arr[0] = 3)
    //Can we make a sum of 3 using just this element? Yes!
    //So dp[0][3] = true
    boolean isSubsetSum(int N, int[] arr, int sum) {
        boolean[][] dp = new boolean[N][sum + 1];

        // Initialize first column as true
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }

        // Initialize first row based on arr[0]
        if (arr[0] <= sum) {
            dp[0][arr[0]] = true;
        }

        // Fill the dp table
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (arr[i] <= j) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }

        return dp[N - 1][sum];
    }
}
