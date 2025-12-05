package graph.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MaxBombDetonation {
    public int maximumDetonation(int[][] bombs) {
        int n = bombs.length;
        // Adjacency list for the directed graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // 1. Build the Graph O(N^2)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;

                long xi = bombs[i][0], yi = bombs[i][1], ri = bombs[i][2];
                long xj = bombs[j][0], yj = bombs[j][1];

                // Calculate Euclidean distance squared: (x1-x2)^2 + (y1-y2)^2
                long distanceSq = (xi - xj) * (xi - xj) + (yi - yj) * (yi - yj);
                long radiusSq = ri * ri;

                // If distance is within radius, i detonates j
                if (distanceSq <= radiusSq) {
                    graph.get(i).add(j);
                }
            }
        }

        int maxDetonated = 0;

        // 2. Perform DFS/BFS from every node to find reachability
        // Total Complexity: O(N * (N + E)) -> Worst case O(N^3)
        for (int i = 0; i < n; i++) {
            int count = performDFS(i, n, graph);
            maxDetonated = Math.max(maxDetonated, count);
        }

        return maxDetonated;
    }

    private int performDFS(int startNode, int n, List<List<Integer>> graph) {
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(startNode);
        visited[startNode] = true;
        int count = 0;

        while (!stack.isEmpty()) {
            int curr = stack.pop();
            count++;

            for (int neighbor : graph.get(curr)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    stack.push(neighbor);
                }
            }
        }
        return count;
    }
}
