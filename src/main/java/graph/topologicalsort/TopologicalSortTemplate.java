package graph.topologicalsort;

import java.util.*;

public class TopologicalSortTemplate {
    public List<Integer> topologicalSort(int n, int[][] adjMat) {

        Map<Integer, Integer> inDegree = new HashMap<>();
        List<List<Integer>> topoMap = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            inDegree.put(i, 0);
            topoMap.add(new ArrayList<>());
        }

        for (int[] edge : adjMat) {
            // An edge from node A to node B means B depends on A
            int curr = edge[0];
            int prev = edge[1];
            inDegree.put(curr, inDegree.get(curr) + 1);
            topoMap.get(prev).add(curr);
        }
        int[] res = new int[n];
        int base = 0;
        Queue<Integer> zeroInDegree = new LinkedList<>();
        for (int key : inDegree.keySet()) {
            if (inDegree.get(key) == 0) {
                zeroInDegree.add(key);
            }
        }

        while (!zeroInDegree.isEmpty()) {
            int key = zeroInDegree.poll();
            res[base++] = key;
            List<Integer> children = topoMap.get(key);
            for (int child : children) {
                inDegree.put(child, inDegree.get(child) - 1);
                if (inDegree.get(child) == 0) {
                    zeroInDegree.add(child);
                }
            }
            inDegree.remove(key);
        }

        if (base != n) {
            return new ArrayList<>();
        }
        return Arrays.stream(res).collect(ArrayList::new, List::add, List::addAll);
    }

    public List<Integer> topologicalSortDFS(int n, int[][] adjMat) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : adjMat) {
            adj.get(edge[1]).add(edge[0]);
        }
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, adj, visited, stack);
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }
        return res;
    }

    public void dfs(int node, List<List<Integer>> adjList, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;
        for (int neighbor : adjList.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adjList, visited, stack);
            }
        }
        stack.push(node);
    }

}
