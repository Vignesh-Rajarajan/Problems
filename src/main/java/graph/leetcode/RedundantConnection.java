package graph.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Input: [[1,2], [1,3], [2,3]]
 * Output: [2,3]
 * Explanation: The given undirected graph will be like this:
 * 1
 * / \
 * 2 - 3
 * <p>
 * Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
 * Output: [1,4]
 * Explanation: The given undirected graph will be like this:
 * 5 - 1 - 2
 * |   |
 * 4 - 3
 */
public class RedundantConnection {
    public int[] findRedundantConnection(int[][] edges) {

        UnionFind uf = new UnionFind(edges.length + 1);

        for (int[] edge : edges) {
            if (!uf.union(edge[0], edge[1])) {
                return edge;
            }
        }
        return new int[]{-1, -1};
    }

    // Performs DFS and returns true if there's a path between src and target.
    private boolean isConnected(
            int src,
            int target,
            boolean[] visited,
            List<Integer>[] adjList
    ) {
        visited[src] = true;

        if (src == target) {
            return true;
        }

        boolean isFound = false;
        for (int adj : adjList[src]) {
            if (!visited[adj]) {
                isFound = isFound || isConnected(adj, target, visited, adjList);
            }
        }

        return isFound;
    }

    //The key idea is that we can safely discard an edge if it connects two nodes
    // that are already part of the same connected component.
    // In simple terms, this means that if there's already a path between
    // the two nodes (even without the current edge), adding this edge would create a cycle, making it redundant.
    public int[] findRedundantConnectionDFS(int[][] edges) {
        int N = edges.length;

        List<Integer>[] adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            boolean[] visited = new boolean[N];

            // If DFS returns true, we will return the edge.
            if (isConnected(edge[0] - 1, edge[1] - 1, visited, adjList)) {
                return new int[]{edge[0], edge[1]};
            }

            adjList[edge[0] - 1].add(edge[1] - 1);
            adjList[edge[1] - 1].add(edge[0] - 1);
        }

        return new int[]{};
    }

    static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
            rank = new int[size];
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public boolean union(int x, int y) {
            int xRank = find(x), yRank = find(y);
            if (xRank == yRank) {
                return false;
            } else if (rank[xRank] < rank[yRank]) {
                parent[xRank] = yRank;
            } else if (rank[xRank] > rank[yRank]) {
                parent[yRank] = xRank;
            } else {
                parent[yRank] = xRank;
                rank[xRank]++;
            }
            return true;
        }
    }

}


