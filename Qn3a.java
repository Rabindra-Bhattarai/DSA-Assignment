import java.util.*;

public class Qn3a {
    static class Edge {
        int u, v, cost;

        Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
    }

    static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY)
                return false;
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

    public static int minimumCost(int n, int[] modules, int[][] connections) {
        List<Edge> edges = new ArrayList<>();
        // Step 1: Add virtual edges from node 0 (module installation)
        for (int i = 0; i < n; i++) {
            edges.add(new Edge(0, i + 1, modules[i])); // Connect virtual node 0 to each device
        }
        // Step 2: Add given direct connections
        for (int[] conn : connections) {
            edges.add(new Edge(conn[0], conn[1], conn[2]));
        }
        // Step 3: Sort edges by cost (Greedy approach)
        edges.sort(Comparator.comparingInt(e -> e.cost));
        // Step 4: Use Kruskal’s algorithm to build MST
        UnionFind uf = new UnionFind(n + 1); // +1 to include virtual node 0
        int totalCost = 0, edgesUsed = 0;
        for (Edge edge : edges) {
            if (uf.union(edge.u, edge.v)) {
                totalCost += edge.cost;
                edgesUsed++;
                if (edgesUsed == n)
                    break; // We need exactly `n` edges to connect `n` devices
            }
        }
        return (edgesUsed == n) ? totalCost : -1; // If not all devices are connected, return -1
    }

    public static void main(String[] args) {
        int n = 3;
        int[] modules = { 1, 2, 2 };
        int[][] connections = { { 1, 2, 1 }, { 2, 3, 1 } };

        System.out.println(minimumCost(n, modules, connections)); // Output is 3 sir
    }
}
