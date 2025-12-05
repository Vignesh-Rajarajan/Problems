package graph.leetcode;

/**
 * https://leetcode.com/problems/is-graph-bipartite/
 * https://youtu.be/mev55LTubBY
 */
public class GraphBiPartite {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n]; // Map node i -> odd=1, even=-1

        for (int i = 0; i < n; i++) {
            if (color[i] == 0 && !dfs(graph,color, i, 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph,int[] color, int i, int c) {
        color[i] = c;
        for (int nei : graph[i]) {
            if (color[nei] == c) {
                return false;
            }
            if (color[nei] == 0 && !dfs(graph,color, nei, -c)) {
                return false;
            }
        }
        return true;
    }
}