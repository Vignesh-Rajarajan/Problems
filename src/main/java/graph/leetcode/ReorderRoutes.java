package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/
// yet to fully understand
public class ReorderRoutes {

    public int minReorder(int n, int[][] connections) {
        Set<String> edges = new HashSet<>();
        Map<Integer, List<Integer>> neighbors = new HashMap<>();
        boolean[] visited = new boolean[n];
        int[] changes = {0};

        for (int[] conn : connections) {
            int a = conn[0], b = conn[1];
            edges.add(a + "," + b);
            neighbors.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            neighbors.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
        }

        dfs(0, changes, edges, neighbors, visited);
        return changes[0];
    }

    private void dfs(int city, int[] changes, Set<String> edges, Map<Integer, List<Integer>> neighbors, boolean[] visited) {
        visited[city] = true;
        for (int neighbor : neighbors.get(city)) {
            if (visited[neighbor]) continue;
            if (!edges.contains(neighbor + "," + city)) {
                changes[0]++;
            }
            dfs(neighbor, changes, edges, neighbors, visited);
        }
    }
}
