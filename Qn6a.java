// Class responsible for printing numbers
class NumberPrinter {
    // Prints 0
    public void printZero() {
        System.out.print(0);
    }

    // Prints an even number
    public void printEven(int number) {
        System.out.print(number);
    }

    // Prints an odd number
    public void printOdd(int number) {
        System.out.print(number);
    }
}

// Controller class to synchronize and coordinate the threads
class ThreadController {
    private int n; // Maximum number up to which numbers will be printed
    private int count = 1; // Counter to control execution order
    private final Object lock = new Object(); // Lock object for synchronization

    // Constructor to initialize the number limit
    public ThreadController(int n) {
        this.n = n;
    }

    // 2 Marks - Handles printing of 0 before each number
    public void printZero(NumberPrinter printer) throws InterruptedException {
        synchronized (lock) {
            for (int i = 0; i < n; i++) {
                while (count % 2 != 1) { // Ensures it's Zero's turn
                    lock.wait();
                }
                printer.printZero(); // Print 0
                count++; // Move to next step
                lock.notifyAll(); // Notify other threads
            }
        }
    }

    // 2 Marks - Handles printing of even numbers
    public void printEven(NumberPrinter printer) throws InterruptedException {
        synchronized (lock) {
            for (int i = 2; i <= n; i += 2) { // Loop through even numbers
                while (count % 4 != 0) { // Ensures Zero & Odd run first
                    lock.wait();
                }
                printer.printEven(i); // Print even number
                count++; // Move to next step
                lock.notifyAll(); // Notify other threads
            }
        }
    }

    // 2 Marks - Handles printing of odd numbers
    public void printOdd(NumberPrinter printer) throws InterruptedException {
        synchronized (lock) {
            for (int i = 1; i <= n; i += 2) { // Loop through odd numbers
                while (count % 4 != 2) { // Ensures Zero runs first
                    lock.wait();
                }
                printer.printOdd(i); // Print odd number
                count++; // Move to next step
                lock.notifyAll(); // Notify other threads
            }
        }
    }
}

// 3 Marks - Thread Execution and Coordination
public class Qn6a {
    public static void main(String[] args) {
        int n = 5; // Number limit for the sequence

        NumberPrinter printer = new NumberPrinter();
        ThreadController controller = new ThreadController(n);

        // Thread responsible for printing 0
        Thread zeroThread = new Thread(() -> {
            try {
                controller.printZero(printer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Thread responsible for printing even numbers
        Thread evenThread = new Thread(() -> {
            try {
                controller.printEven(printer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Thread responsible for printing odd numbers
        Thread oddThread = new Thread(() -> {
            try {
                controller.printOdd(printer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start all threads
        zeroThread.start();
        oddThread.start();
        evenThread.start();
    }
}
