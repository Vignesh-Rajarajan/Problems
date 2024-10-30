package graph.leetcode.shortestPath;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

//https://takeuforward.org/graph/g-39-minimum-multiplications-to-reach-end/
public class MinMultiplyToReachEnd {

    public static void main(String[] args) {
        MinMultiplyToReachEnd minMultiplyToReachEnd = new MinMultiplyToReachEnd();
        int[] arr = {3, 4, 65};
        int start = 7;
        int end = 66175;
        System.out.println(minMultiplyToReachEnd.mintSteps(arr, start, end));
    }

    public int mintSteps(int[] arr, int start, int end) {
        int mod = 100000;
        int n = arr.length;
        int[] steps = new int[mod];
        Arrays.fill(steps, Integer.MAX_VALUE);
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, start});
        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int step = curNode[0];
            int node = curNode[1];
            if (node == end) {
                return step;
            }
            for (int j : arr) {
                int nextNode = (node * j) % mod;
                int nextSteps = step + 1;
                if (steps[nextNode] <= nextSteps) {
                    continue;
                }
                queue.offer(new int[]{nextSteps, nextNode});
            }
        }
        return -1;
    }
}
