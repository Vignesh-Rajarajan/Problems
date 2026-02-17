package graph.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

//https://leetcode.com/problems/minimum-time-to-visit-a-cell-in-a-grid/
public class MinimumTimeToVisitGrid {
    int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int minimumTime(int[][] grid) {
        if (Math.min(grid[0][1], grid[1][0]) > 1) {
            return -1;
        }
        int r = grid.length;
        int c = grid[0].length;
        boolean[][] visited = new boolean[r][c];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.offer(new int[]{0, 0, 0});
        while (!minHeap.isEmpty()) {
            int[] node = minHeap.poll();
            int time = node[0];
            int row = node[1];
            int col = node[2];

            if (row == r - 1 && col == c - 1) {
                return time;
            }

            if (visited[row][col]) {
                continue;
            }

            visited[row][col] = true;

            for (int[] d : dirs) {
                int newRow = row + d[0];
                int newCol = col + d[1];

                if (newRow < 0 || newCol < 0 || newRow >= r || newCol >= c || visited[newRow][newCol]) {
                    continue;
                }

                //Imagine you are driving to a store.
                //time + 1 (Physical Reality): It takes you 1 minute to drive there.
                //If it's currently 10:00, the earliest you can physically arrive is 10:01. You cannot teleport.
                //grid[newRow][newCol] + wait (The Rules): The store has an opening time.
                //If the store opens at 12:00, you cannot enter at 10:01. You have to wait.

                //Scenario A: The Store is Already Open
                //Current Time: 10
                //Store Opens (Grid Value): 5
                //Physics (time + 1): You arrive at 11.
                //Rules (grid): Store opened at 5.
                //The Logic: Math.max(5, 11) = 11
                //Intuition: The store opened ages ago. The only thing slowing you down is the drive itself. You enter the moment you arrive.

                //Scenario B: The Store is Closed (The "Ping-Pong" Problem)
                //This is where the + wait comes in. In this grid problem, you cannot stay still.
                // To waste time, you must step back and forth between squares (ping-pong),
                // which consumes time in chunks of 2 seconds (1 sec there, 1 sec back).
                //Current Time: 10
                //Store Opens (Grid Value): 12
                //Physics (time + 1): You arrive at the door at 11.
                //The Problem: You are at the door at time 11. The store opens at 12.
                //You can't just stand there for 1 second. You have to move.
                //Move back to previous square: Time becomes 12.
                //Move back to store door: Time becomes 13.

                //Example 1: The "Odd Gap" (Safe)
                //Current Time: 10
                //Target Requirement: 13
                //Gap: 13 - 10 = 3 (Odd) -> Condition is False
                //You step to the target. Your time is 11.
                //Target needs 13. You are 2 seconds early.
                //Can you fix this? Yes! Step back to your previous cell (time 12), then step to target again (time 13).
                //Result: You arrive exactly at 13. No extra penalty needed.
                //You step to the target. Your time is 11.Target needs 12.
                //You are 1 second early.Can you fix this? You need to waste 1 second.
                //But you can only waste time in chunks of 2 (ping-ponging).
                //If you ping-pong once, time becomes $11 + 2 = 13$.You missed the slot of 12!

                int wait = 0;
                if (Math.abs(grid[newRow][newCol] - time) % 2 == 0) {
                    wait = 1;
                }
                int newTime = Math.max(grid[newRow][newCol] + wait, time + 1);
                minHeap.offer(new int[]{newTime, newRow, newCol});

            }

        }

        return -1;

    }
}
