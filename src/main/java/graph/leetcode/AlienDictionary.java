package graph.leetcode;

import java.util.*;

/**
 * tricky topological sort
 * <p>
 * https://leetcode.com/problems/alien-dictionary - premium
 * https://takeuforward.org/data-structure/alien-dictionary-topological-sort-g-26/
 * <p>
 **/

public class AlienDictionary {
    public String alienOrder(String[] words) {
        // Step 0: Create data structures and find all unique letters.
        Map<Character, Set<Character>> adjList = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                inDegree.put(c, 0);
                adjList.put(c, new HashSet<>());
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

            /**
             * Imagine you are comparing two words from the alien dictionary: word1 = "team" word2 = "fun"
             * The first letters are different: 't' and 'f'.
             * Because "team" appears before "fun" in the input list,
             * we know 't' must come before 'f' .
             * So in the alien alphabet.adjList.get('t').add('f');
             * Translation: "Hey 't', add 'f' to your list of neighbors.
             *
             * "Meaning: This draws a directed arrow from t -> f.
             * It establishes that once we process 't', 'f' is the next possibility.inDegree.put('f', ... + 1)
             * Translation: "Hey 'f', increase your dependency count by 1."Meaning: 'f' is not allowed to be processed yet.
             * It is "locked" because it is waiting for 't' to finish.
             * If 'f' had an in-degree of 0, it would be free. Now it has an in-degree of 1.
             */

            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    char source = word1.charAt(j);
                    char target = word2.charAt(j);

                    // adjList.get(source).add(target) returns true only if 'target'
                    // was not already in the set.
                    //Unique Edges: a -> b exists only once in the graph.
                    //Correct Math: The in-degree for b is incremented exactly once per unique prerequisite.
                    if (adjList.get(source).add(target)) {
                        inDegree.put(target, inDegree.get(target) + 1);
                    }
                    break; // Stop after the first difference found
                }
            }
        }

        // Step 2: Breadth-first search.
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (Character c : inDegree.keySet()) {
            if (inDegree.get(c) == 0) {
                queue.add(c);
            }
        }
        while (!queue.isEmpty()) {
            Character c = queue.remove();
            sb.append(c);
            for (Character next : adjList.get(c)) {
                inDegree.put(next, inDegree.get(next) - 1);
                if (inDegree.get(next) == 0) {
                    queue.add(next);
                }
            }
        }

        if (sb.length() != inDegree.size()) {
            return "";
        }
        return sb.toString();
    }
}
