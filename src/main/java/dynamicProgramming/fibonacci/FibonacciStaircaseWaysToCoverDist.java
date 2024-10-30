package dynamicProgramming.fibonacci;

import java.util.Arrays;
import java.util.HashMap;

// https://leetcode.com/problems/climbing-stairs/
public class FibonacciStaircaseWaysToCoverDist {


    public static void main(String args[]) {
        FibonacciStaircaseWaysToCoverDist fs = new FibonacciStaircaseWaysToCoverDist();
        System.out.println(fs.fibonacciSeries(4));
        System.out.println(fs.fibonacciSeriesRecursive(3));
    }

    public int fibonacciSeriesRecursive(int n) {
        if (n == 1)
            return 2;
        if (n == 2)
            return 3;
        return fibonacciSeriesRecursive(n - 1) + fibonacciSeriesRecursive(n - 2);
    }

    public int fibonacciSeries(int n) {
        int n1 = 0;
        int n2 = 1;
        int sum;

        if (n == n1 || n == n2) {
            return n;
        }

        for (int i = 1; i <= n; i++) {
            sum = n1 + n2;
            n1 = n2;
            n2 = sum;
        }
        return n2;
    }

    public int climbStairsBottomUp(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int stair = 3; stair <= n; ++stair) {
            dp[stair] += dp[stair - 1] + dp[stair - 2];
        }

        return dp[n];
    }

    public int climbStairs(int N) {
        int[] cache = new int[N + 1];
        Arrays.fill(cache, -1);
        return fibUtil(N, 0, cache);
    }

    public int fibUtil(int N, int start, int[] cache) {
        if (start > N) return 0;

        if (N == start) return 1;

        if (cache[start] != -1) return cache[start];

        cache[start] = fibUtil(N, start + 1, cache) + fibUtil(N, start + 2, cache);

        return cache[start];
    }
}


