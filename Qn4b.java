// This java program calculates the minimum number of roads that must be traversed to deliver
//  packages in a network of locations connected by roads. It takes two inputs: an array representing 
//  whether a location has a package and a two-dimensional array defining the roads that connect these locations. 
//  The program builds a graph using an adjacency list and then uses Breadth-First Search (BFS) to explore the road network,
//   ensuring that all locations containing packages are visited efficiently.

// The traversal begins at location 0, marking it as visited and adding it to a queue. 
// As the BFS proceeds, it explores all directly connected locations and marks them as visited to 
// prevent redundant visits. If a location has a package, the program increments the road count by two, c
// onsidering both the journey to the location and the return trip.

// Once all locations have been processed, the total number of roads used for package collection and return 
// is returned as the output. This approach ensures an optimal traversal of the road network while minimizing 
// unnecessary movements. The expected output for the provided example is 2, meaning only two roads need to be 
// traversed to collect the required packages and return.

import java.util.*; // Import necessary Java utilities

public class Qn4b {

    // Method to calculate the minimum number of roads to traverse to deliver
    // packages
    public static int minRoadsToTraverse(int[] packages, int[][] roads) {
        int n = packages.length; // Get the number of locations
        List<List<Integer>> graph = new ArrayList<>(); // Create an adjacency list for the graph

        // Initialize adjacency list for each location
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Build the graph by adding bidirectional edges between connected locations
        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }

        boolean[] visited = new boolean[n]; // Array to track visited locations
        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS traversal
        int roadsCount = 0; // Counter for roads traversed

        queue.offer(0); // Start BFS from location 0
        visited[0] = true; // Mark the starting location as visited

        // Perform BFS traversal
        while (!queue.isEmpty()) {
            int currentNode = queue.poll(); // Remove a node from the queue

            // Check all neighboring locations
            for (int neighbor : graph.get(currentNode)) {
                if (!visited[neighbor]) { // If the neighbor is not visited
                    visited[neighbor] = true; // Mark it as visited
                    if (packages[neighbor] == 1) { // If the location has a package to deliver
                        roadsCount += 2; // Add 2 to account for both going and returning
                    }
                    queue.offer(neighbor); // Add the neighbor to the queue for further exploration
                }
            }
        }

        return roadsCount; // Return the total roads traversed
    }

    public static void main(String[] args) {
        int[] packages1 = { 1, 0, 0, 0, 0, 1 }; // Array representing package presence at each location
        int[][] roads1 = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 } }; // Road connections between locations
        System.out.println("Output: " + minRoadsToTraverse(packages1, roads1)); // Expected output: 2
    }
}