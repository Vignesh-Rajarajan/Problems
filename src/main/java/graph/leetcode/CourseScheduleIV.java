package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/course-schedule-iv/
public class CourseScheduleIV {

    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] pre : prerequisites) {
            int dependent = pre[1];
            int course = pre[0];
            adjList.get(dependent).add(course);
        }

        Map<Integer, Set<Integer>> prereqMap = new HashMap<>();

        for (int i = 0; i < numCourses; i++) {
            dfs(adjList, i, prereqMap);
        }


        List<Boolean> result = new ArrayList<>();
        for (int[] q : queries) {
            result.add(prereqMap.get(q[1]).contains(q[0]));
        }

        return result;

    }

    //Imagine the following course structure:
    // •Course 3 requires Course 1  Course 2.
    // •Course 1 requires Course 0.
    // •Course 2 requires Course 0.
    //dfs(3) called, prereqMap is empty

    //dfs(3):
    //  neighbors: 1, 2
    //  call dfs(1):
    //    prereqMap is empty for 1
    //    neighbor: 0
    //    call dfs(0):
    //      prereqMap is empty for 0
    //      no neighbors
    //      add 0 to prereqs: {0}
    //      cache: prereqMap.put(0, {0})
    //      return {0}
    //    add 1 to prereqs: {0, 1}
    //    cache: prereqMap.put(1, {0, 1})
    //    return {0, 1}
    //  add {0, 1} to prereqs: {0, 1}
    //  call dfs(2):
    //    prereqMap is empty for 2
    //    neighbor: 0
    //    call dfs(0):
    //      prereqMap.containsKey(0) is true
    //      return cached {0}
    //    add 2 to prereqs: {0, 2}
    //    cache: prereqMap.put(2, {0, 2})
    //    return {0, 2}
    //  add {0, 2} to prereqs: {0, 1, 2}
    //  add 3 to prereqs: {0, 1, 2, 3}
    //  cache: prereqMap.put(3, {0, 1, 2, 3})
    //  return {0, 1, 2, 3}

    public Set<Integer> dfs(List<List<Integer>> adjList, int course, Map<Integer, Set<Integer>> prereqMap) {
        if (prereqMap.containsKey(course)) {
            return prereqMap.get(course);
        }

        Set<Integer> prereqs = new HashSet<>();
        for (int neighbour : adjList.get(course)) {
            prereqs.addAll(dfs(adjList, neighbour, prereqMap));
        }

        prereqs.add(course);
        prereqMap.put(course, prereqs);
        return prereqs;

    }
}
