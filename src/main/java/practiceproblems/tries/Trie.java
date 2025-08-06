package practiceproblems.tries;

class Trie {
    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode(' '); // Root with empty character
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode(ch);
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    /**
     * Returns true if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Returns true if there is any word in the trie that starts with the prefix.
     */
    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    /**
     * Helper method to find the node corresponding to a string.
     */
    private TrieNode findNode(String str) {
        TrieNode current = root;

        for (char ch : str.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current;
    }

    private static class TrieNode {
        char value;
        TrieNode[] children;
        boolean isEndOfWord;

        TrieNode(char value) {
            this.value = value;
            this.children = new TrieNode[26]; // For English lowercase letters
            this.isEndOfWord = false;
        }
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */