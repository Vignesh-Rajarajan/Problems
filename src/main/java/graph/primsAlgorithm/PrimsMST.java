package graph.primsAlgorithm;

import java.util.*;

public class PrimsMST {
    public static void main(String[] args) {
        PrimsMST primsMST = new PrimsMST();
        int[][] graph = new int[][]{
                {0, 3, 6},
                {0, 1, 2},
                {3, 1, 8},
                {1, 4, 5},
                {1, 2, 3},
                {4, 2, 7}
        };
        int[][] mst = primsMST.primsMST(graph, 5);
        for (int[] edge : mst) {
            System.out.println(edge[0] + " -> " + edge[1] + " : " + edge[2]);
        }

        int[][] graph1 = new int[][]{
                {4,3,9},
                {4,0,4},
                {0,3,1},
                {0,1,2},
                {3,1,3},
                {1,2,3},
                {3,2,5},
                {2,5,8},
                {1,5,7},
        };
        int[][] mst1 = primsMST.primsMST(graph1, 6);
        for (int[] edge : mst1) {
            System.out.println(edge[0]+1 + " -> " + ++edge[1] + " : " + edge[2]);
        }
    }

    public int[][] primsMST(int[][] graph, int n) {
        Map<Integer, List<int[]>> adjList = new HashMap<>();
        for (int[] e : graph) {
            adjList.computeIfAbsent(e[0], x -> new ArrayList<>()).add(new int[]{e[1], e[2]});
            adjList.computeIfAbsent(e[1], x -> new ArrayList<>()).add(new int[]{e[0], e[2]});
        }
        boolean[] visited = new boolean[n];
        int sum = 0;
        List<int[]> mst = new ArrayList<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[]{0, 0, 0}); // weight, node, parent

        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int weight = curNode[0];
            int node = curNode[1];
            int parent = curNode[2];

            if (visited[node]) continue;
            visited[node] = true;
            if (parent != node) {
                mst.add(new int[]{parent, node, weight});
            }
            sum += weight;
            for (int[] neighbour : adjList.getOrDefault(node, new ArrayList<>())) {
                int nextNode = neighbour[0];
                int nextWeight = neighbour[1];
                if (!visited[nextNode]) {
                    queue.add(new int[]{nextWeight, nextNode, node});
                }
            }
        }
        System.out.println("Sum of MST: " + sum);
        return mst.toArray(new int[0][]);
    }
}
