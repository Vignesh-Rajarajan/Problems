package practiceproblems;

import java.util.TreeMap;


//https://leetcode.com/problems/hand-of-straights/description/
//
public class HandOfStraight {
    public boolean isPossibleDivide(int[] nums, int k) {
        // If the total number of elements is not divisible by k, it's impossible to divide.
        if (nums.length % k != 0) {
            return false;
        }

        // Use a TreeMap to store the frequency of each number in sorted order.
        TreeMap<Integer, Integer> numFrequencyMap = new TreeMap<>();
        for (int num : nums) {
            numFrequencyMap.put(num, numFrequencyMap.getOrDefault(num, 0) + 1);
        }

        // Try to form groups of size k.
        while (!numFrequencyMap.isEmpty()) {
            // Get the smallest number available in the map.
            int start = numFrequencyMap.firstKey();

            // Attempt to form a consecutive sequence of length k starting from 'start'.
            for (int i = 0; i < k; i++) {
                int currentNum = start + i; // this iterates through the consecutive sequence.

                // If the current number is missing, return false.
                if (!numFrequencyMap.containsKey(currentNum)) {
                    return false;
                }

                // Decrease the frequency of the current number.
                int count = numFrequencyMap.get(currentNum);
                if (count == 1) {
                    numFrequencyMap.remove(currentNum); // Remove the number if its count becomes zero.
                } else {
                    numFrequencyMap.put(currentNum, count - 1); // Otherwise, decrease the count.
                }
            }
        }

        // If all groups are successfully formed, return true.
        return true;
    }
}
