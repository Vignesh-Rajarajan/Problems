package graph.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/find-eventual-safe-states
 * https://takeuforward.org/data-structure/find-eventual-safe-states-bfs-topological-sort-g-25/
 *
 * This solution works by detecting cycles.
 * Nodes that aren't part of any cycle are safe, either because they are terminal themselves or
 * they lead to terminal nodes eventually.
 *
 * Without cycles, any node can eventually reach a terminal state, since it will never loop back to itself.
 */
public class FindAllSafeStates {

    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> safeNodes = new ArrayList<>(graph.length);
        State[] states = new State[graph.length];
        for (int node = 0; node < graph.length; node++) {
            if (isSafe(graph, node, states)) {
                safeNodes.add(node);
            }
        }
        return safeNodes;
    }

    private boolean isSafe(int[][] graph, int node, State[] states) {
        if (states[node] != null) {
            return states[node] == State.VISITED;
        }

        states[node] = State.VISITING;

        for (int next : graph[node]) {
            if (!isSafe(graph, next, states)) return false;
        }

        states[node] = State.VISITED;
        return true;
    }

    enum State {
        VISITED,
        VISITING
    }
}
