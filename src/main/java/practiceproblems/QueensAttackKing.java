package practiceproblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//https://leetcode.com/problems/queens-that-can-attack-the-king/

/**
 * On an 8x8 chessboard, there can be multiple Black Queens and one White King.
 * <p>
 * Given an array of integer coordinates queens that represents the positions of the Black Queens,
 * and a pair of coordinates king that represent the position of the White King,
 * return the coordinates of all the queens (in any order) that can attack the King.
 */
class QueensAttackKing {
    public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[][] visited = new boolean[8][8];
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {1, 1}, {1, -1}, {-1, 1}};
        // first marking all queen positions
        for (int[] qu : queens) {
            visited[qu[0]][qu[1]] = true;
        }

        for (int[] dir : dirs) {
            List<Integer> temp = findQueensPositions(king, dir[0], dir[1], visited);
            if (temp != null) result.add(temp);
        }

        return result;

    }

    public List<Integer> findQueensPositions(int[] king, int xDir, int yDir, boolean[][] visited) {
        int newX = xDir + king[0];
        int newY = yDir + king[1];
        // going to walk along x,y only not 8 directions at same time
        while (newX < 8 && newY < 8 && newX >= 0 && newY >= 0) {
            if (visited[newX][newY]) {
                return Arrays.asList(newX, newY); // returns when first queen is met in row or column
            }

            newX += xDir;
            newY += yDir;

        }

        return null;
    }

    public static void main(String[] args) {
        int[][] queens = {{0, 1}, {1, 0}, {4, 0}, {0, 4}, {3, 3}, {2, 4}};
        int[] king = {0, 0};
        new QueensAttackKing().queensAttacktheKing(queens, king);
    }
}