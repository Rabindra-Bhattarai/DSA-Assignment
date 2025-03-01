public class Qn4a {

    static class Tweet {
        int userId;
        int tweetId;
        int year;
        int month;
        int day;
        String tweet;

        public Tweet(int userId, int tweetId, int year, int month, int day, String tweet) {
            this.userId = userId;
            this.tweetId = tweetId;
            this.year = year;
            this.month = month;
            this.day = day;
            this.tweet = tweet;
        }
    }

    public static String[][] getTrendingHashtags(Tweet[] tweets) {
        Tweet[] februaryTweets = new Tweet[tweets.length];
        int februaryCount = 0;
        for (Tweet tweet : tweets) {
            if (tweet.month == 2 && tweet.year == 2024) {
                februaryTweets[februaryCount++] = tweet;
            }
        }

        String[] hashtags = new String[100];
        int[] counts = new int[100];
        int hashtagCount = 0;

        for (int i = 0; i < februaryCount; i++) {
            String[] tweetHashtags = extractHashtags(februaryTweets[i].tweet);
            for (String hashtag : tweetHashtags) {
                if (hashtag != null) {
                    int index = findHashtag(hashtags, hashtag, hashtagCount);
                    if (index == -1) {
                        hashtags[hashtagCount] = hashtag;
                        counts[hashtagCount] = 1;
                        hashtagCount++;
                    } else {
                        counts[index]++;
                    }
                }
            }
        }

        sortHashtags(hashtags, counts, hashtagCount);

        String[][] result = new String[3][2];
        for (int i = 0; i < 3 && i < hashtagCount; i++) {
            result[i][0] = hashtags[i];
            result[i][1] = String.valueOf(counts[i]);
        }
        return result;
    }

    private static String[] extractHashtags(String text) {
        String[] words = text.split(" ");
        String[] hashtags = new String[words.length];
        int count = 0;
        for (String word : words) {
            if (word.startsWith("#")) {
                hashtags[count++] = word;
            }
        }
        return hashtags;
    }

    private static int findHashtag(String[] hashtags, String hashtag, int count) {
        for (int i = 0; i < count; i++) {
            if (hashtags[i].equals(hashtag)) {
                return i;
            }
        }
        return -1;
    }

    private static void sortHashtags(String[] hashtags, int[] counts, int count) {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (counts[j] < counts[j + 1]
                        || (counts[j] == counts[j + 1] && hashtags[j].compareTo(hashtags[j + 1]) > 0)) {
                    int tempCount = counts[j];
                    counts[j] = counts[j + 1];
                    counts[j + 1] = tempCount;

                    String tempHashtag = hashtags[j];
                    hashtags[j] = hashtags[j + 1];
                    hashtags[j + 1] = tempHashtag;
                }
            }
        }
    }

    public static void main(String[] args) {
        Tweet[] tweets = new Tweet[] {
                new Tweet(135, 13, 2024, 2, 1, "Enjoying a great start to the day. #HappyDay #morningvibes"),
                new Tweet(136, 14, 2024, 2, 2, "Another #HappyDay with good vibes! #FeelGood"),
                new Tweet(137, 15, 2024, 2, 3, "Productivity hacks! #workLife #ProductiveDay"),
                new Tweet(138, 16, 2024, 2, 4, "Exploring new tech frontiers. #TechLife #Innovation"),
                new Tweet(139, 17, 2024, 2, 5, "Gratitude for today's moments. #HappyDay #Thankful"),
                new Tweet(140, 18, 2024, 2, 7, "Innovation drives us. #TechLife #FutureTech"),
                new Tweet(141, 19, 2024, 2, 9, "Connecting with nature's serenity. #Nature #Peaceful")
        };

        String[][] trendingHashtags = getTrendingHashtags(tweets);
        System.out.println("Output:");
        System.out.println("+---------------+-------+");
        System.out.println("| hashtag       | count |");
        System.out.println("+---------------+-------+");
        for (int i = 0; i < trendingHashtags.length && trendingHashtags[i][0] != null; i++) {
            System.out.printf("| %-13s | %-5s |\n", trendingHashtags[i][0], trendingHashtags[i][1]);
        }
        System.out.println("+---------------+-------+");
    }
}

// Output:
// +---------------+-------+
// | hashtag | count |
// +---------------+-------+
// | #HappyDay | 3 |
// | #TechLife | 2 |
// | #FeelGood | 1 |
// +---------------+-------+
