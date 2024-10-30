package graph.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class CycleDetectionUndirectedMultipleApproach {
    public boolean isCycleExists(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        List<List<Integer>> adjList = new ArrayList<>();

        // Build adjacency list
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
            for (int j : graph[i]) {
                adjList.get(i).add(j);
                adjList.get(j).add(i); // Add this line for undirected graphs
            }
        }

        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }
            queue.offer(new int[]{i, -1});
            visited[i] = true;

            while (!queue.isEmpty()) {
                int[] current = queue.poll();
                int node = current[0];
                int parent = current[1];
                // 0 - 1
                // |   |
                // 2 - 3
                // queue = [[0, -1]]
                //visited = [true, false, false, false]
                // after first iteration
                // queue = [[1, 0], [2, 0]]
                // visited = [true, true, true, false
                // after second iteration
                // queue = [[2, 0], [3, 1]]
                //visited = [true, true, true, true]
                // Dequeue node 2 and check its neighbors (0 and 3).
                //Neighbor 0 is already visited but is the parent of node 2, so no cycle is detected.
                //Neighbor 3 is already visited and is not the parent of node 2, indicating a cycle.
                for (int neighbor : adjList.get(node)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(new int[]{neighbor, node});
                    } else if (neighbor != parent) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean isCycleExistsDFS(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        List<List<Integer>> adjList = new ArrayList<>();

        // Build adjacency list
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
            for (int j : graph[i]) {
                adjList.get(i).add(j);
                adjList.get(j).add(i); // Add this line for undirected graphs
            }
        }

        for (int i = 0; i < graph.length; i++) {
            if (!visited[i] && dfs(i, adjList, visited, -1)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfs(int vertex, List<List<Integer>> adjList, boolean[] visited, int parent) {
        visited[vertex] = true;
        for (int neighbor : adjList.get(vertex)) {
            if (!visited[neighbor] && dfs(neighbor, adjList, visited, vertex)) {
                return true;
            }
            if (neighbor != parent) {
                return true;
            }
        }
        return false;
    }
}
