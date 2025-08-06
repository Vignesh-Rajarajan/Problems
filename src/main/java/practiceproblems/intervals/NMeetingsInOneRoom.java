package practiceproblems.intervals;

import java.util.PriorityQueue;


public class NMeetingsInOneRoom {

    public int maxMeetings(int[] start, int[] end) {
        PriorityQueue<int[]> minQueue = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            } else {
                return Integer.compare(a[0], b[0]);
            }
        });

        for (int i = 0; i < start.length; i++) {
            minQueue.offer(new int[]{start[i], end[i]});
        }

        if (minQueue.isEmpty()) return 0; // handle empty input case

        int prevEnd = minQueue.poll()[1];
        int result = 1;

        while (!minQueue.isEmpty()) {
            int[] curr = minQueue.poll();
            if (prevEnd < curr[0]) {
                prevEnd = curr[1];
                result++;
            }
        }

        return result;
    }
}
