package graph.kruskalAlgorithm;

import graph.disjoints.DisjointSetByRank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KruskalMST {

    public static void main(String[] args) {
        KruskalMST kruskalMST = new KruskalMST();
        int[][] graph = new int[][]{
                {0, 3, 6},
                {0, 1, 2},
                {3, 1, 8},
                {1, 4, 5},
                {1, 2, 3},
                {4, 2, 7}
        };
        int[][] mst = kruskalMST.kruskalMST(graph, 5);
        for (int[] edge : mst) {
            System.out.println(edge[0] + " -> " + edge[1] + " : " + edge[2]);
        }
    }

    public int[][] kruskalMST(int[][] graph, int n) {
        List<int[]> edges = new ArrayList<>();
        for (int[] e : graph) {
            edges.add(new int[]{e[0], e[1], e[2]});
        }
        edges.sort(Comparator.comparingInt(a -> a[2]));

        DisjointSetByRank ds = new DisjointSetByRank(n);
        List<int[]> mst = new ArrayList<>();
        int sum = 0;
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            if (ds.findParent(u) != ds.findParent(v)) {
                ds.union(u, v);
                mst.add(new int[]{u, v, w});
                sum += w;
            }
        }
        System.out.println("Sum of MST: " + sum);
        return mst.toArray(new int[0][]);

    }
}