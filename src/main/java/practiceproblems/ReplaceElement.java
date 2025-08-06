package practiceproblems;

//https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/
// tricky
public class ReplaceElement {
    public int[] replaceElements(int[] arr) {
        if (arr.length == 1) {
            return new int[]{-1};
        }

        int[] result = new int[arr.length];
        int nextBigger = arr[arr.length - 1];
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > nextBigger) {
                result[i] = nextBigger;
                nextBigger = arr[i];
            } else {
                result[i] = nextBigger;
            }
        }

        result[arr.length - 1] = -1;

        return result;
    }
}
