package graph.leetcode.shortestPath;

import java.util.*;

// https://leetcode.com/problems/shortest-path-with-alternating-colors/
// tricky BFS
public class ShortestPathAlternativeColours {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<Integer>[][] adj = new ArrayList[2][n];
        adj[0] = buildAdjList(n, redEdges);
        adj[1] = buildAdjList(n, blueEdges);
        Deque<int[]> queue = new ArrayDeque<>();
        int[][] dist = new int[n][2];
        for (int[] m : dist) {
            Arrays.fill(m, Integer.MAX_VALUE);
        }

        dist[0][0] = 0;
        dist[0][1] = 0;
        queue.offer(new int[]{0, 0});
        queue.offer(new int[]{0, 1});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int node = cur[0], color = cur[1];
            //Let's trace your loop for (int nei : adj[color][node]):
            //The Queue State: You initialize the queue with {0, 0} and {0, 1}.
            //{0, 0} in your code means: "I am at Node 0, and I am ready to take a RED (0) edge next."
            //The Loop (adj[color]): When you pull {node: 0, color: 0}, your code executes: for (int nei : adj[0][node])
            //This iterates through RED neighbors.
            //Crucial Point: You are taking the Red edge now.
            //The Update (dist[nei][color ^ 1]): Since you just traveled over a RED edge to get to nei,
            //the requirement for the next step from nei is that it must be BLUE.
            //color was 0 (Red). color ^ 1 becomes 1 (Blue).
            //You update dist and Queue with 1.
            //The Next Iteration: Later, when you pop that {nei, 1} from the queue:
            //color is 1.
            //The loop runs adj[1][nei].
            //You are now looking at BLUE neighbors.
            for (int nei : adj[color][node]) {
                if (dist[nei][color ^ 1] > dist[node][color] + 1) {
                    dist[nei][color ^ 1] = dist[node][color] + 1;
                    queue.offer(new int[]{nei, color ^ 1});
                }
            }

        }

        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            answer[i] = Math.min(dist[i][0], dist[i][1]);
            if (answer[i] == Integer.MAX_VALUE)
                answer[i] = -1;
        }
        return answer;

    }

    public List<Integer>[] buildAdjList(int n, int[][] edges) {
        List<Integer>[] adjList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            adjList[e[0]].add(e[1]);
        }

        return adjList;
    }

    //https://youtu.be/69rcy6lb-HQ
    public int[] shortestAlternatingPathsAlternative(int n, int[][] redEdges, int[][] blueEdges) {
        List<Integer>[] red = new ArrayList[n], blue = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            red[i] = new ArrayList<>();
            blue[i] = new ArrayList<>();
        }
        for (int[] edge : redEdges) red[edge[0]].add(edge[1]);
        for (int[] edge : blueEdges) blue[edge[0]].add(edge[1]);

        int[] answer = new int[n];
        Arrays.fill(answer, -1);
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0, -1});
        Set<String> visit = new HashSet<>();
        visit.add("0,-1");

        while (!q.isEmpty()) {
            int[] nodeData = q.poll();
            int node = nodeData[0], length = nodeData[1], edgeColor = nodeData[2];
            if (answer[node] == -1) answer[node] = length;
            if (edgeColor != 0) { // the reason of checking != is that the start node initialised with {0, 0, -1},
                                 // so we need that time to come inside it
                for (int nei : red[node]) {
                    if (visit.add(nei + ",0")) {
                        q.offer(new int[]{nei, length + 1, 0});
                    }
                }
            }
            if (edgeColor != 1) {// the reason of checking != is that the start node initialised with {0, 0, -1},
                                // so we need that time to come inside it
                for (int nei : blue[node]) {
                    if (visit.add(nei + ",1")) {
                        q.offer(new int[]{nei, length + 1, 1});
                    }
                }
            }
        }
        return answer;
    }
}
