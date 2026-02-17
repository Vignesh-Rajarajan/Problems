package graph.leetcode;

import java.util.*;

//https://leetcode.com/problems/reconstruct-itinerary/
public class ReconstructItenary {

    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> targets = new HashMap<>();
        List<String> route = new LinkedList<>();
        // 1. Build the graph
        for (List<String> ticket : tickets) {
            //Instead of a visited boolean array for the cities,
            // the queue doubles down as a visited list since we are polling from it .
            targets.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>())
                    .add(ticket.get(1));
        }

        // 2. Start DFS from JFK
        visit("JFK", targets, route);

        return route;
    }

    //THE FLY PHASE (Keep flying forward until stuck)
    //    While FlightBoard[airport] has destinations left:
    //
    //        NextCity = Remove the first destination from FlightBoard[airport]
    //
    //        Call Visit(NextCity)  <-- RECURSION HAPPENS HERE
    //
    //    4. THE RECORD PHASE (Backtracking)
    //    // We only reach this line when there are no flights left from 'airport'
    //    // or we have returned from all future flights.
    //
    //    Add 'airport' to the FRONT of 'Itinerary'
    void visit(String airport, Map<String, PriorityQueue<String>> targets, List<String> route) {
        // 3. While there are outgoing flights from this airport...
        while (targets.containsKey(airport) && !targets.get(airport).isEmpty()) {
            // Pick the lexically smallest destination and REMOVE it from the graph
            String nextAirport = targets.get(airport).poll();
            visit(nextAirport, targets, route);
        }

        // 4. Add to head of linked list (Post-order traversal)
        // This effectively builds the list backwards, handling dead ends correctly.
        route.add(0, airport);
    }


}
    
