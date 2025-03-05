// The implemented Java system functions as a multithreaded web crawler
//  which starts crawling from a specific URL and stops at a limit of 
//  page crawls and extracts links from page contents. The program
//   manages page fetching through an ExecutorService system which 
//   also contains a retry mechanism to handle broken URL crawl processes.
//    The program saves webpage contents through file saving while also
//     printing newly discovered URLs. The crawl queue only contains
//      unique HTTP/HTTPS URLs extracted by a regular expression from 
//      the page content.

import java.util.*; // Importing the necessary classes for collection types
import java.util.concurrent.*; // Importing classes for multithreading and concurrency
import java.net.*; // Importing classes for URL handling
import java.io.*; // Importing classes for input/output operations

class WebCrawler {
    // A thread-safe set to store visited URLs to avoid revisiting them
    private final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());

    // A thread-safe queue to store the URLs to be crawled
    private final Queue<String> urlQueue = new ConcurrentLinkedQueue<>();

    // Thread pool for executing crawling tasks in parallel
    private final ExecutorService threadPool;

    // Maximum number of retries for failed URL crawls
    private final int maxRetries = 2;

    // Constructor to initialize the WebCrawler with a given number of threads
    public WebCrawler(int threadCount) {
        // Create a fixed thread pool with a specified number of threads
        this.threadPool = Executors.newFixedThreadPool(threadCount);
    }

    // Method to start crawling from an initial URL, limiting the number of pages to
    // crawl
    public void startCrawling(String startUrl, int maxPages) {
        // Add the start URL to the queue for crawling
        urlQueue.add(startUrl);

        // Continue crawling as long as there are URLs in the queue and the page limit
        // is not reached
        while (!urlQueue.isEmpty() && visitedUrls.size() < maxPages) {
            // Get and remove the next URL from the queue
            String url = urlQueue.poll();

            // Check if the URL has not been visited already
            if (url != null && !visitedUrls.contains(url)) {
                // Mark the URL as visited
                visitedUrls.add(url);

                // Execute a task to fetch the page content in a new thread
                threadPool.execute(() -> fetchPage(url, 0));
            }
        }

        // Shut down the thread pool once crawling is complete
        threadPool.shutdown();
    }

    // Method to fetch the page content, with retry logic in case of failure
    private void fetchPage(String url, int retryCount) {
        try {
            // Print the URL being crawled
            System.out.println("Crawling: " + url);

            // Open a connection to the URL
            URL website = new URL(url);

            // Read the content of the page through an input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(website.openStream()));

            // StringBuilder to store the page content
            StringBuilder content = new StringBuilder();
            String line;

            // Read each line of the page content
            while ((line = reader.readLine()) != null) {
                // Append the line to the content and move to the next line
                content.append(line).append("\n");
                // Extract and add URLs from the page content
                extractUrls(line);
            }

            // Close the reader once done reading the content
            reader.close();

            // Save the fetched page content to a file
            savePageContent(url, content.toString());

        } catch (Exception e) {
            // If an exception occurs (e.g., the URL is unreachable), print a message
            System.out.println("Failed to crawl: " + url);

            // If the retry count is less than the maxRetries, retry fetching the page
            if (retryCount < maxRetries) {
                System.out.println("Retrying: " + url);
                fetchPage(url, retryCount + 1); // Retry the request
            }
        }
    }

    // Method to extract URLs from the content of the page
    private void extractUrls(String content) {
        // Regular expression to match HTTP and HTTPS URLs
        String regex = "https?://[\\w\\.-]+";

        // Scanner to scan through the content and find matching URLs
        Scanner scanner = new Scanner(content);

        // Find URLs matching the pattern in the content
        while (scanner.findInLine(regex) != null) {
            // Extract the matched URL
            String foundUrl = scanner.match().group();

            // If the URL hasn't been visited yet, add it to the queue for crawling
            if (!visitedUrls.contains(foundUrl)) {
                urlQueue.add(foundUrl);
                // Print the newly found URL
                System.out.println("Found URL: " + foundUrl);
            }
        }

        // Close the scanner once done
        scanner.close();
    }

    // Method to save the crawled page content to a file
    private void savePageContent(String url, String content) {
        try {
            // Define the file name where the data will be stored
            String fileName = "crawled_data.txt";

            // Create a FileWriter to append data to the file
            FileWriter writer = new FileWriter(fileName, true);

            // Write the URL and the corresponding content to the file
            writer.write("URL: " + url + "\n" + content + "\n\n");

            // Close the writer once done
            writer.close();
        } catch (IOException e) {
            // If an error occurs while saving, print a message
            System.out.println("Error saving data for: " + url);
        }
    }

    // Main method to start the web crawler with a specified start URL and limit on
    // pages
    public static void main(String[] args) {
        // Initialize the WebCrawler with 5 threads
        WebCrawler crawler = new WebCrawler(5);

        // Start crawling from the Wikipedia homepage, limit to 10 pages
        crawler.startCrawling("https://www.wikipedia.org", 10);
    }
}

// Output
// Crawling: https://www.wikipedia.org
// Found URL: http://www.w3.org
// Found URL: http://www.w3.org
// Found URL: http://www.w3.org
// Found URL: http://www.w3.org
// Found URL: https://wikis.world
// Found URL: https://upload.wikimedia.org
// Found URL: https://meta.wikimedia.org
// Found URL: https://donate.wikimedia.org
// Found URL: https://en.wikipedia.org
// Found URL: https://play.google.com
// Found URL: https://itunes.apple.com
// Found URL: https://creativecommons.org
// Found URL: https://foundation.wikimedia.org
// Found URL: https://foundation.wikimedia.org
