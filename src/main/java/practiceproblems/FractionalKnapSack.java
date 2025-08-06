package practiceproblems;

import java.util.Arrays;

public class FractionalKnapSack {

    static class Item {
        int value;
        int weight;

        Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }

    public double fractionalKnapsack(int W, int[] weight, int[] value, int n){

        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(value[i], weight[i]);
        }

        // Sort items by value-to-weight ratio in descending order
        //	Value	Weight	Value-to-Weight Ratio
        //   60	    10	  6.0
        //   100	20	  5.0
        //   120	30	  4.0
        Arrays.sort(items, (a, b) -> Double.compare(b.value / (double) b.weight, a.value / (double) a.weight));

        double totalValue = 0.0;

        for (Item item : items) {
            if (W <= 0) {
                break;
            }
            if (item.weight <= W) {
                totalValue += item.value;
                W -= item.weight;
            } else {
                totalValue += item.value * ((double) W / item.weight);
                W = 0;
            }
        }
        return totalValue;
    }
}
