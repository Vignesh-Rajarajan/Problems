package graph.leetcode;

import java.util.List;

public class GraphBipartiteColouring {

    boolean isSafe(int vertex, int[][] graph, int[] color, int currentColor,
                   int totalVertices) {
        // Checking if the connected nodes to vertex have the same color as currentColor
        for (int i = 0; i < totalVertices; i++) {
            if (graph[vertex][i] == 1 && currentColor == color[i]) {
                return false; // Not safe if adjacent vertex has the same color
            }
        }
        return true; // Safe to color
    }

    boolean graphColoringUtil(int[][] graph, int maxColors, int[] color, int vertex,
                              int totalVertices) {
        if (vertex == totalVertices) {
            return true;
        }
        for (int currentColor = 1; currentColor <= maxColors; currentColor++) {
            if (isSafe(vertex, graph, color, currentColor, totalVertices)) {
                color[vertex] = currentColor;

                if (graphColoringUtil(graph, maxColors, color, vertex + 1,
                        totalVertices)) {
                    return true;
                }


                color[vertex] = 0;
            }
        }
        return false;
    }

    boolean graphColoring(int v, List<int[]> edges, int m) {
        int[][] graph = new int[v][v];
        for (int[] edge : edges) {
            graph[edge[0]][edge[1]] = 1;
            graph[edge[1]][edge[0]] = 1;
        }
        int[] color = new int[v]; // Array to store colors assigned to vertices
        return graphColoringUtil(graph, m, color, 0, v);
    }
}
