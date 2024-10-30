package graph.leetcode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/number-of-provinces/
public class FriendCircles {
    public int findCircleNumDFS(int[][] isConnected) {
        List<List<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < isConnected.length; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < isConnected.length; i++) {
            for (int j = 0; j < isConnected[i].length; j++) {
                if (isConnected[i][j] == 1 && i != j) {
                    adjList.get(i).add(j);
                    adjList.get(j).add(i);
                }
            }
        }

        int count = 0;
        boolean[] visited = new boolean[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                count++;
                dfs(adjList, i, visited);
            }
        }
        return count;
    }

    public void dfs(List<List<Integer>> adjList, int v, boolean[] visited) {
        visited[v] = true;
        for (Integer adj : adjList.get(v)) {
            if (!visited[adj]) {
                dfs(adjList, adj, visited);
            }
        }
    }

    public int findCircleNum(int[][] M) {
        UnionFind uf = new UnionFind(M.length);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                if (M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.getCount();
    }

    static class UnionFind {
        int[] parent;
        int[] rank;
        int count;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int node) {
            while (node != parent[node]) {
                node = parent[node];
            }
            return node;
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return;

            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootY] > rank[rootX]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            count--;
        }

        public int getCount() {
            return count;
        }
    }
}
