
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Jupiter on 1/2/2016.
 *
 *                                      MEETIS   /\Oo/\\ *
 */
public class GetTwitterTrends {

    private ArrayList<String> twitterTrends = new ArrayList<>();
    private static Hash hash = new Hash();
    private static String TWITTER_CONSUMER_KEY = hash.getTWITTER_CONSUMER_KEY();
    private static String TWITTER_SECRET_KEY = hash.getTWITTER_SECRET_KEY();
    private static String TWITTER_ACCESS_TOKEN = hash.getTWITTER_ACCESS_TOKEN();
    private static String TWITTER_ACCESS_TOKEN_SECRET = hash.getTWITTER_ACCESS_TOKEN_SECRET();
    private Timer timer0, timer1, timer2, timer3, timer4, timer5, timer6, timer7, timer8, timer9, timer10;

    public GetTwitterTrends(JTextArea textArea) {
        timer0 = new Timer();
        timer0.schedule(new initTimline(textArea), 1000);
        timer1 = new Timer();
        timer1.schedule(new displayKeys(textArea), 3000);
        timer2 = new Timer();
        timer2.schedule(new instantiateTwitterFactory(textArea), 5000);
        timer3 = new Timer();
        timer3.schedule(new getWorldTrends(textArea), 7000);
        timer4 = new Timer();
        timer4.schedule(new getLondonTrends(textArea), 9000);
        timer5 = new Timer();
        timer5.schedule(new getWarsawTrends(textArea), 11000);
        timer6 = new Timer();
        timer6.schedule(new getTelAvivTrends(textArea), 13000);
        timer7 = new Timer();
        timer7.schedule(new getMumbaiTrends(textArea), 15000);
        timer8 = new Timer();
        timer8.schedule(new getManilaTrends(textArea), 17000);
        timer9 = new Timer();
        timer9.schedule(new getDfwTrends(textArea), 19000);
        timer10 = new Timer();
        timer10.schedule(new getMunichTrends(textArea), 21000);
    }

    class initTimline extends TimerTask {
        JTextArea textArea;
        initTimline(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run() {
            textArea.append("\n Initializing Twitter Search...");
            timer0.cancel();
        }
    }
    class displayKeys extends TimerTask {
        JTextArea textArea;
        displayKeys(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run() {
            textArea.append("\nString consumerKey = " + TWITTER_CONSUMER_KEY);
            textArea.append("\nString consumerSecret = " + TWITTER_SECRET_KEY);
            textArea.append("\nString accessToken = " + TWITTER_ACCESS_TOKEN);
            textArea.append("\nString accessTokenSecret = " + TWITTER_ACCESS_TOKEN_SECRET);
            timer1.cancel();
        }
    }
    class instantiateTwitterFactory extends TimerTask {
        JTextArea textArea;
        instantiateTwitterFactory(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run() {
            textArea.append("\nTwitterFactory twitterFactory = new TwitterFactory();");
            textArea.append("\nTwitter twitter = twitterFactory.getInstance();");
            textArea.append("\ntwitter.setOAuthConsumer(consumerKey, consumerSecret);");
            textArea.append("\ntwitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));");
            timer2.cancel();
        }
    }
    class getWorldTrends extends TimerTask {
        JTextArea textArea;

        getWorldTrends(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run(){
            try {
                Trends worldTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                try {
                    worldTrends = twitter.getPlaceTrends(1);
                } catch (TwitterException e1) {
                    textArea.append("\n ( TwitterException )");
                }
                textArea.append("\n   ( World Trends )   ");
                for (int i = 0; i < worldTrends.getTrends().length; i++) {
                    twitterTrends.add(worldTrends.getTrends()[i].getName());
                    textArea.append(" " + worldTrends.getTrends()[i].getName());
                }
            } catch (Exception e) {
                textArea.append(" ( Exception ) ");
            }

            timer3.cancel();
        }
    }
    class getLondonTrends extends TimerTask {
        JTextArea textArea;

        getLondonTrends(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run(){
            try {
                Trends londonTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                try {
                    londonTrends = twitter.getPlaceTrends(44418);
                } catch (TwitterException e1) {
                    textArea.append("\n ( TwitterException )");
                }
                textArea.append("\n   ( London )   ");
                for (int i = 0; i < londonTrends.getTrends().length; i++) {
                    twitterTrends.add(londonTrends.getTrends()[i].getName());
                    textArea.append(" " + londonTrends.getTrends()[i].getName());
                    }
            } catch (Exception e) {
                textArea.append(" ( Exception ) ");
            }
            timer4.cancel();
        }
    }
    class getWarsawTrends extends TimerTask {
        JTextArea textArea;

        getWarsawTrends(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run(){
            try {
                Trends warsawTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                try {
                    warsawTrends = twitter.getPlaceTrends(523920);
                } catch (TwitterException e1) {
                    textArea.append("\n ( TwitterException )");
                }
                textArea.append("\n   ( Warsaw )   ");
                for (int i = 0; i < warsawTrends.getTrends().length; i++) {
                    twitterTrends.add(warsawTrends.getTrends()[i].getName());
                    textArea.append(" " + warsawTrends.getTrends()[i].getName());
                }
            } catch (Exception e) {
                textArea.append(" ( Exception ) ");
            }
            timer5.cancel();
        }
    }
    class getTelAvivTrends extends TimerTask {
        JTextArea textArea;

        getTelAvivTrends(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run(){
            try {
                Trends telAvivTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                try {
                    telAvivTrends = twitter.getPlaceTrends(1968212);
                } catch (TwitterException e1) {
                    textArea.append("\n ( TwitterException )");
                }
                textArea.append("\n   ( Tel Aviv )   ");
                for (int i = 0; i < telAvivTrends.getTrends().length; i++) {
                    twitterTrends.add(telAvivTrends.getTrends()[i].getName());
                    textArea.append(" " + telAvivTrends.getTrends()[i].getName());
                }
            } catch (Exception e) {
                textArea.append(" ( Exception ) ");
            }
            timer6.cancel();
        }
    }
    class getMumbaiTrends extends TimerTask {
        JTextArea textArea;

        getMumbaiTrends(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run(){
            try {
                Trends mumbaiTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                try {
                    mumbaiTrends = twitter.getPlaceTrends(2295411);
                } catch (TwitterException e1) {
                    textArea.append("\n ( TwitterException )");
                }
                textArea.append("\n   ( Mumbai )   ");
                for (int i = 0; i < mumbaiTrends.getTrends().length; i++) {
                    twitterTrends.add(mumbaiTrends.getTrends()[i].getName());
                    textArea.append(" " + mumbaiTrends.getTrends()[i].getName());
                }
            } catch (Exception e) {
                textArea.append(" ( Exception ) ");
            }
            timer7.cancel();
        }
    }
    class getManilaTrends extends TimerTask {
        JTextArea textArea;

        getManilaTrends(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run(){
            try {
                Trends manilaTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                try {
                    manilaTrends = twitter.getPlaceTrends(1199477);
                } catch (TwitterException e1) {
                    textArea.append("\n ( TwitterException )");
                }
                textArea.append("\n   ( Manila )   ");
                for (int i = 0; i < manilaTrends.getTrends().length; i++) {
                    twitterTrends.add(manilaTrends.getTrends()[i].getName());
                    textArea.append(" " + manilaTrends.getTrends()[i].getName());
                }
            } catch (Exception e) {
                textArea.append(" ( Exception ) ");
            }
            timer8.cancel();
        }
    }
    class getDfwTrends extends TimerTask {
        JTextArea textArea;

        getDfwTrends(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run(){
            try {
                Trends dfwTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                try {
                    dfwTrends = twitter.getPlaceTrends(2388929);
                } catch (TwitterException e1) {
                    textArea.append("\n ( TwitterException )");
                }
                textArea.append("\n   ( DFW )   ");
                for (int i = 0; i < dfwTrends.getTrends().length; i++) {
                    twitterTrends.add(dfwTrends.getTrends()[i].getName());
                    textArea.append(" " + dfwTrends.getTrends()[i].getName());
                }
            } catch (Exception e) {
                textArea.append(" ( Exception ) ");
            }
            timer9.cancel();
        }
    }
    class getMunichTrends extends TimerTask {
        JTextArea textArea;

        getMunichTrends(JTextArea textArea){
            this.textArea = textArea;
        }
        public void run(){
            try {
                Trends munichTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                try {
                    munichTrends = twitter.getPlaceTrends(676757);
                } catch (TwitterException e1) {
                    textArea.append("\n ( TwitterException )");
                }
                textArea.append("\n   ( Munich )   ");
                for (int i = 0; i < munichTrends.getTrends().length; i++) {
                    twitterTrends.add(munichTrends.getTrends()[i].getName());
                    textArea.append(" " + munichTrends.getTrends()[i].getName());
                }
            } catch (Exception e) {
                textArea.append(" ( Exception ) ");
            }
            trimHashTags();
            timer10.cancel();
        }
    }
    private static String trimHashtag(String hashedString){
        String tTemp = hashedString;
        if (hashedString.contains("#")){
            int position = hashedString.indexOf("#");
            StringBuilder sTemp = new StringBuilder(hashedString);
            sTemp.deleteCharAt(position);
            tTemp = sTemp.toString();
        }
        return tTemp;
    }
    public void trimHashTags(){
        for (int i = 0; i < twitterTrends.size(); i++) {
            twitterTrends.set(i, trimHashtag(twitterTrends.get(i)));
        }

    }
    public ArrayList<String> getTrends(){
        return twitterTrends;
    }
    public void dumpTrends(){
        twitterTrends.clear();
    }
}