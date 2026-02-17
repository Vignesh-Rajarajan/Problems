package graph.leetcode;

import java.util.HashSet;
import java.util.Set;

// https://leetcode.com/problems/number-of-distinct-islands/
//How many distinct (unique shape) islands are there?
public class NumberOfDistinctIsland {
    class Solution {
        public int numDistinctIslands(int[][] grid) {
            Set<String> distinctShapes = new HashSet<>();
            int m = grid.length;
            int n = grid[0].length;
            boolean[][] visited = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // Found a new island
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        StringBuilder shape = new StringBuilder();
                        // Pass the start coordinates (i, j) to calculate relative offsets
                        dfs(grid, i, j, i, j, visited, shape);
                        distinctShapes.add(shape.toString());
                    }
                }
            }
            return distinctShapes.size();
        }

        private void dfs(int[][] grid, int r, int c, int startR, int startC, boolean[][] visited, StringBuilder shape) {
            int m = grid.length;
            int n = grid[0].length;

            if (r < 0 || c < 0 || r >= m || c >= n || grid[r][c] == 0 || visited[r][c]) {
                return;
            }

            visited[r][c] = true;

            // Record relative position: "rowOffset,colOffset"
            // This makes the shape identical regardless of where it is on the map
            //Imagine an island at (10, 10) and another identical island at (50, 50).
            //Island 1: Starts at (10,10).
            //It has a block at (10,11). Relative pos: (10-10, 11-10) = (0, 1).
            //Island 2: Starts at (50,50).
            //It has a block at (50,51). Relative pos: (50-50, 51-50) = (0, 1).
            //The string signature for both will be identical (e.g., "0,0|0,1|..."), so the HashSet will only count them once.
            shape.append((r - startR) + "," + (c - startC) + "|");

            dfs(grid, r + 1, c, startR, startC, visited, shape);
            dfs(grid, r - 1, c, startR, startC, visited, shape);
            dfs(grid, r, c + 1, startR, startC, visited, shape);
            dfs(grid, r, c - 1, startR, startC, visited, shape);
        }
    }
}
