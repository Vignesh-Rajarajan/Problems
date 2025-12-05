package graph.leetcode;

import java.util.ArrayList;
import java.util.List;

//https://leetcode.com/problems/minimum-fuel-cost-to-report-to-the-capital
public class MinFuelCost {
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length + 1;
        List<Integer>[] adjList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int[] node : roads) {
            adjList[node[0]].add(node[1]);
            adjList[node[1]].add(node[0]);
        }

        long[] result = new long[1];
        dfs(0, -1, adjList, result, seats);
        return result[0];
    }

    public int dfs(int node, int parent, List<Integer>[] adjList, long[] result, int seats) {
        // Initialize passengers from children
        int passengers = 0;

        for (int neigh : adjList[node]) {
            if (neigh == parent) continue;

            // Get passengers from the child node
            int p = dfs(neigh, node, adjList, result, seats);

            // Add valid passengers from subtree
            passengers += p;

            // Calculate fuel cost to bring 'p' people from 'neigh' to 'node'
            // Logic: 1 car can take 'seats' people.
            result[0] += Math.ceil((double) p / seats);
        }

        // IMPORTANT: Return total from children + 1 (the person at the current node)
        return passengers + 1;
    }
}
