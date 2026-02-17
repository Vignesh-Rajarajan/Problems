package practiceproblems.design;

import java.util.PriorityQueue;

//https://leetcode.com/problems/seat-reservation-manager/
public class SeatManager {
    private PriorityQueue<Integer> unreservedHeaps;
    private int marker;

    public SeatManager(int n) {
        unreservedHeaps = new PriorityQueue<>();
        marker = 1; // This is your counter
    }

    public int reserve() {
        // Always prioritize the unreserved "gaps" first
        if (!unreservedHeaps.isEmpty()) {
            return unreservedHeaps.poll();
        }
        // Otherwise, take the next new seat and increment
        return marker++;
    }

    public void unreserve(int seatNumber) {
        unreservedHeaps.offer(seatNumber);
    }
}
