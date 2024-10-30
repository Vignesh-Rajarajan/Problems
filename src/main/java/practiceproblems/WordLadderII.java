package practiceproblems;

import java.util.*;

//https://leetcode.com/problems/word-ladder-ii
// tricky hard bfs+dfs
public class WordLadderII {
    private List<List<String>> results = new ArrayList<>();
    private Map<String, List<String>> adjacencyMap = new HashMap<>();

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return results;
        // BFS to build adjacency map
        bfs(beginWord, endWord, wordSet);
        // DFS to find all paths
        dfs(endWord, beginWord, new ArrayList<>());
        return results;
    }

    private void bfs(String beginWord, String endWord, Set<String> wordSet) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        boolean found = false;
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        while (!queue.isEmpty() && !found) {
            int size = queue.size();
            // check why this is needed
            Set<String> levelVisited = new HashSet<>();

            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                for (String nextWord : getNextWords(currentWord, wordSet)) {
                    if (visited.contains(nextWord)) continue;
                    if (!levelVisited.contains(nextWord)) {
                        queue.offer(nextWord);
                        levelVisited.add(nextWord);
                    }
                    //Start with "hit":
                    //We find that "hot" is one letter away from "hit".
                    //We add to the map: "hot" -> ["hit"]
                    // Next level, we process "hot":
                    //
                    //We find "dot" and "lot" are one letter away from "hot".
                    //We add to the map:
                    //"dot" -> ["hot"]
                    //"lot" -> ["hot"]
                    //Next level, we process "dot" and "lot":
                    //
                    //From "dot", we find "dog".
                    //From "lot", we find "log".
                    //We add to the map:
                    //"dog" -> ["dot"]
                    //"log" -> ["lot"]
                    //Final level, we process "dog" and "log":
                    //
                    //From both "dog" and "log", we find "cog".
                    //We add to the map:
                    //"cog" -> ["dog", "log"]
                    // {
                    //  "hot": ["hit"],
                    //  "dot": ["hot"],
                    //  "lot": ["hot"],
                    //  "dog": ["dot"],
                    //  "log": ["lot"],
                    //  "cog": ["dog", "log"]
                    //}
                    adjacencyMap.computeIfAbsent(nextWord, k -> new ArrayList<>()).add(currentWord);
                    if (nextWord.equals(endWord)) found = true;
                }
            }
            visited.addAll(levelVisited);
        }
    }

    private List<String> getNextWords(String word, Set<String> wordSet) {
        List<String> nextWords = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char original = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == original) continue;
                chars[i] = c;
                String newWord = new String(chars);
                if (wordSet.contains(newWord)) {
                    nextWords.add(newWord);
                }
            }
            chars[i] = original;
        }
        return nextWords;
    }

    //Start with endWord "cog":
    //"cog" has two entries in the adjacency list: ["dog", "log"]
    //We'll explore each of these paths
    //Path 1: cog -> dog
    //We move to "dog"
    //"dog" has one entry: ["dot"]
    //We move to "dot"
    //"dot" has one entry: ["hot"]
    //We move to "hot"
    //"hot" has one entry: ["hit"]
    //We reach "hit", which is our start word
    //We've found a valid path: hit -> hot -> dot -> dog -> cog
    //The DFS will backtrack to explore other possibilities:
    //Path 2: cog -> log
    //We move to "log"
    //"log" has one entry: ["lot"]
    //We move to "lot"
    //"lot" has one entry: ["hot"]
    //We move to "hot"
    //"hot" has one entry: ["hit"]
    //We reach "hit", which is our start word
    //We've found another valid path: hit -> hot -> lot -> log -> cog
    private void dfs(String currentWord, String beginWord, List<String> path) {
        path.add(currentWord);
        if (currentWord.equals(beginWord)) {
            Collections.reverse(path);
            results.add(new ArrayList<>(path));
            Collections.reverse(path);
        } else {
            for (String nextWord : adjacencyMap.getOrDefault(currentWord, new ArrayList<>())) {
                dfs(nextWord, beginWord, path);
            }
        }
        path.remove(path.size() - 1);
    }
}
