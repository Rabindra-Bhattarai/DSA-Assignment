import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.*;

class WebCrawler {
    private final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>()); // Stores visited URLs
    private final Queue<String> urlQueue = new ConcurrentLinkedQueue<>(); // Stores URLs to be crawled
    private final ExecutorService threadPool; // Thread pool for managing threads
    private final int maxRetries = 2; // Retry failed URLs

    public WebCrawler(int threadCount) {
        this.threadPool = Executors.newFixedThreadPool(threadCount);
    }

    // Start crawling from an initial URL
    public void startCrawling(String startUrl, int maxPages) {
        urlQueue.add(startUrl);

        while (!urlQueue.isEmpty() && visitedUrls.size() < maxPages) {
            String url = urlQueue.poll();
            if (url != null && !visitedUrls.contains(url)) {
                visitedUrls.add(url);
                threadPool.execute(() -> fetchPage(url, 0));
            }
        }

        threadPool.shutdown();
    }

    // Fetch the page content with retry logic
    private void fetchPage(String url, int retryCount) {
        try {
            System.out.println("Crawling: " + url);
            URL website = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(website.openStream()));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                extractUrls(line);
            }
            reader.close();

            savePageContent(url, content.toString()); // Store the page data

        } catch (Exception e) {
            System.out.println("Failed to crawl: " + url);
            if (retryCount < maxRetries) {
                System.out.println("Retrying: " + url);
                fetchPage(url, retryCount + 1); // Retry failed requests
            }
        }
    }

    // Extract URLs from page content
    private void extractUrls(String content) {
        String regex = "https?://[\\w\\.-]+";
        Scanner scanner = new Scanner(content);
        while (scanner.findInLine(regex) != null) {
            String foundUrl = scanner.match().group();
            if (!visitedUrls.contains(foundUrl)) {
                urlQueue.add(foundUrl);
                System.out.println("Found URL: " + foundUrl); // Print newly found URLs
            }
        }
        scanner.close();
    }

    // Save the crawled page data
    private void savePageContent(String url, String content) {
        try {
            String fileName = "crawled_data.txt"; // Store in a single file
            FileWriter writer = new FileWriter(fileName, true);
            writer.write("URL: " + url + "\n" + content + "\n\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving data for: " + url);
        }
    }

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler(5); // 5 threads
        crawler.startCrawling("https://www.wikipedia.org", 10); // Start from Wikipedia, limit to 10 pages
    }
}

// output
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
