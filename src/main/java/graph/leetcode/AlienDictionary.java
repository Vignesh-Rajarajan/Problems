package graph.leetcode;

import java.util.*;

/**
 * tricky topological sort
 * <p>
 * https://leetcode.com/problems/alien-dictionary - premium
 * https://takeuforward.org/data-structure/alien-dictionary-topological-sort-g-26/
 * <p>
 * Really nice solution! Let me try to explain the code with example in the problem description:
 * <p>
 * First, build a degree map for each character in all the words:
 * <p>
 * w:0
 * r:0
 * t:0
 * f:0
 * e:0
 * <p>
 * Then build the hashmap by comparing the adjacent words, the first character that is different between two adjacent words reflect the lexicographical order. For example:
 * <p>
 * "wrt",
 * "wrf",
 * first different character is 3rd letter, so t comes before f
 * <p>
 * "wrf",
 * "er",
 * first different character is 1rd letter, so w comes before e
 * <p>
 * The characters in set come after the key. x->y means letter x comes before letter y. x -> set: y,z,t,w means x comes before all the letters in the set. The final HashMap "map" looks like.
 * <p>
 * t -> set: f
 * w -> set: e
 * r -> set: t
 * e -> set: r
 * <p>
 * and final HashMap "degree" looks like, the number means "how many letters come before the key":
 * <p>
 * w:0
 * r:1
 * t:1
 * f:1
 * e:1
 * <p>
 * Then use Kahn's algorithm to do topological sort. This is essentially BFS.
 */

public class AlienDictionary {
    public String alienOrder(String[] words) {
        // Step 0: Create data structures and find all unique letters.
        Map<Character, List<Character>> adjList = new HashMap<>();
        Map<Character, Integer> counts = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                counts.put(c, 0);
                adjList.put(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            // One edge case  is when the second word is the prefix of the first word, for example: ["abc", "ab"]
            // Because the prefix should always be at the front.
            // check for cases like, ["wrtkj","wrt"]; it's invalid, because this input is not in sorted lexicographical order
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // let's say word1 = "baa" and word2 = "abcd", when the first different character is found,
            // we add the edge 'b' -> 'a' to the graph which means b comes before a.
            // and we also increment the in-degree of 'a' by 1 because 'a' has one edge coming to it.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    adjList.get(word1.charAt(j)).add(word2.charAt(j));
                    counts.put(word2.charAt(j), counts.get(word2.charAt(j)) + 1);
                    break;
                }
            }
        }

        // Step 2: Breadth-first search.
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (Character c : counts.keySet()) {
            if (counts.get(c) == 0) {
                queue.add(c);
            }
        }
        while (!queue.isEmpty()) {
            Character c = queue.remove();
            sb.append(c);
            for (Character next : adjList.get(c)) {
                counts.put(next, counts.get(next) - 1);
                if (counts.get(next) == 0) {
                    queue.add(next);
                }
            }
        }

        if (sb.length() < counts.size()) {
            return "";
        }
        return sb.toString();
    }
}
