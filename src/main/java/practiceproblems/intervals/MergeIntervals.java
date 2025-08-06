package practiceproblems.intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/merge-intervals/
 */
class MergeIntervals {

    public static int[][] merge(int[][] intervals) {
        List<int[]> result = new ArrayList<>();
        if (intervals.length == 0) return result.toArray(new int[0][]);

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int[] currentInterval = intervals[0];
        result.add(currentInterval);

        for (int[] interval : intervals) {
            int currentEnd = currentInterval[1];
            int nextStart = interval[0];
            int nextEnd = interval[1];

            if (currentEnd >= nextStart) { // Overlapping intervals
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                currentInterval = interval;
                result.add(currentInterval);
            }
        }

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        // [[1,3],[2,6],[8,10],[15,18]]
        int[][] arr = {{1, 9}, {6, 8}, {2, 4}, {4, 7}};
        //{ { 1, 3 }, { 2, 4 }, { 5, 7 }, { 6, 8 } };
        //{{1, 9}, {2, 4}, {4, 7}, {6, 8}};
        System.out.println(Arrays.deepToString(merge(arr)));
    }
}