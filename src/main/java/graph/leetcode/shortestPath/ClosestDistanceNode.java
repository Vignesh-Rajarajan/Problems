package graph.leetcode.shortestPath;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// https://leetcode.com/problems/find-closest-node-to-given-two-nodes/
public class ClosestDistanceNode {
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int[] distanceFromNode1 = calculateDistance(edges, node1);
        int[] distanceFromNode2 = calculateDistance(edges, node2);
        int minOverAllDistance = Integer.MAX_VALUE;
        int resultIndex = -1;
        for (int i = 0; i < edges.length; i++) {
            if (distanceFromNode1[i] != -1 && distanceFromNode2[i] != -1) {
                int distance = Math.max(distanceFromNode1[i], distanceFromNode2[i]);
                if (distance < minOverAllDistance) {
                    minOverAllDistance = distance;
                    resultIndex = i;
                }
            }
        }

        return resultIndex;
    }

    public int[] calculateDistance(int[] edges, int node1) {
        int[] distance = new int[edges.length];
        Arrays.fill(distance, -1);

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(node1);
        distance[node1] = 0;

        // this is not a level order BFS because we are not looking to
        // calculate something at each level, the explicit for level loop is most useful
        // when you need to perform an action between levels (e.g., "find the sum of nodes at each level").
        // that would've worked too — but it’s overcomplicated for this use case.
        // Your original version is simpler, cleaner, and equally correct.
        while (!queue.isEmpty()) {
            int node = queue.poll();
            int neighbour = edges[node];
            if (neighbour == -1 || distance[neighbour] != -1) {
                continue;
            }
            queue.offer(neighbour);

            distance[neighbour] = distance[node] + 1;
        }

        return distance;

    }
}
