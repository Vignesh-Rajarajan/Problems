package geeksforgeeks;

/*https://leetcode.com/problems/search-in-rotated-sorted-array/
https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
 */
public class SearchElementInSortedAndRotatedArray {

    public static void main(String[] args) {
        int[] arr = { 4, 5, 6, 7, 0, 1, 2 };

        SearchElementInSortedAndRotatedArray search = new SearchElementInSortedAndRotatedArray();
        System.out.println(search.searchI(arr, 0));

        int[] nums = { 2, 5, 6, 0, 0, 1, 2 };
        System.out.println(search.searchII(nums, 2));
    }

    // 4, 5, 0, 1, 2, 3
    int searchI(int[] nums, int target) {

        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[start] <= nums[mid]) {
                if (target < nums[mid] && target >= nums[start]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }

            if (nums[mid] <= nums[end]) {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    //[2,5,6,0,0,1,2]
    //        0
    //duplicates, we know nums[mid] != target, so nums[start] != target //based on current information,
    // we can only move left pointer to skip one cell //thus in the worst case, we would have target: 2, and array like 11111111,
    // then //the running time would be O(n)
    public boolean searchII(int[] nums, int target) {

        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] == target) {
                return true;
            }

            // the only difference from the first one, tricky case, just update start and end
            if ((nums[start] == nums[mid]) && (nums[end] == nums[mid])) {
                ++start;
                --end;
            } else if (nums[start] <= nums[mid]) {
                if ((nums[start] <= target) && (nums[mid] > target)) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                if ((nums[mid] < target) && (nums[end] >= target)) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return false;
    }
}
