public class Qn2a {
    // Function to determine the minimum rewards needed
    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        int[] rewards = new int[n]; // Array to store rewards

        // Assign 1 reward to each initially
        for (int i = 0; i < n; i++) {
            rewards[i] = 1;
        }

        // Left to right pass: Ensuring increasing rating employees get more rewards
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }

        // Right to left pass: Ensuring decreasing rating employees also get proper
        // rewards
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        // Calculate the total rewards needed
        int totalRewards = 0;
        for (int i = 0; i < n; i++) {
            totalRewards += rewards[i];
        }

        return totalRewards;
    }

    public static void main(String[] args) {
        // Test cases
        System.out.println("Minimum rewards needed: " + minRewards(new int[] { 1, 0, 2 }));
        System.out.println("Minimum rewards needed: " + minRewards(new int[] { 1, 2, 2 }));
    }
}

// Output
// Minimum rewards needed: 5
// Minimum rewards needed: 4
