package practiceproblems;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/pascals-triangle/
 * <p>
 * Input: 5
 * Output:
 * [
 * [1],
 * [1,1],
 * [1,2,1],
 * [1,3,3,1],
 * [1,4,6,4,1]
 * ]
 */
public class PascalsTriangle {

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1); // First element is always 1

            // Fill middle elements (if any)
            for (int j = 1; j < i; j++) {
                int prevVal = triangle.get(i - 1).get(j - 1);
                int nextVal = triangle.get(i - 1).get(j);
                row.add(prevVal + nextVal);
            }

            if (i > 0) {
                row.add(1);
            }

            triangle.add(row);
        }

        return triangle;
    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        row.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            List<Integer> newRow = new ArrayList<>();
            newRow.add(1);
            for (int j = 1; j < row.size(); j++) {
                newRow.add(row.get(j - 1) + row.get(j));
            }
            newRow.add(1);

            row = new ArrayList<>(newRow);
        }

        return row;
    }
}