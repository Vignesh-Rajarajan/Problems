package dynamicProgramming;

import java.util.*;

class PerfectSquare {
    // let's solve by dynamic programming approach
    // if we take value 13, the number of perfect squares less than
    // the input at any point is Sqrt(input)=>(sqrt(13)=3, so max it can have answer below)
    // 3,because 4*4 is 16, so perfect squares below 13 are (1*1, 2*2, 3*3)
    //               13         can be broken down into
    //             1/ 4|  \ 9    deducting 1^2 leaves with val 12
    //             /   |   \     deducting 2^2 leaves with val  9
    //          12      9     4   deducting 3^2 leaves with val  4
    //           /|\     /|\    /|
    //           / | \   8 5 0  3 0
    //           11 7  3

    public int numSquaresDp(int n) {
        int[] ns = new int[n+1];

        for(int i=1;i<=n;i++){
            int min = i; // initial value is i because it can have i perfect squares(1^2 +1^2+...i)
            int sqrt = (int)Math.sqrt(i); // because i can have max of sqrt(i) perfect squares
            for(int j=sqrt;j>0;j--){
                int result = i - j*j;
                // the reason to add 1 to the ns[result] is, we take away
                // a square from 'i' initially (j*j) and check for best answer in the 1-D arr
                // we add back the 'taken out' square to min value
                // for e.x if i=13, sqrt is 3, while iterating
                // we subtract 1*1 from 13 and check for best possible answer for 12 ns[result]
                // finally we add back the 1*1 as +1 (ns[result]+1)
                min = Math.min(min, ns[result]+1);
            }
            ns[i] = min;
            System.out.println(Arrays.toString(ns));
        }
        return ns[n];
    }

    public int numSquares(int n){
        Queue<Integer> q = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        q.offer(0);
        visited.add(0);
        int depth = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            depth++;
            while (size > 0) {
                int removed = q.poll();
                for (int i = 1; i * i <= n; i++) {
                    int v = removed + i * i;
                    if (v == n) {
                        return depth;
                    }
                    if (v > n) {
                        break;
                    }
                    if (!visited.contains(v)) {
                        q.offer(v);
                        visited.add(v);
                    }
                }
                size--;
            }
        }
        return depth;
    }

    Integer[]dp= new Integer[10000];
    public int numSquaresRecur(int n) {
        if(n<=0) return 0;
        if(n==1) return 1;
        if(dp[n]!=null) return dp[n];
        int count=100000;
        for(int i=1;i<=Math.sqrt(n);i++){
            count= Math.min(count, 1+numSquaresRecur(n-i*i));
        }
        dp[n]=count;
        return dp[n];
    }

    public static void main(String[] args) {
        new PerfectSquare().numSquaresDp(13);
    }
}