package practiceproblems;

import java.util.*;

public class TopKSumCombination {

    List<Integer> maxCombinations(int N, int K, int[] A, int[] B) {

        // 1. Sort arrays in ascending order
        Arrays.sort(A);
        Arrays.sort(B);

        // 2. Max heap to store {sum, index_A, index_B}
        //    Ordered by sum descending.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(b[0], a[0])
        );

        // 3. Set to keep track of visited index pairs (i, j) to avoid duplicates
        Set<String> used = new HashSet<>();

        // 4. Push the largest possible sum combination onto the heap.
        //    This uses the largest elements from both sorted arrays.
        int initial_i = N - 1;
        int initial_j = N - 1;
        maxHeap.offer(new int[]{A[initial_i] + B[initial_j], initial_i, initial_j});
        used.add(initial_i + "," + initial_j);

        List<Integer> topK = new ArrayList<>();

        // 5. Extract K maximum sum combinations
        //    Ensure we don't try to extract more elements than available in the heap
        for (int count = 0; count < K && !maxHeap.isEmpty(); count++) {
            // Extract the current largest sum combination
            int[] top = maxHeap.poll();
            int sum = top[0];
            int i = top[1]; // Index in A
            int j = top[2]; // Index in B

            topK.add(sum);

            // 6. Explore the next potential candidates by decrementing indices
            //    (moving towards smaller elements in the sorted arrays)

            // Push next combination (i-1, j) if index is valid and pair not used
            int next_i = i - 1;
            int next_j = j;
            if (next_i >= 0 && !used.contains(next_i + "," + next_j)) {
                maxHeap.offer(new int[]{A[next_i] + B[next_j], next_i, next_j});
                used.add(next_i + "," + next_j);
            }

            // Push next combination (i, j-1) if index is valid and pair not used
            next_i = i;
            next_j = j - 1;
            if (next_j >= 0 && !used.contains(next_i + "," + next_j)) {
                maxHeap.offer(new int[]{A[next_i] + B[next_j], next_i, next_j});
                used.add(next_i + "," + next_j);
            }
        }

        return topK;
    }
}
