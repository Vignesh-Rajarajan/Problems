package practiceproblems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/asteroid-collision
 */
public class AsteroidCollision {

    public int[] asteroidCollision(int[] asteroids) {
        if (asteroids.length <= 1) return asteroids;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int asteroid : asteroids) {
            if (asteroid > 0) { // Pushing all +ve asteroid
                stack.push(asteroid);
            } else {
                // This loop removes all positive asteroids from the stack that are smaller than the current negative asteroid.
                while (!stack.isEmpty() && stack.peek() > 0 && Math.abs(stack.peek()) < Math.abs(asteroid)) {
                    stack.pop();
                }
                // Checking if the stack is empty or the recent asteroid is negative!
                if (stack.isEmpty() || stack.peek() < 0) {
                    stack.push(asteroid);
                } else if (stack.peek() == Math.abs(asteroid)) {
                    stack.pop();  // This ensures that when two asteroids of equal size collide, both are destroyed, and neither is added to the stack.
                }
            }
        }
        int[] output = new int[stack.size()];
        for (int i = output.length - 1; i >= 0; i--)
            output[i] = stack.pop();

        return output;
    }
}
