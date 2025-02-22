package bitwise;

import java.util.ArrayList;
import java.util.List;

public class Subset {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n=nums.length;
        /*

         Iteration 1: num = 0 (000 in binary), loop skips.
         Iteration 2: num = 1 (001), subset = [1].
         Iteration 3: num = 2 (010), subset = [2].
         Iteration 4: num = 3 (011), subset = [1, 3].
         Iteration 5: num = 4 (100), subset = [3].
         Iteration 6: num = 5 (101), subset = [2, 3].
         Iteration 7: num = 6 (110), subset = [1, 2].
         Iteration 8: num = 7 (111), subset = [1, 2, 3].
         */
        for (int i = 0; i < (1 << n); i++) {
            ArrayList<Integer> in = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                // For i = 5 (101) and j = 0:
                //(1 << 0) is 001
                //5 & 1 is 1 (non-zero), so include first element
                //For i = 5 (101) and j = 1:
                //(1 << 1) is 010
                //5 & 2 is 0 (zero), so skip second element
                //For i = 5 (101) and j = 2:
                //(1 << 2) is 100
                //5 & 4 is 4 (non-zero), so include third element
                if ((i & (1 << j)) != 0) {
                    in.add(nums[j]);
                }
            }
            result.add(in);
        }

        return result; // total complexity: n.2^n
    }
}
