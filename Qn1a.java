public class Qn1a {

    public static int minMeasurements(int k, int n) {
        // If we only have one sample, we need to check every temperature one by one.
        if (k == 1)
            return n;
        // Create a DP table where dp[i][j] represents the maximum number of temperature
        // and we can test with i samples and j measurements.
        int[][] dp = new int[k + 1][n + 1];

        int moves = 0;
        // Keep increasing the number of moves until we can cover all temperature levels
        // with our samples.
        while (dp[k][moves] < n) {
            moves++;
            // Update our DP table for each sample and the current number of moves.
            for (int i = 1; i <= k; i++) {
                dp[i][moves] = 1 + dp[i - 1][moves - 1] + dp[i][moves - 1];
            }
        }
        return moves; // Return the minimum number of measurements needed.
    }

    public static void main(String[] args) {
        // Let's test the function with a few examples.
        System.out.println(minMeasurements(1, 2)); // Output: 2 (one sample)
        System.out.println(minMeasurements(2, 6)); // Output: 3 (two samples)
        System.out.println(minMeasurements(3, 14)); // Output: 4 (three samples)
    }
}

//
