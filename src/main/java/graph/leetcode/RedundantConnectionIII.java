package graph.leetcode;

//https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/
public class RedundantConnectionIII {

    public int maxNumEdgesToRemove(int n, int[][] edges) {
        DSU alice = new DSU(n), bob = new DSU(n);
        int cnt = 0;

        for (int[] edge : edges) {
            if (edge[0] == 3) {
                // this | is because we return 1 if union is successful else 0
                // so if any of them is successful we can count this edge as used
                cnt += (alice.union(edge[1], edge[2]) | bob.union(edge[1], edge[2]));
            }
        }

        for (int[] edge : edges) {
            if (edge[0] == 1) {
                cnt += alice.union(edge[1], edge[2]);
            } else if (edge[0] == 2) {
                cnt += bob.union(edge[1], edge[2]);
            }
        }

        if (alice.isConnected() && bob.isConnected()) {
            return edges.length - cnt;
        }
        return -1;
    }

    class DSU {
        private int[] parent, size;
        private int n;

        public DSU(int n) {
            this.n = n;
            parent = new int[n + 1];
            size = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int node) {
            if (parent[node] != node) {
                parent[node] = find(parent[node]);
            }
            return parent[node];
        }

        public int union(int u, int v) {
            int pu = find(u), pv = find(v);
            if (pu == pv) {
                return 0;
            }
            if (size[pu] < size[pv]) {
                int temp = pu;
                pu = pv;
                pv = temp;
            }
            size[pu] += size[pv];
            parent[pv] = pu;
            n--;
            return 1;
        }

        public boolean isConnected() {
            return n == 1;
        }
    }
}
