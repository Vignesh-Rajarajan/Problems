package graph.leetcode;

import java.util.*;

public class AllPathsLeadToDestination {
    public boolean leadsToDestination(int n, int[][] edges, int source, int destination) {
        // 1. Build Graph (Adjacency List)
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
        }

        // 2. Initialize states
        State[] states = new State[n];
        Arrays.fill(states, State.UNVISITED);

        // 3. Start DFS
        return dfs(graph, source, destination, states);
    }

    private boolean dfs(Map<Integer, List<Integer>> graph, int node, int dest, State[] states) {
        // If we found a cycle (loop back to a node currently in the stack)
        if (states[node] == State.VISITING) return false;

        // If we already verified this node is safe, no need to check again
        if (states[node] == State.VERIFIED) return true;

        List<Integer> neighbors = graph.getOrDefault(node, new ArrayList<>());

        // CHECK 1: Leaf Node Check
        // If no outgoing edges, this MUST be the destination
        if (neighbors.isEmpty()) {
            return node == dest;
        }

        // Mark as currently visiting (for cycle detection)
        states[node] = State.VISITING;

        // CHECK 2: Traverse all paths
        for (int neighbor : neighbors) {
            if (!dfs(graph, neighbor, dest, states)) {
                return false; // One of the paths failed
            }
        }

        // Mark as verified safe (backtracking)
        states[node] = State.VERIFIED;
        return true;
    }

    // 0 = unvisited, 1 = visiting (cycle check), 2 = verified
    enum State {UNVISITED, VISITING, VERIFIED}

}
