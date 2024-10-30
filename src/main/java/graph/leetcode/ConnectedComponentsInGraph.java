package graph.leetcode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/
// Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.
//
//Example 1:
//
//Input: n = 5 and edges = [[0, 1], [1, 2], [3, 4]]
//
//     0          3
//     |          |
//     1 --- 2    4
//
//Output: 2
//Example 2:
//
//Input: n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]]
//
//     0           4
//     |           |
//     1 --- 2 --- 3
//
//Output:  1
public class ConnectedComponentsInGraph {

    public int countComponentsDFS(int n, int[][] edges) {
        List<List<Integer>> adjList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]); // Add this line for undirected graphs
        }

        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                dfs(i, adjList, visited);
            }
        }
        return count;
    }

    private void dfs(int vertex, List<List<Integer>> adjList, boolean[] visited) {
        visited[vertex] = true;
        for (int neighbor : adjList.get(vertex)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adjList, visited);
            }
        }
    }

    public int countComponents(int n, int[][] edges) {
        int[] roots = new int[n];

        for (int i = 0; i < n; i++) {
            roots[i] = i;
        }

        for (int[] edge : edges) {
            int r1 = find(roots, edge[0]);
            int r2 = find(roots, edge[1]);

            if (r1 != r2) {
                roots[r1] = r2;
                n--;
            }
        }

        return n;
    }

    private int find(int[] roots, int key) {
        while (roots[key] != key) {
            key = roots[key];
        }

        return key;
    }
}
