package graph.leetcode;

//https://leetcode.com/problems/count-sub-islands/
//tricky
public class SubIsland {
    int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int result = 0;

        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if (grid2[i][j] == 1) {
                    if (dfs(grid2, i, j, grid1)) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    public boolean dfs(int[][] grid2, int i, int j, int[][] grid1) {
        // Base case: out of bounds or water (0) → return true
        //If we're out of bounds or hit water (0), it doesn't hurt the sub-island condition.
        //So we return true — "this direction is fine".
        if (i < 0 || j < 0 || i >= grid2.length || j >= grid2[0].length || grid2[i][j] == 0) {
            return true;
        }

        // If grid1[i][j] is not 1, then this cell is NOT part of an island in grid1
        // So this island can't be a sub-island
        //So the whole island cannot be a sub-island → mark result = false.
        //But note: we don’t return immediately — we still need to flood-fill and mark visited to avoid revisiting this island later.
        boolean result = grid1[i][j] == 1;
        // Mark current cell as visited (set to 0)
        grid2[i][j] = 0;

        // Recursively check all 4 neighbors
        for (int[] d : dirs) {
            //Even if one cell in the island fails the sub-island condition (grid1[i][j] != 1), the whole island fails.
            //The &= (logical AND) propagates failure: one false makes the whole DFS return false.
            result &= dfs(grid2, i + d[0], j + d[1], grid1);
        }

        return result;
    }
}
