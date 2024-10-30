package graph.leetcode.shortestPath;

import java.util.*;

public class ShortestPath {

    public static void main(String[] args) {
        ShortestPath sp = new ShortestPath();
        int[][] matrix = {{0, 1}, {0, 3}, {3, 4}, {4, 5}, {5, 6}, {1, 2}, {2, 6}, {6, 7}, {7, 8}, {6, 8}};
        int[] res = sp.shortestPathToAllNodesWithUnitDistanceDfs(matrix, 0, 9);
        System.out.println(Arrays.toString(res));
    }

    public int[] shortestPathToAllNodesWithUnitDistance(int[][] matrix, int source, int nodes) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] ints : matrix) {
            adjList.get(ints[0]).add(ints[1]);
            adjList.get(ints[1]).add(ints[0]);
        }
        int[] res = new int[nodes];
        Arrays.fill(res, Integer.MAX_VALUE);

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{source, 0});
        res[source] = 0;

        while (!queue.isEmpty()) {
            int[] currNode = queue.poll();
            int nextDistance = currNode[1] + 1;
            for (int neighbour : adjList.get(currNode[0])) {
                if (res[neighbour] > nextDistance) {
                    res[neighbour] = nextDistance;
                    queue.offer(new int[]{neighbour, nextDistance});
                }
            }
        }
        return res;
    }

    //Unlike BFS that typically starts from one source, this DFS is potentially started from every node.
    // This is useful for finding shortest paths in graphs that might not be fully connected
    // It's important to note that while this DFS approach can find shortest paths in some cases,
    // it's not guaranteed to be as efficient or correct as BFS for all types of graphs, especially those with cycles
    public int[] shortestPathToAllNodesWithUnitDistanceDfs(int[][] matrix, int source, int nodes) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] ints : matrix) {
            adjList.get(ints[0]).add(ints[1]);
            adjList.get(ints[1]).add(ints[0]);
        }
        int[] res = new int[nodes];
        Arrays.fill(res, Integer.MAX_VALUE);

        for (int i = 0; i < nodes; i++) {
            //It calls DFS on each node that hasn't been visited yet (distance is still MAX_VALUE)
            if (res[i] == Integer.MAX_VALUE) {
                dfs(i, 0, res, adjList);
            }
        }

        return res;
    }

    public void dfs(int node, int distance, int[] result, List<List<Integer>> adjList) {
        if (result[node] <= distance) {
            return;
        }
        result[node] = distance;
        for (int neighbour : adjList.get(node)) {
            dfs(neighbour, distance + 1, result, adjList);
        }
    }
}
