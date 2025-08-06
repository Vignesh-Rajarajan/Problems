package graph.leetcode;

//https://leetcode.com/problems/find-the-town-judge/
public class FindJudge {
    public int findJudge(int N, int[][] trust) {
        if(trust.length==0 && N==1) return 1;
        int[] inEdge= new int[N+1];
        int[] outEdge= new int[N+1];

        for(int[] val: trust){
            outEdge[val[0]]++;
            inEdge[val[1]]++;
        }

        for(int i=1;i<=N;i++){
            if(outEdge[i]==0 && inEdge[i]==N-1) return i;
        }

        return -1;
    }
}
