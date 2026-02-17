package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/parallel-courses
public class MinimumSemester {
    public int calculateMinimumSemesters(int totalCourses, int[][] prerequisites) {
        // Build adjacency list and in-degree array
        List<Integer>[] courseGraph = new List[totalCourses];
        Arrays.setAll(courseGraph, k -> new ArrayList<>());
        int[] inDegree = new int[totalCourses];

        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0] - 1;
            int dependentCourse = prerequisite[1] - 1;
            courseGraph[course].add(dependentCourse);
            inDegree[dependentCourse]++;
        }

        // Initialize queue with courses having no prerequisites
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < totalCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int semesters = 0;
        while (!queue.isEmpty()) {
            semesters++;
            int coursesInCurrentSemester = queue.size();
            for (int i = 0; i < coursesInCurrentSemester; i++) {
                int currentCourse = queue.poll();
                totalCourses--;

                for (int nextCourse : courseGraph[currentCourse]) {
                    if (--inDegree[nextCourse] == 0) {
                        queue.offer(nextCourse);
                    }
                }
            }
        }

        // If there are still courses left, return -1 (cycle detected)
        return totalCourses == 0 ? semesters : -1;
    }

    public int calculateMinimumTime(int totalCourses, int[][] prerequisites, int[] courseTimes) {
        // Build adjacency list and in-degree array
        List<Integer>[] courseGraph = new List[totalCourses];
        Arrays.setAll(courseGraph, k -> new ArrayList<>());
        int[] inDegree = new int[totalCourses];

        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0] - 1;
            int dependentCourse = prerequisite[1] - 1;
            courseGraph[course].add(dependentCourse);
            inDegree[dependentCourse]++;
        }

        // Initialize queue with courses having no prerequisites
        Deque<Integer> queue = new ArrayDeque<>();
        int[] earliestCompletionTime = new int[totalCourses];
        int totalTime = 0;

        for (int i = 0; i < totalCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                earliestCompletionTime[i] = courseTimes[i];
                totalTime = Math.max(totalTime, courseTimes[i]);
            }
        }

        while (!queue.isEmpty()) {
            int currentCourse = queue.poll();

            for (int nextCourse : courseGraph[currentCourse]) {
                earliestCompletionTime[nextCourse] = Math.max(
                        earliestCompletionTime[nextCourse],
                        earliestCompletionTime[currentCourse] + courseTimes[nextCourse]
                );
                totalTime = Math.max(totalTime, earliestCompletionTime[nextCourse]);

                if (--inDegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        return totalTime;
    }

}
