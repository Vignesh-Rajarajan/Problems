package practiceproblems.tries;

public class DistinctSubstrings {

    public int distinctSubstrings(String s) {
        TrieNode root = new TrieNode();
        int distinctCount = 0;

        for (int i = 0; i < s.length(); i++) {
            TrieNode currentNode = root;
            for (int j = i; j < s.length(); j++) {
                char ch = s.charAt(j);
                if (currentNode.children[ch - 'a'] == null) {
                    currentNode.children[ch - 'a'] = new TrieNode();
                    distinctCount++;
                }
                currentNode = currentNode.children[ch - 'a'];
            }
        }

        return distinctCount + 1; // +1 for the empty substring
    }

    static class TrieNode {
        TrieNode[] children;
        int count;

        TrieNode() {
            children = new TrieNode[26];
            count = 0;
        }
    }
}
