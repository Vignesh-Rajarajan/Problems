package practiceproblems;

import java.util.LinkedList;
import java.util.Queue;

//https://leetcode.com/problems/max-consecutive-ones-iii/
public class MaxConsecutiveOnes {

    //Input: nums = [1,0,1,1,0]
    //Output: 4
    //Explanation:
    //- If we flip the first zero, nums becomes [1,1,1,1,0] and we have 4 consecutive ones.
    //- If we flip the second zero, nums becomes [1,0,1,1,1] and we have 3 consecutive ones.
    //The max number of consecutive ones is 4.
    public int findMaxConsecutiveOnesWithOneReplacement(int[] nums){
        int left = 0, right = 0, max = 0;

        //Move right to expand the window.
        //If nums[right] is zero:
        //If nums[left] is also zero, move left to the right to exclude the zero at left.
        //Otherwise, move left to right + 1 to include the zero at right.
        while (right < nums.length) {
            if (nums[right] == 0) {
                if (nums[left] == 0) {
                    left++;
                } else {
                    left = right + 1;
                }
            }
            max = Math.max(max, right - left + 1);
            right++;
        }

        return max;
    }

    private int left = 0;
    private int right = 0;
    private int max = 0;
    private final Queue<Integer> zeroPositions = new LinkedList<>();

    public int findMaxConsecutiveOnesStreamWithOneReplacement(int num) {
        if (num == 0) {
            zeroPositions.add(right);
            if (zeroPositions.size() > 1) {
                left = zeroPositions.poll() + 1;
            }
        }
        max = Math.max(max, right - left + 1);
        right++;
        return max;
    }

    public int longestOnes(int[] nums, int k) {
        int left=0,right=0,result=0;
        int zeroCount=0;

        while(right<nums.length){
            if(nums[right]==0){
                zeroCount++;
            }

            while(zeroCount>k){
                if(nums[left]==0){
                    zeroCount--;
                }
                left++;
            }

            result = Math.max(result,right-left+1);
            right++;
        }

        return result;
    }
}
