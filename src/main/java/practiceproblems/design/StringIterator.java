package practiceproblems.design;

/**
 * https://leetcode.com/articles/desing-compressed-string-iterator/
 **/
public class StringIterator {
    private final String compressedString;
    private int index;
    private char currChar;
    private int currCount;

    public StringIterator(String compressedString) {
        this.compressedString = compressedString;
        this.index = 0;
        this.currChar = ' ';
        this.currCount = 0;
        // Load the first character and count
        loadNext();
    }

    private void loadNext() {
        if (index >= compressedString.length()) {
            currChar = ' ';
            currCount = 0;
            return;
        }

        // Read the character
        currChar = compressedString.charAt(index++);

        // Read the number (could be multi-digit)
        int start = index;
        while (index < compressedString.length() && Character.isDigit(compressedString.charAt(index))) {
            index++;
        }
        currCount = Integer.parseInt(compressedString.substring(start, index));
    }

    public char next() {
        if (!hasNext()) {
            return ' '; // or throw, but problem expects string, so maybe return "" in string context
        }
        currCount--;
        return currChar;
    }

    public boolean hasNext() {
        // If current count is 0, try to load next char
        if (currCount == 0) {
            loadNext();
        }
        return currCount > 0;
    }
}
