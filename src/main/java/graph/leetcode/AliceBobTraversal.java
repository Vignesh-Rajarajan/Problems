package graph.leetcode;

import java.util.*;
// https://leetcode.com/problems/most-profitable-path-in-a-tree/
public class AliceBobTraversal {
    // --- PART 1: Bob's Journey (DFS) ---
    // records the time Bob arrives at each node in the 'bobTimeline' map.
    private boolean findBobPath(int currNode, int parent, int time, Map<Integer, Integer> bobTimeline, List<List<Integer>> graph) {
        // Record Bob's arrival time
        bobTimeline.put(currNode, time);

        // If Bob reached home (Node 0), we found the path
        if (currNode == 0) {
            return true;
        }

        for (int neighbor : graph.get(currNode)) {
            // PREVENT CYCLE: Just check if the neighbor is where we came from
            if (neighbor != parent) {
                if (findBobPath(neighbor, currNode, time + 1, bobTimeline, graph)) {
                    return true;
                }
            }
        }

        // Backtrack
        bobTimeline.remove(currNode);
        return false;
    }

    // --- MAIN FUNCTION ---
    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        int n = amount.length;

        // 1. Build the Map (Graph)
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // 2. Create Bob's Timeline
        // We calculate exactly when Bob visits specific nodes.
        Map<Integer, Integer> bobTimeline = new HashMap<>();
        boolean[] visited = new boolean[n];
        findBobPath(bob, -1, 0, bobTimeline, graph);

        // 3. Alice's Adventure (BFS)
        // Queue stores int array: {node, time, income}
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0, 0}); // Alice starts at node 0, time 0, income 0

        // Reset visited array for Alice's turn
        Arrays.fill(visited, false);

        int maxIncome = Integer.MIN_VALUE;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int node = curr[0];
            int time = curr[1];
            int income = curr[2];

            visited[node] = true;

            // --- INTERACTION LOGIC ---

            // Scenario 1: Bob never visited this node.
            if (!bobTimeline.containsKey(node)) {
                income += amount[node];
            }
            // Scenario 2: Alice arrived FIRST.
            else if (time < bobTimeline.get(node)) {
                income += amount[node];
            }
            // Scenario 3: They arrived at the SAME TIME.
            else if (time == bobTimeline.get(node)) {
                income += (amount[node] / 2);
            }
            // Scenario 4: Alice arrived LATE (time > bobTimeline).
            // Income doesn't change (+0) because the gate is already open.

            // --- CHECK IF LEAF NODE ---
            // A leaf has only 1 connection (the one we came from), unless it's the root (0) acting as a leaf.
            if (graph.get(node).size() == 1 && node != 0) {
                maxIncome = Math.max(maxIncome, income);
            }

            // --- CONTINUE EXPLORING ---
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    q.add(new int[]{neighbor, time + 1, income});
                }
            }
        }

        return maxIncome;
    }
}
