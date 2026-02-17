package graph.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

//https://leetcode.com/problems/the-maze/
//https://leetcode.com/problems/the-maze-ii/
//https://leetcode.com/problems/the-maze-iii/
public class Maze {


    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        int rows = maze.length;
        int cols = maze[0].length;

        // Using a boolean array to track where the ball has STOPPED.
        // We do not mark paths it rolls over, only the stopping points.
        boolean[][] visited = new boolean[rows][cols];
        visited[start[0]][start[1]] = true;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);

        // Explicit directions: Up, Down, Left, Right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] currentPos = queue.poll();
            int currRow = currentPos[0];
            int currCol = currentPos[1];

            // Try rolling in all 4 directions from the current stopped position
            for (int[] dir : directions) {
                int moveRow = dir[0];
                int moveCol = dir[1];

                // Initialize rolling coordinates starting from current position
                int rollRow = currRow;
                int rollCol = currCol;

                // ROLL: Keep moving in the current direction until we hit a wall or boundary
                while (rollRow + moveRow >= 0 && rollRow + moveRow < rows &&
                        rollCol + moveCol >= 0 && rollCol + moveCol < cols &&
                        maze[rollRow + moveRow][rollCol + moveCol] == 0) {

                    rollRow += moveRow;
                    rollCol += moveCol;
                }

                // At this point, the loop has broken because the NEXT step is a wall.
                // So, (rollRow, rollCol) is the valid stopping point.

                // Check if we stopped at the destination
                if (rollRow == destination[0] && rollCol == destination[1]) {
                    return true;
                }

                // If we haven't stopped at this specific spot before, add it to queue
                if (!visited[rollRow][rollCol]) {
                    visited[rollRow][rollCol] = true;
                    queue.offer(new int[]{rollRow, rollCol});
                }
            }
        }

        return false;
    }

    //Maze 1: Can I stop here? (Yes/No) $\rightarrow$ Use boolean visited.
    //Maze 2: What is the shortest distance to stop here? $\rightarrow$ Use int distance and update if new < old.
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int rows = maze.length;
        int cols = maze[0].length;

        // Initialize distances to Infinity
        int[][] distance = new int[rows][cols];
        for (int[] row : distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Distance to start is 0
        distance[start[0]][start[1]] = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] currentPos = queue.poll();
            int currRow = currentPos[0];
            int currCol = currentPos[1];

            for (int[] dir : directions) {
                int moveRow = dir[0];
                int moveCol = dir[1];

                int rollRow = currRow;
                int rollCol = currCol;
                int stepCount = 0; // Track steps for this specific roll

                // ROLL: Count steps as we move
                while (rollRow + moveRow >= 0 && rollRow + moveRow < rows &&
                        rollCol + moveCol >= 0 && rollCol + moveCol < cols &&
                        maze[rollRow + moveRow][rollCol + moveCol] == 0) {

                    rollRow += moveRow;
                    rollCol += moveCol;
                    stepCount++;
                }

                // Calculate total distance from start to this new stop
                // = (Distance to reach previous stop) + (steps rolled just now)
                int newDist = distance[currRow][currCol] + stepCount;

                // RELAXATION: Only update and push to queue if we found a SHORTER path
                if (newDist < distance[rollRow][rollCol]) {
                    distance[rollRow][rollCol] = newDist;
                    queue.offer(new int[]{rollRow, rollCol});
                }
            }
        }

        // If destination is still Infinity, we never reached it
        return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : distance[destination[0]][destination[1]];
    }

    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int rows = maze.length;
        int cols = maze[0].length;

        int holeRow = hole[0];
        int holeCol = hole[1];

        // Distance array to store minimum steps to reach each cell
        int[][] distance = new int[rows][cols];
        for (int[] row : distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Path array to store the actual string instructions (e.g., "lur")
        String[][] instructions = new String[rows][cols];

        // Initialization
        distance[ball[0]][ball[1]] = 0;
        instructions[ball[0]][ball[1]] = "";

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(ball);

        // Directions defined clearly: {rowOffset, colOffset}
        // and a separate array for the String representation.
        int[][] dirOffsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] dirNames = {"u", "d", "l", "r"};

        while (!queue.isEmpty()) {
            int[] currentPos = queue.poll();
            int currRow = currentPos[0];
            int currCol = currentPos[1];

            // Explore all 4 directions
            for (int k = 0; k < 4; k++) {
                int moveRow = dirOffsets[k][0];
                int moveCol = dirOffsets[k][1];
                String moveName = dirNames[k];

                int rollRow = currRow;
                int rollCol = currCol;
                int steps = distance[currRow][currCol];

                // ROLL LOOP
                // We keep rolling if:
                // 1. Next cell is within bounds
                // 2. Next cell is not a wall
                // 3. We are NOT currently sitting in the hole (Important!)
                while (rollRow + moveRow >= 0 && rollRow + moveRow < rows &&
                        rollCol + moveCol >= 0 && rollCol + moveCol < cols &&
                        maze[rollRow + moveRow][rollCol + moveCol] == 0) {

                    rollRow += moveRow;
                    rollCol += moveCol;
                    steps++;

                    // CRITICAL: Check if we fell into the hole while rolling
                    if (rollRow == holeRow && rollCol == holeCol) {
                        break; // Stop rolling immediately
                    }
                }

                // We have now stopped at (rollRow, rollCol).
                // Let's see if this path is better than what we found before.

                String newPath = instructions[currRow][currCol] + moveName;

                // LOGIC: Update if:
                // 1. We found a strictly shorter distance
                // 2. OR, distance is equal, but the new path string is alphabetically smaller
                if (steps < distance[rollRow][rollCol] ||
                        (steps == distance[rollRow][rollCol] && newPath.compareTo(instructions[rollRow][rollCol]) < 0)) {

                    distance[rollRow][rollCol] = steps;
                    instructions[rollRow][rollCol] = newPath;

                    // Only continue exploring from this spot if we are NOT in the hole.
                    // If we are in the hole, the ball stops forever.
                    if (!(rollRow == holeRow && rollCol == holeCol)) {
                        queue.offer(new int[]{rollRow, rollCol});
                    }
                }
            }
        }

        // If the hole's instruction string is null or empty (and we aren't at start), it's impossible.
        // Note: The problem guarantees ball != hole at start.
        return instructions[holeRow][holeCol] == null ? "impossible" : instructions[holeRow][holeCol];
    }
}
