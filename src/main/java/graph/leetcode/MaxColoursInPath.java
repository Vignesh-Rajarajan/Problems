package graph.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//https://leetcode.com/problems/largest-color-value-in-a-directed-graph/
public class MaxColoursInPath {
    private int n;
    private List<Integer>[] adj;
    private boolean[] visit, path;
    private int[][] count;

    public int largestPathValueTopologicalSort(String colors, int[][] edges) {
        int n = colors.length();
        List<Integer>[] adj = new ArrayList[n];
        int[] indegree = new int[n];
        int[][] count = new int[n][26];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            indegree[edge[1]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        int visit = 0, res = 0;
        while (!q.isEmpty()) {
            int node = q.poll();
            visit++;
            int colorIndex = colors.charAt(node) - 'a';
            count[node][colorIndex]++;
            res = Math.max(res, count[node][colorIndex]);

            for (int nei : adj[node]) {
                for (int c = 0; c < 26; c++) {
                    count[nei][c] = Math.max(count[nei][c], count[node][c]);
                }
                if (--indegree[nei] == 0) {
                    q.add(nei);
                }
            }
        }

        return visit == n ? res : -1;
    }

    public int largestPathValueDFS(String colors, int[][] edges) {
        this.n = colors.length();
        this.adj = new ArrayList[n];
        this.visit = new boolean[n];
        this.path = new boolean[n];
        this.count = new int[n][26];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            if (dfs(i, colors) == Integer.MAX_VALUE) return -1;
            for (int c = 0; c < 26; c++) {
                res = Math.max(res, count[i][c]);
            }
        }
        return res;
    }

    private int dfs(int node, String colors) {
        if (path[node]) return Integer.MAX_VALUE;
        if (visit[node]) return 0;

        visit[node] = true;
        path[node] = true;
        int colorIndex = colors.charAt(node) - 'a';
        count[node][colorIndex] = 1;

        for (int nei : adj[node]) {
            if (dfs(nei, colors) == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            for (int c = 0; c < 26; c++) {
                count[node][c] = Math.max(
                        count[node][c],
                        (c == colorIndex ? 1 : 0) + count[nei][c]
                );
            }
        }

        path[node] = false;
        return 0;
    }
}
