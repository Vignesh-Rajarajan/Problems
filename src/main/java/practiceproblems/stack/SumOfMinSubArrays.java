package practiceproblems.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * https://leetcode.com/problems/sum-of-subarray-minimums/
 * tricky Stack
 */
public class SumOfMinSubArrays {

    public static void main(String[] args) {
        System.out.println(new SumOfMinSubArrays().sumSubarrayMins(new int[]{5, 3, 4, 1, 2, 7}));
    }

    public int sumSubarrayMins(int[] arr) {
        int length = arr.length;
        long mod = (long) 1e9 + 7;
        long result = 0;
        int[] nextSmallest = nextSmallestElement(arr);
        int[] previousSmallest = previousSmallestEqualElement(arr);

        for(int i = 0; i < length; i++){
            int left = i - previousSmallest[i];
            int right = nextSmallest[i] - i;
            result = (result + ((long) left * right * arr[i])%mod) % mod;
        }
        return (int) result;
    }

    public int[] nextSmallestElement(int[] arr) {
        int[] result = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = arr.length - 1; i >= 0; i--) {
           while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
               stack.pop();
           }
              result[i] = stack.isEmpty() ? arr.length : stack.peek();
             stack.push(i);
        }
        return result;
    }

    public int[] previousSmallestEqualElement(int[] arr){
        int[] result = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < arr.length; i++){
            while(!stack.isEmpty() && arr[stack.peek()] > arr[i]){
                stack.pop();
            }
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return result;
    }

    public int sumSubarrayMinsTLE(int[] arr) {
        if (arr.length == 1) return arr[0];
        int mod = (int) Math.pow(10, 9) + 7;

        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            for (int j = i; j < arr.length; j++) {
                // As j increases, the subarray grows,
                // and the minimum value of the subarray (min) is updated dynamically.
                //For each new element arr[j] added to the subarray, the minimum value (min) might change.
                //You need to add this updated min to the result immediately because
                // it represents the minimum value of the current subarray.
                min = Math.min(min, arr[j]);
                result += min;
                result %= mod;
            }
        }
        return result;

    }

    public int sumSubarrayMinsMLE(int[] arr) {
        if (arr.length == 1) return arr[0];
        int mod = (int) Math.pow(10, 9) + 7;
        int[][] dp = new int[arr.length][arr.length];
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            dp[i][i] = arr[i];
            result += dp[i][i];
        }

        for (int l = 1; l < arr.length; l++) {
            for (int i = 0; i < arr.length - l; i++) {
                int j = i + l;
                dp[i][j] = Math.min(dp[i + 1][j] % mod, dp[i][j - 1] % mod);
                result += dp[i][j];
                result %= mod;
            }
        }

        return result;
    }
}
