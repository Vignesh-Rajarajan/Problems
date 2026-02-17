package graph.leetcode;

import java.util.ArrayList;
import java.util.List;
//https://leetcode.com/problems/maximum-number-of-k-divisible-components/
public class MaxDivisibleComponenets {
    private int componentCount = 0;

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        // 1. Build Adjacency List
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        // 2. Start DFS from the root (node 0)
        dfs(0, -1, adj, values, k);

        return componentCount;
    }

    private long dfs(int currentNode, int parentNode, List<List<Integer>> adj, int[] values, int k) {
        // Initialize sum with the current node's value
        long sum = values[currentNode];

        // Traverse neighbors
        for (int neighbor : adj.get(currentNode)) {
            if (neighbor != parentNode) {
                // Add the sum returned from the child subtree
                sum += dfs(neighbor, currentNode, adj, values, k);
            }
        }

        // 3. Check if the current subtree sum is divisible by k
        if (sum % k == 0) {
            componentCount++;
            return 0; // Cut the edge effectively by returning 0 to the parent
        }

        // If not divisible, pass the sum up to the parent to be combined
        return sum;
    }
}
