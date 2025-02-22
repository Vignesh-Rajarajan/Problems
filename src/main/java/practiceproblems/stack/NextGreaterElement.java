package practiceproblems.stack;

import java.util.*;

// Monotonic Stack: By iterating from right to left,
// we can maintain a monotonic stack (a stack where elements are in increasing order from top to bottom)
// https://leetcode.com/problems/next-greater-element-i/
class NextGreaterElement {

    static int arr[] = {1, 3, 4, 2};

    // 9,1,2,3,4,5,6,7
    public static void printNGE() {
        Stack<Integer> s = new Stack<>();
        int[] nge = new int[arr.length];

        for (int i = arr.length - 1; i >= 0; i--) {

            while (!s.empty() && s.peek() <= arr[i]) {
                s.pop();
            }
            nge[i] = s.empty() ? -1 : s.peek();
            s.push(arr[i]);

        }
        for (int i = 0; i < arr.length; i++)
            System.out.println(arr[i] + " --> " + nge[i]);

    }

    //     Input: [1,2,1]
    // Output: [2,-1,2]
    // Explanation: The first 1's next greater number is 2
    // The number 2 can't find next greater number;
    // The second 1's next greater number needs to search circularly, which is also 2.
    public static int[] nextGreaterElementCircular(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];
        int[] result = new int[nums.length];
        Arrays.fill(result, -1);
        Deque<Integer> deque = new ArrayDeque<>();
        // to mimic the circular array we iterate for 2*n because input [1,2,1] will be like [1,2,1,1,2,1]
        // and we take mod of 'n' to update the correct index
        for (int i = 2 * nums.length - 1; i >= 0; --i) {

            while (!deque.isEmpty() && nums[deque.peek()] <= nums[i % nums.length]) {
                deque.pop();
            }
            // The stack is either empty, when no "greater element" is found to the right of nums[i],
            // or contains the next greater element of nums[i] at the top.
            result[i % nums.length] = deque.isEmpty() ? -1 : nums[deque.peek()];

            // Push i into stack, so that nums[i-1] will compare with nums[i] first, before falling back to
            // the next greater element of nums[i]
            deque.push(i % nums.length);
        }

        return result;
    }

    public static int[] prevSmallerElement(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        int n = nums.length;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()%n] > nums[i%n]) {
                stack.pop();
            }
            res[i%n] = stack.isEmpty() ? -1 : nums[stack.peek() % n];
            stack.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(prevSmallerElement(new int[]{4, 5, 2, 10, 8})));

    }

    //     Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
    //     Output: [-1,3,-1]
    // Explanation:
    //     For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    //     For number 1 in the first array, the next greater number for it in the second array is 3.
    //     For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        int[] ret = new int[findNums.length];
        Arrays.fill(ret,-1);
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            // Remove elements from the stack that are less than
            // or equal to the current element
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }

            ret[i] = stack.isEmpty()?-1:stack.peek();
            stack.push(nums[i]);
        }
        return ret;
    }

    public int[] nextGreaterElementBruteForce(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            hash.put(nums2[i], i);
        }

        int[] res = new int[nums1.length];
        int j;

        for (int i = 0; i < nums1.length; i++) {
            for (j = hash.get(nums1[i]) + 1; j < nums2.length; j++) {
                if (nums1[i] < nums2[j]) {
                    res[i] = nums2[j];
                    break;
                }
            }
            if (j == nums2.length) {
                res[i] = -1;
            }
        }

        return res;
    }

    public int[] nextGreaterElementCircularBruteForce(int[] nums) {
        int[] res = new int[nums.length];
        int[] doublenums = new int[nums.length * 2];
        System.arraycopy(nums, 0, doublenums, 0, nums.length);
        System.arraycopy(nums, 0, doublenums, nums.length, nums.length);
        for (int i = 0; i < nums.length; i++) {
            res[i] = -1;
            for (int j = i + 1; j < doublenums.length; j++) {
                if (doublenums[j] > doublenums[i]) {
                    res[i] = doublenums[j];
                    break;
                }
            }
        }
        return res;
    }

    public static int[] count_NGEs(int N, int arr[], int queries, int indices[]) {
        int[] result = new int[N];
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i= N-1; i>=0 ; i--){
            while(!stack.isEmpty() && arr[stack.peek()] <= arr[i]){
                stack.pop();
            }
            result[i] = stack.size();
            stack.push(i);
        }
        int[] ans = new int[queries];
        for(int i=0;i<queries;i++){
            ans[i] = result[indices[i]];
        }
        return ans;
    }
}
