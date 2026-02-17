package graph.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// tricky MST problem with kruskal's algorithm
//https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/
public class CriticalPath {
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        List<int[]> edgeList = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            edgeList.add(new int[]{edges[i][0], edges[i][1], edges[i][2], i});
        }

        edgeList.sort(Comparator.comparingInt(a -> a[2]));
        int mstWeight = 0;
        UnionFind uf = new UnionFind(n);
        for (int[] edge : edgeList) {
            if (uf.union(edge[0], edge[1])) {
                mstWeight += edge[2];
            }
        }

        List<Integer> critical = new ArrayList<>();
        List<Integer> pseudo = new ArrayList<>();

        for (int[] edge : edgeList) {
            UnionFind ufWithout = new UnionFind(n);
            int weight = 0;

            //Critical Edge: An edge that MUST be in the MST.
            //If you remove it, the MST weight increases or the graph breaks. (You already understood this part).
            for (int[] other : edgeList) {
                if (other[3] != edge[3] && ufWithout.union(other[0], other[1])) {
                    weight += other[2];
                }
            }

            if (!ufWithout.isConnected() || weight > mstWeight) {
                critical.add(edge[3]);
                continue;
            }

            //Pseudo-Critical Edge: An edge that can be in an MST, but doesn't have to be.
            // It is part of some optimal MSTs, but not all of them.
            //By manually adding edge[0], edge[1] before the loop runs, you are overriding the greedy choice.
            // You are saying, "I don't care if this is the cheapest edge or not, take it first."
            // Then, you let Kruskal's algorithm fill in the rest of the graph around this forced edge using the cheapest available options.
            UnionFind ufWith = new UnionFind(n);
            ufWith.union(edge[0], edge[1]);
            weight = edge[2];
            for (int[] other : edgeList) {
                if (ufWith.union(other[0], other[1])) {
                    weight += other[2];
                }
            }
            //If I force this edge to be part of the graph, can I still build a valid Minimum Spanning Tree with the original optimal weight?
            if (weight == mstWeight) {
                pseudo.add(edge[3]);
            }

        }

        return Arrays.asList(critical, pseudo);
    }
}

class UnionFind {
    int[] par, rank;
    int n;

    public UnionFind(int n) {
        this.n = n;
        par = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            par[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int v) {
        if (par[v] != v) {
            par[v] = find(par[v]);
        }
        return par[v];
    }

    public boolean union(int v1, int v2) {
        int p1 = find(v1), p2 = find(v2);
        if (p1 == p2) return false;
        if (rank[p1] > rank[p2]) {
            par[p2] = p1;
            rank[p1] += rank[p2];
        } else {
            par[p1] = p2;
            rank[p2] += rank[p1];
        }
        n--;
        return true;
    }

    public boolean isConnected() {
        return n == 1;
    }
}
