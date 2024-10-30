package graph.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/number-of-operations-to-make-network-connected
 */
public class NetworkConnection {

    public int makeConnectedDFS(int n, int[][] connections) {
        if (connections.length < n - 1) return -1; // To connect all nodes need at least n-1 edges
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] c : connections) {
            graph[c[0]].add(c[1]);
            graph[c[1]].add(c[0]);
        }
        int components = 0;
        boolean[] visited = new boolean[n];
        for (int v = 0; v < n; v++) components += dfs(v, graph, visited);
        return components - 1; // Need (components-1) cables to connect components together
    }
    int dfs(int u, List<Integer>[] graph, boolean[] visited) {
        if (visited[u]) return 0;
        visited[u] = true;
        for (int v : graph[u]) dfs(v, graph, visited);
        return 1;
    }

    public int makeConnected(int n, int[][] connections) {
        UnionFind uf = new UnionFind(n);
        if (connections.length < n - 1) return -1;
        int extra = 0;
        for (int[] connection : connections) {
            if (!uf.union(connection[0], connection[1])) {
                extra++; // we are counting the number of extra edges that are not needed,since the nodes are already connected
            }
        }
        int parents = 0;
        for (int i = 0; i < n; i++) {
            if (uf.find(i) == i) {
                parents++; // counting the number of disconnected components
            }
        }
        // if the number of extra edges is less than the number of disconnected components,
        // then we cannot connect all the components
        if (extra < parents - 1) return -1;

        // the question is asking for the number of edges that can be added to make the network connected
        // for n nodes, we need n - 1 edges to connect all the nodes
        return parents - 1;

    }

    static class UnionFind {
        int[] parent;
        int[] rank;
        int components = 0;

        public UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
            rank = new int[size];
            this.components = size;
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public boolean union(int x, int y) {
            int parentX = find(x), parentY = find(y);
            if (parentX == parentY) {
                return false;
            } else if (rank[parentX] < rank[parentY]) {
                parent[parentX] = parentY;
            } else if (rank[parentX] > rank[parentY]) {
                parent[parentY] = parentX;
            } else {
                parent[parentY] = parentX;
                rank[parentX]++;
            }
            components--;
            return true;

        }
    }
}
