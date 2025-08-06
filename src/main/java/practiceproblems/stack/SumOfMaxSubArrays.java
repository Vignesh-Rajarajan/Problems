package practiceproblems.stack;

import java.util.*;

public class SumOfMaxSubArrays {

    public int sumOfMaxSubArrays(int[] nums) {
        int N = nums.length;
        Deque<Integer> st = new ArrayDeque<>();
        int[] pge = new int[N];
        int[] nge = new int[N];
        for(int i=N-1;i>=0;i--){
            while(!st.isEmpty() && nums[st.peek()]<=nums[i])
                st.pop();
            nge[i] = st.isEmpty()?N:st.peek();
            st.push(i);
        }
        st = new ArrayDeque<>();
        for(int i=0;i<N;i++){
            while(!st.isEmpty() && nums[st.peek()]<nums[i])
                st.pop();
            pge[i] = st.isEmpty()?-1:st.peek();
            st.push(i);
        }
        int sumOfSubarrayMaximums = 0;
        for(int i=0;i<N;i++){
            long cnteleleft = i-pge[i];
            long cnteleright = nge[i]-i;
            sumOfSubarrayMaximums+= (int) (cnteleleft*cnteleright*nums[i]);
        }

        return sumOfSubarrayMaximums;
    }
}
