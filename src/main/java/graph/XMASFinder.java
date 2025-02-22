package graph;

public class XMASFinder {

    //  specifically looking for words that exist in a straight line (horizontal, vertical, or diagonal)
    // if there's a change in direction, the program will stop checking for the word
    // like L or inverted L, the program will not be able to find the word
    public static void main(String[] args) {
        String input = "MMMSXXMASM\n" +
                "MSAMXMSMSA\n" +
                "AMXSXMAAMM\n" +
                "MSAMASMSMX\n" +
                "XMASAMXAMM\n" +
                "XXAMMXXAMA\n" +
                "SMSMSASXSS\n" +
                "SAXAMASAAA\n" +
                "MAMMMXMMMM\n" +
                "MXMXAXMASX";

        int result = part1(input);
        System.out.println(result); // Output should be 18
    }

    public static int part1(String input) {
        long start = System.nanoTime();
        int result = 0;
        String[] lines = input.split("\n");
        int n = lines.length;
        int m = lines[0].length();

        // Directions: 8 possible directions (horizontal, vertical, diagonal)
        int[][] directions = {
                {1, 0},   // Right
                {-1, 0},  // Left
                {0, 1},   // Down
                {0, -1},  // Up
                {1, 1},   // Diagonal right-down
                {1, -1},  // Diagonal right-up
                {-1, 1},  // Diagonal left-down
                {-1, -1}  // Diagonal left-up
        };

        // Iterate over every cell and every direction
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int[] dir : directions) {
                    if (hasXMAS(lines, i, j, dir[0], dir[1])) {
                        result++;
                    }
                }
            }
        }

        long end = System.nanoTime();
        System.out.println("Time taken: " + (end - start) / 1_000_000 + " ms");
        return result;
    }

    // Function to check if "XMAS" exists starting at (i, j) in direction (dx, dy)
    public static boolean hasXMAS(String[] lines, int i, int j, int dx, int dy) {
        String target = "XMAS";
        //If going right: dx=1, dy=0, so it checks (i,j), (i,j+1), (i,j+2), (i,j+3)
        //If    diagonal: dx=1, dy=1, so it checks (i,j), (i+1,j+1), (i+2,j+2), (i+3,j+3)
        //We couldn't check consecutive positions in different directions
        //We wouldn't be able to verify if the letters appear in the correct order
        //We wouldn't be able to validate if we're staying within grid boundaries
        //The program would only check a single position instead of the 4 positions needed for "XMAS"
        for (int k = 0; k < target.length(); k++) {
            int x = i + k * dx;
            int y = j + k * dy;
            if (x < 0 || x >= lines.length || y < 0 || y >= lines[0].length() || lines[x].charAt(y) != target.charAt(k)) {
                return false;
            }
        }
        return true;
    }
}
