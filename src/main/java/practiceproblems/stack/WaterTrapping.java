package practiceproblems.stack;

/**
 * pseudocode : GetTotalWater(H)
 * total <- 0
 * for every i in H
 * leftMax <- findMax(0,i);
 * rightMax <- findMax(i,n);
 * permissibleWaterStored <- min(leftMax,rightMax);
 * waterStored <- permissibleWaterStored-H[i];
 * total <- total+waterStored;
 * return total;
 */
class WaterTrapping {

    public static int trapBruteForce(int[] height) {
        int result = 0;
        for (int i = 0; i < height.length; i++) {
            int leftMax = 0;
            int rightMax = 0;
            for (int j = 0; j < i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }
            for (int j = i + 1; j < height.length; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }
            int waterStored = Math.min(leftMax, rightMax) - height[i];
            if (waterStored > 0) {
                result += waterStored;
            }
        }
        return result;
    }

    public static int trap(int[] height) {

        int[] maxLeft = new int[height.length];
        int[] maxRight = new int[height.length];
        int maxHeight = 0;
        int minHeight = 0;

        for (int i = 0; i < height.length; i++) {
            maxHeight = Math.max(maxHeight, height[i]);
            maxLeft[i] = maxHeight;

        }
        maxHeight = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            maxHeight = Math.max(maxHeight, height[i]);
            maxRight[i] = maxHeight;
        }
        int result = 0;
        for (int i = 0; i < height.length; i++) {
            minHeight = Math.min(maxLeft[i], maxRight[i]);
            result += Math.max(0, minHeight - height[i]);
        }

        return result;

    }


    public int trapAnother(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int maxLeft = 0;
        int maxRight = 0;

        int totalWater = 0;
        while (left < right) {
            if (height[left] <= height[right]) {
                maxLeft = Math.max(maxLeft, height[left]);
                totalWater += maxLeft - height[left];

                left++;
            } else {
                maxRight = Math.max(maxRight, height[right]);
                totalWater += maxRight - height[right];
                right--;
            }
        }
        // Return the sum we've been adding to.
        return totalWater;
    }

    public static void main(String[] args) {
        int[] arr = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println("Maximum water that " + "can be accumulated is " + trapBruteForce(arr));
    }
}