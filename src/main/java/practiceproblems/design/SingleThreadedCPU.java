package practiceproblems.design;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//https://leetcode.com/problems/single-threaded-cpu/
public class SingleThreadedCPU {
    // A simple helper to keep task data organized
    private record Task(int id, int enqueueTime, int processingTime) {
    }

    public int[] getOrder(int[][] tasksData) {
        int n = tasksData.length;
        Task[] allTasks = new Task[n];

        for (int i = 0; i < n; i++) {
            allTasks[i] = new Task(i, tasksData[i][0], tasksData[i][1]);
        }

        // 1. Sort tasks by arrival (enqueue) time
        Arrays.sort(allTasks, Comparator.comparingInt(t -> t.enqueueTime));

        // 2. PQ stores "available" tasks: prioritize processing time, then original index
        PriorityQueue<Task> availableTasks = new PriorityQueue<>((a, b) ->
                a.processingTime == b.processingTime ? a.id - b.id : a.processingTime - b.processingTime
        );

        int[] result = new int[n];
        int resultIdx = 0;
        int taskIdx = 0;
        long currentTime = 0;

        while (resultIdx < n) {
            // A. If no tasks are available to run, jump time to the next task's arrival
            // this is not a while loop because we only want to jump time once to the next task's arrival, not multiple times
            if (availableTasks.isEmpty() && currentTime < allTasks[taskIdx].enqueueTime) {
                currentTime = allTasks[taskIdx].enqueueTime;
            }

            // B. Add all tasks that have arrived by the current time to the pool
            while (taskIdx < n && allTasks[taskIdx].enqueueTime <= currentTime) {
                availableTasks.offer(allTasks[taskIdx]);
                taskIdx++;
            }

            // C. Process the best task in the pool (Shortest Job First)
            Task nextTask = availableTasks.poll();
            currentTime += nextTask.processingTime;
            result[resultIdx++] = nextTask.id;
        }

        return result;
    }
}
