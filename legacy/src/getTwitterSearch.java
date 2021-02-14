
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Trend;
import twitter4j.Trends;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public final class getTwitterSearch {

    private static final String TWITTER_CONSUMER_KEY = "0uoimhtIBHrSHXK1Sw3SCsspX";
    private static final String TWITTER_SECRET_KEY = "4jRwUdYVamOzdr8CHvX6bPnPcmJjUi8zl98j4GpEX5gsAxZd9I";
    private static final String TWITTER_ACCESS_TOKEN = "2371825659-whASvMYl7wRC3pvEw1ZwyvhzUYv6OhPFrSyDr2G";
    private static final String TWITTER_ACCESS_TOKEN_SECRET = "R01lKmGlvKziQYnidstXV2gGNspCv2wby9ipLwsxaj9dj";

    public static void search(List<String> twitterTrends) throws IOException, TwitterException, InterruptedException {

            //Your Twitter App's Consumer Key
            String consumerKey = TWITTER_CONSUMER_KEY;

            //Your Twitter App's Consumer Secret
            String consumerSecret = TWITTER_SECRET_KEY;

            //Your Twitter Access Token
            String accessToken = TWITTER_ACCESS_TOKEN;

            //Your Twitter Access Token Secret
            String accessTokenSecret = TWITTER_ACCESS_TOKEN_SECRET;

            //Instantiate a re-usable and thread-safe factory
            TwitterFactory twitterFactory = new TwitterFactory();

            //Instantiate a new Twitter instance
            Twitter twitter = twitterFactory.getInstance();

            //setup OAuth Consumer Credentials
            twitter.setOAuthConsumer(consumerKey, consumerSecret);

            //setup OAuth Access Token
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

        Trends worldTrends = twitter.getPlaceTrends(1);
        System.out.println("\t\t( World Trends )");
        for (int i = 0; i < worldTrends.getTrends().length; i++) {
            twitterTrends.add(worldTrends.getTrends()[i].getName());
            System.out.println(worldTrends.getTrends()[i].getName());
        }
        waitRandom();

        Trends londonTrends = twitter.getPlaceTrends(44418);
        System.out.println("\t\t( London )");
        for (int i = 0; i < londonTrends.getTrends().length; i++) {
            twitterTrends.add(londonTrends.getTrends()[i].getName());
            System.out.println(londonTrends.getTrends()[i].getName());
        }
        waitRandom();


        Trends warsawTrends = twitter.getPlaceTrends(523920);
        System.out.println("\t\t( Warsaw )");
        for (int i = 0; i < warsawTrends.getTrends().length; i++) {
            twitterTrends.add(warsawTrends.getTrends()[i].getName());
            System.out.println(warsawTrends.getTrends()[i].getName());
        }
        waitRandom();

        Trends mumbaiTrends = twitter.getPlaceTrends(2295411);
        System.out.println("\t\t( Mumbai )");
        for (int i = 0; i < mumbaiTrends.getTrends().length; i++) {
            twitterTrends.add(mumbaiTrends.getTrends()[i].getName());
            System.out.println(mumbaiTrends.getTrends()[i].getName());
        }
        waitRandom();

        Trends telAvivTrends = twitter.getPlaceTrends(1968212);
        System.out.println("\t\t( Tel Aviv )");
        for (int i = 0; i < telAvivTrends.getTrends().length; i++) {
            twitterTrends.add(telAvivTrends.getTrends()[i].getName());
            System.out.println(telAvivTrends.getTrends()[i].getName());
        }
        waitRandom();

        Trends manilaTrends = twitter.getPlaceTrends(1199477);
        System.out.println("\t\t( Manila )");
        for (int i = 0; i < manilaTrends.getTrends().length; i++) {
            twitterTrends.add(manilaTrends.getTrends()[i].getName());
            System.out.println(manilaTrends.getTrends()[i].getName());
        }
        waitRandom();

        Trends hoChiMinhCityTrends = twitter.getPlaceTrends(676757);
        System.out.println("\t\t( Ho Chi Minh City )");
        for (int i = 0; i < hoChiMinhCityTrends.getTrends().length; i++) {
            twitterTrends.add(hoChiMinhCityTrends.getTrends()[i].getName());
            System.out.println(hoChiMinhCityTrends.getTrends()[i].getName());
        }
        waitRandom();

        Trends munichTrends = twitter.getPlaceTrends(676757);
        System.out.println("\t\t( Munich )");
        for (int i = 0; i < munichTrends.getTrends().length; i++) {
            twitterTrends.add(munichTrends.getTrends()[i].getName());
            System.out.println(munichTrends.getTrends()[i].getName());
        }
        waitRandom();



/*        try {
            ResponseList<Location> locations;
            locations = twitter.getAvailableTrends();
            System.out.println("Showing available twitterTrends");
            for (Location location : locations) {
                System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");
            }
            System.out.println("done.");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get twitterTrends: " + te.getMessage());
            System.exit(-1);
        }*/
    }

    private static void waitRandom() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(300);
        for (int i = 0; i < 30; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
        }
        System.out.printf("\n");
    }
/*
        try {
            Query query = new Query("war");
            QueryResult result;
            result = twitter.search(query);
            result = twitter.getCurrentTrends();
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                System.out.println(tweet.getText());
            }

            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }*/


}