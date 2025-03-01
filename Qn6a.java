class NumberPrinter {
    public void printZero() {
        System.out.print(0);
    }

    public void printEven(int number) {
        System.out.print(number);
    }

    public void printOdd(int number) {
        System.out.print(number);
    }
}

class ThreadController {
    private int n;
    private int count = 1;
    private final Object lock = new Object();

    public ThreadController(int n) {
        this.n = n;
    }

    public void printZero(NumberPrinter printer) throws InterruptedException {
        synchronized (lock) {
            for (int i = 0; i < n; i++) {
                while (count % 2 != 1) { // Wait if it's not Zero's turn
                    lock.wait();
                }
                printer.printZero();
                count++; // Move to next step
                lock.notifyAll();
            }
        }
    }

    public void printEven(NumberPrinter printer) throws InterruptedException {
        synchronized (lock) {
            for (int i = 2; i <= n; i += 2) {
                while (count % 4 != 0) { // Wait for Zero & Odd to print first
                    lock.wait();
                }
                printer.printEven(i);
                count++; // Move to next step
                lock.notifyAll();
            }
        }
    }

    public void printOdd(NumberPrinter printer) throws InterruptedException {
        synchronized (lock) {
            for (int i = 1; i <= n; i += 2) {
                while (count % 4 != 2) { // Wait for Zero to print first
                    lock.wait();
                }
                printer.printOdd(i);
                count++; // Move to next step
                lock.notifyAll();
            }
        }
    }
}

public class Qn6a {
    public static void main(String[] args) {
        int n = 5; // Change this number for different outputs
        NumberPrinter printer = new NumberPrinter();
        ThreadController controller = new ThreadController(n);

        Thread zeroThread = new Thread(() -> {
            try {
                controller.printZero(printer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread evenThread = new Thread(() -> {
            try {
                controller.printEven(printer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread oddThread = new Thread(() -> {
            try {
                controller.printOdd(printer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        zeroThread.start();
        oddThread.start();
        evenThread.start();
    }
}
