package graph.leetcode;

import java.util.Deque;
import java.util.LinkedList;

//https://neetcode.io/solutions/walls-and-gates
public class WallsAndGates {
    public void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        int n = rooms[0].length;
        Deque<int[]> q = new LinkedList<>();
        final int GATE = 0;
        final int INF = Integer.MAX_VALUE;

        int[] dirX = {-1, 0, 1, 0};
        int[] dirY = {0, 1, 0, -1};

        // Enqueue all gates
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (rooms[i][j] == GATE) {
                    q.offer(new int[]{i, j});
                }
            }
        }

        int d = 0;
        while (!q.isEmpty()) {
            ++d;
            int size = q.size();
            for (int i = 0; i < size; ++i) {
                int[] p = q.poll();
                for (int j = 0; j < 4; ++j) {
                    int x = p[0] + dirX[j];
                    int y = p[1] + dirY[j];
                    if (x >= 0 && x < m && y >= 0 && y < n && rooms[x][y] == INF) {
                        rooms[x][y] = d;
                        q.offer(new int[]{x, y});
                    }
                }
            }
        }
    }
}
