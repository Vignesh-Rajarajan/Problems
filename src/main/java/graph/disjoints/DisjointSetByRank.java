package graph.disjoints;

public class DisjointSetByRank {
    int[] parent;
    int[] rank;

    public DisjointSetByRank(int n) {
        parent = new int[n];
        rank = new int[n]; // rank is the depth of the tree
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int findParent(int n) {
        if (parent[n] == n) {
            return n;
        }
        // path compression
        int ultimateParent = findParent(parent[n]);
        parent[n] = ultimateParent;
        return ultimateParent;
    }

    public void union(int i, int j) {
        int parentI = findParent(i);
        int parentJ = findParent(j);
        if (parentI == parentJ) {
            return;
        }
        // rank is nothing but the depth of the tree
        // attach the smaller rank tree under the root of the larger rank tree
        // the reason for this is to keep distance from the root to the leaf nodes as small as possible
        // if we attach the larger rank tree under the root of the smaller rank tree,
        // then the distance for the larger rank tree will be more
        // if we do the reverse the distance for larger will be same but the distance for smaller will be more
        // which is good

        if (rank[parentI] > rank[parentJ]) {
            parent[parentJ] = parentI;
        } else if (rank[parentJ] > rank[parentI]) {
            parent[parentI] = parentJ;
        } else {
            // if ranks are same, then make one as root and increment its rank
            parent[parentI] = parentJ;
            rank[parentI]++;
        }
    }

    public boolean isConnected(int i, int j) {
        return findParent(i) == findParent(j);
    }
}
