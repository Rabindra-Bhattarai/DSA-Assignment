import java.util.*;

class Qn1b {
    public static int kthSmallestProduct(int[] returns1, int[] returns2, int k) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(a -> returns1[a[0]] * returns2[a[1]]));
        Set<String> visited = new HashSet<>();

        minHeap.offer(new int[] { 0, 0 }); // Start from the smallest pair (0,0)
        visited.add("0,0");

        int count = 0;
        while (!minHeap.isEmpty()) {
            int[] pair = minHeap.poll();
            int i = pair[0], j = pair[1];
            int product = returns1[i] * returns2[j];
            count++;

            if (count == k)
                return product;

            // Try adding the next element from `returns1`
            if (i + 1 < returns1.length && visited.add((i + 1) + "," + j)) {
                minHeap.offer(new int[] { i + 1, j });
            }

            // Try adding the next element from `returns2`
            if (j + 1 < returns2.length && visited.add(i + "," + (j + 1))) {
                minHeap.offer(new int[] { i, j + 1 });
            }
        }
        return -1; // Should not reach here
    }

    public static void main(String[] args) {
        int[] returns1 = { -4, -2, 0, 3 };
        int[] returns2 = { 2, 4 };
        int k = 6;
        System.out.println(kthSmallestProduct(returns1, returns2, k));
    }
}

// Output: 0
