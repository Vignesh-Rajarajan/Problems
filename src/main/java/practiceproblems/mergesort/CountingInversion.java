package practiceproblems.mergesort;

class CountingInversion {

    static int mergeSort(int[] arr, int arrSize) {
        return mergeSort(arr, 0, arrSize - 1);
    }

    static int mergeSort(int[] arr, int left, int right) {
        if (left >= right)
            return 0;
        int invCount = 0;
        int mid = ((right - left) / 2) + left;

        invCount += mergeSort(arr, left, mid);
        invCount += mergeSort(arr, mid + 1, right);

        invCount += merge(arr, left, mid + 1, right);

        return invCount;
    }

    static int merge(int[] arr, int left, int mid, int right) {
        int invCount = 0;
        int[] temp = new int[arr.length];
        int i = left;
        int j = mid+1;
        int k = left;
        while ((i <= mid) && (j <= right)) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                // the reason to put mid-i is take example of  [1,3,5] [2,4,6]
                // left is 0 and right is mid at start
                // when i=1 and j=0 (value 3 and 2) we see an inversion, since
                // the first part is sorted and values after i=1(3) will be greater than j=0(2)
                // so we consider all elements after 3 as inversions
                invCount += mid - i + 1;
            }
        }

        while (i <= mid - 1)
            temp[k++] = arr[i++];
        while (j <= right)
            temp[k++] = arr[j++];

        for (i = left; i <= right; i++)
            arr[i] = temp[i];

        return invCount;
    }

    public static void main(String[] args) {
        //int arr[] = new int[] { 4, 6, 2, 1, 9, 7 };
        int arr[] = new int[]{5, 1, 4, 2};
        System.out.println("Number of inversions are " + mergeSort(arr, arr.length));
    }

    //https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/editorial/
    public boolean check(int[] nums) {
        int n = nums.length;
        if (n <= 1) return true;

        int inversionCount = 0;

        // For every pair, count the number of inversions.
        for (int i = 1; i < n; ++i) {
            if (nums[i] < nums[i - 1]) {
                ++inversionCount;
                if (inversionCount > 1) return false;
            }
        }

        // Also check between the last and the first element due to rotation
        if (nums[0] < nums[n - 1]) {
            ++inversionCount;
        }

        return inversionCount <= 1;
    }
}
