package practiceproblems.prefixsum;

// https://leetcode.com/problems/count-number-of-nice-subarrays/
// this is same as NumberOfSubArraysWithSum problem, if you assume odd numbers as 1 and even numbers as 0
public class NumberOfSubArrayWithOddCount {
    // careful with count of subarrays
    // Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
    // Output: 16
    public int numberOfSubarrays(int[] nums, int k) {
        int left = 0, right = 0;
        int oddCount = 0;
        int result = 0;
        int count = 0;
        while (right < nums.length) {

            if (nums[right] % 2 == 1) {
                oddCount++;
                count = 0;
            }
            while (oddCount >= k) {
                count++;
                if (nums[left] % 2 == 1) {
                    oddCount--;
                }
                left++;
            }
            result += count;

            right++;
        }

        return result;
    }
}
