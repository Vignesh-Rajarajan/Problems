package practiceproblems.design;

public class MovingAverage {
    int[] arr;
    int count; int sum;
    public MovingAverage(int size) {
        arr = new int[size];
    }
    public double next(int val) {
        int idx = count % arr.length;
        sum+= val - arr[idx];
        arr[idx] = val;

        return (sum * 1.0) / Math.min(count, arr.length);
    }
}
