package dynamicProgramming.lcs;

public class LongestBitonicSequence {

    public static int LongestBitonicSequence(int n, int[] arr) {
        int[] lis = new int[arr.length];
        int[] lds = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            lis[i] = 1;
            lds[i] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    lis[i] = Math.max(lis[i], lis[j] + 1);
                }
            }
        }

        for (int i = arr.length - 2; i >= 0; i--) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[i] > arr[j]) {
                    lds[i] = Math.max(lds[i], lds[j] + 1);
                }
            }
        }

        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            if (lds[i] > 1 && lis[i] > 1) {
                // because that middle element is common in both sequence
                result = Math.max(result, lds[i] + lis[i] - 1);
            }

        }

        return result;

    }
}
