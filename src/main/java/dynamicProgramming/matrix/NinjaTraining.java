package dynamicProgramming.matrix;

// https://takeuforward.org/data-structure/dynamic-programming-ninjas-training-dp-7/
public class NinjaTraining {

    public int maximumPointsTabulation(int[][] points, int N) {
        int[][] dp = new int[N][4];
        //this is same as  i != lastTask
        // on 0th day if we considered task 0 from prev day then we can't consider task 0 on 1st day
        dp[0][0] = Math.max(points[0][1], points[0][2]);
        // on 0th day if we considered task 1 from prev day then we can't consider task 1 on 1st day
        dp[0][1] = Math.max(points[0][0], points[0][2]);
        // on 0th day if we considered task 2 from prev day then we can't consider task 2 on 1st day
        dp[0][2] = Math.max(points[0][0], points[0][1]);
        // on 0th day if we considered task 3 from prev day then we can't consider task 3 on 1st day
        dp[0][3] = Math.max(Math.max(points[0][0], points[0][1]), points[0][2]);

        for (int day = 1; day < N; day++) { // This loop iterates through each day, starting from day 1 (since day 0 was initialized separately).
            for (int last = 0; last < 4; last++) {
                //This loop considers all possible 'last' tasks.0, 1, 2 represent the actual tasks
                //3 represents the case where we're not constrained by any previous task
                dp[day][last] = 0; // Initialize the maximum points for the current day and last activity
                // Consider each possible task for the current day
                for (int task = 0; task <= 2; task++) {
                    if (task == last) {
                        continue;
                    } // Ensure that the current task is different from the last
                    // Calculate the points for the current activity and add it to the maximum points from the previous day
                    int activity = points[day][task] + dp[day - 1][task];
                    // Update the maximum points for the current day and last activity
                    dp[day][last] = Math.max(dp[day][last], activity);

                }
            }
        }
        // Return the maximum points achievable after all days (last activity is 3)
        return dp[N - 1][3];
    }

    public int maximumPoints(int[][] points, int N) {
        Integer[][] cache = new Integer[points.length][4];
        // start with task number which is not in the array to avoid the condition i == lastTask
        // if we pass 2 then that task will be skipped, because of that increase the array size to 4
        return recursionHelper(points, points.length - 1, 3, cache);
    }

    // pattern of inclusive and exclusive recursion calls doesn't work in this case because
    //  It would assume that the maximum points can be obtained by either including or excluding the current task,
    //  without considering the constraint of different tasks on consecutive days.
    public int recursionHelper(int[][] points, int day, int lastTask, Integer[][] cache) {
        if (cache[day][lastTask] != null) {
            return cache[day][lastTask];
        }
        int maxReturn = 0;
        for (int i = 0; i <= 2; i++) {
            if (i == lastTask) {
                continue;
            }
            int currentPoints = points[day][i];
            if (day > 0) {
                currentPoints += recursionHelper(points, day - 1, i, cache);
            }
            maxReturn = Math.max(maxReturn, currentPoints);
        }
        return cache[day][lastTask] = maxReturn;
    }
}
