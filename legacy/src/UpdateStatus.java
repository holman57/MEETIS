
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

public final class UpdateStatus {

    private Hash hash = new Hash();
    private String TWITTER_CONSUMER_KEY = hash.getTWITTER_CONSUMER_KEY();
    private String TWITTER_SECRET_KEY = hash.getTWITTER_SECRET_KEY();
    private String TWITTER_ACCESS_TOKEN = hash.getTWITTER_ACCESS_TOKEN();
    private String TWITTER_ACCESS_TOKEN_SECRET = hash.getTWITTER_ACCESS_TOKEN_SECRET();

    public UpdateStatus(internalComparison element, JTextArea outputTextArea) throws IOException, TwitterException {

        outputTextArea.append("\n                  Sending tweet... ");

        //convert String to Stream
        URL url = new URL(element.img);
        URLConnection urlConnection = url.openConnection();
        InputStream imgStream = new BufferedInputStream(urlConnection.getInputStream());

        String consumerKey = TWITTER_CONSUMER_KEY;
        String consumerSecret = TWITTER_SECRET_KEY;
        String accessToken = TWITTER_ACCESS_TOKEN;
        String accessTokenSecret = TWITTER_ACCESS_TOKEN_SECRET;

        // Instantiate a re-usable and thread-safe factory
        TwitterFactory twitterFactory = new TwitterFactory();

        // Instantiate a new Twitter instance
        Twitter twitter = twitterFactory.getInstance();

        // setup OAuth Consumer Credentials
        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        // setup OAuth Access Token
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

        // Instantiate and initialize a new twitter status update
        StatusUpdate statusUpdate = new StatusUpdate(
                // TEXT TITLE message recommend LIMIT 140char - 160max
                element.title + " " + element.url
        );
        //IMG media
        statusUpdate.setMedia("img", imgStream);

        Status status = twitter.updateStatus(statusUpdate);

        String omicron = element.title+ " " + element.url + element.img;
        outputTextArea.append("\n ( tweet successful )");
        outputTextArea.append("\n ( Total chars: " + omicron.length() + " ) ( Priority : " + element.priority + " )");
        outputTextArea.append("\n ( Title length: " + element.title + " )");
        outputTextArea.append("\n ( URL length: " + element.url + " )");
        outputTextArea.append("\n ( IMG Length: " + element.img + " )");
    }
    public UpdateStatus(internalComparison element, JTextArea outputTextArea, Boolean bool) throws IOException, TwitterException {

        outputTextArea.append("\n                  Sending tweet... ");

        //convert String to Stream
        URL url = new URL(element.img);
        URLConnection urlConnection = url.openConnection();
        InputStream imgStream = new BufferedInputStream(urlConnection.getInputStream());


        String consumerKey = TWITTER_CONSUMER_KEY;
        String consumerSecret = TWITTER_SECRET_KEY;
        String accessToken = TWITTER_ACCESS_TOKEN;
        String accessTokenSecret = TWITTER_ACCESS_TOKEN_SECRET;

        //Instantiate a re-usable and thread-safe factory
        TwitterFactory twitterFactory = new TwitterFactory();

        //Instantiate a new Twitter instance
        Twitter twitter = twitterFactory.getInstance();

        //setup OAuth Consumer Credentials
        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        //setup OAuth Access Token
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

        //Instantiate and initialize a new twitter status update
        StatusUpdate statusUpdate = new StatusUpdate(
                // TEXT TITLE message recommend LIMIT 140char - 160max
                element.title.substring(0, (int) (element.title.length() * 0.7)) + " " + element.url
        );
        //IMG media
        statusUpdate.setMedia("img", imgStream);

        Status status = twitter.updateStatus(statusUpdate);

        String omicron = element.title.substring(0, (int) (element.title.length() * 0.7)) + " " + element.url + element.img;
        outputTextArea.append("\n ( tweet successful )");
        outputTextArea.append("\n ( Total chars: " + omicron.length() + " ) ( Priority : " + element.priority + " )");
        outputTextArea.append("\n ( Title length: " + element.title.substring(0, (int) (element.title.length() * 0.7)) + " )");
        outputTextArea.append("\n ( URL length: " + element.url + " )");
        outputTextArea.append("\n ( IMG Length: " + element.img + " )");
    }
    public static void tweet(internalComparison beta) throws IOException, TwitterException {

        System.out.println("\n( Sending tweet )");

            //convert String to Stream
            URL url = new URL(beta.img);
            URLConnection urlConnection = url.openConnection();
            InputStream imgStream = new BufferedInputStream(urlConnection.getInputStream());

        Hash hash = new Hash();

        String consumerKey = hash.getTWITTER_CONSUMER_KEY();
        String consumerSecret = hash.getTWITTER_SECRET_KEY();
        String accessToken = hash.getTWITTER_ACCESS_TOKEN();
        String accessTokenSecret = hash.getTWITTER_ACCESS_TOKEN_SECRET();

            // Instantiate a re-usable and thread-safe factory
            TwitterFactory twitterFactory = new TwitterFactory();

            // Instantiate a new Twitter instance
            Twitter twitter = twitterFactory.getInstance();

            // setup OAuth Consumer Credentials
            twitter.setOAuthConsumer(consumerKey, consumerSecret);

            // setup OAuth Access Token
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

            // Instantiate and initialize a new twitter status update
            StatusUpdate statusUpdate = new StatusUpdate(
                    // TEXT TITLE message recommend LIMIT 140char - 160max
                    beta.title + " " + beta.url
            );
            //IMG media
            statusUpdate.setMedia("img", imgStream);

            Status status = twitter.updateStatus(statusUpdate);

        String omicron = beta.title+ " " + beta.url + beta.img;
        System.out.println("( tweet )   ^____^\"*");
        System.out.println("Follow me @MeetisSpeaks");
/*        System.out.println("( Total chars: " + omicron.length() + " ) ( Priority : " + beta.priority + " )");
        System.out.println("( Title length: " + beta.title + " )");
        System.out.println("( URL length: " + beta.url + " )");
        System.out.println("( IMG Length: " + beta.img + " )\n");*/

    }
}