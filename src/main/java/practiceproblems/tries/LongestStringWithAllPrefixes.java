package practiceproblems.tries;
// tricky
//Given an array of strings, 'A', of size 'N', each element being a string.
// A string is considered complete if every prefix of this string is also present in the array 'A'.
//Find the longest complete string in the array 'A'.
// If there are multiple strings with the same length, return the lexicographically smallest one.
// If no such string exists, return "None".
// Example 1:
//		Input:Input Words: [‘apple’, ‘app’, ‘applet’]
//		Output: Complete String: ‘applet’
public class LongestStringWithAllPrefixes {
    static class Trie {
        // Trie nodes for storing 26 English lowercase letters.
        private Trie[] children = new Trie[26];
        // Flag to mark the end of the word.
        private boolean isEnd;
        char ch;

        // Constructor to initialize the Trie node.
        public Trie( char ch) {
           this.ch = ch;
        }

        // Method to insert a word into the Trie.
        public void insert(String word) {
            Trie node = this;
            for (char c : word.toCharArray()) {
                int index = c - 'a'; // Find the position for the character.
                if (node.children[index] == null) {
                    node.children[index] = new Trie(c); // If not present, create a Trie node.
                }
                node = node.children[index]; // Move to the child node.
            }
            node.isEnd = true; // Mark the end of the word.
        }

        // Method to search for a word in the Trie, ensuring the path of characters exists and each node is an end of a word.
        public boolean search(String word) {
            Trie node = this;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                node = node.children[index]; // Move to the child node.

                // If there's no node or the node is not marked as end of a word,
                // it implies the word or its prefix does not exist.
                if (node == null || !node.isEnd) {
                    return false;
                }
            }
            return true; // If loop completes, the word's path is found with end nodes.
        }
    }

    public String longestWord(String[] words) {
        // Initialize the Trie.
        Trie trie = new Trie('\0');
        // Insert all words into the Trie.
        for (String word : words) {
            trie.insert(word);
        }

        String answer = ""; // To store the longest word with lexicographical order.
        for (String word : words) {
            // Skip this word if:
            // 1. Current answer is longer than this word, or
            // 2. They are of the same length but answer is lexicographically smaller.
            if (!answer.isEmpty()
                    && (answer.length() > word.length()
                    || (answer.length() == word.length() && answer.compareTo(word) < 0))) {
                continue;
            }

            // If the word is found in the Trie following the rule that
            // each prefix is marked as an end of a word.
            if (trie.search(word)) {
                answer = word;
            }
        }
        return answer; // Return the longest word that satisfies the condition.
    }
    public static void main(String[] args) {
        LongestStringWithAllPrefixes longestStringWithAllPrefixes = new LongestStringWithAllPrefixes();
        String[] words = {"apple", "app", "applet"};
        System.out.println(longestStringWithAllPrefixes.longestWord(words)); // Output: ""
        words = new String[]{"a", "ap", "app", "appl", "apple"};
        System.out.println(longestStringWithAllPrefixes.longestWord(words)); // Output: apple
        words = new String[]{"a", "banana", "app", "appl", "ap", "apply", "apple"};
        System.out.println(longestStringWithAllPrefixes.longestWord(words)); // Output: apple

    }
}
