package graph.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Why the "Trimming" Logic Works (Step-by-Step)
 * Let's look at why we delete the leaves:
 * <p>
 * Leaves can never be the answer: A leaf node, by definition, is at the extreme edge.
 * If you root the tree at a leaf, the path to the other side of the tree is the longest possible path.
 * So, we know the answer isn't a leaf.
 * <p>
 * Remove the worst options: Since leaves are the "worst" candidates, we discard them.
 * <p>
 * New Leaves are the "Second Worst": Once you cut off the original leaves,
 * the nodes that were connected to them become the new "edges" of the remaining graph.
 * They are now the worst remaining candidates.
 * <p>
 * Repeat until the middle: We keep discarding the "edges" until we run out of nodes.
 * The last 1 or 2 nodes remaining are the ones that were deepest inside the graph.
 * <p>
 * Why 1 or 2 nodes?
 * Think of a simple line.
 * <p>
 * Line with 5 nodes: O — O — X — O — O. If you trim the ends twice, you are left with 1 center node (X).
 * <p>
 * Line with 4 nodes: O — X — X — O. If you trim the ends once, you are left with 2 center nodes (X — X).
 * You can't trim anymore because they are connected to each other.
 */
public class MinimumHeightTrees {

    //FUNCTION findMinHeightTreesBruteforce(n, edges):
    //    adj = Array of Lists[n]
    //    FOR [u, v] IN edges:
    //        adj[u].add(v), adj[v].add(u)
    //
    //    min_h = INFINITY
    //    res = []
    //
    //    FOR i FROM 0 TO n-1:
    //        h = DFS(i, -1, adj)
    //
    //        IF h < min_h:
    //            min_h = h
    //            res = [i]
    //        ELSE IF h == min_h:
    //            res.add(i)
    //
    //    RETURN res
    //
    //FUNCTION DFS(node, parent, adj):
    //    max_depth = 0
    //    FOR neighbor IN adj[node]:
    //        IF neighbor != parent:
    //            max_depth = MAX(max_depth, DFS(neighbor, node, adj))
    //
    //    RETURN max_depth + 1


    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        if (n <= 0) return res;
        //this is needed...since when there is only 1 vertex...
        // the indegree of it will be 0..this case is not included in the following discussion...
        if (n == 1) {
            res.add(0);
            return res;
        }
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int[] indegree = new int[n];
        int cnt = n;
        Deque<Integer> leaves = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            indegree[i] = graph[i].size();
            if (indegree[i] == 1) {
                leaves.add(i);
            }
        }
        while (cnt > 2) {
            int size = leaves.size();
            cnt -= size;
            for (int i = 0; i < size; i++) {
                int v = leaves.poll();
                for (int w : graph[v]) {
                    indegree[w]--;
                    if (indegree[w] == 1) {
                        leaves.add(w);
                    }
                }
            }
        }
        res.addAll(leaves);
        return res;
    }
}
