package graph.leetcode;

// https://leetcode.com/problems/flood-fill/
public class FloodFill {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        boolean[][] vis = new boolean[image.length][image[0].length];
        helper(image, sr, sc, color, vis, image[sr][sc]);
        return image;
    }

    public void helper(int[][] image, int sr, int sc, int targetColor, boolean[][] vis, int parentColor) {
        if (sr < 0 || sc < 0 || sr >= image.length || sc >= image[0].length || vis[sr][sc] || image[sr][sc] != parentColor) {
            return;
        }
        image[sr][sc] = targetColor;
        vis[sr][sc] = true;
        helper(image, sr - 1, sc, targetColor, vis, parentColor);
        helper(image, sr + 1, sc, targetColor, vis, parentColor);
        helper(image, sr, sc - 1, targetColor, vis, parentColor);
        helper(image, sr, sc + 1, targetColor, vis, parentColor);

    }
}
