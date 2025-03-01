public class Qn2b { // Class name as requested
    // Function to find the closest lexicographical pair of points
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length;
        int minDist = Integer.MAX_VALUE; // Store the minimum distance found
        int[] result = { 0, 0 }; // Store the closest pair of indices

        // Loop through all unique pairs of points
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate Manhattan distance
                int dist = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // Update if a smaller distance is found or if lexicographically smaller
                if (dist < minDist ||
                        (dist == minDist && (i < result[0] || (i == result[0] && j < result[1])))) {

                    minDist = dist;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result; // Return the closest pair of indices
    }

    public static void main(String[] args) {
        // Example test case
        int[] x_coords = { 1, 2, 3, 2, 4 };
        int[] y_coords = { 2, 3, 1, 2, 3 };

        // Find the closest pair
        int[] closestPair = findClosestPair(x_coords, y_coords);

        // Print the result
        System.out.println("Closest pair of points: [" + closestPair[0] + ", " + closestPair[1] + "]");
    }
}
