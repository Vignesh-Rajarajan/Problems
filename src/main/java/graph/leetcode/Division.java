package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/evaluate-division/
public class Division {

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // 1. Build the Graph
        Map<String, List<DivisionNode>> adjList = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            double weight = values[i];

            // Add u -> v with weight
            adjList.computeIfAbsent(u, k -> new ArrayList<>()).add(new DivisionNode(v, weight));
            // Add v -> u with 1/weight
            adjList.computeIfAbsent(v, k -> new ArrayList<>()).add(new DivisionNode(u, 1.0 / weight));
        }

        // 2. Process Queries
        double[] results = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String target = queries.get(i).get(1);

            // Perform BFS for each query
            results[i] = bfs(start, target, adjList);
        }

        return results;
    }

    public double bfs(String start, String target, Map<String, List<DivisionNode>> adjList) {
        // Case 1: If either node doesn't exist in our graph, return -1.0
        if (!adjList.containsKey(start) || !adjList.containsKey(target)) {
            return -1.0;
        }

        // Case 2: If finding path from A to A, return 1.0
        if (start.equals(target)) {
            return 1.0;
        }

        // BFS Setup
        Set<String> visited = new HashSet<>();
        Deque<DivisionNode> queue = new ArrayDeque<>();

        queue.offer(new DivisionNode(start, 1.0)); // Start with cumulative product 1.0
        visited.add(start);

        while (!queue.isEmpty()) {
            DivisionNode curr = queue.poll();

            // Found target? Return the accumulated weight
            if (curr.node.equals(target)) {
                return curr.weight;
            }

            // Explore neighbors
            if (adjList.containsKey(curr.node)) {
                for (DivisionNode neigh : adjList.get(curr.node)) {
                    if (!visited.contains(neigh.node)) {
                        visited.add(neigh.node);
                        // Multiply current path weight by edge weight
                        queue.offer(new DivisionNode(neigh.node, curr.weight * neigh.weight));
                    }
                }
            }
        }

        // Target not reachable
        return -1.0;
    }

    public double[] calcEquationDFS(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // Step 1: Build the graph
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            double val = values[i];

            graph.putIfAbsent(u, new HashMap<>());
            graph.putIfAbsent(v, new HashMap<>());

            graph.get(u).put(v, val);
            graph.get(v).put(u, 1.0 / val);
        }

        // Step 2: Process queries
        double[] results = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);
            Set<String> visited = new HashSet<>();
            results[i] = dfs(start, end, graph, visited);
        }

        return results;
    }

    private double dfs(String curr, String target, Map<String, Map<String, Double>> graph, Set<String> visited) {
        if (!graph.containsKey(curr) || !graph.containsKey(target)) {
            return -1.0;
        }
        if (curr.equals(target)) return 1.0;

        visited.add(curr);

        for (Map.Entry<String, Double> neighbor : graph.get(curr).entrySet()) {
            String next = neighbor.getKey();
            double weight = neighbor.getValue();

            if (!visited.contains(next)) {
                double result = dfs(next, target, graph, visited);
                if (result != -1.0) {
                    return weight * result;
                }
            }
        }

        return -1.0;
    }

    // Helper class
    class DivisionNode {
        String node;
        double weight;

        public DivisionNode(String node, double weight) {
            this.node = node;
            this.weight = weight;
        }
    }

}
