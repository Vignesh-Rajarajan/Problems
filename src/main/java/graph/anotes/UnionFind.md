
Union-Find is designed to answer connectivity questions ("Are node A and node B in the same component?"). 
It collapses nodes into sets.
Loss of Distance: Standard DSU does not inherently track distance or path length. It only tracks parentage.
Directionality: DSU works best on undirected graphs. This problem is a directed graph ($u \to v$).
