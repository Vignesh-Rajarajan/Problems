package combinationsandpermutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode.com/problems/permutations/
 */
public class Permutations {

    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) return Collections.emptyList();
        List<List<Integer>> result = new ArrayList<>();
        permuteUtils(nums, result, new ArrayList<>(), new HashSet<>());

        return result;
    }

    public void permuteUtils(int[] nums, List<List<Integer>> result, List<Integer> tempList, Set<Integer> set) {

        if (tempList.size() == nums.length) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        for (int num : nums) {
//            1 + (permutations of 2, 3, 4)
//
//            2 + (permutations of 1, 3, 4)
//
//            3 + (permutations of 1, 2, 4)
//
//            4 + (permutations of 1, 2, 3)
            if (set.contains(num)) continue;
            tempList.add(num);
            set.add(num);
            permuteUtils(nums, result, tempList, set);
            set.remove(tempList.get(tempList.size() - 1));
            tempList.remove(tempList.size() - 1);
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, boolean[] used) {
        if (tempList.size() == nums.length) {
            list.add(new ArrayList<>(tempList));
        } else {
            for (int i = 0; i < nums.length; i++) {
                //[1, 1, 2][1, 2, 1][2, 1, 1]
                //[1, 2, 3][1, 3, 2][2, 1, 3][2, 3, 1][3, 1, 2][3, 2, 1]
                if (used[i] || i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
                used[i] = true;
                tempList.add(nums[i]);
                backtrack(list, tempList, nums, used);
                used[i] = false;
                tempList.remove(tempList.size() - 1);
            }
        }
    }

}


