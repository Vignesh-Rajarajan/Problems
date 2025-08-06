package graph.leetcode;
//https://leetcode.com/problems/count-servers-that-communicate
public class CommunicatingServers {
    public int countServers(int[][] grid) {
        int result = 0;
        int[] rowCount = new int[grid.length];
        int[] colCount = new int[grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    rowCount[i]++;
                    colCount[j]++;
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && (rowCount[i] > 1 || colCount[j] > 1)) {
                    result++;
                }
            }
        }

        return result;

    }
}
