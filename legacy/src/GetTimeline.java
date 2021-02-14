
/*   Luke Holman
*    https://twitter.com/luke_holman57
*
* */
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetTimeline {

    private static final String TWITTER_CONSUMER_KEY = "0uoimhtIBHrSHXK1Sw3SCsspX";
    private static final String TWITTER_SECRET_KEY = "4jRwUdYVamOzdr8CHvX6bPnPcmJjUi8zl98j4GpEX5gsAxZd9I";
    private static final String TWITTER_ACCESS_TOKEN = "2371825659-whASvMYl7wRC3pvEw1ZwyvhzUYv6OhPFrSyDr2G";
    private static final String TWITTER_ACCESS_TOKEN_SECRET = "R01lKmGlvKziQYnidstXV2gGNspCv2wby9ipLwsxaj9dj";

    public static List<String> timeline() throws IOException, TwitterException {

        System.out.println("( Getting Timeline )");

        String consumerKey = TWITTER_CONSUMER_KEY;
        String consumerSecret = TWITTER_SECRET_KEY;
        String accessToken = TWITTER_ACCESS_TOKEN;
        String accessTokenSecret = TWITTER_ACCESS_TOKEN_SECRET;

        //  Instantiate a re-usable and thread-safe factory
        TwitterFactory twitterFactory = new TwitterFactory();

        //  Instantiate a new Twitter instance
        Twitter twitter = twitterFactory.getInstance();

        //  Setup OAuth Consumer Credentials
        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        //  Setup OAuth Access Token
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

        //  Status Array
        List<Status> statuses;

        //  String Array
        List<String> timeline = new ArrayList<String>();
        
        try {
            String user;
            user = twitter.verifyCredentials().getScreenName();
            System.out.println("( Current Timeline )( @" + user + ")");

            Paging page = new Paging (1, 25);
            statuses = twitter.getUserTimeline("luke_holman57", page);

            for (Status status : statuses) {
                if(status != null) {
                    timeline.add(status.getText());
                    System.out.println("( " + status.getText() + ") ");
                }
                else{
                    System.out.println("( Status returned Null )");
                }
            }
            page.setPage(2);
            statuses = twitter.getUserTimeline("luke_holman57", page);
            for (Status status : statuses) {
                if(status != null) {
                    timeline.add(status.getText());
                    System.out.println("( " + status.getText() + ") ");
                }
                else{
                    System.out.println("( Status returned Null )");
                }
            }


        } catch (TwitterException e) {
            System.out.println("( TwitterException )( Failed to load timeline )");
            return timeline;
        }
        return timeline;
    }

}
