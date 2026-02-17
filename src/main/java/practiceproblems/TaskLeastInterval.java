package practiceproblems;

import java.util.*;

/**
 * https://leetcode.com/problems/task-scheduler/
 * <p>
 * tricky priority queue
 */


public class TaskLeastInterval {

    public static int leastInterval(char[] tasks, int n) {
        Map<Character, Integer> cache = new HashMap<>();

        for (char c : tasks) {
            cache.put(c, cache.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Pair> queue = new PriorityQueue<>((a, b) -> Integer.compare(b.freq, a.freq));

        for (char key : cache.keySet()) {
            queue.offer(new Pair(key, cache.get(key)));
        }
        int result = 0;
        // At each iteration, we process at most 'n' elements,
        // and move forwards exactly n+1 in time (regardless of how many elements we processed:
        // Read the topmost from the queue and increment the time. Add it to a temp list to be added later.
        // Add the element back to the queue from the temp list if count is > 0.
        // if al elements are done, we're done too.
        // Move time forward by n + 1
        // Return time in the end.
        while (!queue.isEmpty()) {
            //If n = 2 (wait 2 slots): Slot 1: A (Work) Slot 2: _ (Wait 1) Slot 3: _ (Wait 2) Slot 4: A (Ready again!)
            //Total slots consumed before you can repeat 'A' = 1 (Work) + 2 (Wait) = 3. 3 is n + 1.
            int slots = n + 1;
            List<Pair> tempList = new ArrayList<>();

            while (slots > 0 && !queue.isEmpty()) {
                Pair temp = queue.poll();  // most frequency task
                temp.freq -= 1; // decrease frequency, meaning it got executed
                tempList.add(temp); // collect task to add back to queue
                result++; //successfully executed task
                slots--;
            }

            for (Pair t : tempList) {
                if (t.freq > 0) queue.offer(t); // add valid tasks
            }

            if (queue.isEmpty()) break;

            result += slots; // if k > 0, then it means we need to be idle

        }
        return result;
    }

    public static void main(String[] args) {
        char[] arr = "A".toCharArray();
        System.out.println(leastInterval(arr, 2));
    }

    public int leastIntervalOptimised(char[] tasks, int n) {
        // 1. Frequency Map
        int[] freqs = new int[26];
        int maxFreq = 0;

        for (char c : tasks) {
            freqs[c - 'A']++;
            maxFreq = Math.max(maxFreq, freqs[c - 'A']);
        }

        // 2. Count how many tasks have that maximum frequency
        // (e.g., if A=3 and B=3, maxFreqCount is 2)
        int maxFreqCount = 0;
        for (int f : freqs) {
            if (f == maxFreq) {
                maxFreqCount++;
            }
        }


        // 3. Calculate minimum length based on the "Frame"
        // Formula: (Groups of tasks - 1) * (Group Size) + (Count of max freq tasks)
        // Group Size is (n + 1) because it's the task + n idle slots
        int calculation = (maxFreq - 1) * (n + 1) + maxFreqCount;

        //Imagine tasks = ["A","A","A","B","B","B"] and n = 2. maxFreq = 3 (A), maxFreqCount = 2 (A and B).
        //The Frame (determined by maxFreq - 1): We create (3 - 1) = 2 groups of size (n + 1) = 3. [ ? ? ? ] [ ? ? ? ] ... remaining ...
        //Filling the Frame:
        //Placement: A _ _ A _ _ A B _ (The last row is handled by maxFreqCount)
        //Calculation: Time = (3 - 1) * (2 + 1) + 2 Time = 2 * 3 + 2 = 8
        //Result: A B _ A B _ A B (Total 8 units).
        // 4. Return the maximum of the calculation OR the raw length of tasks
        // (If we have so many tasks that we don't need idle time, the answer is just tasks.length)
        return Math.max(calculation, tasks.length);
    }

    static class Pair {
        char task;
        int freq;

        public Pair(char task, int freq) {
            this.task = task;
            this.freq = freq;
        }
    }
}
