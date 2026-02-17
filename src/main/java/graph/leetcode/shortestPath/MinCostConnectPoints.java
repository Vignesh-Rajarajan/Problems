package graph.leetcode.shortestPath;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//https://leetcode.com/problems/min-cost-to-connect-all-points/

public class MinCostConnectPoints {

    // O(n^2 * log(n)) - time
    // O(n) - space
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        var pq = new PriorityQueue<int[]>(Comparator.comparingInt(a -> a[0]));
        var visited = new boolean[n];
        int minCost = 0;
        pq.offer(new int[]{0, 0});
        int[] cache = new int[n];
        Arrays.fill(cache, Integer.MAX_VALUE);
        while (!pq.isEmpty()) {
            int[] item = pq.poll();
            int cost = item[0];
            int u = item[1];

            if (visited[u]) {
                continue;
            }

            visited[u] = true;
            minCost += cost;

            for (int v = 0; v < n; v++) {
                if (visited[v]) {
                    continue;
                }

                int dist = Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]);

                if (dist < cache[v]) {
                    cache[v] = dist;
                    pq.offer(new int[]{dist, v});
                }
            }
        }
        return minCost;
    }

    //O(n^2) - time
    //O(n) - space
    public int minCostConnectPointsWithoutPQ(int[][] points) {
        int n = points.length, node = 0;
        int[] dist = new int[n];
        boolean[] visit = new boolean[n];
        Arrays.fill(dist, 100000000);
        int edges = 0, res = 0;

        while (edges < n - 1) {
            visit[node] = true;
            int nextNode = -1;
            for (int i = 0; i < n; i++) {
                if (visit[i]) continue;
                int curDist = Math.abs(points[i][0] - points[node][0]) +
                        Math.abs(points[i][1] - points[node][1]);
                dist[i] = Math.min(dist[i], curDist);
                if (nextNode == -1 || dist[i] < dist[nextNode]) {
                    nextNode = i;
                }
            }
            res += dist[nextNode];
            node = nextNode;
            edges++;
        }
        return res;
    }
}
