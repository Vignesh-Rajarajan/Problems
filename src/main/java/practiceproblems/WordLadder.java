package practiceproblems;

import java.util.*;

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
        if(beginWord.equals(endWord)) return result;

        List<String> currentList = new ArrayList<>();
        Set<String> set = new HashSet<>(wordList);

        backTrackingHelper(beginWord,endWord,set,currentList,result);
        return result;

    }

    public void backTrackingHelper(String currWord, String endWord, Set<String>set,
                                   List<String> currList, List<List<String>> result){
        if(currWord.equals(endWord)){
            result.add(new ArrayList<>(currList));
            return;
        }
        currList.add(currWord);
        Queue<String> queue = new LinkedList<>();
        queue.add(currWord);
        while (!queue.isEmpty()) {
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
                        queue.add(newWord);// else add to queue and continue
                        set.remove(newWord);// because you already reached this word, no need to see again
                    }
                }
                charArray[j] = temp;
            }
        }
        currList.remove(currList.size()-1);
        return;
    }
}