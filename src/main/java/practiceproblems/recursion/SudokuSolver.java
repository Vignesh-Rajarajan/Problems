package practiceproblems.recursion;

import java.util.Arrays;

/**
 * tricky recursion
 */
public class SudokuSolver {
    public void solveSudoku(char[][] board) {
        solveBoard(board, 0, 0);
    }

    private boolean solveBoard(char[][] board, int row, int col) {
        if (col == 9) {
            row++;
            col = 0;
        }
        if (row == 9) return true;

        if (board[row][col] != '.') {
            return solveBoard(board, row, col + 1);
        }

        for (char num = '1'; num <= '9'; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                if (solveBoard(board, row, col + 1)) { // We only need to find ONE valid solution
                    // Without this check, we would keep exploring even after finding a solution
                    return true;
                }
            }
        }
        board[row][col] = '.';
        return false;
    }

    // Make sure the digit doesn't exist in the current row, col or square.
    private boolean isValid(char[][] board, int row, int col, char num) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) return false;
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) return false;
        }

        // Check 3x3 box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRow + i][boxCol + j] == num) return false;
            }
        }

        return true;
    }

    /**
     * start from 0 and go till 81 (9*9), for each cell do a dfs, if not backtrack and change the value
     */
    public void solveSudokuDFS(char[][] board) {
        dfs(board, 0);
    }

    private boolean dfs(char[][] board, int d) {
        if (d == 81) return true; //found solution
        int i = d / 9, j = d % 9;
        if (board[i][j] != '.') return dfs(board, d + 1);//prefill number skip

        boolean[] flag = new boolean[10];
        validate(board, i, j, flag);
        for (int k = 1; k <= 9; k++) {
            if (flag[k]) {
                board[i][j] = (char) ('0' + k);
                if (dfs(board, d + 1)) return true;
            }
        }
        board[i][j] = '.'; //if you can not solve, in the wrong path, change back to '.' and out
        return false;
    }

    // in arr[0..9] fill the values with numbers in row and col as false
    private void validate(char[][] board, int i, int j, boolean[] flag) {
        Arrays.fill(flag, true);
        for (int k = 0; k < 9; k++) {
            if (board[i][k] != '.') flag[board[i][k] - '0'] = false;
            if (board[k][j] != '.') flag[board[k][j] - '0'] = false;
            int r = i / 3 * 3 + k / 3;
            int c = j / 3 * 3 + k % 3;
            if (board[r][c] != '.') flag[board[r][c] - '0'] = false;
        }
    }
}
