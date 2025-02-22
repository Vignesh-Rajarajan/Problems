package dynamicProgramming.partition;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-cost-to-cut-a-stick
 * https://www.youtube.com/watch?v=xwomavsC86c
 * <p>
 * the difference with other rod cutting problem is
 * Must make all specified cuts, Each cut position is used exactly once
 */
public class RodCuttingMinimise {

    public static void main(String args[]) {
        RodCuttingMinimise cr = new RodCuttingMinimise();
        int[] price = {1, 5, 3, 6};
        System.out.println(cr.minCost(9, price));
    }

    public int minCost(int n, int[] cuts) {
        int len = cuts.length + 2;
        int[] cutsWithLen = new int[len];

        for (int i = 1; i < len - 1; i++)
            cutsWithLen[i] = cuts[i - 1];
        cutsWithLen[len - 1] = n;
        Arrays.sort(cutsWithLen);
        int[][] dp = new int[len][len];

        for (int i = len - 2; i > 0; i--) {
            for (int j = i; j <= len - 2; j++) {

                int min = Integer.MAX_VALUE;
                int cutLen = cutsWithLen[j + 1] - cutsWithLen[i - 1];
                for (int k = i; k <= j; k++) {
                    int cost = cutLen + dp[i][k - 1] + dp[k + 1][j];
                    min = Math.min(min, cost);
                }


                dp[i][j] = min;
            }
        }

        return dp[1][len - 2];
    }

    /**
     * The algorithm is quite Brute Force, we would try to generate all possible permutations of cuts,
     * and would try to know what permutation would lead to best result, i.e. minimize our cost for cutting.
     * <p>
     * Let us suppose we are currently having a wood piece from index l to index r (i.e. the length of the wood is r - l, indexing is done as illustrated in the problem).
     * Now, we try every possible cut that we could perform in the range from l to r.
     * <p>
     * Since a cut (let's say, cut is at i index) results in our original piece to further split into 2 parts (one from [l, i], and second from [i, r]).
     * Also, lets suppose the minimum cost of cutting, the segment [l, i] is minLeft and similarly for [i, r] is minRight.
     * Hence the cost to cut the rod segment [l, r] would be cost_i = minLeft + minRight + (r - l) (r - l is the cost to perform the cut at i).
     * Similarly, a cut at j index would cost in total, say, cost_j, similarly at k be cost_k and so on...
     * <p>
     * The minimum cost to cut the rod from index l to r hence would be min(cost_i, cost_j, cost_k, ...).
     */
    public int minCostRecursive(int n, int[] cuts) {
        Integer[][] dp = new Integer[101][101];
        int[] cutsWithLen = Arrays.copyOf(cuts, cuts.length + 2);
        cutsWithLen[cutsWithLen.length - 1] = n;
        Arrays.sort(cutsWithLen);
        return recursionHelper(cutsWithLen, 1, cutsWithLen.length - 2, dp);
    }

    public int recursionHelper(int[] cutsWithLengthOfRod, int i, int j, Integer[][] dp) {
        if (i > j) return 0;
        if (dp[i][j] != null) return dp[i][j];
        int min = Integer.MAX_VALUE;

        for (int mid = i; mid <= j; mid++) {

            int cost = cutsWithLengthOfRod[j + 1] - cutsWithLengthOfRod[i - 1] +
                    recursionHelper(cutsWithLengthOfRod, i, mid - 1, dp) +
                    recursionHelper(cutsWithLengthOfRod, mid + 1, j, dp);
            min = Math.min(cost, min);
        }

        return dp[i][j] = min;

    }
}