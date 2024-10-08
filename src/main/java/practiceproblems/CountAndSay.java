package practiceproblems;


/**
 * https://leetcode.com/problems/count-and-say/
 * tricky
 */
public class CountAndSay {
    public String countAndSay(int n) {
        if (n <= 0) {
            return "-1";
        }
        String result = "1";

        for (int i = 1; i < n; i++) {
            result = build(result);
        }
        return result;
    }

    private String build(String result) {
        StringBuilder builder = new StringBuilder();
        int p = 0;
        while (p < result.length()) {
            char val = result.charAt(p);
            int count = 0;

            while (p < result.length() && result.charAt(p) == val) {  // note that p and val will be same in first run, to count single instance
                p++;
                count++;
            }
            builder.append(count);
            builder.append(val);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        CountAndSay countAndSay = new CountAndSay();
        System.out.println(countAndSay.countAndSay(4));
    }
}