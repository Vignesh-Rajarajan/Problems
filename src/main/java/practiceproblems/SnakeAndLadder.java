package practiceproblems;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * tricky bfs
 * https://leetcode.com/problems/snakes-and-ladders/
 */
public class SnakeAndLadder {

    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{1, 0});
        Set<Integer> visit = new HashSet<>();

        while (!queue.isEmpty()) {
            int[] tmp = queue.poll();
            int pos = tmp[0];
            int moves = tmp[1];
            for (int i = 1; i <= 6; i++) {
                int nextPos = pos + i;
                int[] boardPos = toBoardPos(nextPos, n);
                int row = boardPos[0];
                int col = boardPos[1];
                if (board[row][col] != -1) {
                    nextPos = board[row][col];
                }

                if (nextPos == n * n) {
                    return moves + 1;
                }

                if (visit.contains(nextPos)) {
                    continue;
                }

                visit.add(nextPos);
                queue.offer(new int[]{nextPos, moves + 1});

            }
        }

        return -1;
    }

    /**
     * Let nextPos=1
     * 1/6=0
     * 6-0-1=5 <-- this is the row we are at
     * This will work for 1,2,3,4,5 but not for 6
     * 6/6=1
     * 6-1-1=4, but we are still at row 5
     * So the way to tackle this is by using this:
     * int oldRow=(next_step-1)/n;
     * row=n-1-oldRow;
     * This will make 1,2,3,4,5,6 all in the same row.
     * column is easy all u have to do is int oldCol=(next_step-1)%n;
     * 2. For flipping direction  (i.e snake goes upward in zig-zag fashion )all we are going to dow is we have found oldRow in the previous step.
     * so for every odd oldRow we flip the direction and for every even oldRow we maintain our normal direction.
     * <p>
     * if(x%2==1) col =n-1-oldCol;
     */
    public int[] toBoardPos(int nextPos, int n) {
        int row = (nextPos - 1) / n;
        int col = (nextPos - 1) % n;
        if (row % 2 == 1) {
            col = n - 1 - col;
        }
        row = n - 1 - row;

        return new int[]{row, col};
    }
}