package graph.leetcode;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

// https://youtu.be/9twcmtQj4DU
public class CycleDetectionDirectedMultipleApproach {

    // 0 -> 1 -> 2 -> 3
    //          ^    |
    //          |    v
    //          5 <- 4
    // Initialize the inDegree array: [0, 0, 0, 0, 0, 0]
    //After calculation: [0, 1, 1, 1, 1, 1]
    //Initialize the queue:
    //Only vertex 0 has in-degree 0, so queue = [0]
    //BFS process:
    //Visit 0:
    //visitedCount = 1
    //Decrease in-degree of 1: [0, 0, 1, 1, 1, 1]
    //Add 1 to queue: queue = [1]
    //Visit 1:
    //visitedCount = 2
    //Decrease in-degree of 2: [0, 0, 0, 1, 1, 1]
    //Add 2 to queue: queue = [2]
    //Visit 2:
    //visitedCount = 3
    //Decrease in-degree of 3: [0, 0, 0, 0, 1, 1]
    //Add 3 to queue: queue = [3]
    //Visit 3:
    //visitedCount = 4
    //Decrease in-degree of 4: [0, 0, 0, 0, 0, 1]
    //Add 4 to queue: queue = [4]
    //Visit 4:
    //visitedCount = 5
    //Decrease in-degree of 5: [0, 0, 0, 0, 0, 0]
    //Add 5 to queue: queue = [5]
    //Visit 5:
    //visitedCount = 6
    //Decrease in-degree of 2: [0, 0, -1, 0, 0, 0]
    //2's in-degree is not 0, so it's not added to the queue
    //The process ends as the queue is empty.
    //Check if all vertices were visited:
    //visitedCount (6) == V (6), so return false (no cycle detected)
    public boolean bfsCycleDetect(List<List<Integer>> adj) {
        int V = adj.size();
        int[] inDegree = new int[V];
        for (List<Integer> integers : adj) {
            for (int neighbor : integers) {
                inDegree[neighbor]++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int visitedCount = 0;
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            visitedCount++;
            for (int neighbor : adj.get(vertex)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return visitedCount != V;
    }

    public boolean dfsCycleDetect(List<List<Integer>> adj) {
        int V = adj.size();
        boolean[] visited = new boolean[V];
        boolean[] recStack = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (dfsUtil(i, adj, visited, recStack)) {
                return true;
            }
        }
        return false;
    }

    // 0 -> 1 -> 2 -> 3
    //          ^    |
    //          |    v
    //          5 <- 4
    // Call dfsUtil(0, visited, recStack):
    //Mark 0 as visited and add to recStack
    //Explore neighbor 1
    //Call dfsUtil(1, visited, recStack):
    //Mark 1 as visited and add to recStack
    //Explore neighbor 2
    //Call dfsUtil(2, visited, recStack):
    //Mark 2 as visited and add to recStack
    //Explore neighbor 3
    //Call dfsUtil(3, visited, recStack):
    //Mark 3 as visited and add to recStack
    //Explore neighbor 4
    //Call dfsUtil(4, visited, recStack):
    //Mark 4 as visited and add to recStack
    //Explore neighbor 5
    //Call dfsUtil(5, visited, recStack):
    //Mark 5 as visited and add to recStack
    //Explore neighbor 2
    //Call dfsUtil(2, visited, recStack):
    //2 is already in recStack, so we return true
    private boolean dfsUtil(int v, List<List<Integer>> adj, boolean[] visited, boolean[] recStack) {
        if (recStack[v]) {
            return true;
        }
        if (visited[v]) {
            return false;
        }

        visited[v] = true;
        recStack[v] = true;

        for (int neighbor : adj.get(v)) {
            if (dfsUtil(neighbor, adj, visited, recStack)) {
                return true;
            }
        }

        recStack[v] = false;
        return false;
    }
}
