package graph.leetcode.shortestPath;

import java.util.*;

// For directed acyclic graphs (DAGs) and weighted graphs: code is implementing Dijkstra's algorithm, which uses  priority queue.
// This choice is not because the graph is directed, but because it's weighted.
//In a DAG or any weighted graph, this property is crucial for finding the shortest paths efficiently.
public class DijkstraShortestPathWeight {

    public static void main(String[] args) {
        DijkstraShortestPathWeight sp = new DijkstraShortestPathWeight();
        int[][] matrix = {{0, 1, 2}, {0, 4, 1}, {4, 5, 4}, {4, 2, 2}, {1, 2, 3}, {2, 3, 6}, {5, 3, 1}};
        int[] res = sp.shortestPathDAG(matrix, 0, 6);
        System.out.println(Arrays.toString(res));
    }

    public int[] shortestPathDAG(int[][] matrix, int src, int nodes) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : matrix) {
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        queue.offer(new int[]{src, 0});
        int[] res = new int[nodes];
        Arrays.fill(res, Integer.MAX_VALUE);
        res[src] = 0;
        while (!queue.isEmpty()) {
            int[] currNode = queue.poll();
            int distance = currNode[1];
            for (int[] neighbour : graph.get(currNode[0])) {
                int nextNode = neighbour[0];
                int weight = neighbour[1];
                if (res[nextNode] > distance + weight) {
                    res[nextNode] = distance + weight;
                    queue.offer(new int[]{nextNode, distance + weight});
                }
            }
        }
        return res;
    }
}
