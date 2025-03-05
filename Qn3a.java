// The code uses Kruskal's algorithm with a Union-Find data structure
//  to find the minimum cost to connect devices by considering both 
//  module installation costs and direct connections, ensuring all 
//  devices are connected with the least expense.

import java.util.*; // Importing the necessary classes from the java.util package, including List, ArrayList, and Comparator.

class UnionFind { // Class definition for Union-Find data structure, which helps in managing
                  // disjoint sets.
    private int[] parent, rank; // Arrays to hold the parent of each node and the rank (or depth) of each node
                                // for optimization.

    // Constructor to initialize the Union-Find data structure
    public UnionFind(int n) {
        parent = new int[n]; // Array to store the parent of each node.
        rank = new int[n]; // Array to store the rank (depth) of each node.
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Initially, each node is its own parent.
            rank[i] = 0; // Initially, the rank of each node is set to 0.
        }
    }

    // Find function with path compression for optimization
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression: Update the parent to the root.
        }
        return parent[x]; // Return the parent (root) of the node.
    }

    // Union function to merge two sets
    public boolean union(int x, int y) {
        int rootX = find(x); // Find the root of x.
        int rootY = find(y); // Find the root of y.

        // If both nodes are already in the same set, no need to merge.
        if (rootX == rootY)
            return false;

        // Union by rank: attach the smaller tree under the larger tree to keep the tree
        // balanced.
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX; // Make rootX the parent of rootY.
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY; // Make rootY the parent of rootX.
        } else {
            parent[rootY] = rootX; // If both ranks are equal, make rootX the parent and increase its rank.
            rank[rootX]++;
        }
        return true; // Return true indicating that the sets were merged.
    }
}

public class Qn3a {

    // Function to find the minimum cost to connect all devices.
    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        List<int[]> edges = new ArrayList<>(); // List to store all edges (connections between nodes).

        // Step 1: Add module installation costs as virtual edges (0 → i)
        for (int i = 0; i < n; i++) {
            edges.add(new int[] { modules[i], 0, i + 1 }); // Add a virtual edge connecting node 0 to node i+1.
        }

        // Step 2: Add given direct connection costs between devices.
        for (int[] conn : connections) {
            edges.add(new int[] { conn[2], conn[0], conn[1] }); // Add direct connection costs between devices.
        }

        // Step 3: Sort edges by cost (ascending order) for Kruskal’s Algorithm.
        edges.sort(Comparator.comparingInt(a -> a[0])); // Sort edges based on the cost of connection.

        // Step 4: Apply Kruskal’s Algorithm using Union-Find.
        UnionFind uf = new UnionFind(n + 1); // Create a Union-Find instance to manage the devices and virtual node (0).
        int totalCost = 0, edgesUsed = 0; // Initialize total cost and count of edges used.

        // Loop through each edge and connect the devices using the Union-Find data
        // structure.
        for (int[] edge : edges) {
            int cost = edge[0], u = edge[1], v = edge[2]; // Extract the cost and the devices being connected.

            // Connect the devices only if they are not already in the same set.
            if (uf.union(u, v)) {
                totalCost += cost; // Add the cost of the current edge to the total cost.
                edgesUsed++; // Increase the count of edges used.
                if (edgesUsed == n) // Stop once all devices are connected.
                    break;
            }
        }

        return totalCost; // Return the total cost to connect all devices.
    }

    public static void main(String[] args) {
        int n = 3; // Number of devices.
        int[] modules = { 1, 2, 2 }; // The installation costs of the modules for each device.
        int[][] connections = { { 1, 2, 1 }, { 2, 3, 1 } }; // Direct connection costs between devices.

        // Call the minCostToConnectDevices function and print the result.
        System.out.println(minCostToConnectDevices(n, modules, connections)); // Output: 3
    }
}

// Output
// 3