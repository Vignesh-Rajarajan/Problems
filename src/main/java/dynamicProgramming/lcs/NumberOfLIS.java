package dynamicProgramming.lcs;

import java.util.Arrays;

/**
 * tricky lis
 * https://www.youtube.com/watch?v=_eHbuLHo6pM&ab_channel=LearnCodeRepeat
 * <p>
 * While you're iterating over all the elements from '0' to 'i-1',
 * <p>
 * First check whether the addition of the current element will form a LIS or not.
 * (This statements denotes to check condition of nums[i] > nums[j])
 * <p>
 * If a LIS is forming with the inclusion of element nums[i], then only a simple problem remains - Whether a subsequece of an equal length is already present or not.
 * <p>
 * To resolve this problem, check whether you've already acheived a LIS of that length or not by simply comparing with the count[i].
 * <p>
 * If yes, the count all the subsequences that have been formed at index 'j' and add them to the subsequences formed without subsequences ending at nums[j].
 * (This statement denotes - dp[j] + 1 == dp[i])
 * <p>
 * If not, then count all the subsequences formed with the subsequences ending at nums[j]
 * (This statement denotes - dp[j] + 1 > dp[i])
 * <p>
 * Arr => 1, 3, 5, 4, 7
 * dp =>  1, 2, 3, 3, 4
 * cnt=>  1, 1, 1, 1, 2  when i at 7 and j at 4 dp[j] + 1 == dp[i]  so count is 2
 */
public class NumberOfLIS {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] count = new int[n];
        int[] lis = new int[n];

        Arrays.fill(count, 1);
        Arrays.fill(lis, 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {

                if (nums[i] > nums[j] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                    count[i] = count[j];
                }
                // if lis is seen before then add the count of that lis
                else if (nums[i] > nums[j] && lis[i] == lis[j] + 1) {
                    count[i] += count[j];
                }
            }
        }

        // find the max length of lis
        // and add all the count of that length
        int max = Arrays.stream(lis).max().orElse(-1);
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (max == lis[i]) {
                result += count[i];
            }
        }

        return result;

    }
}
