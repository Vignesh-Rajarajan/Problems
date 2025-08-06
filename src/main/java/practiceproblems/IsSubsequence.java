package practiceproblems;

//https://leetcode.com/problems/is-subsequence/
public class IsSubsequence {
    // Input: s = "abc", t = "ahbgdc"
    // Output: true
    // Input: s = "axc", t = "ahbgdc"
    // Output: false
    public boolean isSubsequence(String s, String t) {
        if (s.length() > t.length()){
            return false;
        }
        if (s.isEmpty()){
            return true;
        }
        int searchNext = 0;
        for (int i = 0; i < t.length(); i++){
            if (t.charAt(i) == s.charAt(searchNext)){
                searchNext++;
                if (searchNext == s.length()){
                    return true;
                }
            }
        }
        return false;
    }
    // https://leetcode.com/problems/append-characters-to-string-to-make-subsequence/
    public int appendCharacters(String s, String t) {
        if (s.isEmpty()){
            return t.length();
        }

        int searchNext=0;
        for(int i=0;i<s.length();i++){
            if (s.charAt(i) == t.charAt(searchNext)){
                searchNext++;
            }
            if (searchNext==t.length()){
                return 0;
            }
        }

        return t.length()-searchNext;
    }
}