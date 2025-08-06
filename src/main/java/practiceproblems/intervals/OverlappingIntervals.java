package practiceproblems.intervals;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/non-overlapping-intervals/
 */

public class OverlappingIntervals {

    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length <= 1) return 0;

        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int removals = 0;
        int lastEnd = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            int currentStart = intervals[i][0];
            int currentEnd = intervals[i][1];

            if (currentStart < lastEnd) {  // Overlap found
                removals++;
                // Keep the interval with smaller end time
                lastEnd = Math.min(lastEnd, currentEnd);
            } else {
                // No overlap, move to next interval
                lastEnd = currentEnd;
            }
        }

        return removals;
    }

}