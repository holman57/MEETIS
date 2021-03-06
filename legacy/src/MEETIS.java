import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by Jupiter on 1/1/2016.
 *
 *                                          MEETIS   /\Oo/\\ *
 */
public class MEETIS extends JFrame {

    static Hash hash = new Hash();

    static String TWITTER_CONSUMER_KEY = hash.getTWITTER_CONSUMER_KEY();
    static String TWITTER_SECRET_KEY = hash.getTWITTER_SECRET_KEY();
    static String TWITTER_ACCESS_TOKEN = hash.getTWITTER_ACCESS_TOKEN();
    static String TWITTER_ACCESS_TOKEN_SECRET = hash.getTWITTER_ACCESS_TOKEN_SECRET();

    static ArrayList<String> keywords = new ArrayList<>();
    static ArrayList<String> keywordPhrase = new ArrayList<>();
    static ArrayList<String> avoidURL = new ArrayList<>();
    static ArrayList<String> bounceIMG = new ArrayList<>();
    static ArrayList<String> buildPoints = new ArrayList<>();
    static ArrayList<String> alphaURL = new ArrayList<>();
    static ArrayList<String> twitterTrends = new ArrayList<>();

    static ArrayList<internalComparison> omegaURL = new ArrayList<>();

    static boolean keywordsExists = false;
    static boolean bounceIMGExists = false;
    static boolean avoidURLExists = false;
    static boolean buildPointsExist = false;
    static boolean ready = false;

    static int alphaPosition = 0;
    static int omegaPosition = 0;
    static int cycle = 0;
    static int positiveSQL = 0;
    static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static int windowWidth = gd.getDisplayMode().getWidth();
    static int windowHeight = gd.getDisplayMode().getHeight();
    static int screenSizeAverage = ((windowHeight + windowWidth) / 60);
    static Font listFont = new Font("Arial", Font.BOLD, (screenSizeAverage) * 3 / 4);
    static Font LargeFont = new Font("Arial", Font.BOLD, (screenSizeAverage * 9 / 4));
    static long startTime = System.currentTimeMillis();

    public static void main(String[] args) {


        try {
            PrintWriter writer = new PrintWriter("MEETIS_RUNTIME.txt");
            writer.println(ManagementFactory.getRuntimeMXBean().getName());
            System.out.println(ManagementFactory.getRuntimeMXBean().getName());
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        MEETIS();




    }

    public static void MEETIS() {

        try {
            Robot r = new Robot();
            r.mouseMove(358, 441);
            sleep(100);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            sleep(100);
            r.keyPress(KeyEvent.VK_ALT);
            r.keyPress(KeyEvent.VK_A);
            r.keyRelease(KeyEvent.VK_ALT);
            r.keyRelease(KeyEvent.VK_A);
        } catch (Exception ignore) {
        }

        JFrame frame = new JFrame("MEETIS");

        JTextArea centralComDisplayMessage = new JTextArea();
        JTextArea centralComMasterTextArea = new JTextArea();

        centralComDisplayMessage.setBackground(Color.BLACK);
        centralComDisplayMessage.setForeground(Color.white);
        centralComDisplayMessage.setFont(listFont);
        centralComDisplayMessage.setLineWrap(true);
        centralComDisplayMessage.setWrapStyleWord(true);

        DefaultCaret caret = (DefaultCaret) centralComDisplayMessage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        centralComMasterTextArea.setBackground(Color.BLACK);
        centralComMasterTextArea.setForeground(Color.white);
        centralComMasterTextArea.setFont(listFont);

        frame.setLayout(new GridLayout(0, 1));
        frame.setUndecorated(true);
        frame.setBackground(Color.black);
        frame.setForeground(Color.white);
        frame.setPreferredSize(new Dimension(windowWidth, windowHeight + (windowHeight / 2)));

        JScrollPane jpane = new JScrollPane(centralComDisplayMessage);
        jpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JScrollPane masterScroll = new JScrollPane(centralComMasterTextArea);
        masterScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        try {
            sleep(4000);
        } catch (InterruptedException e) {
        }

        frame.add(masterScroll);
        frame.add(jpane);
        frame.pack();
        frame.setVisible(true);
        Thread uploadthread = new Thread() {
            public void run() {
                do {
                    Random rand = new Random();
                    int sleepDuration = 1 + rand.nextInt(10000);
                    if (sleepDuration % 2 == 0) centralComDisplayMessage.setText("");
                    try {
                        sleep(sleepDuration);
                        Random rand1 = new Random();
                        int attempt = rand1.nextInt(omegaURL.size());
                        if (omegaURL.get(attempt) != null) {
                            SQLquery.exUpdate(omegaURL.get(attempt), centralComDisplayMessage);
                            positiveSQL++;
                        //    System.out.println(" SQL stmts: " + positiveSQL);
                            centralComDisplayMessage.append("\nSuccessful SQL Stmt: " + positiveSQL + "\n");
                            if (rand.nextInt(50) == 2)
                                UpdateStatus.tweet(omegaURL.get(attempt));
                            try {
                                sleep(10);
                            } catch (InterruptedException ignore) {
                            }
                            omegaURL.remove(attempt);
                        }
                    } catch (Exception e) {
                        centralComDisplayMessage.append("\n\t(" + e.getMessage() + " )");
                    }
                } while (true);
            }
        };
        uploadthread.start();
/*        Thread kill = new Thread() {
            public void run() {
                do {
                    try {
                        sleep(60000);
                        restartApplication();
                    } catch (Exception e) {
                    }
                } while (true);
            }
        };
        kill.start();*/

        do {
            ready = false;
            try {
                Trends worldTrends = null;
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                worldTrends = twitter.getPlaceTrends(1);
                System.out.println("\n   ( World Trends )   ");
                zZ(10);
                for (int i = 0; i < worldTrends.getTrends().length && i < 9; i++) {
                    twitterTrends.add(worldTrends.getTrends()[i].getName());
                    zZ(10);
                }
                Trends londonTrends = null;
                twitterFactory = new TwitterFactory();
                twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                londonTrends = twitter.getPlaceTrends(44418);
                System.out.println("\n   ( London )   ");
                zZ(10);
                for (int i = 0; i < londonTrends.getTrends().length && i < 9; i++) {
                    twitterTrends.add(londonTrends.getTrends()[i].getName());
                    System.out.print(" " + londonTrends.getTrends()[i].getName());
                    zZ(10);
                }
                Trends warsawTrends = null;
                twitterFactory = new TwitterFactory();
                twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                warsawTrends = twitter.getPlaceTrends(523920);
                System.out.println("\n   ( Warsaw )   ");
                zZ(10);
                for (int i = 0; i < warsawTrends.getTrends().length && i < 9; i++) {
                    twitterTrends.add(warsawTrends.getTrends()[i].getName());
                    System.out.print(" " + warsawTrends.getTrends()[i].getName());
                    zZ(10);
                }
                Trends telAvivTrends = null;
                twitterFactory = new TwitterFactory();
                twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                telAvivTrends = twitter.getPlaceTrends(1968212);
                System.out.println("\n   ( Tel Aviv )   ");
                zZ(10);
                for (int i = 0; i < telAvivTrends.getTrends().length && i < 9; i++) {
                    twitterTrends.add(telAvivTrends.getTrends()[i].getName());
                    System.out.print(" " + telAvivTrends.getTrends()[i].getName());
                    zZ(10);
                }
                Trends mumbaiTrends = null;
                twitterFactory = new TwitterFactory();
                twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                mumbaiTrends = twitter.getPlaceTrends(2295411);
                System.out.println("\n   ( Mumbai )   ");
                zZ(10);
                for (int i = 0; i < mumbaiTrends.getTrends().length && i < 9; i++) {
                    twitterTrends.add(mumbaiTrends.getTrends()[i].getName());
                    System.out.print(" " + mumbaiTrends.getTrends()[i].getName());
                    zZ(10);
                }
                Trends manilaTrends = null;
                twitterFactory = new TwitterFactory();
                twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                manilaTrends = twitter.getPlaceTrends(1199477);
                System.out.println("\n   ( Manila )   ");
                zZ(10);
                for (int i = 0; i < manilaTrends.getTrends().length && i < 9; i++) {
                    twitterTrends.add(manilaTrends.getTrends()[i].getName());
                    System.out.print(" " + manilaTrends.getTrends()[i].getName());
                    zZ(10);
                }
                Trends dfwTrends = null;
                twitterFactory = new TwitterFactory();
                twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                dfwTrends = twitter.getPlaceTrends(2388929);
                System.out.println("\n   ( DFW )   ");
                zZ(100);
                for (int i = 0; i < dfwTrends.getTrends().length && i < 9; i++) {
                    twitterTrends.add(dfwTrends.getTrends()[i].getName());
                    System.out.print(" " + dfwTrends.getTrends()[i].getName());
                    zZ(100);
                }
                Trends munichTrends = null;
                twitterFactory = new TwitterFactory();
                twitter = twitterFactory.getInstance();
                twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY);
                twitter.setOAuthAccessToken(new AccessToken(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET));
                munichTrends = twitter.getPlaceTrends(676757);
                System.out.println("\n   ( Munich )   ");
                zZ(10);
                for (int i = 0; i < munichTrends.getTrends().length && i < 9; i++) {
                    twitterTrends.add(munichTrends.getTrends()[i].getName());
                    System.out.print(" " + munichTrends.getTrends()[i].getName());
                    zZ(10);
                }
            } catch (Exception e) {
                System.out.println("\n\t(Twitter Exception)  ( " + e.getMessage() + " ) ");
            }
            try {
                centralComDisplayMessage.append("updating AvoidURL");
                URL url = new URL("http://www.lukeholman.net/meetis/avoidURL.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String everything = "";
                String concatenate;
                while ((concatenate = in.readLine()) != null) {
                    everything += concatenate;
                }
                in.close();
                String[] Keywords = everything.split("\", \"");
                avoidURL.removeAll(avoidURL);
                for (String element : Keywords) {
                    avoidURL.add(element);
                    centralComDisplayMessage.append(" ( " + element + " ) ");
                }
                if (!Keywords.equals(0) & !Keywords.equals(null)) {
                    centralComDisplayMessage.append("\n\n                                          ( AvoidURL Array Set* )\n\n");
                    avoidURLExists = true;
                }

            } catch (MalformedURLException e) {
                centralComDisplayMessage.append("      Something went wrong... bounceIMG do not exist");
                avoidURLExists = false;
            } catch (IOException e) {
                centralComDisplayMessage.append("    Something went wrong... bounceIMG do not exist");
                avoidURLExists = false;
            }
            try {
                centralComDisplayMessage.append("updating buildPoints");
                URL url = new URL("http://www.lukeholman.net/meetis/buildPoints.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String everything = "";
                String concatenate;
                while ((concatenate = in.readLine()) != null) {
                    everything += concatenate;
                }
                in.close();
                String[] Keywords = everything.split(", ");
                buildPoints.removeAll(buildPoints);

                for (String element : Keywords) {
                    buildPoints.add(element);
                    centralComDisplayMessage.append(" ( " + element + " ) ");
                }
                if (!Keywords.equals(0) & !Keywords.equals(null)) {
                    centralComDisplayMessage.append("\n\n                                  ( Build Points Up* )\n\n");
                    buildPointsExist = true;
                }
            } catch (MalformedURLException e) {
                centralComDisplayMessage.append("     Something went wrong... buildPoints do not exist");
                buildPointsExist = false;
            } catch (IOException e) {
                centralComDisplayMessage.append("      Something went wrong... buildPoints do not exist");
                buildPointsExist = false;
            }
            try {
                centralComDisplayMessage.append("updating Keywords");
                URL url = new URL("http://www.lukeholman.net/meetis/keywords.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String everything = "";
                String concatenate;
                while ((concatenate = in.readLine()) != null) {
                    everything += concatenate;
                }
                in.close();
                String[] Keywords = everything.split("\", \"");
                keywords.removeAll(keywords);
                for (String element : Keywords) {
                    keywords.add(element);
                    centralComDisplayMessage.append(" ( " + element + " ) ");
                }
                if (!Keywords.equals(0) & !Keywords.equals(null)) {
                    centralComDisplayMessage.append("\n\n                             ( Keyword Array Set* )\n\n");
                    keywordsExists = true;
                }
            } catch (MalformedURLException e) {
                centralComDisplayMessage.append("      Something went wrong... Keywords do not exist");
                keywordsExists = false;
            } catch (IOException e) {
                centralComDisplayMessage.append("        Something went wrong... Keywords do not exist");
                keywordsExists = false;
            }
            try {
                centralComDisplayMessage.append("updating Keyword Phrase");
                URL url = new URL("http://www.lukeholman.net/meetis/keywordPhrase.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String everything = "";
                String concatenate;
                while ((concatenate = in.readLine()) != null) {
                    everything += concatenate;
                }
                in.close();
                String[] Keywords = everything.split("\", \"");
                keywordPhrase.removeAll(keywordPhrase);
                for (String element : Keywords) {
                    keywordPhrase.add(element);
                    centralComDisplayMessage.append(" ( " + element + " ) ");
                }
                if (!keywordPhrase.equals(0) & !keywordPhrase.equals(null)) {
                    centralComDisplayMessage.append("\n\n                                                  ( Keyword Phrase Array Set* )\n\n");
                }
            } catch (MalformedURLException e) {
                centralComDisplayMessage.setText("      Keyword Phrase went bust  ( MalformedURLException ) ");
            } catch (IOException e) {
                centralComDisplayMessage.setText("      Keyword Phrase went bust   ( IOException ) ");
            }
            try {
                centralComDisplayMessage.append("updating bounceIMG");
                URL url = new URL("http://www.lukeholman.net/meetis/bounceIMG.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String everything = "";
                String concatenate;
                while ((concatenate = in.readLine()) != null) {
                    everything += concatenate;
                }
                in.close();
                String[] Keywords = everything.split("\", \"");
                bounceIMG.removeAll(bounceIMG);
                for (String element : Keywords) {
                    bounceIMG.add(element);
                    centralComDisplayMessage.append(" ( " + element + " ) ");
                }
                if (!Keywords.equals(0) & !Keywords.equals(null)) {
                    centralComDisplayMessage.append("                                ( bounceIMG Array Set* )\n\n");
                    bounceIMGExists = true;
                }
            } catch (MalformedURLException e) {
                centralComDisplayMessage.setText("         Something went wrong... bounceIMG do not exist");
                bounceIMGExists = false;
            } catch (IOException e) {
                centralComDisplayMessage.setText("     Something went wrong... bounceIMG do not exist");
                bounceIMGExists = false;
            }
            Random rand = new Random();
            centralComDisplayMessage.append("[ " + alphaPosition + " ] ");
            centralComDisplayMessage.append("   Alpha Position");
            alphaPosition = 0;
            centralComDisplayMessage.append("\n                          First Parse Attempt...                           <-- 1st\n");
            //  First Parse-------------------------------------------------
            for (String urlElement : buildPoints) {
                try {
                    Document doc = Jsoup.connect(urlElement).get();
                    Elements link = doc.getElementsByTag("link");
                    for (Element element : link) {
                        String src = element.text();
                        if (!(src == null | src.length() == 0)) {
                            centralComDisplayMessage.append("[ " + alphaPosition + " ] ");
                            centralComDisplayMessage.append("   " + src);
                            alphaPosition++;
                            alphaURL.add(src);
                            diveURLdeep(src, centralComDisplayMessage, centralComMasterTextArea);
                        }
                    }
                    Elements aTitle = doc.getElementsByTag("a.title");
                    for (Element element : aTitle) {
                        String src = element.text();
                        if (!(src == null | src.length() == 0)) {
                            centralComDisplayMessage.append("[ " + alphaPosition + " ] ");
                            centralComDisplayMessage.append("   " + src);
                            alphaPosition++;
                            alphaURL.add(src);
                            diveURLdeep(src, centralComDisplayMessage, centralComMasterTextArea);
                        }
                    }
                } catch (IOException ex) {
                    centralComDisplayMessage.append("   ( URL Exception )  ");
                }
            }
            centralComDisplayMessage.append("\n\n  ====================     Attempting a Second Parse...                           <-- 2nd\n");
            for (String urlElement : buildPoints) {
                try {
                    Document doc = Jsoup.connect(urlElement).get();
                    Elements a = doc.getElementsByTag("a");
                    for (Element element : a) {
                        String src = element.attr("abs:href");
                        boolean Continue = true;
                        for (int i = 0; i < avoidURL.size(); i++) {
                            if (src.contains(avoidURL.get(i)))
                                Continue = false;
                        }
                        if (Continue)
                            if (!(src == null | (src != null ? src.length() : 0) == 0)) {
                                centralComDisplayMessage.append("[ " + alphaPosition + " ] ");
                                centralComDisplayMessage.append("   " + src);
                                alphaPosition++;
                                alphaURL.add(src);
                                diveURLdeep(src, centralComDisplayMessage, centralComMasterTextArea);
                            }
                    }
                } catch (IOException ex) {
                    centralComDisplayMessage.append(" ( Second Parse )( Failure )* " + urlElement);
                }
            }


            for (int i = 0; i < alphaURL.size(); i++) {
                int counter = 0;
                for (int j = 0; j < alphaURL.get(i).length(); j++) {
                    if (alphaURL.get(i).charAt(j) == '/') {
                        counter++;
                    }
                }
                if (counter < 5 && counter > 1) {
                    centralComDisplayMessage.append("   ");
                    centralComDisplayMessage.append(" ( parseNode ) ");
                    centralComDisplayMessage.append("   " + alphaURL.get(i) + "  ");
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(alphaURL.get(i)).get();
                    } catch (Exception e) {
                        centralComDisplayMessage.append(" ( parseUnknown )( Exception )   ");
                        centralComDisplayMessage.append(e.getMessage());
                    }
                    if (doc != null) {
                        Elements link = doc.select("h1 a[href], h2 a[href], h3 a[href]");
                        for (int j = 0; j < 20 && j < link.size(); j++) {
                            Element el = link.get(j);
                            String src = el.attr("abs:href");
                            if (src.contains("http")) {
                                boolean redundancy = false;
                                for (int k = 0; k < alphaURL.size(); k++) {
                                    if (alphaURL.get(k).equals(src))
                                        redundancy = true;
                                }
                                if (!redundancy) {
                                    Boolean BadURL = false;
                                    for (int k = 0; k < avoidURL.size(); k++) {
                                        if (src.toLowerCase().contains(avoidURL.get(k).toLowerCase())) {
                                            BadURL = true;
                                        }
                                    }
                                    if (!BadURL) {
                                        alphaPosition++;
                                        alphaURL.add(src);
                                        centralComDisplayMessage.append("\n     " + src);
                                        diveURLdeep(src, centralComDisplayMessage, centralComMasterTextArea);
                                    }
                                }
                            }
                        }

                    }
                }
            }

            centralComDisplayMessage.append("\n                 alphaURL complete    \n");
            ready = true;
            cycle++;
            System.out.println("\n Completed Cycles: " + cycle);
            System.out.println("start time: " + startTime);

        } while (true);
    }

    private static void checkConnection() {
        while (!connectionOpen()) {
            try {
                System.out.println("\tConnection Timed Out...");
                sleep(100000);
            } catch (InterruptedException ignore) {
            }
            System.out.println("\t\t\t ( Trying Again )");
        }
    }

    private static void diveURLdeep(
            String element,
            JTextArea centralComDisplayMessage,
            JTextArea centralComMasterTextArea
    ) {
        Document doc = null;
        try {
            doc = Jsoup.connect(element).get();
        } catch (Exception e1) {
            centralComDisplayMessage.setText("(Failure)*    doc = Jsoup.connect(element).get();");
        }
        Elements link = null;
        try {
            link = doc.getElementsByTag("title");
        } catch (Exception e1) {
            centralComDisplayMessage.setText("(Failure)*   link = doc.getElementsByTag(\"title\");");
        }

        String src = null;
        if (doc != null && link != null)

            for (Element el : link) {
                if (el != null)
                    if (el.hasText())
                        if (el.text().length() < 100)
                            src = el.text();
                boolean breakMe = false;
                boolean die = false;
                boolean Continue = false;
                for (String keyword : keywords) {
                    if (src != null ? src.toLowerCase().contains(keyword) : false) {
                        Continue = true;
                    }
                }
                if (twitterTrends.size() != 0) {
                    for (String twitterTrend : twitterTrends) {
                        if (src != null ? src.toLowerCase().contains(twitterTrend) : false) {
                            Continue = true;
                        }
                    }
                }
                if (Continue)
                    if (!(src.length() == 0)) {
                        Elements images = doc.select("img");
                        if (images != null)
                            for (Element image : images) {
                                URL url;
                                if ((image.attr("src").contains("gif")) || (image.attr("src").contains("jpg")) || (image.attr("src").contains("png")))
                                    if (image.attr("src").contains("//"))
                                        if (!(image.attr("src").length() < 5))
                                            if (!(breakMe)) {
                                                try {
                                                    url = new URL(image.attr("src"));
                                                    Image img = new ImageIcon(url).getImage();
                                                    if (img.getWidth(null) < 300)
                                                        die = true;
                                                    if (img.getHeight(null) < 200)
                                                        die = true;
                                                } catch (MalformedURLException e) {
                                                    die = true;
                                                    centralComDisplayMessage.append(" ( Image Failure )* ( " + e.getMessage() + " )");
                                                }
                                                if (!die) {
                                                    if (bounceIMG != null)
                                                        for (int z = 0; z < bounceIMG.size(); z++) {
                                                            if (image.attr("src").contains(bounceIMG.get(z))) {
                                                                die = true;
                                                            }
                                                        }
                                                }
                                                if (!die) {

                                                    try {
                                                        Boolean internalRedundancy = false;
                                                        for (int i = 0; i < omegaURL.size(); i++) {
                                                            if ((
                                                                    trimHashtag(omegaURL.get(i).title).toLowerCase().substring(0, 10)
                                                                            .contains(
                                                                                    trimHashtag(src).toLowerCase().substring(0, 10)
                                                                            )
                                                            ) || (
                                                                    trimHashtag(omegaURL.get(i).title).toLowerCase().substring(10, 20)
                                                                            .contains(
                                                                                    trimHashtag(src).toLowerCase().substring(10, 20)
                                                                            )
                                                            ) || (
                                                                    trimHashtag(omegaURL.get(i).title).toLowerCase().substring(20, 30)
                                                                            .contains(
                                                                                    trimHashtag(src).toLowerCase().substring(20, 30)
                                                                            )
                                                            )
                                                                    ) {
                                                                internalRedundancy = true;
                                                            }
                                                        }
                                                        if (!internalRedundancy) {

                                                            Boolean trending = false;
                                                            for (int i = 0; i < twitterTrends.size() && !trending; i++) {
                                                                if (src.toLowerCase().contains(twitterTrends.get(i))) {
                                                                    int position = src.toLowerCase().indexOf(twitterTrends.get(i));
                                                                    while ((position > 0) && (!Objects.equals(Character.toString(src.charAt(position - 1)), " "))) {
                                                                        position--;
                                                                    }
                                                                    StringBuilder bTemp = new StringBuilder(src);
                                                                    bTemp.insert(position, '#');
                                                                    centralComDisplayMessage.append("   ( Twitter Trend )  ( " + bTemp + " ) ");
                                                                    src = bTemp.toString();
                                                                    trending = true;
                                                                }
                                                            }
                                                            Boolean compoundPhrase = false;
                                                            if (!trending)
                                                                for (int j = 0; j < keywordPhrase.size() && !compoundPhrase; j++) {
                                                                    if (src.toLowerCase().contains(keywordPhrase.get(j))) {
                                                                        compoundPhrase = true;
                                                                        int position = src.toLowerCase().indexOf(keywordPhrase.get(j));
                                                                        StringBuilder bTemp = new StringBuilder(src);
                                                                        compoundPhraseFormat(bTemp, position, keywordPhrase.get(j).length());
                                                                        centralComDisplayMessage.append("     ( Keyword Phrase )  ( " + bTemp + " ) ");
                                                                        src = bTemp.toString();
                                                                    }
                                                                }
                                                            Boolean keywordFound = false;
                                                            if (!compoundPhrase)
                                                                for (int i = 0; i < keywords.size() && !keywordFound; i++) {
                                                                    if (src.toLowerCase().contains(keywords.get(i))) {
                                                                        int position = src.toLowerCase().indexOf(keywords.get(i));
                                                                        while ((position > 0) && (!Objects.equals(Character.toString(src.charAt(position - 1)), " "))) {
                                                                            position--;
                                                                        }
                                                                        StringBuilder bTemp = new StringBuilder(src);
                                                                        bTemp.insert(position, '#');
                                                                        centralComDisplayMessage.append("   ( Keyword )  ( " + bTemp + " ) ");
                                                                        src = bTemp.toString();
                                                                        keywordFound = true;
                                                                    }
                                                                }

                                                            internalComparison mark = new internalComparison(centralComDisplayMessage);
                                                            mark.setTitle(src);
                                                            mark.setUrl(element);
                                                            mark.setImg(image.attr("src"));

                                                            try {
                                                                mark.increasePriority(prioritizer(mark, twitterTrends, keywords, centralComDisplayMessage));
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                                centralComDisplayMessage.append(" ( Image Failure )* ( " + e.getMessage() + " )");

                                                            }

                                                            Collections.sort(omegaURL);

                                                            Boolean redundant = false;
                                                            for (int i = 0; i < omegaURL.size(); i++) {
                                                                if (mark.title.equalsIgnoreCase(omegaURL.get(i).title)){
                                                                    redundant = true;
                                                                }
                                                            }
                                                            if (!redundant) {
                                                                centralComDisplayMessage.append("  --->  " + mark.titleToString() );
                                                                breakMe = true;
                                                                omegaURL.add(mark);
                                                                omegaPosition++;
                                                                centralComMasterTextArea.setText(" ");
                                                                for (internalComparison listOfMarks : omegaURL)
                                                                    centralComMasterTextArea.append("\n        " +
                                                                            "" + listOfMarks.titleToString());
                                                            }
                                                        }
                                                    } catch (Exception ignore) {
                                                    }

                                                }
                                                die = false;
                                            }
                            }
                    }
            }


    }

    private static void waitRandomShort(JProgressBar progressbar) {
        Random rand = new Random();
        int sleepDuration = 10000 + rand.nextInt(30000);
        for (int i = 0; i < sleepDuration; i++) {
            try {
                sleep(1);
                progressbar.setValue((int) ((((double) i) / sleepDuration) * 100));

            } catch (InterruptedException e1) {
            }
        }
    }

    static void removeBlankSpace(StringBuilder sb) {
        int j = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (!Character.isWhitespace(sb.charAt(i))) {
                sb.setCharAt(j++, sb.charAt(i));
            }
        }
        sb.delete(j, sb.length());
    }

    static void compoundPhraseFormat(StringBuilder sb, int startingPointPhrase, int phraseSize) {
        sb.insert(startingPointPhrase, '#');
        for (int i = startingPointPhrase; i < (startingPointPhrase + phraseSize); i++) {
            if (Character.isWhitespace(sb.charAt(i))) {
                sb.delete(i, (i + 1));
            }
        }
    }

    private static boolean connectionOpen() {
        try {
            cls();
            Process p = Runtime.getRuntime().exec("ping google.com");
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String s = "";
            // reading output stream of the command
            while ((s = inputStream.readLine()) != null) {
                System.out.println(s);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }


    static ArrayList<String> removeDuplicates(ArrayList<String> list) {

        // Store unique items in result.
        ArrayList<String> result = new ArrayList<>();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<>();

        // Loop over argument list.
        for (String item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }

    private static int prioritizer(

            internalComparison mark,
            ArrayList<String> trends,
            ArrayList<String> keywords,
            JTextArea prioritizerTextArea
    ) {
        boolean foundDate = false;
        int returnValue = 0;

        for (int k = 0; k < keywordArray.goodURL.length; k++) {
            if (mark.url.toLowerCase().contains(keywordArray.goodURL[k].toLowerCase())) {
                prioritizerTextArea.append(" ( Good URL + )( " + keywordArray.goodURL[k] + " ) ");
                returnValue += (132000 / (k + 1));
            }
        }

        if ((mark.url.toLowerCase().contains(getMonth().toLowerCase() + "/" + getDay())) ||
                (mark.url.toLowerCase().contains(getMonth().toLowerCase() + "/" + getZeroDay())) ||
                (mark.url.toLowerCase().contains(getYear() + "/" + getMonth())) ||
                (mark.url.toLowerCase().contains(getYear() + "/" + getNumericMonth())) ||
                (mark.url.toLowerCase().contains(getMonth().toLowerCase() + "-" + getDay())) ||
                (mark.url.toLowerCase().contains(getMonth().toLowerCase() + "-" + getZeroDay())) ||
                (mark.url.toLowerCase().contains(getYear() + "-" + getMonth())) ||
                (mark.url.toLowerCase().contains(getYear() + "-" + getNumericMonth()))) {
            returnValue += 5000;

            if ((mark.url.contains(getYear() + "/" + getNumericMonth() + "/" + getDay())) ||
                    (mark.url.contains(getYear() + "/" + getZeroNumericMonth() + "/" + getDay())) ||
                    (mark.url.contains(getYear() + "/" + getNumericMonth() + "/" + getZeroDay())) ||
                    (mark.url.contains(getYear() + "/" + getZeroNumericMonth() + "/" + getZeroDay()))) {
                returnValue += 52000;
                prioritizerTextArea.append(" ( " + getMonth().toUpperCase() + " " + getDay() + " )( +52000 )");
            }
        }

        Document doc = null;
        try {
            doc = Jsoup.connect(mark.url).get();
        } catch (Exception e) {
            prioritizerTextArea.setText(" (Prioritizer FAILURE)*  ( " + e.getMessage() + " )");
            prioritizerTextArea.append(" (Prioritizer   fuk  )*  ( " + e.getMessage() + " )");
            prioritizerTextArea.append(" (Prioritizer FAILURE)*  ( " + e.getMessage() + " )");
        }
        String[] bodyText;
        Elements links = doc != null ? doc.select("body") : null;
        if (doc != null)
            for (Element el : links) {
                String linkText = el.text();
                bodyText = linkText.split(" ");
                for (int i = 0; i < bodyText.length; i++) {
                    if (!foundDate) {
                        if (bodyText[i].toLowerCase().contains(getMonth())) {
                            for (int j = -1; j < 1; j++) {
                                if (((i + j) > 0) && ((i + j) < bodyText.length)) {
                                    if (bodyText[(i + j)].toLowerCase().contains(getDay())) {
                                        prioritizerTextArea.append(" ( " + getMonth().toUpperCase() + " " + getDay() + " ) ( + 52000 )");
                                        returnValue += 52000;
                                        foundDate = true;
                                    }
                                }
                            }
                        }
                    }
                    int a = keywords.size();
                    for (String keyword : keywords) {
                        a--;
                        if (keyword.equals(bodyText[i])) {
                            returnValue += a;
                            prioritizerTextArea.append(" ( " + keyword + " ) +" + returnValue + " ");
                        }
                    }
                    for (String trend : trends) {
                        if (trend.equals(bodyText[i])) {
                            prioritizerTextArea.append(" ( Twitter Trend ) ( " + trend + " ) ( +132000 ) ");
                            returnValue += 132000;
                        }
                    }
                }


            }
        return returnValue;
    }

    public static ArrayList<internalComparison> hashTag(
            ArrayList<internalComparison> list,
            JTextArea textArea,
            ArrayList<String> trends,
            ArrayList<String> keywords
    ) {
        boolean compoundPhrase = false;
        for (int k = 0; k < list.size(); k++) {
            boolean trending = false;
            String sTemp = list.get(k).getTitle();
            for (int i = 0; i < trends.size() && !trending; i++) {
                if (sTemp.toLowerCase().contains(trends.get(i))) {
                    int position = sTemp.toLowerCase().indexOf(trends.get(i));
                    while ((position > 0) && (!Objects.equals(Character.toString(sTemp.charAt(position - 1)), " "))) {
                        position--;
                    }
                    StringBuilder bTemp = new StringBuilder(sTemp);
                    bTemp.insert(position, '#');
                    textArea.append("\n   ( Twitter Trend )  ( " + bTemp + " ) ");
                    list.get(k).setTitle(bTemp.toString());
                    trending = true;
                }
            }
            if (!trending)
                for (int j = 0; j < keywordPhrase.size() && !compoundPhrase; j++) {
                    if (sTemp.toLowerCase().contains(keywordPhrase.get(j))) {
                        compoundPhrase = true;
                        int position = sTemp.toLowerCase().indexOf(keywordPhrase.get(j));
                        StringBuilder bTemp = new StringBuilder(sTemp);
                        compoundPhraseFormat(bTemp, position, keywordPhrase.get(j).length());
                        textArea.append("\n     ( Keyword Phrase )  ( " + bTemp + " ) ");
                        list.get(k).setTitle(bTemp.toString());
                    }
                }
            Boolean keywordFound = false;
            if (!compoundPhrase)
                for (int i = 0; i < keywords.size() && !keywordFound; i++) {
                    if (sTemp.toLowerCase().contains(keywords.get(i))) {
                        int position = sTemp.toLowerCase().indexOf(keywords.get(i));
                        while ((position > 0) && (!Objects.equals(Character.toString(sTemp.charAt(position - 1)), " "))) {
                            position--;
                        }
                        StringBuilder bTemp = new StringBuilder(sTemp);
                        bTemp.insert(position, '#');
                        textArea.append("\n   ( Keyword )  ( " + bTemp + " ) ");
                        list.get(k).setTitle(bTemp.toString());
                        keywordFound = true;
                    }
                }
            compoundPhrase = false;
        }
        return list;
    }

    private static List<String> trimHashtag(List<String> twitterTrends) {
        List<String> tTemp = new ArrayList<>();
        int y;
        for (int i = 0; i < twitterTrends.size(); i++) {
            if (twitterTrends.get(i).contains("#")) {
                int position = twitterTrends.get(i).indexOf("#");
                StringBuilder sTemp = new StringBuilder(twitterTrends.get(i));
                int x;
                sTemp.deleteCharAt(position);
                tTemp.add(sTemp.toString());
            } else {
                tTemp.add(twitterTrends.get(i));
            }
        }
        return tTemp;
    }

    public static String getMonth() {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        fmt = new Formatter();
        fmt.format("%tB", cal);
        return fmt.toString().toLowerCase();
    }

    public static String getNumericMonth() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return dayOfMonthStr;
    }

    public static String getZeroNumericMonth() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return ("0" + dayOfMonthStr);
    }

    public static String getDay() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return dayOfMonthStr;
    }

    public static String getZeroDay() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return ("0" + dayOfMonthStr);
    }

    public static String getYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String sYear = String.valueOf(year);
        return sYear;
    }

    public static void restartApplication() throws URISyntaxException, IOException {

        //      Runtime.getRuntime().exec("taskkill /f /im java.exe");

        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(MEETIS.class.getProtectionDomain().getCodeSource().getLocation().toURI());

  /* is it a jar file? */
        if (!currentJar.getName().endsWith(".jar"))
            return;

  /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();

        System.exit(0);
        //      Runtime.getRuntime().exec("taskkill /f /im cmd.exe");

    }

    public void timeLineRedundancy(JTextArea centralComDisplayMessage) {
        try {//  ----------------------------------  Back-check against twitter timeline (GetTimeline.java) ----->
            List<String> rawTimeline = trimHashtag(GetTimeline.timeline());
            centralComDisplayMessage.append("\n          Cross match omegaURL against timeline \n");
            if (rawTimeline != null) {
                for (int k = 0; k < rawTimeline.size(); k++) {
                    centralComDisplayMessage.append("\n    " + rawTimeline.get(k));
                }

                for (int i = 0; i < omegaURL.size(); i++) {
                    for (String aRawTimeline : rawTimeline) {

                        if (trimHashtag(omegaURL.get(i).title).toLowerCase().substring(0, 10)
                                .contains(
                                        trimHashtag(aRawTimeline).toLowerCase().substring(0, 10))) {

                            centralComDisplayMessage.append("\n    ->   Found Something (*) " + trimHashtag(omegaURL.get(i).title).toLowerCase().substring(0, 10));
                            centralComDisplayMessage.append("\n     ->       (raw timeline) " + trimHashtag(aRawTimeline).toLowerCase().substring(0, 10) + "\n");
                            omegaURL.remove(i);

                        }
                    }
                }
            } else {
                centralComDisplayMessage.append("\n     rawTimeline == null ");
            }
        } catch (IOException e) {
            centralComDisplayMessage.append("\n          ( IOException )");
        } catch (TwitterException e) {
            centralComDisplayMessage.append("\n                             ( TwitterException )");
        } catch (IndexOutOfBoundsException e) {
            centralComDisplayMessage.append("\n           ( IndexOutOfBoundsException ) <--- Check this");
        }

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

    private List<String> trimHTTP(List<String> twitterTrends) {
        List<String> tTemp = new ArrayList<>();
        for (int i = 0; i < twitterTrends.size(); i++) {
            if (twitterTrends.get(i).toLowerCase().contains("http")) {
                int position = twitterTrends.get(i).indexOf("http");
                StringBuilder sTemp = new StringBuilder(twitterTrends.get(i));
                sTemp.delete((position - 1), sTemp.length());
                tTemp.add(sTemp.toString());
            } else {
                tTemp.add(twitterTrends.get(i));
            }
        }
        return tTemp;
    }

    private String getFileSuffix(final String path) {
        String result = null;
        if (path != null) {
            result = "";
            if (path.lastIndexOf('.') != -1) {
                result = path.substring(path.lastIndexOf('.'));
                if (result.startsWith(".")) {
                    result = result.substring(1);
                }
            }
        }
        return result;
    }

    public static void cls() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception e) {
        }
    }
    private static void zZZZ(int minutes) {
        try {
            TimeUnit.MINUTES.sleep(minutes);
        } catch (Exception ignore) {
        }
    }
    private static void zZZ(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignore) {
        }
    }
    private static void zZ(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (Exception ignore) {
        }
    }
}
