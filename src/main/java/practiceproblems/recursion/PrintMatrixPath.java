package practiceproblems.recursion;

import java.util.ArrayList;
import java.util.Collections;

public class PrintMatrixPath {

    public ArrayList<String> findPath(ArrayList<ArrayList<Integer>> mat) {
        ArrayList<String> result = new ArrayList<>();

        // Check if the matrix is empty or the starting cell is blocked
        if (mat == null || mat.isEmpty() || mat.get(0).isEmpty() || mat.get(0).get(0) == 0) {
            return result;
        }

        // Initialize visited array
        boolean[][] visited = new boolean[mat.size()][mat.get(0).size()];

        // Start recursion from the top-left corner (0, 0)
        recursionHelper(mat, 0, 0, result, new StringBuilder(), visited);
        Collections.sort(result);
        return result;
    }

    public void recursionHelper(ArrayList<ArrayList<Integer>> mat, int i, int j, ArrayList<String> result,
                                StringBuilder sb, boolean[][] visited) {
        // Check if the current cell is out of bounds, blocked, or already visited
        if (i < 0 || i >= mat.size() || j < 0 || j >= mat.get(0).size() || mat.get(i).get(j) == 0 || visited[i][j]) {
            return;
        }

        // If we reach the bottom-right corner, add the path to the result
        if (i == mat.size() - 1 && j == mat.get(0).size() - 1) {
            result.add(sb.toString());
            return;
        }

        // Mark the current cell as visited
        visited[i][j] = true;

        if (i - 1 >= 0 && mat.get(i - 1).get(j) == 1) { // Up
            sb.append("U");
            recursionHelper(mat, i - 1, j, result, sb, visited);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }

        if (j - 1 >= 0 && mat.get(i).get(j - 1) == 1) { // Left
            sb.append("L");
            recursionHelper(mat, i, j - 1, result, sb, visited);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }

        if (i + 1 < mat.size() && mat.get(i + 1).get(j) == 1) { // Down
            sb.append("D");
            recursionHelper(mat, i + 1, j, result, sb, visited);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }

        // Explore all four directions: Right, Down, Left, Up
        if (j + 1 < mat.get(0).size() && mat.get(i).get(j + 1) == 1) { // Right
            sb.append("R");
            recursionHelper(mat, i, j + 1, result, sb, visited);
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }
        // Unmark the current cell to allow other paths to explore it
        visited[i][j] = false;
    }
}
