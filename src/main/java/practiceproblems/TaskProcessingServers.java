package practiceproblems;

import java.util.PriorityQueue;

//https://leetcode.com/problems/process-tasks-using-servers/
public class TaskProcessingServers {
    public int[] assignTasks(int[] servers, int[] tasks) {
        // Available: {weight, index}
        PriorityQueue<int[]> available = new PriorityQueue<>(
                (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));

        // Busy: {readyTime, weight, index}
        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 0; i < servers.length; i++) {
            available.offer(new int[]{servers[i], i});
        }

        int[] res = new int[tasks.length];
        int time = 0;
        for (int i = 0; i < tasks.length; i++) {
            // The current task i arrives at time 'i'.
            // Time cannot be earlier than the task arrival.
            time = Math.max(time, i);

            // 1. Move ALL finished servers to available pool
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] s = busy.poll();
                available.offer(new int[]{s[1], s[2]});
            }

            // 2. If no servers available, jump time to the next available server
            if (available.isEmpty()) {
                time = busy.peek()[0];
                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    int[] s = busy.poll();
                    available.offer(new int[]{s[1], s[2]});
                }
            }

            // 3. Assign the task
            int[] server = available.poll();
            res[i] = server[1];
            busy.offer(new int[]{time + tasks[i], server[0], server[1]});
        }

        return res;

    }
}
