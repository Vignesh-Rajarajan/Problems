package graph.leetcode.shortestPath;

import java.util.*;

//https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/
public class WaysToArriveDestination {
    public int countPaths(int n, int[][] roads) {
        Map<Integer, List<int[]>> adjList = new HashMap<>();
        long[] dist = new long[n];
        long[] ways = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        for (int[] road : roads) {
            adjList.computeIfAbsent(road[0], x -> new ArrayList<>()).add(new int[]{road[1], road[2]});
            adjList.computeIfAbsent(road[1], x -> new ArrayList<>()).add(new int[]{road[0], road[2]});
        }
        long mod = (int) (1e9 + 7);
        dist[0] = 0;
        ways[0] = 1;

        PriorityQueue<long[]> queue = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

        queue.offer(new long[]{0, 0});

        while (!queue.isEmpty()) {
            long[] curNode = queue.poll();
            int node = (int) curNode[0];
            long cost = curNode[1];

            for (int[] neighbour : adjList.getOrDefault(node, new ArrayList<>())) {
                int nextNode = neighbour[0];
                long nextCost = neighbour[1];

                // this is the key part, else this loop will run forever
                //    2
                //   /   \
                //  0 --- 3
                //   \   /
                //     1
                // After processing node 0:
                //
                //ways[0] = 1
                //ways[1] = 1
                //ways[2] = 1
                //ways[3] = 1
                if (cost + nextCost < dist[nextNode]) {
                    dist[nextNode] = cost + nextCost;
                    queue.offer(new long[]{nextNode, cost + nextCost});
                    // The number of ways to reach nextNode is now equal to the number of ways to reach node,
                    // because every shortest path to node can be extended to reach nextNode
                    ways[nextNode] = ways[node];
                } else if (cost + nextCost == dist[nextNode]) {
                    //The algorithm is counting the number of shortest paths to each node.
                    // When we find a new path to nextNode with the same distance as the current shortest path,
                    // we need to add the number of ways to reach the current node (ways[node])
                    // to the existing number of ways to reach nextNode (ways[nextNode]).
                    ways[nextNode] = (ways[node] + ways[nextNode]) % mod;
                }

            }
        }
        return (int) (ways[n - 1] % mod);
    }
}
