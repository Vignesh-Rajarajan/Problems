package graph.leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/critical-connections-in-a-network/
 * Tarjan's algorithm for finding critical connections (or bridges) in a graph
 * Lowest Reachable Discovery Time:
 * For each node, we keep track of the lowest discovery time it can reach. This includes:
 * i)  Its own discovery time
 * ii) The discovery time of any node it can reach through its neighbors
 * Critical Connection:
 * Check all neighbors (except the parent you came from).
 * If a node can't reach any node numbered earlier than itself (except through its parent), then the connection to its parent is critical.
 * <p>
 * A connection is critical if it's the only way to reach a part of the graph. In our maze analogy,
 * it's like a bridge that, if removed, would disconnect part of the maze.
 * <p>
 * Complexity:
 * Time = O(|E| + |V|)
 * Space = O(|V| + |E|)
 */
public class CriticalConnectionInGraph {
    int startTime = 1;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        if (connections == null) {
            return Collections.emptyList();
        }
        Map<Integer, List<Integer>> adjList = new HashMap<>();

        for (List<Integer> edge : connections) {
            adjList.computeIfAbsent(edge.get(0), x -> new ArrayList<>()).add(edge.get(1));
            adjList.computeIfAbsent(edge.get(1), x -> new ArrayList<>()).add(edge.get(0));
        }


        List<List<Integer>> criticalConns = new ArrayList<>();
        dfs(adjList, new int[n], 0, -1, criticalConns); // -1 is a dummy parent of the server 0
        return criticalConns;
    }

    private int dfs(Map<Integer, List<Integer>> graph, int[] visitedAt, int server, int parent, List<List<Integer>> criticalConnections) {
        if (visitedAt[server] > 0) { // return immediately if a node has been visited before
            return visitedAt[server];
        }

        visitedAt[server] = startTime++;

        int minVisitedAtOfAllNeighbors = Integer.MAX_VALUE;
        for (int neighbor : graph.getOrDefault(server, new ArrayList<>())) {
            if (neighbor == parent) { // skip parent as we only want to explore down, not up
                continue;
            }

            int neighborVisitedAt = dfs(graph, visitedAt, neighbor, server, criticalConnections);
            minVisitedAtOfAllNeighbors = Math.min(minVisitedAtOfAllNeighbors, neighborVisitedAt);
        }

        // discovery time of the current node is less than the lowest discovery time that can be reached from this node through its neighbors
        // it means that the current node cannot reach any node with a lower discovery time than itself through its neighbors
        // If a node can't reach any earlier-discovered node except through its parent,
        // it means the edge to its parent is the only way to connect this node
        // We're essentially saying: "The connection between this node and the node that led us here (its parent in the DFS tree) is critical."
        if (visitedAt[server] <= minVisitedAtOfAllNeighbors && parent != -1) { // parent != 1 to avoid creating invalid critical connection, e.g., [-1, 0]
            criticalConnections.add(Arrays.asList(parent, server));
        }

        return Math.min(visitedAt[server], minVisitedAtOfAllNeighbors);
    }
}
