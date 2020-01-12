import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MeetisTwitter {
    public static class cls {
        public cls() {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    static String TWITTER_CONSUMER_KEY = "0uoimhtIBHrSHXK1Sw3SCsspX";
    static String TWITTER_SECRET_KEY = "4jRwUdYVamOzdr8CHvX6bPnPcmJjUi8zl98j4GpEX5gsAxZd9I";
    static String TWITTER_ACCESS_TOKEN = "2371825659-whASvMYl7wRC3pvEw1ZwyvhzUYv6OhPFrSyDr2G";
    static String TWITTER_ACCESS_TOKEN_SECRET = "R01lKmGlvKziQYnidstXV2gGNspCv2wby9ipLwsxaj9dj";
    static ArrayList<String> twitterTrends = new ArrayList<>();
    static ArrayList<String> keywords = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        Console console = System.console();
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        new cls();
        URL url = null;
        try {
            url = new URL("http://www.lukeholman.net/meetis/keywords.txt");
        } catch (MalformedURLException e) {
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String everything = "";
        String concatenate;
        while ((concatenate = in.readLine()) != null) {
            everything += concatenate;
        }
        in.close();
        url = new URL("http://www.lukeholman.net/meetis/keywordPhrase.txt");
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        while ((concatenate = in.readLine()) != null) {
            everything += concatenate;
        }
        in.close();
        String[] Keywords = everything.split("\", \"");
        keywords.removeAll(keywords);
        for (String element : Keywords) {
            keywords.add(element);
            StringBuilder sb = new StringBuilder();
            Random rand = new Random();
            for (int i = 0; i < rand.nextInt(20); i++) {
                sb.append(" ");
            }
            System.out.println(sb.toString() + "( " + element + " )");
            TimeUnit.MILLISECONDS.sleep(3);
        }
        new cls();
        System.out.println("\n            Initializing Twitter Search...");
        justVerbalize("Initializing Twitter Search", voice);
        waitRandom();
        System.out.println("String consumerKey = " + "TWITTER_CONSUMER_KEY");
        System.out.println("String consumerSecret = " + "TWITTER_SECRET_KEY");
        System.out.println("String accessToken = " + "TWITTER_ACCESS_TOKEN");
        System.out.println("String accessTokenSecret = " + "TWITTER_ACCESS_TOKEN_SECRET");
        System.out.println("TwitterFactory twitterFactory = new TwitterFactory();");
        System.out.println("Twitter twitter = twitterFactory.getInstance();");
        System.out.println("twitter.setOAuthConsumer(consumerKey, consumerSecret);");
        System.out.println("twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));");
        new cls();
        verbalize(Meetis_Personality.HelloTwitter(), voice);
        Trends worldTrends = null;
        TwitterFactory twitterFactory = new TwitterFactory();
        Twitter twitter = twitterFactory.getInstance();
        generateAutho(twitter);
        try {
            worldTrends = twitter.getPlaceTrends(1);
        } catch (TwitterException e1) {
            System.out.println(" ( TwitterException ) World Trends");
        }
        new cls();
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(20); i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + "( World Trends )");
        justVerbalize("I wonder what's trending around the world right now?", voice);
        grabTweets(worldTrends, voice);
        Trends londonTrends = null;
        twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        generateAutho(twitter);
        try {
            londonTrends = twitter.getPlaceTrends(44418);
            GoogleEarth("London, England");
        } catch (TwitterException e1) {
            System.out.println(" ( TwitterException ) London Trends");
        }
        new cls();
        sb.setLength(0);
        for (int i = 0; i < rand.nextInt(20); i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + "( London )");
        justVerbalize("London, England", voice);
        grabTweets(londonTrends, voice);
        Trends warsawTrends = null;
        twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        generateAutho(twitter);
        try {
            warsawTrends = twitter.getPlaceTrends(523920);
            GoogleEarth("Warsaw, Poland");
        } catch (TwitterException e1) {
            System.out.println(" ( TwitterException ) Warsaw Trends");
        }
        new cls();
        sb.setLength(0);
        for (int i = 0; i < rand.nextInt(20); i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + "( Warsaw )");
        justVerbalize("Warsaw, Poland", voice);
        grabTweets(warsawTrends, voice);
        Trends telAvivTrends = null;
        twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        generateAutho(twitter);
        try {
            telAvivTrends = twitter.getPlaceTrends(1968212);
            GoogleEarth("Tel Aviv, Israel");
        } catch (TwitterException e1) {
            System.out.println(" ( TwitterException ) Tel Aviv");
        }
        new cls();
        sb.setLength(0);
        for (int i = 0; i < rand.nextInt(20); i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + "( Tel Aviv )");
        justVerbalize("Tel Aviv, Israel", voice);
        grabTweets(telAvivTrends, voice);
        Trends mumbaiTrends = null;
        twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        generateAutho(twitter);
        try {
            mumbaiTrends = twitter.getPlaceTrends(2295411);
            GoogleEarth("Mumbai, India");
        } catch (TwitterException e1) {
            System.out.println(" ( TwitterException ) Mumbai");
        }
        new cls();
        sb.setLength(0);
        for (int i = 0; i < rand.nextInt(20); i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + "( Mumbai )");
        justVerbalize("Mumbai, India", voice);
        grabTweets(mumbaiTrends, voice);
        Trends manilaTrends = null;
        twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        generateAutho(twitter);
        try {
            manilaTrends = twitter.getPlaceTrends(1199477);
            GoogleEarth("Manila, Philippines");
        } catch (TwitterException e1) {
            System.out.println(" ( TwitterException ) Manila");
        }
        new cls();
        sb.setLength(0);
        for (int i = 0; i < rand.nextInt(20); i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + "( Manila )");
        justVerbalize("Manila, Philippines", voice);
        grabTweets(manilaTrends, voice);
        Trends dfwTrends = null;
        twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        generateAutho(twitter);
        try {
            dfwTrends = twitter.getPlaceTrends(2388929);
            GoogleEarth("Dallas, Texas");
        } catch (TwitterException e1) {
            System.out.println(" ( TwitterException ) DFW");
        }
        new cls();
        sb.setLength(0);
        for (int i = 0; i < rand.nextInt(20); i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + "( DFW )");
        justVerbalize("Dallas, Texas", voice);
        grabTweets(dfwTrends, voice);
        Trends munichTrends = null;
        twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        generateAutho(twitter);
        try {
            munichTrends = twitter.getPlaceTrends(676757);
            GoogleEarth("Munich, Germany");
        } catch (TwitterException e1) {
            System.out.println(" ( TwitterException ) Munich");
        }
        new cls();
        sb.setLength(0);
        for (int i = 0; i < rand.nextInt(20); i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + "( Munich )");
        justVerbalize("Munich, Germany", voice);
        grabTweets(munichTrends, voice);
        ExitTwitter(voice);
    }
    private static void verbalize(String phrase, Voice voice){
        System.out.println(phrase);
        try {
            PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
            writer.println("Talking");
            writer.close();
        } catch (Exception ignore) {
        }
        voice.speak("    " + phrase);
        try {
            PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
            writer.println("Not Talking");
            writer.close();
        } catch (Exception ignore) {
        }
    }
    private static void justVerbalize(String phrase, Voice voice){
        try {
            TimeUnit.MILLISECONDS.sleep(400);
            PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
            writer.println("Talking");
            writer.close();
        } catch (Exception ignore) {
        }
        voice.speak(phrase);
        try {
            PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
            writer.println("Not Talking");
            writer.close();
        } catch (Exception ignore) {
        }
    }
    private static void grabTweets(Trends twitterLocation, Voice voice) {
        for (int i = 0; i < twitterLocation.getTrends().length && i < 20; i++) {
            twitterTrends.add(trimHashtag(twitterLocation.getTrends()[i].getName()));
            if (!checkKeywords(twitterLocation.getTrends()[i].getName(), voice)) {
                System.out.println(twitterLocation.getTrends()[i].getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception ignore) {
                }
            }
            Random rand = new Random();
            if (rand.nextInt(2) == 1) {
                justVerbalize(twitterLocation.getTrends()[i].getName(), voice);
            }
        }
    }
    private static void GoogleEarth(String location) {
        try {
            PrintWriter writer = new PrintWriter("Google_Earth_Position.txt");
            writer.println(location);
            writer.close();
        } catch (Exception ignore) {
        }
    }
    private static void generateAutho(Twitter twitter) {
        twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
        twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
    }
    private static boolean checkKeywords(String name, Voice voice) {
        for (int j = 0; j < keywords.size(); j++) {
            if (name.toString()
                    .toLowerCase()
                    .contains(keywords.get(j)
                            .toString()
                            .toLowerCase())) {
                String speakMe = Meetis_Personality.randomIntrest();
                System.out.println(name +"         " + speakMe);
                try {
                    PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
                    writer.println("Talking");
                    writer.close();
                } catch (Exception ignore) {
                }
                voice.speak(name);
                voice.speak(speakMe);
                try {
                    PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
                    writer.println("Not Talking");
                    writer.close();
                } catch (Exception ignore) {
                }
                return true;
            }
        }
        return false;
    }
    private static void waitRandomShort() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(50);
        for (int i = 0; i < 40; i++) {
            TimeUnit.MILLISECONDS.sleep(random);
        }
    }
    private static void waitRandom() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(100);
        for (int i = 0; i < 40; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
        }
        System.out.printf("\n");
    }
    private static void waitRandomLong() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(200);
        random += 200;
        for (int i = 0; i < 40; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
        }
        System.out.printf("\n");
    }
    private static String trimHashtag(String hashedString) {
        String tTemp = hashedString;
        int x;
        if (hashedString.contains("#")) {
            int position = hashedString.indexOf("#");
            StringBuilder sTemp = new StringBuilder(hashedString);
            int y;
            sTemp.deleteCharAt(position);
            tTemp = sTemp.toString();
        }
        return tTemp;
    }
    private static void ExitTwitter(Voice voice) {
        try {
            PrintWriter writer = new PrintWriter("MeetisStatus.txt", "UTF-8");
            writer.println("Ready");
            writer.close();
            voice.speak("Status Updated");
        } catch (IOException e) {
            try {
                waitRandomShort();
            } catch (InterruptedException e1) {
            }
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("MeetisStatus.txt", "UTF-8");
                writer.println("Ready");
            } catch (FileNotFoundException e1) {
                voice.speak("ERROR FATAL ERROR WARNING ALERT");
            } catch (UnsupportedEncodingException e1) {
                voice.speak("uhhhhh ohhhhh");
                voice.speak("oh crapppshhhhi");
            }
            writer.close();
        }
        try {
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
        } catch (Exception e) {
        }
        System.exit(0);
    }
}
