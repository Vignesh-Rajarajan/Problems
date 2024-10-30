package graph.leetcode.shortestPath;

import java.util.*;

//https://leetcode.com/problems/network-delay-time/
public class NetworkDelayTime {

    //The visited array is used to ensure we don't process a node more than once,
    // as once we've found the shortest path to a node, we don't need to consider it again.
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int[] time : times) {
            adj.computeIfAbsent(time[0], x -> new ArrayList<>()).add(new int[]{time[1], time[2]});
        }

        int[] signalReceivedAt = new int[N + 1];
        Arrays.fill(signalReceivedAt, Integer.MAX_VALUE);
        //distance, node into pq
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        pq.add(new int[]{0, K});

        signalReceivedAt[K] = 0;
        Set<Integer> visited = new HashSet<>();
        while (!pq.isEmpty()) {
            int[] cur = pq.remove();

            int currNode = cur[1];
            int currNodeTime = cur[0];

            if (visited.contains(currNode)) {
                continue;
            }
            if (currNodeTime > signalReceivedAt[currNode]) {
                continue;
            }
            visited.add(currNode);
            for (int[] next : adj.getOrDefault(currNode, new ArrayList<>())) {
                int neighborNode = next[0];
                int time = next[1];
                if (signalReceivedAt[neighborNode] > currNodeTime + time) {
                    signalReceivedAt[neighborNode] = currNodeTime + time;
                    pq.add(new int[]{signalReceivedAt[neighborNode], neighborNode});
                }
            }
        }
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            answer = Math.max(answer, signalReceivedAt[i]);
        }

        // INT_MAX signifies atleat one node is unreachable
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    public int networkDelayTime_BF(int[][] times, int N, int K) {
        double[] disTo = new double[N + 1];
        Arrays.fill(disTo, Double.POSITIVE_INFINITY);
        disTo[K - 1] = 0;
        for (int i = 1; i < N; i++) {
            for (int[] edge : times) {
                int u = edge[0] - 1, v = edge[1] - 1, w = edge[2];
                disTo[v] = Math.min(disTo[v], disTo[u] + w);
            }
        }
        double res = Double.MIN_VALUE;
        for (double i : disTo) {
            res = Math.max(i, res);
        }
        return res == Double.POSITIVE_INFINITY ? -1 : (int) res;
    }

}

