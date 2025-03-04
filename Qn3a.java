import java.util.*;

class UnionFind {
    private int[] parent, rank;

    // Constructor to initialize Union-Find data structure
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Each node is initially its own parent
            rank[i] = 0; // Rank helps balance tree depth
        }
    }

    // Find function with path compression for optimization
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    // Union function to merge two sets
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // If both nodes are already in the same set, no need to merge
        if (rootX == rootY)
            return false;

        // Union by rank: attach smaller tree under the larger one
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
}

public class Qn3a {

    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        List<int[]> edges = new ArrayList<>();

        // Step 1: Add module installation costs as virtual edges (0 → i)
        for (int i = 0; i < n; i++) {
            edges.add(new int[] { modules[i], 0, i + 1 }); // Virtual node 0 connects to device i+1
        }

        // Step 2: Add given direct connection costs
        for (int[] conn : connections) {
            edges.add(new int[] { conn[2], conn[0], conn[1] }); // {cost, device1, device2}
        }

        // Step 3: Sort edges by cost (ascending order)
        edges.sort(Comparator.comparingInt(a -> a[0]));

        // Step 4: Apply Kruskal’s Algorithm using Union-Find
        UnionFind uf = new UnionFind(n + 1); // +1 for the virtual node
        int totalCost = 0, edgesUsed = 0;

        for (int[] edge : edges) {
            int cost = edge[0], u = edge[1], v = edge[2];

            if (uf.union(u, v)) { // Connect only if not already in the same set
                totalCost += cost;
                edgesUsed++;
                if (edgesUsed == n)
                    break; // Stop once all devices are connected
            }
        }

        return totalCost;
    }

    public static void main(String[] args) {
        int n = 3;
        int[] modules = { 1, 2, 2 };
        int[][] connections = { { 1, 2, 1 }, { 2, 3, 1 } };

        System.out.println(minCostToConnectDevices(n, modules, connections)); // Output: 3
    }
}
