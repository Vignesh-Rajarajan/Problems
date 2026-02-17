package graph.leetcode;

import java.util.Arrays;

public class MinimumCostToWalk {

    int[] parent;
    int[] depth;

    public int[] minimumCost(int n, int[][] edges, int[][] queries) {
        // Initialize the parent array with -1 as initially each node belongs to its own component
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = -1;

        depth = new int[n];

        // All values are initially set to the number with only 1s in its binary representation
        int[] componentCost = new int[n];
        Arrays.fill(componentCost, Integer.MAX_VALUE);

        // Construct the connected components of the graph
        for (int[] edge : edges) {
            union(edge[0], edge[1]);
        }

        //The "Greedy" Nature of Bitwise ANDThe most important mathematical property here is:
        // Bitwise AND never increases a value; it only decreases it or keeps it the same.
        // 5 (101) & 3 (011) = 1 (001)
        // Because A & B <= A and A & B <= B, the more numbers you AND together, the smaller your result gets.
        // Therefore, to get the Minimum Cost, you want to AND as many edge weights as possible.
        //We iterate through every single edge in the graph.
        //find(edge[0]) tells us which group (Connected Component) this edge belongs to.
        //componentCost[root] &= edge[2] takes the current accumulated value for that group and ANDs it with the new edge weight.
        //By the time this loop finishes, componentCost[root] contains the result of AND-ing every single edge that belongs to that group.
        //This represents the theoretical minimum possible value you can achieve by walking around that component.
        for (int[] edge : edges) {
            int root = find(edge[0]);
            componentCost[root] &= edge[2];
        }

        int[] answer = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0];
            int end = queries[i][1];

            // If the two nodes are in different connected components, return -1
            if (find(start) != find(end)) {
                answer[i] = -1;
            } else {
                // Find the root of the edge's component
                int root = find(start);
                // Return the precomputed cost of the component
                answer[i] = componentCost[root];
            }
        }
        return answer;
    }

    // Find function to return the root (representative) of a node's component
    private int find(int node) {
        // If the node is its own parent, it is the root of the component
        if (parent[node] == -1) return node;
        // Otherwise, recursively find the root and apply path compression
        return parent[node] = find(parent[node]);
    }

    // Union function to merge the components of two nodes
    private void union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);

        // If the two nodes are already in the same component, do nothing
        if (root1 == root2) return;

        // Union by depth: ensure the root of the deeper tree becomes the parent
        if (depth[root1] < depth[root2]) {
            int temp = root1;
            root1 = root2;
            root2 = temp;
        }

        // Merge the two components by making root1 the parent of root2
        parent[root2] = root1;

        // If both components had the same depth, increase the depth of the new root
        if (depth[root1] == depth[root2]) {
            depth[root1]++;
        }
    }
}
