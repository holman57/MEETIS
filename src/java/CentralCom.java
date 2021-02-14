import org.jsoup.Jsoup;
import org.jsoup.nodes.BooleanAttribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.TwitterException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by Jupiter on 1/1/2016.
 *
 *                                          MEETIS   /\Oo/\\ *
 */
public class CentralCom extends JFrame {

    JPanel panel1;
    JTextArea centralComDisplayMessage;
    JPanel centralComMasterJPanel;
    JScrollPane masterScrollPanel;
    JProgressBar centralComProgressBar;

    static Thread alphaOmegaThread;

    ArrayList<String> keywords = new ArrayList<>();
    ArrayList<String> keywordPhrase = new ArrayList<>();
    ArrayList<String> avoidURL = new ArrayList<>();
    ArrayList<String> bounceIMG = new ArrayList<>();
    ArrayList<String> buildPoints = new ArrayList<>();

    static boolean trendsExist = false;
    static boolean keywordsExists = false;
    static boolean bounceIMGExists = false;
    static boolean avoidURLExists = false;
    static boolean buildPointsExist = false;
    static boolean fullAutoEngaged = false;
    //-------------------------------------------------
    static boolean omegaURLbeingAccesed = false;
    static boolean alphaURLbeingAccesed = false;
    static boolean ready = false;

    ArrayList<String> alphaURL = new ArrayList<>();
    ArrayList<internalComparison> omegaURL = new ArrayList<>();

    static boolean initUpdate = true;
    static boolean updateReady = true;
    static int alphaPosition = 0;
    static int omegaPosition = 0;
    static int cycle = 0;
    static int positiveSQL = 0;

    long startTime = System.currentTimeMillis();

    GetTwitterTrends trends;
    static List<String> rawTimeline;

/*    static {
        try {
            rawTimeline = trimHashtag(GetTimeline.timeline());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (TwitterException e) {
            System.out.println(e.getMessage());
        }
    }*/

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int windowWidth = gd.getDisplayMode().getWidth();
    int windowHeight = gd.getDisplayMode().getHeight();

    Font averagedFont;
    Font listFont;

    public CentralCom() {
         Font smallFont = new Font("Arial", Font.BOLD, (((windowHeight + windowWidth) / 110) * 3 / 5));

        centralComMasterJPanel.setLayout(new GridLayout(0, 1));

        masterScrollPanel.setBackground(Color.BLACK);
        masterScrollPanel.setForeground(Color.white);
        masterScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        masterScrollPanel.getVerticalScrollBar().setBackground(Color.BLACK);
        masterScrollPanel.getVerticalScrollBar().setForeground(Color.white);
        masterScrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
        masterScrollPanel.getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder());
        masterScrollPanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.white;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });

        centralComDisplayMessage.setFont(smallFont);
        centralComProgressBar.setIndeterminate(true);

        DefaultCaret caret0 = (DefaultCaret) centralComDisplayMessage.getCaret();
        caret0.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        Thread uploadthread = new Thread() {
            public void run() {
                try {
                    sleep(1000000);
                } catch (InterruptedException ignore) {}

                do {
                    Random rand = new Random();
                    int attempt = rand.nextInt(5);
                    int sleepDuration = 10000 + rand.nextInt(20000);

                    try {
                        if (omegaURL.get(attempt) != null)
                            SQLquery.exUpdate(omegaURL.get(attempt), centralComDisplayMessage);
                        positiveSQL++;
                        centralComDisplayMessage.append("\n\tSuccessful SQL Stmt: " + positiveSQL + "\n");

                    } catch (ClassNotFoundException e) {
                        centralComDisplayMessage.append("\n     ( Exception )( ClassNotFoundException )");
                        centralComDisplayMessage.append("\n     (" + omegaURL.get(attempt).title + " )");
                    }

                    try {
                        sleep(sleepDuration);
                    } catch (InterruptedException e) {
               //         System.out.println(e.getMessage());
                    }
                } while (true);

            }
        };
        uploadthread.start();

        do {

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
                    centralComDisplayMessage.append("\n\n                                ( bounceIMG Array Set* )\n\n");
                    bounceIMGExists = true;
                }

            } catch (MalformedURLException e) {
                centralComDisplayMessage.setText("         Something went wrong... bounceIMG do not exist");
                bounceIMGExists = false;
            } catch (IOException e) {
                centralComDisplayMessage.setText("     Something went wrong... bounceIMG do not exist");
                bounceIMGExists = false;
            }

            Thread buildURLthread = new Thread() {
                public void run() {
                    Random rand = new Random();
                    centralComDisplayMessage.append("\n\talphaPosition = 0");
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
                                    centralComDisplayMessage.append("\n" + src);
                                    alphaURLbeingAccesed = true;
                                    alphaPosition++;
                                    alphaURL.add(alphaPosition + " " + src);
                                    alphaURLbeingAccesed = false;
                                    sleep(rand.nextInt(35));
                                }
                            }
                            Elements aTitle = doc.getElementsByTag("a.title");
                            for (Element element : aTitle) {
                                String src = element.text();
                                if (!(src == null | src.length() == 0)) {
                                    centralComDisplayMessage.append(alphaPosition + " " + src);
                                    alphaURLbeingAccesed = true;
                                    alphaPosition++;
                                    alphaURL.add(alphaPosition + " " + src);
                                    alphaURLbeingAccesed = false;
                                    sleep(rand.nextInt(35));
                                }
                            }
                        } catch (IOException ex) {
                            centralComDisplayMessage.append("\n               ( URL Exception )  ");
                        } catch (InterruptedException e1) {
                        }
                    }
                    centralComDisplayMessage.append("\n\n  ====================     Attempting a Second Parse...                           <-- 2nd\n");
                    try {
                        sleep(30);
                    } catch (InterruptedException e1) {
                    }
                    //  Second Parse------------------------------------------------
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
                                    if (!(src == null | src.length() == 0)) {
                                        centralComDisplayMessage.append(alphaPosition + " " + src);
                                        alphaURLbeingAccesed = true;
                                        alphaPosition++;
                                        alphaURL.add(alphaPosition + " " + src);
                                        alphaURLbeingAccesed = false;
                                        sleep(rand.nextInt(35));
                                    }
                            }
                        } catch (IOException ex) {
                            centralComDisplayMessage.append("\n ( Second Parse )( Failure )* " + urlElement);
                        } catch (InterruptedException e1) {
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
                            try {
                                centralComDisplayMessage.append("\n       ( parseNode )    " + alphaURL.get(i));
                                Document doc = Jsoup.connect(alphaURL.get(i)).get();
                                Elements link = doc.select("h1 a[href], h2 a[href], h3 a[href]");
                                if (link.size() >= 10)
                                    for (int j = 0; j < 15; j++) {
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
                                                for (int k = 0; k < alphaURL.size(); k++) {
                                                    if (alphaURL.get(k).toLowerCase().contains(keywordArray.badURL[k].toLowerCase())) {
                                                        BadURL = true;
                                                    }
                                                }
                                                if (!BadURL) {
                                                    alphaURLbeingAccesed = true;
                                                    alphaPosition++;
                                                    alphaURL.add(alphaPosition + " " + src);
                                                    alphaURLbeingAccesed = false;
                                                    centralComDisplayMessage.append("\n" + src);
                                                    startDiveURLthread(src);
                                                }
                                            }
                                        }
                                    }
                            } catch (IOException e) {
                                centralComDisplayMessage.append("\n          ( parseUnknown )( IOException )   ");
                            }
                        }
                    }

                    centralComDisplayMessage.append("\n                 alphaURL complete    \n");
                    alphaURLbeingAccesed = false;
                    ready = true;
                }
            };
            buildURLthread.start();

            do {
                try {
                    sleep(100000);
                } catch (InterruptedException e) {
                }
            } while (!ready);

        } while (true);


    }

    public void startDiveURLthread(String alphaInput) {
        Thread omegaURLthread = new Thread() {
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(alphaInput).get();
                } catch (Exception e1) {
                    centralComDisplayMessage.setText("(Failure)*    doc = Jsoup.connect(element).get();");
                }
                Elements link = new Elements();
                try {
                    link = doc.getElementsByTag("title");
                } catch (Exception e1) {
                    centralComDisplayMessage.append("(Failure)*   link = doc.getElementsByTag(\"title\");");
                }
                if (doc != null)
                    if (link != null)
                        for (Element el : link) {
                            String src = " ";
                            if (el != null)
                                if (el.hasText())
                                    if (el.text().length() < 100)
                                        src = el.text();
                            boolean breakMe = false;                //    breaks image loop             (and try again)
                            boolean die = false;                    //    breaks method and returns
                            boolean Continue = false;               //    check keyword gate
                            for (int i = 0; i < keywords.size(); i++) {
                                if (src.toLowerCase().contains(keywords.get(i))) {
                                    Continue = true;
                                }
                            }
                            if (trends.getTrends() != null) {
                                for (int i = 0; i < trends.getTrends().size(); i++) {
                                    if (src.toLowerCase().contains(trends.getTrends().get(i))) {
                                        Continue = true;
                                    }
                                }
                            }
                            if (Continue)//     Keyword Matches
                                if (!(src == null | src.length() == 0)) {
                                    Elements images = doc.select("img");
                                    if (images != null)
                                        for (Element image : images) {
                                            URL url = null;
                                            if ((image.attr("src").contains("gif")) || (image.attr("src").contains("jpg")) || (image.attr("src").contains("png")))
                                                if (image.attr("src").contains("//"))
                                                    if (!(image == null | image.attr("src").length() < 5))
                                                        if (!(breakMe)) {                                   //     Check Image width and height
                                                            //     Create a URL for the image's location
                                                            try {
                                                                url = new URL(image.attr("src"));
                                                                Image img = new ImageIcon(url).getImage();
                                                                if (img.getWidth(null) < 300) die = true;
                                                                if (img.getHeight(null) < 200) die = true;
                                                            } catch (Exception e) {
                                                                die = true;
                                                            }
                                                            if (!die) {
                                                                if (bounceIMG != null)
                                                                    for (int i = 0; i < bounceIMG.size(); i++) {
                                                                        if (image.attr("src").contains(bounceIMG.get(i))) {
                                                                            die = true;
                                                                        }
                                                                    }
                                                            }
                                                            if (!die) {
                                                                internalComparison mark = new internalComparison(centralComDisplayMessage);
                                                                omegaURLbeingAccesed = true;
                                                                omegaURL.add(mark);
                                                                mark.setTitle(src);
                                                                mark.setUrl(alphaInput);
                                                                mark.setImg(image.attr("src"));
                                                                mark.printMark();
                                                                breakMe = true;
                                                                omegaPosition++;
                                                                omegaURLbeingAccesed = false;
                                                            }
                                                            die = false;
                                                        }
                                        }
                                }
                        }

                omegaURL.get(omegaPosition).increasePriority(
                        prioritizer(omegaURL.get(omegaPosition).url, omegaURL, trends.getTrends(), keywords, centralComDisplayMessage));

                Collections.sort(omegaURL);

                omegaURL = (ArrayList<internalComparison>)
                        hashTag(omegaURL, centralComProgressBar, centralComDisplayMessage, trends.getTrends(), keywords).clone();

                int a = 1;
                centralComMasterJPanel.removeAll();
                for (internalComparison element : omegaURL) {
                    JButton masterListButton = new JButton(a++ + " " + omegaURL.get(omegaPosition) + " *" + element.printButtton());
                    masterListButton.setBackground(Color.black);
                    masterListButton.setForeground(Color.white);
                    masterListButton.setFont(MEETIS.LargeFont);
                    masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                    centralComMasterJPanel.add(masterListButton);
                    centralComMasterJPanel.updateUI();
                }

                cycle++;
                centralComDisplayMessage.setText("\n         ( Completed Cycle ) ( " + cycle + " ) \n");
                waitRandomShort(centralComProgressBar);
                omegaURLbeingAccesed = false;
                updateReady = true;
                initUpdate = true;
                trendsExist = false;
                avoidURLExists = false;
                bounceIMGExists = false;
                buildPointsExist = false;
                keywordsExists = false;
                centralComDisplayMessage.append("\n                      ( alpha omega URL will be cleared )");
                waitRandomShort(centralComProgressBar);
                omegaURL.removeAll(omegaURL);
                alphaURL.removeAll(alphaURL);
            }

        };
        omegaURLthread.start();

    }


    private void waitRandomShort(JProgressBar progressbar) {
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

    private static boolean testConnection() throws MalformedURLException {
        final URL url = new URL("http://www.lukeholman.net/meetis.php");
        final URLConnection conn;
        try {
            conn = url.openConnection();
            conn.connect();
            return true;
        } catch (IOException e) {
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

    private ArrayList<String> buildURL(JTextArea masterPanel, JProgressBar progressBar) {
        Random rand = new Random();
        centralComDisplayMessage.append("\n\tposition = 0");
        alphaPosition = 0;
        try {
            sleep(1000);
        } catch (InterruptedException e1) {
        }
        masterPanel.append("\n                          First Parse Attempt...                           <-- 1st\n");

        //  First Parse-------------------------------------------------
        for (String urlElement : buildPoints) {
            try {
                Document doc = Jsoup.connect(urlElement).get();
                Elements link = doc.getElementsByTag("link");
                for (Element element : link) {
                    String src = element.text();
                    if (!(src == null | src.length() == 0)) {
                        masterPanel.append("\n" + src);
                        alphaURLbeingAccesed = true;
                        alphaPosition++;
                        alphaURL.add(alphaPosition + " " + src);
                        alphaURLbeingAccesed = false;
                        sleep(rand.nextInt(35));
                    }
                }
                Elements aTitle = doc.getElementsByTag("a.title");
                for (Element element : aTitle) {
                    String src = element.text();
                    if (!(src == null | src.length() == 0)) {
                        masterPanel.append("\n" + src);
                        alphaURLbeingAccesed = true;
                        alphaPosition++;
                        alphaURL.add(alphaPosition + " " + src);
                        alphaURLbeingAccesed = false;
                        sleep(rand.nextInt(35));
                    }
                }
            } catch (IOException ex) {
                masterPanel.append("\n               ( URL Exception )  ");
            } catch (InterruptedException e1) {
            }
        }
        masterPanel.append("\n\n  ====================     Attempting a Second Parse...                           <-- 2nd\n");
        try {
            sleep(1000);
        } catch (InterruptedException e1) {
        }
        //  Second Parse------------------------------------------------
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
                        if (!(src == null | src.length() == 0)) {
                            masterPanel.append("\n" + src);
                            alphaURLbeingAccesed = true;
                            alphaPosition++;
                            alphaURL.add(alphaPosition + " " + src);
                            alphaURLbeingAccesed = false;
                            sleep(rand.nextInt(35));
                        }
                }
            } catch (IOException ex) {
                centralComDisplayMessage.append("\n ( Second Parse )( Failure )* " + urlElement);
            } catch (InterruptedException e1) {
            }
        }


        for (int i = 0; i < alphaURL.size(); i++) {
            int counter = 0;
            for (int j = 0; j < alphaURL.get(i).length(); j++) {
                if (alphaURL.get(i).charAt(j) == '/') {
                    counter++;
                }
            }
            if (counter < 4 && counter > 1) {
                try {
                    masterPanel.append("\n       ( parseNode )    " + alphaURL.get(i));
                    Document doc = Jsoup.connect(alphaURL.get(i)).get();
                    Elements link = doc.select("h1 a[href], h2 a[href], h3 a[href]");
                    if (link.size() >= 10)
                        for (int j = 0; j < 10; j++) {
                            Element el = link.get(j);
                            String src = el.attr("abs:href");
                            if (src.contains("http")) {
                                boolean redundancy = false;
                                for (int k = 0; k < alphaURL.size(); k++) {
                                    if (alphaURL.get(k).equals(src))
                                        redundancy = true;
                                }
                                if (!redundancy) {
                                    alphaURLbeingAccesed = true;
                                    alphaPosition++;
                                    alphaURL.add(alphaPosition + " " + src);
                                    alphaURLbeingAccesed = false;
                                    masterPanel.append("\n   " + src);
                                }
                            }
                        }
                } catch (IOException e) {
                    masterPanel.append("\n          ( parseUnknown )( IOException )   ");
                }
            }
        }
        ArrayList<String> betaURL = new ArrayList<String>();
        for (int i = 0; i < alphaURL.size(); i++) {
            int counter = 0;
            for (int j = 0; j < alphaURL.get(i).length(); j++) {
                if (alphaURL.get(i).charAt(j) == '/') {
                    counter++;
                }
            }
            if (counter < 5) {
                masterPanel.append("\n           ( Removed )     " + alphaURL.get(i));
            } else {
                alphaURLbeingAccesed = true;
                betaURL.add(alphaURL.get(i));
                alphaURLbeingAccesed = false;
            }
        }
        alphaURL.clear();
        // Store unique items in result.
        ArrayList<String> result1 = new ArrayList<>();
        //
        // Record encountered Strings in HashSet.
        HashSet<String> set1 = new HashSet<>();
        //
        // Loop over argument list.                REDUNDANCY CHECK
        for (String item : betaURL) {
            //
            // If String is not in set, add it to the list and the set.
            if (!set1.contains(item)) {
                result1.add(item);
                set1.add(item);
            }
        }
        Collections.shuffle(betaURL);
        for (String element : betaURL) {
            masterPanel.append("\n   " + element);
        }
        masterPanel.append("\n                 Thank you I'm done now...     \n");
        alphaURLbeingAccesed = false;
        return result1;
    }

    public ArrayList<internalComparison> diveURL(JProgressBar diveURLprogress,
                                                 JTextArea centralArea,
                                                 JPanel centralComMasterJPanel,
                                                 GetTwitterTrends trends,
                                                 ArrayList<internalComparison> omegaURL,
                                                 ArrayList<String> alphaURL,
                                                 ArrayList<String> keywords,
                                                 ArrayList<String> bounceIMG,
                                                 Font averagedFont) {

        for (String element : alphaURL) {
            diveURLprogress.setValue(10);
            Document doc = null;
            try {
                doc = Jsoup.connect(element).get();
            } catch (Exception e1) {
                centralArea.setText("(Failure)*    doc = Jsoup.connect(element).get();");
            }
            Elements link = new Elements();
            try {
                link = doc.getElementsByTag("title");
            } catch (Exception e1) {
                centralArea.append("link = doc.getElementsByTag(\"title\");");
            }
            if (doc != null)
                if (link != null)
                    for (Element el : link) {
                        diveURLprogress.setValue(25);
                        String src = " ";
                        if (el != null)
                            if (el.hasText())
                                if (el.text().length() < 100)
                                    src = el.text();
                        boolean breakMe = false;                //    breaks image loop             (and try again)
                        boolean die = false;                    //    breaks method and returns
                        boolean Continue = false;               //    check keyword gate
                        diveURLprogress.setValue(30);
                        for (int i = 0; i < keywords.size(); i++) {
                            if (src.toLowerCase().contains(keywords.get(i))) {
                                Continue = true;
                                diveURLprogress.setValue(35);
                            }
                        }
                        diveURLprogress.setValue(40);
                        if (trends.getTrends() != null) {
                            diveURLprogress.setValue(45);
                            for (int i = 0; i < trends.getTrends().size(); i++) {
                                if (src.toLowerCase().contains(trends.getTrends().get(i))) {
                                    Continue = true;
                                    diveURLprogress.setValue(50);
                                }
                            }
                        }
                        diveURLprogress.setValue(60);
                        if (Continue)//     Keyword Matches
                            if (!(src == null | src.length() == 0)) {
                                Elements images = doc.select("img");
                                diveURLprogress.setValue(65);
                                if (images != null)
                                    for (Element image : images) {
                                        diveURLprogress.setValue(70);
                                        URL url = null;
                                        if ((image.attr("src").contains("gif")) || (image.attr("src").contains("jpg")) || (image.attr("src").contains("png")))
                                            if (image.attr("src").contains("//"))
                                                if (!(image == null | image.attr("src").length() < 5))
                                                    if (!(breakMe)) {                                   //     Check Image width and height
                                                        //     Create a URL for the image's location
                                                        try {
                                                            url = new URL(image.attr("src"));
                                                            diveURLprogress.setValue(75);
                                                            Image img = new ImageIcon(url).getImage();
                                                            if (img.getWidth(null) < 300) die = true;
                                                            if (img.getHeight(null) < 200) die = true;
                                                        } catch (Exception e) {
                                                            die = true;
                                                        }
                                                        diveURLprogress.setValue(80);
                                                        if (!die) {
                                                            if (bounceIMG != null)
                                                                for (int i = 0; i < bounceIMG.size(); i++) {
                                                                    if (image.attr("src").contains(bounceIMG.get(i))) {
                                                                        die = true;
                                                                        diveURLprogress.setValue(85);
                                                                    }
                                                                }
                                                        }
                                                        diveURLprogress.setValue(90);
                                                        if (!die) {

                                                            diveURLprogress.setValue(100);
                                                            internalComparison mark = new internalComparison(centralArea);
                                                            omegaURL.add(mark);
                                                            mark.setTitle(src);
                                                            mark.setUrl(element);
                                                            mark.setImg(image.attr("src"));
                                                            mark.printMark();
                                                            breakMe = true;
                                                        }
                                                        die = false;
                                                    }
                                    }
                            }
                    }
        }
        try {
            int i = 1;
            for (internalComparison element : omegaURL) {
                JButton masterListButton = new JButton(" ( " + i++ + " ) " + element.printButtton());
                masterListButton.setBackground(Color.black);
                masterListButton.setForeground(Color.white);
                masterListButton.setFont(MEETIS.LargeFont);
                masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                centralComMasterJPanel.add(masterListButton);
            }
        } catch (Exception e) {
        }

        return omegaURL;
    }

    public void restartApplication() throws URISyntaxException, IOException {
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
    }

    private int prioritizer(String URL,
                            ArrayList<internalComparison> omegaURL,
                            ArrayList<String> trends,
                            ArrayList<String> keywords,
                            JTextArea prioritizerTextArea
    ) {
        boolean foundDate = false;
        Random rand = new Random();
        int random = rand.nextInt(800);
        Document doc = null;
        int returnValue = 0;

        for (int k = 0; k < keywordArray.goodURL.length; k++) {
            if (URL.toLowerCase().contains(keywordArray.goodURL[k].toLowerCase())) {
                prioritizerTextArea.append(" ( Good URL + )( " + keywordArray.goodURL[k] + " )( +1500 )");
                returnValue += 10000;
            }
        }
        for (int i = 0; i < omegaURL.size(); i++) {
            if ((omegaURL.get(i).url.toLowerCase().contains(getMonth().toLowerCase() + "/" + getDay())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getMonth().toLowerCase() + "/" + getZeroDay())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getYear() + "/" + getMonth())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getYear() + "/" + getNumericMonth())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getMonth().toLowerCase() + "-" + getDay())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getMonth().toLowerCase() + "-" + getZeroDay())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getYear() + "-" + getMonth())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getYear() + "-" + getNumericMonth()))) {
                returnValue += 100;
                if ((omegaURL.get(i).url.contains(getYear() + "/" + getNumericMonth() + "/" + getDay())) ||
                        (omegaURL.get(i).url.contains(getYear() + "/" + getZeroNumericMonth() + "/" + getDay())) ||
                        (omegaURL.get(i).url.contains(getYear() + "/" + getNumericMonth() + "/" + getZeroDay())) ||
                        (omegaURL.get(i).url.contains(getYear() + "/" + getZeroNumericMonth() + "/" + getZeroDay()))) {
                    returnValue += 1200;
                    prioritizerTextArea.append(" ( " + getMonth().toUpperCase() + " " + getDay() + " )( +1200 )");
                }
            }
        }
        try {
            if (Jsoup.connect(URL).get() != null)
                doc = Jsoup.connect(URL).get();
            String[] bodyText;
            Elements links = doc.select("body");
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
                                            prioritizerTextArea.append(" ( " + getMonth().toUpperCase() + " " + getDay() + " ) ( + 500 )");
                                            returnValue += 500;
                                            foundDate = true;
                                        }
                                    }
                                }
                            }
                        }
                        for (int k = 0; k < keywords.size(); k++) {
                            if (keywords.get(k).equals(bodyText[i])) {
                                prioritizerTextArea.setText(" ( *Keyword ) ( " + keywords.get(k) + " ) ( +" + random + " ) ");
                                returnValue += random;
                            }
                        }
                        for (int k = 0; k < trends.size(); k++) {
                            if (trends.get(k).equals(bodyText[i])) {
                                prioritizerTextArea.setText(" ( Twitter Trend ) ( " + trends.get(k) + " ) ( +1200 ) ");
                                returnValue += 1200;
                            }
                        }
                    }
                }
        } catch (IOException e) {
            prioritizerTextArea.append("    ( Jsoup.connect ) ( IOException )");
        }
        return returnValue;
    }

    public ArrayList<internalComparison> hashTag(ArrayList<internalComparison> list,
                                                 JProgressBar progressBar,
                                                 JTextArea textArea,
                                                 ArrayList<String> trends,
                                                 ArrayList<String> keywords) {
        boolean compoundPhrase = false;
        for (int k = 0; k < list.size(); k++) {
            boolean trending = false;
            String sTemp = list.get(k).getTitle();
            for (int i = 0; i < trends.size(); i++) {
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
                for (int j = 0; j < keywordPhrase.size(); j++) {
                    if (sTemp.toLowerCase().contains(keywordPhrase.get(j))) {
                        compoundPhrase = true;
                        int position = sTemp.toLowerCase().indexOf(keywordPhrase.get(j));
                        StringBuilder bTemp = new StringBuilder(sTemp);
                        compoundPhraseFormat(bTemp, position, keywordPhrase.get(j).length());
                        textArea.append("\n     ( Keyword Phrase )  ( " + bTemp + " ) ");
                        list.get(k).setTitle(bTemp.toString());
                    }
                }
            if (!compoundPhrase)
                for (int i = 0; i < keywords.size(); i++) {
                    if (sTemp.toLowerCase().contains(keywords.get(i))) {
                        int position = sTemp.toLowerCase().indexOf(keywords.get(i));
                        while ((position > 0) && (!Objects.equals(Character.toString(sTemp.charAt(position - 1)), " "))) {
                            position--;
                        }
                        StringBuilder bTemp = new StringBuilder(sTemp);
                        bTemp.insert(position, '#');
                        textArea.append("\n   ( Keyword )  ( " + bTemp + " ) ");
                        list.get(k).setTitle(bTemp.toString());
                    }
                }
            compoundPhrase = false;
        }
        return list;
    }

    public void timeLineRedundancy() {
        try {//  ----------------------------------  Back-check against twitter timeline (GetTimeline.java) ----->
            List<String> rawTimeline = trimHashtag(GetTimeline.timeline());
            //       currentCycle.setText("\n       Cross match against timeline");
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

    public JPanel getPanel1() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int windowWidth = gd.getDisplayMode().getWidth();
        int windowHeight = gd.getDisplayMode().getHeight();
        panel1.setPreferredSize(new Dimension((864), (486)));
        return panel1;
    }

    private String trimHashtag(String hashedString) {
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

    public String getMonth() {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        fmt = new Formatter();
        fmt.format("%tB", cal);
        return fmt.toString().toLowerCase();
    }

    public String getNumericMonth() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return dayOfMonthStr;
    }

    public String getZeroNumericMonth() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return ("0" + dayOfMonthStr);
    }

    public String getDay() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return dayOfMonthStr;
    }

    public String getZeroDay() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return ("0" + dayOfMonthStr);
    }

    public String getYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String sYear = String.valueOf(year);
        return sYear;
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

    private Dimension getImageDim(final String path) {
        Dimension result = null;
        try {
            String suffix = this.getFileSuffix(path);
            Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
            if (iter.hasNext()) {
                ImageReader reader = iter.next();
                ImageInputStream stream = new FileImageInputStream(new File(path));
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                result = new Dimension(width, height);
            } else {
                //  log("No reader found for given format: " + suffix));
            }
        } catch (Exception e) {
            // ImageIO exception possible with CYMK
            // Convert from CYMK to RGB maybe?
        }
        return result;
    }

    public int inhibitorShort() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(1000000);
        centralComDisplayMessage.append("\n              inhibitor triggered (*) \n                   "
                + random + " milliseconds                  " + random / 60000 + " minutes \n");
        return random;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
