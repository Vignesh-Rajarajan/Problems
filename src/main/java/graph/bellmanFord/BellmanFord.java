package graph.bellmanFord;

import java.util.Arrays;

public class BellmanFord {

    public static void main(String[] args) {
        BellmanFord bellmanFord = new BellmanFord();
        int[][] graph = new int[][]{
                {3, 2, 6},
                {5, 3, 1},
                {0, 1, 5},
                {1, 5, -3},
                {1, 2, -2},
                {3, 4, -2},
                {2, 4, 3}
        };
        System.out.println(Arrays.toString(bellmanFord.shortestPath(graph, 6, 0)));
    }

    private int[] shortestPath(int[][] graph, int edges, int start) {
        int[] dist = new int[edges];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        for (int i = 0; i < edges - 1; i++) {
            for (int[] edge : graph) {
                int u = edge[0];
                int v = edge[1];
                int weight = edge[2];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }
        // code to find negative cycle
        for (int[] edge : graph) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("Graph contains negative cycle");
                return new int[0];
            }
        }
        return dist;
    }

}
