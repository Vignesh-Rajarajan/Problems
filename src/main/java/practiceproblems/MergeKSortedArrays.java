package practiceproblems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedArrays {
    public static ArrayList<Integer> mergeKArrays(int[][] arr, int K) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int i = 0;
        for (int[] list : arr) {
            if (list.length > 0) {
                queue.offer(new int[]{list[0], i, 0});
            }
            i++;
        }

        ArrayList<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            result.add(temp[0]);
            int idx = temp[1];
            int pos = temp[2] + 1;

            if (pos < arr[idx].length) {
                queue.offer(new int[]{arr[idx][pos], idx, pos});
            }
        }

        return result;
    }
}
