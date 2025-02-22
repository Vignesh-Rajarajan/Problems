package dynamicProgramming.lcs;

import java.util.*;

// https://www.geeksforgeeks.org/problems/printing-longest-increasing-subsequence
public class PrintLIS {
    public List<Integer> longestIncreasingSubsequence(int n, int nums[]) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] result = new int[nums.length];
        Arrays.fill(result, 1);
        int maximumSoFar = 1;
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(i, x -> new ArrayList<>()).add(nums[i]);
        }

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && result[j] + 1 > result[i]) {
                    result[i] = result[j] + 1;
                    map.get(i).clear();
                    map.get(i).addAll(map.get(j));
                    map.get(i).add(nums[i]);

                }
            }
            maximumSoFar = Math.max(maximumSoFar, result[i]);
        }

        for (int key : map.keySet()) {
            if (map.get(key).size() == maximumSoFar) {
                return new ArrayList<>(map.get(key));
            }
        }

        return new ArrayList<>();
    }
}
