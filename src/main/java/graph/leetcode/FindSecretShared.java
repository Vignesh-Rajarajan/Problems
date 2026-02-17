package graph.leetcode;

import java.util.*;

// https://leetcode.com/problems/find-all-people-with-secret/description/
public class FindSecretShared {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        Set<Integer> secrets = new HashSet<>(), visit;
        secrets.add(0);
        secrets.add(firstPerson);
        Map<Integer, Map<Integer, List<Integer>>> timeMap = new TreeMap<>();

        for (int[] meet : meetings) {
            int src = meet[0], dst = meet[1], t = meet[2];
            timeMap.putIfAbsent(t, new HashMap<>());
            timeMap.get(t).putIfAbsent(src, new ArrayList<>());
            timeMap.get(t).putIfAbsent(dst, new ArrayList<>());
            timeMap.get(t).get(src).add(dst);
            timeMap.get(t).get(dst).add(src);
        }

        for (int t : timeMap.keySet()) {
            //If you don't reset visit, you are essentially saying:
            // "If Person 1 attended a meeting at 5:00 PM, they are banned from doing anything at 10:00 PM."
            visit = new HashSet<>();
            for (int src : timeMap.get(t).keySet()) {
                if (secrets.contains(src)) {
                    dfs(src, timeMap.get(t), secrets, visit);
                }
            }
        }

        return new ArrayList<>(secrets);
    }

    public void dfs(int src, Map<Integer, List<Integer>> adjList, Set<Integer> secrets, Set<Integer> visit) {
        if (!visit.add(src)) return; // duplicate addition returns false

        secrets.add(src);
        for (int nei : adjList.getOrDefault(src, new ArrayList<>())) {
            dfs(nei, adjList, secrets, visit);
        }

    }
}
