public class Qn2a {
    public int candy(int[] ratings) {
        int n = ratings.length; // Get the number of children
        if (n == 0)
            return 0; // If there are no children, return 0 candies
        // Step 1: Create an array for candies; everyone starts with 1 candy
        int[] candies = new int[n];
        for (int i = 0; i < n; i++) {
            candies[i] = 1; // Each child gets at least one candy
        }
        // Step 2: Go through the ratings from left to right
        for (int i = 1; i < n; i++) {
            // If the current child has a higher rating than the one on the left
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1; // Give this child more candy than the left child
            }
        }
        // Step 3: Go through the ratings from right to left
        for (int i = n - 2; i >= 0; i--) {
            // If the current child has a higher rating than the one on the right
            if (ratings[i] > ratings[i + 1]) {
                // Ensure this child has more candy than the right child
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        // Step 4: Add up all the candies to get the total
        int totalCandies = 0;
        for (int candy : candies) {
            totalCandies += candy; // Sum all the candies
        }

        return totalCandies; // Return the total number of candies
    }

    public static void main(String[] args) {
        Qn2a sol = new Qn2a(); // Create an instance of the class
        // Example 1
        int[] ratings1 = { 1, 0, 2 }; // Ratings of the children
        System.out.println(sol.candy(ratings1)); // Expected Output: 5
        // Example 2
        int[] ratings2 = { 1, 2, 2 }; // Ratings of the children
        System.out.println(sol.candy(ratings2)); // Expected Output: 4
    }
}

// output
// 5
// 4
