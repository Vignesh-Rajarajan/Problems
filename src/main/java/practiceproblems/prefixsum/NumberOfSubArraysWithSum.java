package practiceproblems.prefixsum;

import java.util.HashMap;
import java.util.Map;
//https://leetcode.com/problems/binary-subarrays-with-sum/
// tricky revise
public class NumberOfSubArraysWithSum {
    public int numSubarraysWithSum(int[] nums, int goal) {
        int currSum=0;
        int result = 0;
        Map<Integer,Integer> freq = new HashMap<>();

        for(int i: nums){
            currSum+=i;
            if(currSum == goal){
                result++;
            }
            //currSum - goal represents the sum we would need to have seen earlier
            // in the array to have a subarray that adds up to goals
            //Let's say goal is 3, and we're iterating through the array. At some point, currSum is 5.
            // Then, currSum - goal would be 2. This means that if we've seen a sum of 2 before,
            // then the subarray between that point and the current point adds up to 3 (because 5 - 2 = 3).
            if(freq.containsKey(currSum - goal)){
                result+=freq.get(currSum - goal);
            }

            freq.put(currSum,freq.getOrDefault(currSum,0)+1);
        }

        return result;
    }

    public int numSubarraysWithSumOptimised(int[] nums, int goal) {
        int start = 0;
        int prefixZeros = 0;
        int currentSum = 0;
        int totalCount = 0;

        for (int end = 0; end < nums.length; end++) {
            currentSum += nums[end];

            //If the current window sums to goal, and there are prefixZeros leading zeros,
            //then each of these zeros can be excluded or included to form new valid subarrays. For example:
            //If the window is [0, 0, 1, 2] and goal = 3, the valid subarrays are:
            //[0, 0, 1, 2]
            //[0, 1, 2]
            //[1, 2]
            //Here, the two leading zeros (prefixZeros = 2) allow us to form 3 valid subarrays (1 + prefixZeros = 3).
            while (start < end && (nums[start] == 0 || currentSum > goal)) {
                //When the start element is 0, it means we can exclude it from the window without changing the sum.
                // This increases prefixZeros.
                //When the start element is 1, it resets prefixZeros to 0 because we cannot exclude 1 without changing the sum.
                if (nums[start] == 1) {
                    prefixZeros = 0;
                } else {
                    prefixZeros++;
                }

                currentSum -= nums[start];
                start++;
            }

            if (currentSum == goal) {
                totalCount += 1 + prefixZeros;
            }
        }

        return totalCount;
    }
}
