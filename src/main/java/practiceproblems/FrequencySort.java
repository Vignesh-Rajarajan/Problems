package practiceproblems;

import java.util.*;

//* https://leetcode.com/problems/sort-characters-by-frequency/

public class FrequencySort {
    // this could be easily done with priority queue but this is ref for bucket sort
    public String frequencySort(String s) {
        // Count character frequencies
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Create a list of characters sorted by frequency
        List<Character> chars = new ArrayList<>(freqMap.keySet());
        chars.sort((a, b) -> freqMap.get(b) - freqMap.get(a));

        // Build the result string
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(String.valueOf(c).repeat(freqMap.get(c)));
        }

        return sb.toString();
    }

    public String frequencySortEff(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        pq.addAll(map.entrySet());

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            Map.Entry<Character, Integer> e = pq.poll();
            sb.append(String.valueOf(e.getKey()).repeat(Math.max(0, e.getValue())));
        }

        return sb.toString();
    }
}