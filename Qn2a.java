public class Qn2a { // Class definition

    // Function to determine the minimum rewards needed
    public static int minRewards(int[] ratings) {
        int n = ratings.length; // Get the length of the ratings array
        int[] rewards = new int[n]; // Create an array to store rewards for each employee

        // Assign 1 reward to each employee initially
        for (int i = 0; i < n; i++) {
            rewards[i] = 1; // Each employee starts with 1 reward
        }

        // Left to right pass: Ensuring increasing rating employees get more rewards
        for (int i = 1; i < n; i++) {
            // If the current rating is greater than the previous, give more reward
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1; // Give 1 more reward than the previous employee
            }
        }

        // Right to left pass: Ensuring decreasing rating employees also get proper
        // rewards
        for (int i = n - 2; i >= 0; i--) {
            // If the current rating is greater than the next, adjust rewards
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1); // Ensure max reward considering both directions
            }
        }

        // Calculate the total rewards needed
        int totalRewards = 0; // Variable to store total rewards
        for (int i = 0; i < n; i++) {
            totalRewards += rewards[i]; // Add each employee's reward to the total
        }

        return totalRewards; // Return the total rewards needed
    }

    public static void main(String[] args) {
        // Test cases
        System.out.println("Minimum rewards needed: " + minRewards(new int[] { 1, 0, 2 })); // Test case 1
        System.out.println("Minimum rewards needed: " + minRewards(new int[] { 1, 2, 2 })); // Test case 2
    }
}

// Output
// Minimum rewards needed: 5
// Minimum rewards needed: 4
