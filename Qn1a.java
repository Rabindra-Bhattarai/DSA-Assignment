// The program calculates the minimal measurement count for discovering the highest 
// secure temperature range using present sample information. The measurement process 
// demands two examinations if there is one sample at two temperature settings. 
// The experimental process needs three measurement sets for its execution when 
// using two sample groups at six temperature points. The program establishes that 
// four measurements will be necessary to find the highest safe temperature efficiently 
// when working with three samples and fourteen temperature levels.

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
        // Test cases
        System.out.println(minMeasurements(1, 2));

        System.out.println(minMeasurements(2, 6));

        System.out.println(minMeasurements(3, 14));
    }
}
// output
// 2
// 3
// 4
