package practiceproblems.recursion;

// https://leetcode.com/problems/powx-n/
public class Pow {
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;
        boolean isNeg = false;

        if (n < 0) {
            isNeg = true;
            n = Math.abs(n);
        }

        double result = recursion(x, n);

        return isNeg ? (1 / result) : result;
    }

    // 2^10 = (2^5)^2
    //2^5 = ((2^2)^2) * 2
    //2^2 = (2^1)^2
    //2^1 = 2
    //
    //Then working back up:
    //2^1 = 2
    //2^2 = 4
    //2^5 = 32
    //2^10 = 1024
    //Instead of multiplying x, n times
    //We reduce the problem size by half each time (n/2)
    //This gives us logarithmic time complexity O(log n)
    public double recursion(double x, int n) {
        if (n == 0) {
            return 1;
        }

        double half = recursion(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }

    public double pow1(double x, int n) {
        double result = 1.0;
        for (int i = n; i != 0; i /= 2, x *= x) {
            if (i % 2 != 0) {
                result *= x;
            }
        }
        return n < 0 ? 1.0 / result : result;
    }

}