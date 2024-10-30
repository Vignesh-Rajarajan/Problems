package graph.leetcode;

import graph.disjoints.DisjointSetBySize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://leetcode.com/problems/number-of-islands-ii/
// https://takeuforward.org/graph/number-of-islands-ii-online-queries-dsu-g-51/
public class NoOfIslandsII {

    public static void main(String[] args) {
        NoOfIslandsII noOfIslandsII = new NoOfIslandsII();
        int m = 3;
        int n = 3;
        int[][] queries = new int[][]{{0, 0}, {0, 0}, {1, 1}, {1, 0}, {0, 1}, {0, 3}, {1, 3}, {0, 4}, {3, 2}, {2, 2}, {1, 2}, {0, 2}};
        System.out.println(Arrays.toString(noOfIslandsII.noOfIslands(4, 5, queries)));
    }

    public int[] noOfIslands(int m, int n, int[][] queries) {
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        DisjointSetBySize ds = new DisjointSetBySize(m * n);
        boolean[][] visited = new boolean[m][n];
        List<Integer> result = new ArrayList<>();
        int count = 0;
        for (int[] query : queries) {
            int row = query[0];
            int col = query[1];

            if (visited[row][col]) {
                result.add(count);
                continue;
            }
            visited[row][col] = true;
            count++;
            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && visited[newRow][newCol]) {
                    int nodeNum = row * n + col;
                    int newNodeNum = newRow * n + newCol;
                    if (ds.findParent(nodeNum) != ds.findParent(newNodeNum)) {
                        ds.unionByRank(nodeNum, newNodeNum);
                        count--;
                    }
                }
            }
            result.add(count);
        }

        return result.stream().mapToInt(i -> i).toArray();
    }
}
