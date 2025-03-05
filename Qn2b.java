// This code develops a mechanism to identify two neighboring points that hold 
// the shortest distance according to Manhattan distance. The algorithm probes 
// all point combinations then evaluates distances to modify the minimum pair 
// relationship. If two points share equal distance then the algorithm selects 
// the pair of indices whose names come before the others in alphabetical order.
//  This method provides the indices of closest pair points while the main function 
//  shows results from testing sample coordinates and prints the output.

public class Qn2b { // Define the class Qn2b

    // Function to find the closest lexicographical pair of points
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Get the number of points
        int minDist = Integer.MAX_VALUE; // Initialize the minimum distance with the largest possible value
        int[] result = { 0, 0 }; // Store the indices of the closest pair of points, initially set to [0, 0]

        // Loop through all unique pairs of points (i, j) where i < j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate Manhattan distance between the pair of points
                int dist = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // Update the result if a smaller distance is found or if the same distance but
                // lexicographically smaller
                if (dist < minDist || (dist == minDist && (i < result[0] || (i == result[0] && j < result[1])))) {
                    minDist = dist; // Update minimum distance
                    result[0] = i; // Store the first index of the closest pair
                    result[1] = j; // Store the second index of the closest pair
                }
            }
        }

        return result; // Return the indices of the closest pair of points
    }

    public static void main(String[] args) {
        // Example test case
        int[] x_coords = { 1, 2, 3, 2, 4 }; // X coordinates of the points
        int[] y_coords = { 2, 3, 1, 2, 3 }; // Y coordinates of the points

        // Find the closest pair by calling the findClosestPair method
        int[] closestPair = findClosestPair(x_coords, y_coords);

        // Print the result (indices of the closest pair)
        System.out.println("Closest pair of points: [" + closestPair[0] + ", " + closestPair[1] + "]");
    }
}

// output
// Closest pair of points: [0, 3]
