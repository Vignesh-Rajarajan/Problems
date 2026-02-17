package graph.leetcode;

//https://leetcode.com/problems/bus-routes/
// tricky variant of bfs where we have to track both visited stops and visited routes (buses)

import java.util.*;

public class BusStops {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // Edge case: If start and end are the same, no bus needed.
        if (source == target) {
            return 0;
        }

        // 1. Map Stops to Routes
        // Key: Stop ID, Value: List of Bus Routes (indices) passing through this stop
        //You must realize that a "Bus Route" acts as a hyper-edge or a node in the graph, rather than just a collection of edges.
        Map<Integer, List<Integer>> stopToRoutes = new HashMap<>();

        for (int i = 0; i < routes.length; i++) {
            for (int stop : routes[i]) {
                stopToRoutes.computeIfAbsent(stop, k -> new ArrayList<>()).add(i);
            }
        }

        // 2. BFS Setup
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);

        // Track visited stops to avoid cycles in stop traversal
        Set<Integer> visitedStops = new HashSet<>();
        visitedStops.add(source);

        // Track visited routes to prevent checking the same bus multiple times (CRITICAL for performance)
        boolean[] visitedRoutes = new boolean[routes.length];

        int busCount = 0;

        // 3. Run BFS
        while (!queue.isEmpty()) {
            int size = queue.size();

            // Process level by level
            for (int i = 0; i < size; i++) {
                int currentStop = queue.poll();
                for (int busId : stopToRoutes.getOrDefault(currentStop, new ArrayList<>())) {
                    if (visitedRoutes[busId]) continue; // Skip if we've already taken this bus

                    // Check all stops reachable by this bus
                    for (int nextStop : routes[busId]) {
                        if (nextStop == target) {
                            return busCount + 1;
                        }

                        if (visitedStops.add(nextStop)) {
                            queue.offer(nextStop);
                        }
                    }

                    // Mark bus as visited so we don't process its stops again
                    visitedRoutes[busId] = true;
                }
            }
            busCount++;
        }

        return -1;
    }
}
