package graph.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

//https://leetcode.com/problems/trapping-rain-water-ii/
public class TrappedRainWater {

    //Imagine you are an engineer trying to calculate how much water a terrain can hold.
    //You are standing on the outside of a fence that surrounds the entire grid.
    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return 0;
        }

        int m = heightMap.length;
        int n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        //You surround the entire grid with a fence (the visited set).
        //You put every fence post into a list (the Priority Queue), sorted by height.
        //Why? Because the water level of the entire system is currently dictated by the shortest fence post.
        //Water cannot rise higher than the lowest opening.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    minHeap.offer(new int[]{i, j, heightMap[i][j]});
                    visited[i][j] = true;
                }
            }
        }

        int waterVolume = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        //You look at your list and find the shortest fence post. Let's say it has a height of 3.
        //Crucial Realization: No matter what monsters or mountains exist deep inside the grid,
        // any water attempting to rest near this specific post cannot exceed height 3.
        // If it tried to be height 4, it would spill out over this post.
        //This post is the "gatekeeper" for its immediate inner neighbors.

        //Case 1: Neighbor is Higher (Tall Wall)
        //Situation: Current boundary is 3. Neighbor is 5.
        //Action: We can't pour water onto a 5 from a 3. The water stops.
        //Heap Update: This neighbor 5 is now a new boundary. We push 5 into the heap.
        //Wait: Since 5 is tall, it will sink to the bottom of the Min-Heap.
        //We won't touch it again until all the other boundary walls shorter than 5 are processed.
        //This effectively "seals" that side until the water level of the whole lake rises to 5.
        //Case 2: Neighbor is Lower (Valley)
        //Situation: Current boundary is 5. Neighbor is 2.
        //Action: Water pours over the 5 wall and fills the 2 valley.
        //Calculation: Water trapped = 5 - 2 = 3.
        //Heap Update: This is the key part. The 2 is now full of water up to level 5.
        // It is now a "water surface" acting as a wall.
        //We do not add 2 to the heap.
        //We add 5 (the effective water height) to the heap at the new coordinates.
        //Wait: Since 5 might still be the smallest number in the heap (or close to it),
        //we might pop it again very soon to fill the next neighbor. This simulates the water spreading flat across the surface.
        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();

            for (int[] dir : dirs) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];

                // Check bounds and if already visited
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;

                    // 3. Calculate Water
                    // If the neighbor is lower than the current boundary (current.height),
                    // it traps water. The difference is the trapped volume.
                    if (heightMap[newRow][newCol] < current[2]) {
                        waterVolume += current[2] - heightMap[newRow][newCol];
                    }

                    //When you move from a boundary of height 3 to a neighbor of height 1,
                    // why do we push 3 into the PQ for the next step?
                    //Physics: Once the neighbor fills with water, it becomes a "puddle."
                    //If you are an inner cell standing next to this puddle, the "wall" holding you in is the surface of the water.
                    //The surface of the water is at height 3. So, for all intents and purposes, that cell is now a wall of height 3.
                    minHeap.offer(new int[]{newRow, newCol, Math.max(heightMap[newRow][newCol], current[2])});
                }
            }
        }

        return waterVolume;
    }
}
