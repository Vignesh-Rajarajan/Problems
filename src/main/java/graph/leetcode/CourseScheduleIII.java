package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/parallel-courses-iii
public class CourseScheduleIII {
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<List<Integer>> adj = new ArrayList<>();
        int[] indegree = new int[n];
        int[] maxTime = Arrays.copyOf(time, n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] rel : relations) {
            int src = rel[0] - 1;
            int dst = rel[1] - 1;
            adj.get(src).add(dst);
            indegree[dst] += 1;
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int nei : adj.get(node)) {
                maxTime[nei] = Math.max(maxTime[nei], maxTime[node] + time[nei]);
                if (--indegree[nei] == 0) {
                    queue.add(nei);
                }
            }
        }

        return Arrays.stream(maxTime).max().getAsInt();
    }
}
