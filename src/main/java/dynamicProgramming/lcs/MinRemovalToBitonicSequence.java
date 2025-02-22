package dynamicProgramming.lcs;

import java.util.Arrays;

// https://leetcode.com/problems/minimum-number-of-removals-to-make-mountain-array
public class MinRemovalToBitonicSequence {
    //Concept: We need to find the maximum number of elements of the array that can be
    //	involved in a mountain array. We know, that a mountain array contains a peak element
    //	and there is an increasing subsequence in the left of the peak and a decreasing subsequence in the right.
    //	So, we need to find out the element(peak), for which the total number of elements from the
    //	original array involved in the left increasing subsequence and the right decreasing
    //	subsequence, in maximum. This will create a mountain array with the peak element.
    //	Then, we can delete the rest of the elements of the array not involved in this mountain array.
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] left = new int[n]; // maximum increasing subsequence in the left of an element.
        int[] right = new int[n]; // maximum increasing subsequence in the left of an element.
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);

        // calculating maximum increasing subsequence for the left of an index.
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && left[i] < left[j] + 1)
                    left[i] = left[j] + 1;
            }
        }

        // calculating maximum increasing subsequence for the right of an index.
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (nums[j] < nums[i] && right[i] < right[j] + 1)
                    right[i] = right[j] + 1;
            }
        }

        // calculating the maximum number of elements that can be involved in a mountain array.
        int max = 0;
        for (int i = 1; i < n - 1; i++) {
            if (right[i] > 1 && left[i] > 1) // if element nums[i] is a valid peak,
                max = Math.max(max, left[i] + right[i] - 1);
        }

        // we need to delete the rest of the elements.
        return n - max;
    }
}
