package graph.leetcode;

import java.util.*;

/**
 * tricky union find
 * <p>
 * https://youtu.be/FMwpt_aQOGw
 * https://leetcode.com/problems/accounts-merge/
 * <p>
 */
public class AccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        UnionFind uf = new UnionFind(accounts.size());
        Map<String, Integer> emailsToIdMapper = new TreeMap<>();

        for (int i = 0; i < accounts.size(); i++) {
            List<String> emails = accounts.get(i);
            // we are starting j=1 because j=0 will contain the name not the email
            for (int j = 1; j < emails.size(); j++) {
                String email = emails.get(j);
                // we have already seen the email, so we need to merge the last
                // seen value with i
                if (emailsToIdMapper.containsKey(email)) {
                    uf.union(emailsToIdMapper.get(email), i);
                } else {
                    emailsToIdMapper.put(email, i);
                }
            }
        }

        Map<Integer, List<String>> result = new HashMap<>();

        for (String email : emailsToIdMapper.keySet()) {
            int id = emailsToIdMapper.get(email);
            // Find the root parent for the set this email belongs to. This parentId
            // represents the merged account.
            int parentId = uf.find(id);

            // If we haven't seen this parentId before, it means this is the first email
            // for this merged account. We need to initialize its list and add the name.
            // The name is retrieved from the account list corresponding to the parentId.
            if (!result.containsKey(parentId)) {
                result.computeIfAbsent(parentId, x -> new ArrayList<>()).add(accounts.get(parentId).get(0));
            }
            // Add the current email to the list of the merged account.
            // Since emailsToIdMapper is a TreeMap, emails are iterated in sorted order,
            // so they will be added to the result list in sorted order.
            result.get(parentId).add(email);
        }

        return new ArrayList<>(result.values());
    }

    static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int n) {
            this.parent = new int[n];
            this.rank = new int[n];

            for (int i = 0; i < n; i++) {
                this.parent[i] = i;
            }
        }

        public boolean union(int x, int y) {

            int parentX = find(x);
            int parentY = find(y);

            if (parentX == parentY) return false;

            if (rank[parentX] > rank[parentY]) {
                parent[parentY] = parentX;
            } else if (rank[parentY] > rank[parentX]) {
                parent[parentX] = parentY;
            } else {
                parent[parentY] = parentX;
                rank[parentX]++;
            }

            return true;
        }

        public int find(int x) {
            if (parent[x] == x) return x;

            parent[x] = find(parent[x]);
            return parent[x];
        }

        public boolean isSameComponent(int p, int q) {
            return find(p) == find(q);
        }
    }
}