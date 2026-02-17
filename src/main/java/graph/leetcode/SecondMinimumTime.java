package graph.leetcode;

import java.util.*;

// tricky bfs with state tracking

//https://leetcode.com/problems/second-minimum-time-to-reach-destination/
//https://youtu.be/2F7gwxfy1CU
public class SecondMinimumTime {
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        // 1. Build Adjacency List
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int[] edge : edges) {
            adj.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            adj.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }

        // 2. Setup BFS state
        // We use simple Integers in the queue because time is tracked globally per level
        Queue<Integer> q = new LinkedList<>();
        q.offer(1);

        int curTime = 0;
        int res = -1; // Acts as the flag for the first time we reach 'n'

        // Visited arrays to track visit times (similar to visit_times in Python)
        // dist1 represents len(visit_times) == 0 logic
        // dist2 represents len(visit_times) == 1 logic
        int[] dist1 = new int[n + 1];
        int[] dist2 = new int[n + 1];
        Arrays.fill(dist1, -1);
        Arrays.fill(dist2, -1);

        // We mark node 1 as visited at time 0
        dist1[1] = 0;

        // 3. Level-by-Level BFS
        while (!q.isEmpty()) {
            // Only process the nodes currently in the queue (current level)
            int size = q.size();

            for (int i = 0; i < size; i++) {
                int node = q.poll();

                // Check if we reached the target
                if (node == n) {
                    if (res != -1) {
                        // If res is set, we found it before, so this is the second time
                        return curTime;
                    }
                    // First time reaching target
                    res = curTime;
                }

                // Process neighbors
                if (adj.containsKey(node)) {
                    for (int neighbor : adj.get(node)) {
                        // Logic mirroring: if len(nei_times) == 0
                        if (dist1[neighbor] == -1) {
                            dist1[neighbor] = curTime;
                            q.offer(neighbor);
                        }
                        // Logic mirroring: or (len == 1 and nei_time[0] != cur_time)
                        else if (dist2[neighbor] == -1 && dist1[neighbor] != curTime) {
                            dist2[neighbor] = curTime;
                            q.offer(neighbor);
                        }
                    }
                }
            }

            // 4. Update Time (Traffic Light Logic)
            // This happens once per level, exactly like the Python screenshot
            if ((curTime / change) % 2 == 1) {
                // If red, wait until it turns green
                curTime += change - (curTime % change);
            }
            curTime += time;
        }
        return 0;
    }
}
