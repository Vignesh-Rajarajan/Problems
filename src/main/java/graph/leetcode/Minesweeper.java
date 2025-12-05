package graph.leetcode;


import java.util.ArrayDeque;
import java.util.Deque;

//https://leetcode.com/problems/minesweeper/
public class Minesweeper {
    public char[][] updateBoard(char[][] board, int[] click) {
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        int r = click[0], c = click[1];
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(click);
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        visited[r][c] = true;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int currR = cell[0], currC = cell[1];

            int minesCount = countMines(board, currR, currC);
            if (minesCount > 0) {
                board[currR][currC] = (char) (minesCount + '0');
            } else {
                board[currR][currC] = 'B';
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }

                        int nr = currR + i;
                        int nc = currC + j;

                        // Check bounds and if it is an unvisited Empty square
                        if (nr >= 0 && nr < m && nc >= 0 && nc < n && !visited[nr][nc] && board[nr][nc] == 'E') {
                            queue.offer(new int[]{nr, nc});
                            visited[nr][nc] = true; // Mark visited immediately when adding to queue
                        }

                    }
                }
            }

        }

        return board;
    }

    private int countMines(char[][] board, int curRow, int curCol) {
        int result = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                int nr = curRow + i;
                int nc = curCol + j;
                if (nr >= 0 && nr < board.length && nc >= 0 && nc < board[0].length) {
                    if (board[nr][nc] == 'M') {
                        result++;
                    }
                }

            }
        }

        return result;
    }
}
