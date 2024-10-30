package graph.leetcode;

import java.util.*;

// https://leetcode.com/problems/course-schedule
class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //inDegree:
        //Keeps track of how many prerequisites each course has.
        //Courses with inDegree of 0 are ready to be taken (processed).
        //topoMap:
        //Shows which courses become available after completing a particular course.
        List<List<Integer>> adjList = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] pre : prerequisites) {
            //the reason we add pre[0] to pre[1] is because
            // for prerequisites = [[1,0], [2,1], [3,1]]
            // this provides a map
            //      0 -> [1]
            //      1 -> [2, 3]
            //      2 -> []
            //      3 -> []
            // so when we finish 1, we can take 2 and 3
            // the path to arrive at 1 is from 0 since 0 has no inDegree
            //we typically want to know which courses we can take next
            // (i.e., which courses have prerequisites that we've already completed).
            adjList.get(pre[1]).add(pre[0]);
            inDegree[pre[0]]++;
        }

        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int temp = queue.poll();
            if (inDegree[temp] == 0) {
                count++; // if cond for duplicates
            }
            for (int i : adjList.get(temp)) {
                if (--inDegree[i] == 0) {
                    queue.offer(i);
                }
            }
        }

        return count == numCourses;
    }

    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        int[] indegree = new int[numCourses];
        Map<Integer, Set<Integer>> adj = new HashMap<>();
        Map<Integer, Set<Integer>> prerequisitesMap = new HashMap<>();

        for (int i = 0; i < numCourses; i++) {
            prerequisitesMap.put(i, new HashSet<>());
            adj.put(i, new HashSet<>());
        }

        for (int[] pre : prerequisites) {
            int dst = pre[0];
            int src = pre[1];

            indegree[dst]++;
            adj.get(src).add(dst);
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {
            int node = q.poll();
            Set<Integer> adjset = adj.get(node);
            for (int depcrs : adjset) {
                prerequisitesMap.get(depcrs).add(node);
                prerequisitesMap.get(depcrs).addAll(prerequisitesMap.get(node));
                indegree[depcrs]--;
                if (indegree[depcrs] == 0) {
                    q.offer(depcrs);
                }
            }
        }

        List<Boolean> rslt = new ArrayList<>();
        for (int[] qry : queries) {
            Set<Integer> pset = prerequisitesMap.get(qry[0]);
            if (pset.contains(qry[1])) {
                rslt.add(true);
            } else {
                rslt.add(false);
            }
        }
        return rslt;
    }

    // this follows the same approach as bipartite graph
    //This code: Uses three states (0: unvisited, 1: visiting, 2: visited) to track the DFS progress.
    public boolean canFinishDfs(int numCourses, int[][] prerequisites) {

        List<List<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] pre : prerequisites) {
            int curCourse = pre[0];
            int dependent = pre[1];

            adjList.get(dependent).add(curCourse);
        }

        int[] color = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            if (!dfs(adjList, i, color)) {
                return false;
            }
        }

        return true;
    }

    public boolean dfs(List<List<Integer>> adjList, int node, int[] color) {
        if (color[node] == 1) {
            return false;
        }

        // Return true if the node is completed processing
        if (color[node] == 2) {
            return true;
        }
        color[node] = 1;

        for (int neighbour : adjList.get(node)) {
            if (color[neighbour] != 2 && !dfs(adjList, neighbour, color)) {
                return false;
            }
        }
        // It marks the node as fully processed,
        // indicating that all its neighbors have been explored and no cycles were found starting from this node.
        color[node] = 2;
        return true;
    }


    // this is to get the order of course as output
    public boolean dfs(List<Integer>[] adjList, int[] visited, List<Integer> result, int node) {
        if (visited[node] == 1) return false;
        if (visited[node] == 2) return true;

        visited[node] = 1;
        for (int adj : adjList[node]) {
            if (!dfs(adjList, visited, result, adj)) {
                return false;
            }
        }
        visited[node] = 2;
        result.add(node); // this will keep track of which to finish first and last
        return true;
    }
}