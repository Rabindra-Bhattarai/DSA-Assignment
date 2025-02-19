import java.util.*;

public class Qn2b {
    public static int[] closestLexicographicalPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Get the number of points
        int minDistance = Integer.MAX_VALUE; // Start with a very large distance
        int[] result = new int[2]; // Array to store the indices of the closest pair

        // Step 1: Check every possible pair of points (i, j)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate the Manhattan distance between points i and j
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // Step 2: If this distance is smaller than our current minimum
                if (distance < minDistance) {
                    minDistance = distance; // Update the minimum distance
                    result[0] = i; // Store the index of the first point
                    result[1] = j; // Store the index of the second point
                }
                // Step 3: If the distance is the same, check for lexicographical order
                else if (distance == minDistance) {
                    // Choose the pair that is lexicographically smaller
                    if (i < result[0] || (i == result[0] && j < result[1])) {
                        result[0] = i; // Update the index of the first point
                        result[1] = j; // Update the index of the second point
                    }
                }
            }
        }
        return result; // Return the indices of the closest pair
    }

    public static void main(String[] args) {
        // Example coordinates
        int[] x_coords = { 1, 2, 3, 2, 4 };
        int[] y_coords = { 2, 3, 1, 2, 3 };

        // Get the closest lexicographical pair and print the result
        int[] result = closestLexicographicalPair(x_coords, y_coords);
        System.out.println(Arrays.toString(result)); // Output: [0, 3]
    }
}
