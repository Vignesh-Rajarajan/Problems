package geeksforgeeks;

class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0)
            return;

        Queue<Pair> queue = new ArrayDeque<>();
        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
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
                int newx = temp.x + dir[0];
                int newy = temp.y + dir[1];

                if (isValid(newx, newy, board) && board[newx][newy] == 'O') {
                    board[newx][newy] = '1';
                    queue.offer(new Pair(newx, newy, 0));
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
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length)
            return false;
        return true;
    }

    public void solveDFS(char[][] board) {
        if (board.length == 0 || board[0].length == 0)
            return;
        if (board.length < 2 || board[0].length < 2)
            return;
        int m = board.length, n = board[0].length;
        // Any 'O' connected to a boundary can't be turned to 'X', so ...
        // Start from first and last column, turn 'O' to '*'.
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O')
                boundaryDFS(board, i, 0);
            if (board[i][n - 1] == 'O')
                boundaryDFS(board, i, n - 1);
        }
        // Start from first and last row, turn '0' to '*'
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O')
                boundaryDFS(board, 0, j);
            if (board[m - 1][j] == 'O')
                boundaryDFS(board, m - 1, j);
        }
        // post-prcessing, turn 'O' to 'X', '*' back to 'O', keep 'X' intact.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O')
                    board[i][j] = 'X';
                else if (board[i][j] == '*')
                    board[i][j] = 'O';
            }
        }
    }

    // Use DFS algo to turn internal however boundary-connected 'O' to '*';
    //Use DFS algo to turn internal however boundary-connected 'O' to '*';
private void boundaryDFS(char[][] board, int i, int j) {
	if (i < 0 || i > board.length - 1 || j <0 || j > board[0].length - 1 )
		return;
	if (board[i][j] == 'O'){
		board[i][j] = '*';
    
		boundaryDFS(board, i-1, j);
	
		boundaryDFS(board, i+1, j);
	
		boundaryDFS(board, i, j-1);

		boundaryDFS(board, i, j+1);
    }
}
}

class Pair {
    int x;
    int y;
    int level;

    public Pair(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }
}