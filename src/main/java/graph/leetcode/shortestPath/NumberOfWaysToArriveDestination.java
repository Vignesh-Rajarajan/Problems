package graph.leetcode.shortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class NumberOfWaysToArriveDestination {
    public int countPaths(int n, int[][] roads) {
        int MOD = 1_000_000_007;
        // 1. Build Adjacency List (Node -> {Neighbor, Time})
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] road : roads) {
            adj[road[0]].add(new int[]{road[1], road[2]});
            adj[road[1]].add(new int[]{road[0], road[2]});
        }

        // 2. Arrays for Distance and Ways
        long[] dist = new long[n];
        long[] ways = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);

        dist[0] = 0;
        ways[0] = 1; // One way to start at 0 (do nothing)

        // 3. Min-Heap Priority Queue: [node, current_dist]
        // Sort by distance (time)
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            int u = (int) curr[0];
            long d = curr[1];

            // Standard Dijkstra Optimization: If we found a shorter way to u before, skip this stale entry
            if (d > dist[u]) continue;

            for (int[] edge : adj[u]) {
                int v = edge[0];
                int time = edge[1];

                // Case 1: Found a strictly better path
                if (dist[u] + time < dist[v]) {
                    dist[v] = dist[u] + time;
                    ways[v] = ways[u]; // Reset ways
                    pq.offer(new long[]{v, dist[v]});
                }
                // Case 2: Found an equal shortest path
                else if (dist[u] + time == dist[v]) {
                    ways[v] = (ways[v] + ways[u]) % MOD; // Append ways
                }
            }
        }

        return (int) ways[n - 1];
    }

}
