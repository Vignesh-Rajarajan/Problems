package dynamicProgramming.subsequence;


import java.util.Arrays;

// https://leetcode.com/problems/partition-equal-subset-sum/
class EqualSubsetSumPartition {

// 			  0	  1	  2   3   4  5
//			+---+---+---+---+---+---+
//	{1}		| T | T | F | F | F | F |
//			+---+---+---+---+---+---+
//	{1,2}	| T | T | T | T | F | F |
//			+---+---+---+---+---+---+
//	{1,2,3}	| T | T | T | T | T | T |
//			+---+---+---+---+---+---+
//{1,2,3,4} | T | T | T | T | T | T |
//			+---+---+---+---+---+---+

    Boolean[][] cache;

    public static void main(String[] args) {
        EqualSubsetSumPartition ps = new EqualSubsetSumPartition();
        int[] num = {2, 3, 4, 5};
        System.out.println(ps.canPartition(num));
    }

    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 == 1)
            return false;
        int half = sum / 2;
        boolean[][] dp = new boolean[nums.length][half + 1];

        for (int i = 0; i < nums.length; i++) {
            dp[i][0] = true;
        }

        // It's saying "if the first number is not bigger than half,
        // then we can definitely form a sum equal to that number"
        if (nums[0] <= half) { // intialize first row
            dp[0][nums[0]] = true;
        }

        for (int i = 1; i < nums.length; i++) {
            int val = nums[i];
            for (int j = 1; j <= half; j++) {
                boolean with = j - val >= 0 && dp[i - 1][j - val];
                boolean without = dp[i - 1][j];
                dp[i][j] = with || without;
            }
        }
        return dp[nums.length - 1][half];
    }

    public boolean canPartitionBottomUp(int[] nums) {

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 == 1) return false;
        int target = sum / 2;
        cache = new Boolean[nums.length + 1][target + 1];
        return sumPossible(nums, 0, target);
    }

    public boolean sumPossible(int[] nums, int i, int target) {
        if (target < 0) return false;
        if (target == 0) return true;
        if (i >= nums.length) return false;
        if (cache[i][target] != null) return cache[i][target];

        return cache[i][target] = sumPossible(nums, i + 1, target - nums[i]) || sumPossible(nums, i + 1, target);
    }

    public boolean canPartitionSpace(int[] nums) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }

        if (sum % 2 == 1) return false;
        int half = sum / 2;
        boolean[] dp = new boolean[half + 1];
        dp[0] = true;
        // It's saying "if the first number is not bigger than half,
        // then we can definitely form a sum equal to that number"
        if (nums[0] <= half) dp[nums[0]] = true;

        for (int j = 1; j < nums.length; j++) {
            boolean[] current = new boolean[half + 1];
            current[0] = true;
            for (int target = 1; target <= half; target++) {
                boolean without = dp[j];
                boolean with = target - nums[j] >= 0 && dp[target - nums[j]];
                current[target] = with || without;
            }
            dp = current;
        }

        return dp[half];
    }
}
