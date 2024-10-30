package graph.floydwarshall;

import java.util.Arrays;

public class FloydWarshall {

    private static int INF = 100000;

    public static void main(String[] args) {
        int[][] graph = {{0, 3, INF, 7}, {8, 0, 2, INF}, {5, INF, 0, 1}, {2, INF, INF, 0}};
        FloydWarshall floyd = new FloydWarshall();
        floyd.shortestPath(graph);
    }

    private void shortestPath(int[][] graph) {
        for (int k = 0; k < graph.length; k++) {
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph[i].length; j++) {
                    if (i != k && j != k && i != j) {
                        graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                    }
                }
            }
        }

        for (int[] ints : graph) {
            System.out.println(Arrays.toString(ints));
        }
    }
}
