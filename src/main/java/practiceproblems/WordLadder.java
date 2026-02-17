package practiceproblems;

import java.util.*;

//https://leetcode.com/problems/word-ladder/
public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> set = new HashSet<>(wordList);
        if (!set.contains(endWord)) return 0; // end word itself not in set
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        int level = 1;

        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i++) { // going level by level
                String currentWord = queue.poll();
                char[] charArray = currentWord.toCharArray();

                for (int j = 0; j < charArray.length; j++) {
                    char temp = charArray[j]; // storing the value to reset back
                    for (char ch = 'a'; ch <= 'z'; ch++) { // try all letters in alphabets
                        if (temp == ch) {
                            continue;
                        }
                        charArray[j] = ch;
                        String newWord = String.valueOf(charArray);
                        if (set.contains(newWord)) {
                            if (newWord.equals(endWord)) { // if found return level
                                return level + 1;
                            }
                            queue.add(newWord);// else add to queue and continue
                            set.remove(newWord);// because you already reached this word, no need to see again
                        }
                    }
                    charArray[j] = temp;
                }
            }
            level++;
        }

        return 0;
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) {
            return result;
        }

        // BFS initialization
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);

        // Remove start word to avoid going back to it
        wordSet.remove(beginWord);

        // Map to store the graph: Child -> List of Parents (Reverse Graph)
        // This helps us traverse backward from endWord to beginWord later.
        Map<String, List<String>> parentMap = new HashMap<>();

        boolean foundEnd = false;

        // BFS: Level by Level
        while (!queue.isEmpty() && !foundEnd) {
            int levelSize = queue.size();
            Set<String> visitedThisLevel = new HashSet<>();

            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();
                char[] chars = currentWord.toCharArray();

                for (int j = 0; j < chars.length; j++) {
                    char originalChar = chars[j];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) continue;

                        chars[j] = c;
                        String newWord = new String(chars);

                        // If the word exists in the dictionary
                        if (wordSet.contains(newWord)) {
                            // Link the newWord (child) to currentWord (parent)
                            parentMap.computeIfAbsent(newWord, k -> new ArrayList<>()).add(currentWord);

                            // Only add to queue if we haven't visited it IN THIS LEVEL yet.
                            // This ensures we process the word, but don't duplicate it in the queue.
                            if (!visitedThisLevel.contains(newWord)) {
                                queue.offer(newWord);
                                visitedThisLevel.add(newWord);
                            }

                            if (newWord.equals(endWord)) {
                                foundEnd = true;
                            }
                        }
                    }
                    chars[j] = originalChar; // Reset
                }
            }
            // Remove all words visited in this level from the global set
            // so we don't visit them again in future levels.
            wordSet.removeAll(visitedThisLevel);
        }

        // DFS (Backtracking) to reconstruct paths
        if (foundEnd) {
            List<String> path = new ArrayList<>();
            path.add(endWord);
            backtrack(endWord, beginWord, parentMap, path, result);
        }

        return result;
    }

    private void backtrack(String currentWord, String beginWord, Map<String, List<String>> parentMap,
                           List<String> path, List<List<String>> result) {

        // Base Case: We reached the start word
        if (currentWord.equals(beginWord)) {
            List<String> temp = new ArrayList<>(path);
            Collections.reverse(temp); // Reverse because we built it backwards
            result.add(temp);
            return;
        }

        // If no parents exist (shouldn't happen in valid path logic, but safe to check)
        if (!parentMap.containsKey(currentWord)) {
            return;
        }

        // Recursive Step: Explore all parents
        for (String parent : parentMap.get(currentWord)) {
            path.add(parent);
            backtrack(parent, beginWord, parentMap, path, result);
            path.remove(path.size() - 1); // Backtrack
        }
    }
}

/*
    Complexity Analysis:

    N = Number of words in wordList
    L = Length of each word
    P = Total number of shortest paths from beginWord to endWord
    K = Number of words (steps) in the shortest path

    Time Complexity: O(N * L^2 + P * K)
       - BFS Part: O(N * L^2). We process each word, and for each char (L),
         we try 26 replacements. String operations take O(L).
       - DFS Part: O(P * K). We reconstruct every valid path.

    Space Complexity: O(N * L + P * K)
       - O(N * L) to store the wordSet and parentMap.
       - O(P * K) to store the result list of paths.
*/