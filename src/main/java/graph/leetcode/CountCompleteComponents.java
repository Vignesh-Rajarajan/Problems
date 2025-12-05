package graph.leetcode;

//https://leetcode.com/problems/count-the-number-of-complete-components/
public class CountCompleteComponents {
    public int countCompleteComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);

        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }

        int count = 0;
        // Iterate through all nodes to find the roots (representatives)
        for (int i = 0; i < n; i++) {
            if (uf.parent[i] == i) { // Found a root of a component
                int v = uf.nodeCount[i];
                int e = uf.edgeCount[i];

                // Mathematical property of a complete graph:
                // Edges = V * (V - 1) / 2
                if (e == v * (v - 1) / 2) {
                    count++;
                }
            }
        }
        return count;
    }

    class UnionFind {
        int[] parent;
        int[] rank;
        int[] nodeCount; // Track number of nodes in this component
        int[] edgeCount; // Track number of edges in this component

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            nodeCount = new int[size];
            edgeCount = new int[size];

            for (int i = 0; i < size; i++) {
                parent[i] = i;
                nodeCount[i] = 1; // Each node starts as a component of size 1
                edgeCount[i] = 0; // Each node starts with 0 edges
            }
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            // Case 1: They are already in the same component
            if (rootX == rootY) {
                // Node Count = unchanged
                //Edge Count = edges + 1  ← Still count the edge even though it's internal to component
                edgeCount[rootX]++;
                return false;
            }

            // Case 2: We need to merge two different components
            // We add +1 to edges to account for the edge connecting x and y
            //Step 1: Union(0, 1)
            //Component 0: nodes=2, edges=1
            //  0
            //  |
            //  1
            //
            //Step 2: Union(1, 2)
            //Component 0: nodes=3, edges=2
            //  0
            //  |\
            //  1 2
            //
            //Step 3: Union(0, 2)  ← Already in same component!
            //Component 0: nodes=3, edges=3  ← Complete triangle
            //  0
            //  /\
            // 1--2
            if (rank[rootX] < rank[rootY]) {
                //New Node Count = countX + countY
                //New Edge Count = edgesX + edgesY + 1  ← The +1 is for the NEW edge connecting them
                parent[rootX] = rootY;
                nodeCount[rootY] += nodeCount[rootX];
                edgeCount[rootY] += edgeCount[rootX] + 1;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
                nodeCount[rootX] += nodeCount[rootY];
                edgeCount[rootX] += edgeCount[rootY] + 1;
            } else {
                parent[rootY] = rootX;
                nodeCount[rootX] += nodeCount[rootY];
                edgeCount[rootX] += edgeCount[rootY] + 1;
                rank[rootX]++;
            }
            return true;
        }
    }
}
