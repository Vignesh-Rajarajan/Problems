package graph.leetcode.shortestPath;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

//https://leetcode.com/problems/path-with-maximum-probability/
public class PathMaxProbability {

    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        // 1. Build Adjacency List
        List<List<Pair>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            double p = succProb[i];
            // Undirected graph
            graph.get(u).add(new Pair(v, p));
            graph.get(v).add(new Pair(u, p));
        }

        // 2. Initialize Probabilities
        double[] maxProb = new double[n];
        maxProb[start_node] = 1.0;

        // 3. Priority Queue (Max Heap)
        // Orders elements by probability descending (Largest first)
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Double.compare(b.probability, a.probability));
        pq.offer(new Pair(start_node, 1.0));

        // 4. Dijkstra Loop
        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int u = curr.node;
            double curProb = curr.probability;

            // Optimization: If we extracted the target, this is the best path
            if (u == end_node) {
                return curProb;
            }

            // Optimization: If current path is worse than what we already found for u, skip
            if (curProb < maxProb[u]) {
                continue;
            }

            for (Pair neighbor : graph.get(u)) {
                int v = neighbor.node;
                double edgeProb = neighbor.probability;

                // Relaxation step: If new path is better (higher probability)
                if (curProb * edgeProb > maxProb[v]) {
                    maxProb[v] = curProb * edgeProb;
                    pq.offer(new Pair(v, maxProb[v]));
                }
            }
        }

        // If end_node is unreachable, maxProb[end_node] will remain 0.0
        return maxProb[end_node];
    }

    // Helper class to store node and its probability from start
    static class Pair {
        int node;
        double probability;

        Pair(int node, double probability) {
            this.node = node;
            this.probability = probability;
        }
    }

}
