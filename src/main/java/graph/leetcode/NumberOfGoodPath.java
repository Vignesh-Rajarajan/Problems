package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/number-of-good-paths/
//https://youtu.be/rv2GBYQm7xM
public class NumberOfGoodPath {
    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        int n = vals.length;
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        //"Good Path" says: All nodes between the start and end must have values less than or equal to the start/end.
        //When we are processing nodes with value 10, we have already processed all nodes with values 1, 5, 8, etc.
        //Nodes with values 20 or 50 have not been processed yet. They are effectively invisible walls.
        //This is perfect! If a 20 is between two 10s, we shouldn't be able to cross it. By processing 10 before 20, the DSU naturally prevents us from crossing the 20 because the 20 isn't in the DSU yet.
        // These smaller nodes are already "connected" in our DSU structure. They act as valid bridges.
        TreeMap<Integer, List<Integer>> valToIndex = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            valToIndex.computeIfAbsent(vals[i], x -> new ArrayList<>()).add(i);
        }

        MinCostConnectPoints.DSU dsu = new MinCostConnectPoints.DSU(n);
        int res = 0; //res is calculating the total number of good paths (single nodes + pairs)

        for (int val : valToIndex.keySet()) {
            for (int i : valToIndex.get(val)) {
                for (int nei : adj[i]) {
                    //Here, i is the current node we are processing (e.g., value 10).
                    //Case A (vals[nei] < vals[i]): The neighbor is smaller (e.g., 5).
                    // Because of our sorting, the 5 is already active and processed. We connect to it.
                    // This means we can walk from our 10 onto the 5.
                    //Case B (vals[nei] == vals[i]): The neighbor is equal (another 10).
                    // We connect to it. We can walk between equal values.
                    if (vals[nei] <= vals[i]) dsu.union(nei, i);
                }
            }


            Map<Integer, Integer> count = new HashMap<>();
            for (int i : valToIndex.get(val)) {
                int root = dsu.find(i);
                //Process Node A: It's the first 3 in this component.count becomes 1.res += 1.
                //Meaning: We found the path A -> A (a single node is a valid path).
                //Process Node B (in same component):It's the second 3 count becomes 2
                //res += 2. Meaning: We add 2 new paths:B -> B (itself).B -> A (connects to the previous one).
                //Current Total: 1 + 2 = 3.(Paths: A, B, A-B)
                //Process Node C (in same component):It's the third 3.count becomes 3.res += 3.
                //Meaning: We add 3 new paths:C -> C (itself).C -> A (connects to first).C -> B (connects to second).
                //Current Total: $1 + 2 + 3 = 6$. (Paths: A, B, C, A-B, A-C, B-C)
                count.put(root, count.getOrDefault(root, 0) + 1);
                res += count.get(root);
            }
        }
        return res;
    }
}
