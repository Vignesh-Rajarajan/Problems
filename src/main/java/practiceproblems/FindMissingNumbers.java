package practiceproblems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Input: [2, 3, 1, 8, 2, 3, 5, 1]
// Output: 4, 6, 7
public class FindMissingNumbers {

    //Brute Force
    // Set<Integer> set = new HashSet<>();
    // for (int i = 0; i < nums.length; i++) set.add(i + 1);
    // for (int i = 0; i < nums.length; i++) set.remove(nums[i]);
    // return new ArrayList<>(set);
    public List<Integer> findDisappearedNumbers(int[] nums) {
        if (nums.length == 0) return Collections.emptyList();
        int i = 0;
        // cyclic sort begins
        while (i < nums.length) {
            int j = nums[i] - 1;
            if (nums[i] != nums[j]) {
                swap(nums, i, j);
            } else {
                i++;
            }
        }
        // cyclic sort ends
        // when cyclic sort ends the i+1 element will be in correct index
        List<Integer> result = new ArrayList<>();
        i = 0;
        while (i < nums.length) {
            if (i + 1 != nums[i]) result.add(i + 1);
            i++;
        }
        return result;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public int missingNumber(int[] nums) {
        //using a bit modify version of cyclic sort
        int index = 0;
        int n = nums.length;
        while (index < n) {
            int correct = nums[index];
            if (nums[index] < n && nums[index] != nums[correct]) //ignore if value is equal to n
            {
                int temp = nums[index];
                nums[index] = nums[correct];
                nums[correct] = temp;

            } else {
                index++;
            }
        }

        //now search
        for (int i = 0; i < n; i++) {
            if (nums[i] != i)//we know every element should equal to its index
            {
                return i;
            }
        }
        //case 2 , when missing number is not in array
        return n;


    }
}