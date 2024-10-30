package graph.leetcode;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * https://leetcode.com/problems/surrounded-regions
 * https://leetcode.com/problems/number-of-enclaves
 */
public class SurroundedRegions {

    // since we want to exclude all the 'O's that are connected to the boundary, we can start from the boundary and do a BFS/DFS
    public void solve(char[][] board) {
        if (board == null || board.length == 0)
            return;

        Queue<Pair> queue = new ArrayDeque<>();
        int[][] dirs = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') {
                    if (i == board.length - 1 || j == board[0].length - 1 || i == 0 || j == 0) {
                        board[i][j] = '1';
                        queue.offer(new Pair(i, j, 0));
                    }
                }
            }
        }

        while (!queue.isEmpty()) {
            Pair temp = queue.poll();

            for (int[] dir : dirs) {
                int row = temp.x + dir[0];
                int col = temp.y + dir[1];

                if (isValid(row, col, board) && board[row][col] == 'O') {
                    board[row][col] = '1';
                    queue.offer(new Pair(row, col, 0));
                }
            }

        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }

    }

    public boolean isValid(int x, int y, char[][] board) {
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }

    public void solveDFS(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O' && (i == 0 || j == 0 || i == m - 1 || j == n - 1)) {
                    boundaryDFS(board, i, j);
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void boundaryDFS(char[][] board, int i, int j) {
        if (i < 0 || i > board.length - 1 || j < 0 || j > board[0].length - 1 || board[i][j] != 'O')
            return;

        board[i][j] = 'A';
        boundaryDFS(board, i - 1, j);
        boundaryDFS(board, i + 1, j);

        boundaryDFS(board, i, j - 1);

        boundaryDFS(board, i, j + 1);
    }

    private static class Pair {
        int x;
        int y;
        int level;

        public Pair(int x, int y, int level) {
            this.x = x;
            this.y = y;
            this.level = level;
        }
    }
}

