package practiceproblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class JobSequencing {
    class Pair{
        int deadline;
        int profit;
        int id;

        Pair(int deadline, int profit, int id){
            this.id = id;
            this.profit = profit;
            this.deadline = deadline;
        }
    }

    public List<Integer> jobSequencing(int[] deadline, int[] profit) {

        PriorityQueue<Pair> maxQueue = new PriorityQueue<>((a, b)-> Integer.compare(b.profit,a.profit));

        for(int i=0;i<deadline.length;i++){
            maxQueue.offer(new Pair(deadline[i],profit[i],i));
        }

        int[] days = new int[deadline.length];
        Arrays.fill(days,-1);
        int maxJobs = 0;
        int maxProfit = 0;
        while(!maxQueue.isEmpty()){
            Pair temp = maxQueue.poll();

            for(int i=temp.deadline-1;i>=0;i--){
                if (days[i]==-1){
                    maxProfit+=temp.profit;
                    maxJobs++;
                    days[i] = temp.deadline;
                    break;
                }
            }
        }
        List<Integer> result = new ArrayList<Integer>(2);
        result.add(maxJobs);
        result.add(maxProfit);

        return result;

    }
}
