package practiceproblems;

import java.util.PriorityQueue;

/**
 * tricky priority queue
 */
public class FurthestBuildingJump {

    //in games, you want to save your "best" items (ladders) for the biggest obstacles.
    //This code actually does exactly that, but it uses a "regret-based" greedy strategy.
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        for (int reach = 0; reach < heights.length - 1; reach++) {
            int diff = heights[reach + 1] - heights[reach];
            if (diff <= 0) {
                continue;
            }

            //We use bricks because they are granular. You can use exactly 2 bricks for a small gap or 50 bricks for a large gap.
            //Ladders, however, are binary—they cover a gap of size 1 or size 1,000 just the same.
            //If you use a ladder on a small gap early on, you can't "break" that ladder into pieces
            //later to cover multiple small gaps. By using bricks first, you keep your options open.
            bricks -= diff;
            queue.offer(diff);

            //The moment bricks < 0, you’ve hit a wall.
            //We realize, "I shouldn't have spent my bricks on one of the jumps I already passed."
            //Instead of restarting the level, you perform a retroactive swap:
            //You look at your maxQueue (which is a history of all the brick "payments" you've made).
            //You pick the single largest jump you've made so far.
            //You "refund" those bricks back into your pocket.
            //You use one ladder to cover that massive gap instead.
            //Why this is mathematically optimalBecause you always pull the maximum value from the heap,
            // you are guaranteed to get the "biggest bang for your buck" for that ladder.
            // Imagine these jumps: 5, 2, 10. You have 10 bricks and 1 ladder.
            // Jump 5: Use 5 bricks. (Bricks left: 5)
            // Jump 2: Use 2 bricks. (Bricks left: 3)
            // Jump 10: Use 10 bricks. (Bricks left: -7) ❌ Oops!
            // The Swap: You look at your history: [5, 2, 10].
            // The max is 10.The Result: You take back the 10 bricks and use a ladder for that jump.
            //Bricks: -7 + 10 = 3.
            // Ladders: 1 - 1 = 0.
            // By waiting until you run out of bricks to use the ladder, you ensure the ladder is used on the largest possible jump encountered so far, which maximizes the bricks you have left for future, smaller jumps.
            if (bricks < 0) {
                if (ladders == 0) return reach;
                ladders--;
                bricks += queue.poll();
            }

        }
        return heights.length - 1;
    }
}
