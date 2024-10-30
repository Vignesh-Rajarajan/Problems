package graph.leetcode.shortestPath;

import java.util.*;

/**
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 * DFS: O(n^k) recursion depth is k, at most n flights from each city. You can have visited set but it does not change the complexity.
 * Dijkstra: O(V + logV)
 * BF: O(m*k)
 */
public class CheapestFlightKStops {
    int ans_dfs;

    // We don't use a visited array because a node might be visited multiple times with different numbers of stops,
    // potentially leading to a cheaper overall path.
    //A -> B (cost: 100)
    //A -> C -> B (cost: 50 + 40 = 90)
    //If we marked B as visited after the direct flight from A, we'd miss the cheaper path through C.
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        // K doesn't include destination,
        // because that's how it is for flights, hence K++ to account a hop for destination
        k++;
        Map<Integer, List<int[]>> adjList = new HashMap<>();

        for (int[] flight : flights) {
            adjList.computeIfAbsent(flight[0], x -> new ArrayList<>()).add(new int[]{flight[1], flight[2]});
        }

        int[] costs = new int[n];
        int[] stops = new int[n];

        Arrays.fill(costs, Integer.MAX_VALUE);
        Arrays.fill(stops, Integer.MAX_VALUE);

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        costs[src] = 0;
        stops[src] = 0;

        queue.offer(new int[]{src, 0, 0});

        while (!queue.isEmpty()) {

            int[] node = queue.poll();
            int current = node[0], cost = node[1], stop = node[2];


            if (current == dst) return cost;


            if (stop == k) continue; // if enqueued enough stops, skip

            for (int[] adj : adjList.getOrDefault(current, new ArrayList<>())) {

                int next = adj[0];
                int costNext = adj[1];

                // Add for better cost, or stop
                if (cost + costNext < costs[next] || stop + 1 < stops[next]) {
                    queue.offer(new int[]{next, cost + costNext, stop + 1});
                    costs[next] = cost + costNext;
                    stops[next] = stop + 1;
                }
            }

        }
        return -1;
    }

    /**
     * // BELLMON FORD: Relax all edges |V| - 1 times. A simple shortest path from src to any other vertex can have
     * // at-most |V| - 1 edges since there will not be any multiple flights between two cities.
     * // Here we will relax K times.
     * // Much like BFS, run the algorithm K times, if the answer exists, it should be stored in the helper matrix
     * // O(K⋅E)
     */
    public int findCheapestPriceBellman(int n, int[][] flights, int src, int dst, int k) {
        int[] cheapestPrices = new int[n];
        Arrays.fill(cheapestPrices, Integer.MAX_VALUE);
        cheapestPrices[src] = 0;

        for (int i = 0; i < k + 1; i++) { // k = 1 means # edges <= 2

            // Copy to ensure one vertex doesn't go through multiple relaxation in same iteration
            // If there was not a limit on K stops (intermediary nodes), then we DO NOT need copy
            // other way would be to use 2D array to track K as well which won't require copy.
            int[] prevCheapestPrices = cheapestPrices.clone();

            for (int[] flight : flights) {
                int source = flight[0];
                int dest = flight[1];
                int price = flight[2];

                if (prevCheapestPrices[source] != Integer.MAX_VALUE) {
                    cheapestPrices[dest] = Math.min(cheapestPrices[dest], prevCheapestPrices[source] + price);
                }
            }
        }

        return cheapestPrices[dst] == Integer.MAX_VALUE ? -1 : cheapestPrices[dst];
    }

    public int findCheapestPriceDFS(int n, int[][] flights, int src, int dst, int K) {
        ans_dfs = Integer.MAX_VALUE;
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int[] flight : flights) {
            map.computeIfAbsent(flight[0], x -> new ArrayList<>()).add(new int[]{flight[1], flight[2]});
        }
        dfs(map, src, dst, K + 1, 0);
        return ans_dfs == Integer.MAX_VALUE ? -1 : ans_dfs;
    }

    public void dfs(Map<Integer, List<int[]>> map, int src, int dst, int k, int cost) {
        if (k < 0)
            return;
        if (src == dst) {
            ans_dfs = cost;
            return;
        }

        if (!map.containsKey(src))
            return;

        for (int[] i : map.get(src)) {
            if (cost + i[1] > ans_dfs)               //Pruning, check the sum of current price and next cost. If it's greater then the ans so far, continue
                continue;
            dfs(map, i[0], dst, k - 1, cost + i[1]);
        }
    }

}
