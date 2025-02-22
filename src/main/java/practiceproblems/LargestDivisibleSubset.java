package practiceproblems;

import java.util.*;

// https://leetcode.com/problems/largest-divisible-subset/
public class LargestDivisibleSubset {

    /**
     * if a%b==0 means a>b, if b>a then the ans is b itself
     * inorder to have that we need to sort the array in increasing order
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        Arrays.sort(nums);
        int[] lis = new int[n];
        Arrays.fill(lis, 1);
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(i, x -> new ArrayList<>()).add(nums[i]);
        }

        int maxLength = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 || nums[j] % nums[i] == 0) {
                    if (lis[j] + 1 > lis[i]) {
                        lis[i] = lis[j] + 1;
                        map.get(i).clear();
                        map.get(i).addAll(map.get(j));
                        map.get(i).add(nums[i]);
                    }
                }
            }

            maxLength = Math.max(maxLength, lis[i]);

        }

        for (int key : map.keySet()) {
            if (map.get(key).size() == maxLength) {
                return new ArrayList<>(map.get(key));
            }
        }

        return new ArrayList<>();
    }
}