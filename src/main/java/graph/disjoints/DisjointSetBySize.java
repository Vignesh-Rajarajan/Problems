package graph.disjoints;

public class DisjointSetBySize {
    int[] parent;
    int[] size;
    int[] rank;

    public DisjointSetBySize(int n) {
        parent = new int[n];
        size = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
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

    public void unionByRank(int i, int j) {
        int parentI = findParent(i);
        int parentJ = findParent(j);
        if (parentI == parentJ) {
            return;
        }
        if (rank[parentI] > rank[parentJ]) {
            parent[parentJ] = parentI;
            size[parentI] += size[parentJ];
        } else if (rank[parentJ] > rank[parentI]) {
            parent[parentI] = parentJ;
            size[parentJ] += size[parentI];
        } else {
            // if ranks are same, then make one as root and increment its rank
            parent[parentI] = parentJ;
            rank[parentI]++;
            size[parentJ] += size[parentI];
        }
    }

    public void unionBySize(int i, int j){
        int parentI = findParent(i);
        int parentJ = findParent(j);
        if (parentI == parentJ) {
            return;
        }
        if (size[parentI] > size[parentJ]) {
            parent[parentJ] = parentI;
            size[parentI] += size[parentJ];
        } else {
            parent[parentI] = parentJ;
            size[parentJ] += size[parentI];
        }
    }
}
