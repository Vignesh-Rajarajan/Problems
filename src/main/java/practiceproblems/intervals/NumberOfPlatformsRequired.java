package practiceproblems.intervals;

import java.util.Arrays;
import java.util.PriorityQueue;

//Goal: Find the minimum number of platforms needed so that no train has to wait for a platform.
//Approach: Determine the maximum number of overlapping intervals at any point in time.
public class NumberOfPlatformsRequired {
    static int findPlatform(int arr[], int dep[]) {
        // Create an array of trains with arrival and departure times
        int[][] trains = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            trains[i][0] = arr[i];
            trains[i][1] = dep[i];
        }

        // Sort the trains based on arrival time
        Arrays.sort(trains, (a, b) -> Integer.compare(a[0], b[0]));

        // Min-heap to keep track of departure times
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(trains[0][1]);
        int platformsNeeded = 1;

        for (int i = 1; i < trains.length; i++) {
            int currentArrival = trains[i][0];
            int earliestDeparture = minHeap.peek();

            if (currentArrival > earliestDeparture) {
                minHeap.poll();
            } else {
                platformsNeeded++;
            }
            minHeap.offer(trains[i][1]);
        }

        return platformsNeeded;
    }
}
