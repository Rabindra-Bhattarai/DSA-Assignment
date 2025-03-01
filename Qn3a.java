public class Qn3a {
    // Helper method to find the parent (root) of a device
    static int findParent(int device, int[] parent) {
        if (parent[device] != device) {
            parent[device] = findParent(parent[device], parent); // Path compression
        }
        return parent[device];
    }

    // Helper method to unite two sets (Union by rank)
    static void union(int dev1, int dev2, int[] parent, int[] rank) {
        int root1 = findParent(dev1, parent);
        int root2 = findParent(dev2, parent);

        if (root1 != root2) { // Only merge if they are in different sets
            if (rank[root1] > rank[root2]) {
                parent[root2] = root1;
            } else if (rank[root1] < rank[root2]) {
                parent[root1] = root2;
            } else {
                parent[root2] = root1;
                rank[root1]++;
            }
        }
    }

    // Main method to compute the minimum cost to connect all devices
    public static int minNetworkCost(int n, int[] modules, int[][] connections) {
        int totalCost = 0;
        int[] parent = new int[n + 1]; // Parent array for union-find
        int[] rank = new int[n + 1]; // Rank array to optimize union-find

        // Step 1: Initially, each device is its own parent
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        // Step 2: Connect the cheapest module as a starting point
        int cheapestDevice = 1;
        for (int i = 2; i <= n; i++) {
            if (modules[i - 1] < modules[cheapestDevice - 1]) {
                cheapestDevice = i;
            }
        }
        totalCost += modules[cheapestDevice - 1]; // Install module on the cheapest device

        // Step 3: Sort connections by cost (manual sorting to avoid imports)
        for (int i = 0; i < connections.length - 1; i++) {
            for (int j = 0; j < connections.length - i - 1; j++) {
                if (connections[j][2] > connections[j + 1][2]) {
                    int[] temp = connections[j];
                    connections[j] = connections[j + 1];
                    connections[j + 1] = temp;
                }
            }
        }

        // Step 4: Use a greedy approach to connect devices at minimum cost
        for (int i = 0; i < connections.length; i++) {
            int dev1 = connections[i][0];
            int dev2 = connections[i][1];
            int cost = connections[i][2];

            if (findParent(dev1, parent) != findParent(dev2, parent)) {
                union(dev1, dev2, parent, rank);
                totalCost += cost; // Add cost to the total
            }
        }

        return totalCost;
    }

    // Example test cases
    public static void main(String[] args) {
        int n1 = 3;
        int[] modules1 = { 1, 2, 2 };
        int[][] connections1 = { { 1, 2, 1 }, { 2, 3, 1 } };
        System.out.println(minNetworkCost(n1, modules1, connections1)); // Output: 3

    }
}
// Output
// 3