package practiceproblems;

/**
 * https://leetcode.com/problems/reverse-words-in-a-string/
 * tricky
 */
public class ReverseString {
    public static void main(String[] args) {
        ReverseString reverseString = new ReverseString();

        String s2 = "  lets go to 'new york'  ";
        System.out.println(reverseString.reverseWords(s2)); // Output: "'new york' to go lets"
    }

    public String reverseWords(String s) {
        // Step 1: Replace spaces inside quotes with hyphens
        char[] str = s.toCharArray();
        boolean inQuote = false;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == '\'') {
                inQuote = !inQuote;
            } else if (inQuote && str[i] == ' ') {
                str[i] = '-';
            }
        }

        // Step 2: Reverse the entire string (your original code)
        reverse(str, 0, str.length - 1);

        int start = 0, end = 0;
        int n = str.length;
        int resultPos = 0;

        // Step 3: Process words (modified to handle hyphens)
        while (end < n) {
            while (end < n && str[end] == ' ') end++;
            if (end == n) break;

            if (resultPos > 0) {
                str[resultPos++] = ' ';
            }

            start = resultPos;
            while (end < n && str[end] != ' ') {
                str[resultPos++] = str[end++];
            }

            reverse(str, start, resultPos - 1);
        }

        // Step 4: Convert hyphens back to spaces
        for (int i = 0; i < resultPos; i++) {
            if (str[i] == '-') {
                str[i] = ' ';
            }
        }

        return new String(str, 0, resultPos);
    }

    private void reverse(char[] arr, int i, int j) {
        while (i < j) {
            char tmp = arr[i];
            arr[i++] = arr[j];
            arr[j--] = tmp;
        }
    }
}

