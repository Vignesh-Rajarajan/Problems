package practiceproblems.tries;

import java.util.ArrayList;
import java.util.List;

public class SubstringMatch {
    public List<String> stringMatching(String[] words) {
        List<String> res = new ArrayList<>();
        Trie trie = new Trie();

        for (String word : words) {
            trie.insertSuffixes(word);
        }

        for (String word : words) {
            if (trie.search(word)) {
                res.add(word);
            }
        }

        return res;
    }

    static class TrieNode {
        TrieNode[] children;
        int cnt;

        TrieNode() {
            children = new TrieNode[26];
            cnt = 0;
        }
    }

    class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        void insertSuffixes(String word) {
            for (int i = 0; i < word.length(); i++) {
                TrieNode node = root;
                for (int j = i; j < word.length(); j++) {
                    int idx = word.charAt(j) - 'a';
                    if (node.children[idx] == null) {
                        node.children[idx] = new TrieNode();
                    }

                    node = node.children[idx];
                    node.cnt++;
                }
            }
        }

        boolean search(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                node = node.children[idx];
            }
            return node.cnt > 1;
        }
    }

}
