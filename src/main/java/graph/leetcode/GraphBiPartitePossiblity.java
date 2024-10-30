package graph.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/possible-bipartition
 * https://youtu.be/KG5YFfR0j8A
 */
public class GraphBiPartitePossiblity {
    public boolean possibleBipartition(int N, int[][] dislikes) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] dislike : dislikes) {
            graph.get(dislike[0]).add(dislike[1]);
            graph.get(dislike[1]).add(dislike[0]);
        }

        Integer[] colors = new Integer[N + 1];
        for (int i = 1; i <= N; i++) {
            if (colors[i] == null && !dfs(graph, colors, i, 1)) return false;
        }
        return true;
    }

    private boolean dfs(List<List<Integer>> graph, Integer[] colors, int currNode, int currColor) {
        colors[currNode] = currColor;
        // Color all uncolored adjacent nodes.
        for (Integer adjacentNode : graph.get(currNode)) {
            if (colors[adjacentNode] == null && (!dfs(graph, colors, adjacentNode, currColor * -1))) {
                return false;
            }
            if (colors[adjacentNode] == currColor) {
                return false;
            }
        }
        return true;
    }
}
