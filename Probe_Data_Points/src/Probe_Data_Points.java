import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Probe_Data_Points {
    public static class cls {
        public cls() {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
          //      System.out.println(e.getMessage());
            }
        }
    }
    static ArrayList<String> keywords = new ArrayList<>();
    static ArrayList<String> twitterTrends = new ArrayList<>();
    static ArrayList<String> bounceSpan = new ArrayList<>();
    static ArrayList<String> Data_Points = new ArrayList<>();
    static ArrayList<String> Locations = new ArrayList<>();
    static ArrayList<String> bounceIMG = new ArrayList<>();
    static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static int windowWidth = gd.getDisplayMode().getWidth();
    static int windowHeight = gd.getDisplayMode().getHeight();
    static boolean googleEarthStatus;

    public static void main(String[] args) throws InterruptedException {
        Hash hash = new Hash();
        new cls();
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        System.setProperty("mbrola.base", "lib\\mbr301d");
        voice = voiceManager.getVoice("mbrola_us1");
        voice.allocate();
        try {
            new cls();
            waitRandomShort();
            new cls();
        } catch (InterruptedException ignored) {
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            new cls();
            System.out.println("\n      ClassNotFoundException com.mysql.jdbc.Driver");
            voice.speak("Class Not Found Exception J D B C Driver Exception");
        }
        String jdbcURL = hash.getJdbcURL();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcURL, hash.getUsername(), hash.getPassword());
        } catch (Exception e) {
            new cls();
            System.out.println("\n   DriverManager.getConnection     SQLException *");
            System.out.println(e.getMessage());
            voice.speak("S Q L Exception ERROR ERROR Sequel Exception Driver Manager");
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (Exception e) {
            new cls();
            System.out.println("\n      stmt = conn. SQLException *");
            System.out.println(e.getMessage());
            voice.speak("S Q L Exception DANGER Sequel Exception");
        }
        ResultSet resultset = null;
        try {
            resultset = stmt.executeQuery("SELECT COUNT(*) FROM GEOPOLITICS.DATA_PROBE");
        } catch (Exception e) {
            new cls();
            System.out.println("\n   SELECT COUNT(*) FROM     SQLException *");
            System.out.println(e.getMessage());
            voice.speak("S Q L Exception DANGER select Count from data probe Sequel Exception");
        }
        int dbSize = 0;
        try {
            while (resultset.next()) {
                dbSize = resultset.getInt(1);
            }
        } catch (Exception e) {
            new cls();
            System.out.println("\n      SQLException * dbSize = set");
            System.out.println(e.getMessage());
            voice.speak("S Q L Exception metaData ResultSetMetaData Sequel Exception count failure");
        }
        try {
            Random rand = new Random();
            int numberOfQueries = 5 + rand.nextInt(10);
            resultset = stmt.executeQuery("SELECT * FROM  `GEOPOLITICS`.`DATA_PROBE` LIMIT " + (dbSize - numberOfQueries) + " , " + numberOfQueries);
/*            if (args[0] == null) {
            } else {
                resultset = stmt.executeQuery("SELECT * FROM  `GEOPOLITICS`.`DATA_PROBE` LIMIT " + (dbSize - Integer.parseInt(args[0])) + " , " + Integer.parseInt(args[0]));
            }*/
        } catch (Exception e) {
            new cls();
            System.out.println("\n      SELECT * FROM LIMIT     SQL Exception *");
            System.out.println(e.getMessage());
            voice.speak("S Q L Exception SELECT * FROM LIMIT failure");
        }
        int numcols = 4;
        ArrayList<String> list = new ArrayList<String>();
        try {
            ResultSetMetaData rsmd = resultset.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultset.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = resultset.getString(i);
                    try {
                        list.add(columnValue);
                    } catch (Exception ignore) {
                    }
                }
            }
        } catch (Exception e) {
            new cls();
            System.out.println("\n      Dump ResultSet     SQL Exception *");
            System.out.println(e.getMessage());
            voice.speak("S Q L Exception Failure dump result set");
        }
        try {
            conn.close();
        } catch (Exception e) {
            new cls();
            System.out.println("\n                     SQLException *");
            System.out.println(e.getMessage());
            voice.speak("Connection Close Failure");
            voice.speak("Force Terminate");
        }
        try {
            PrintWriter writer = new PrintWriter("Data_Points.txt", "UTF-8");
            for (int i = 0; i < list.size(); i++) {
                writer.println(list.get(i));
            }
            writer.close();
        } catch (Exception e) {
            new cls();
            System.out.println(e.getMessage());
            System.out.println("Failed to write local");
            voice.speak("Failed write to file");
        }

            readLoocalToArray("Data_Points" + ".txt", Data_Points);
            readLoocalToArray("keywords" + ".txt", keywords);
            readLoocalToArray("Twitter_Trends" + ".txt", twitterTrends);
            readLoocalToArray("bounceIMG" + ".txt", bounceIMG);
            readLoocalToArray("Every_Country" + ".txt", Locations);
            readLoocalToArray("BounceSpan" + ".txt", bounceSpan);
            GoogleEarthActive(true);
            for (int i = 0, j = 0; i < Data_Points.size(); i++, j++) {
                switch (j) {
                    case 0: {
                        verbalize(Meetis_Personality.search(), voice);
                        verbalize(Meetis_Personality.checkThis(), voice);
                        break;
                    }
                    case 1: {
                        verbalize(Data_Points.get(i), voice);
                        TimeUnit.MILLISECONDS.sleep(500);
                        verbalize(Meetis_Personality.checkThis(), voice);
                        break;
                    }
                    case 2: {
                        GoogleEarthActive(true);
                        int retrivedIMGcounter = 0;
                        ArrayList<String> retrivedImages = new ArrayList<>();
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(Data_Points.get(i)).get();
                        } catch (Exception e) {
                    //        System.out.println(" " + e.getMessage());
                        }
                        try {
                            Elements images = doc.select("img");
                            if (images != null)
                                for (Element image : images) {
                                    URL url;
                                    Boolean die = false;
                                    if ((image.attr("src").contains("gif")) || (image.attr("src").contains("jpg")) || (image.attr("src").contains("jpeg")) || (image.attr("src").contains("png")))
                                        if (image.attr("src").contains("//"))
                                            if (!(image.attr("src").length() < 5)) {
                                                try {
                                                    url = new URL(image.attr("src"));
                                                    Image img = new ImageIcon(url).getImage();
                                                    if (img.getWidth(null) < 300)
                                                        die = true;
                                                    if (img.getHeight(null) < 200)
                                                        die = true;
                                                } catch (MalformedURLException e) {
                                                    die = true;
                                                    System.out.println(" ( Image Failure )* ( " + e.getMessage() + " )");
                                                }
                                                for (int k = 0; k < retrivedImages.size(); k++) {
                                                    if (retrivedImages.get(k).equals(image.attr("src")))
                                                        die = true;
                                                }
                                                if (!die) {
                                                    retrivedImages.add(image.attr("src"));
                                                    System.out.println(image.attr("src"));
                                                }
                                            }
                                }
                        } catch (Exception e) {
                         //   System.out.println(e.getMessage());
                        }
                        try {
                            PrintWriter writer = new PrintWriter("Images.txt", "UTF-8");
                            for (String retrivedImage : retrivedImages) {
                                writer.println(retrivedImage);
                            }
                            writer.close();
                        } catch (IOException ignore) {
                        }
                        try {
                            System.out.println(Data_Points.get(i));
                            doc = Jsoup.connect(Data_Points.get(i)).get();
                            ArrayList<String> spans = new ArrayList<>();
                            /*Elements a = doc.getElementsByTag("span");
                            if (a != null) {
                                parseHTML(spans, a);
                                verbalize(spans, voice, retrivedImages, retrivedIMGcounter, retrivedImages.size());
                            }*/
                            ArrayList<String> p = new ArrayList<>();
                            Elements a = doc.getElementsByTag("p");
                            if (a != null) {
                                parseHTML(p, a);
                                verbalize(p, voice, retrivedImages, retrivedIMGcounter, retrivedImages.size());
                            }
                        } catch (Exception ignore) {
                        }
                        GoogleEarthActive(true);
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        j = 0;
                        break;
                    }
                }
            }
            try {
                PrintWriter writer = new PrintWriter("MeetisStatus.txt", "UTF-8");
                writer.println("Ready");
                writer.close();
                Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
            } catch (Exception ignore) {
            }

        System.exit(0);
    }
    private static void readLoocalToArray(String local, ArrayList<String> arrayRefrence) {
        BufferedReader bufferedReader = null;
        String line = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(local));
            while ((line = bufferedReader.readLine()) != null) {
                arrayRefrence.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void parseHTML(ArrayList spans, Elements a) {
        for (Element element : a) {
            String raw = element.toString();
            String parsed = FilterHTML.delHTMLTag(raw);
            String arr[] = parsed.split(" ");
            if (arr.length > 2) {
                if (parsed.matches(".*[a-z].[a-z].[a-z].[a-z].*")) {
                    if (!spans.contains(parsed)) {
                        spans.add(parsed);
                    }
                }
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
    public static void verbalize(String phrase, Voice voice) {
        for (int i = 0; i < bounceSpan.size(); i++) {
            if (phrase.contains(bounceSpan.get(i))) {
                return;
            }
        }
        new cls();
        String[] compoundPhrase = phrase.split(" ");
        int maxCharsPerLine = 40;
        int maxCharsPrompt = 200;
        int lineChars;
        int totalChars = 0;
        int i = 0;
        StringBuilder speakBuilder = new StringBuilder();
        do {
            StringBuilder lineBuilder = new StringBuilder();
            for (lineChars = 0; lineChars < maxCharsPerLine && i < compoundPhrase.length; lineChars += compoundPhrase[i].length(), totalChars += compoundPhrase[i].length(), i++) {
                lineBuilder.append(compoundPhrase[i] + " ");
                speakBuilder.append(compoundPhrase[i] + " ");
            }
            System.out.println(lineBuilder.toString());
            if (totalChars > maxCharsPrompt || compoundPhrase.length == i) {
                try {
                    PrintWriter writer = new PrintWriter("MeetisTalking.txt");
                    writer.println("Talking");
                    writer.close();
                } catch (Exception ignore) {
                }
                voice.speak(speakBuilder.toString());
                try {
                    PrintWriter writer = new PrintWriter("MeetisTalking.txt");
                    writer.println("Not Talking");
                    writer.close();
                } catch (Exception ignore) {
                }
                speakBuilder.setLength(0);
                totalChars = 0;
            }
        } while (compoundPhrase.length > i && compoundPhrase.length < maxCharsPrompt);
    }
    public static void verbalize(ArrayList<String> spans,
                                 Voice voice,
                                 ArrayList<String> retrivedImages,
                                 int retrivedIMGcounter,
                                 int retrivedIMGlength
    ) {
        JFrame ImageFrame = new JFrame();
        for (int k = 0; k < spans.size() && k < 5; k++) {
            Boolean found1 = false;
            Boolean found2 = false;
            for (int l = 0; l < Locations.size() && !found2; l++) {
                if (spans.get(k).toLowerCase().contains(Locations.get(l).toLowerCase())) {
                    if (found1) {
                        GoogleEarth(Locations.get(l));
                        found2 = true;
                    }
                    found1 = true;
                }
            }
            if (found1 && !found2) {
                for (int y = 0; y < Locations.size() && found1; y++) {
                    if (spans.get(k).toLowerCase().contains(Locations.get(y).toLowerCase())) {
                        GoogleEarth(Locations.get(y));
                        found1 = false;
                    }
                }
            }
            if (retrivedIMGcounter < retrivedIMGlength) {
                try {
                    GoogleEarthActive(false);
                    zZZ(7);
                    String path = retrivedImages.get(retrivedIMGcounter);
                    URL url = new URL(path);
                    BufferedImage image = ImageIO.read(url);
                    int height = image.getHeight();
                    int width = image.getWidth();
                    JLabel RetrivedImage = new JLabel(new ImageIcon(image));
                    ImageFrame.setPreferredSize(new Dimension(windowWidth, windowHeight));
                    ImageFrame.setUndecorated(true);
                    ImageFrame.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.0f));
                    ImageFrame.isAlwaysOnTop();
                    ImageFrame.setLayout(null);
                    ImageFrame.add(RetrivedImage);
                    RetrivedImage.setLocation(115, 79);
                    RetrivedImage.setSize(width, height);
                    ImageFrame.setVisible(true);
                    ImageFrame.pack();
                } catch (Exception e) {
                 //   System.out.println(e.getMessage());
                }
                retrivedIMGcounter++;
            }
            verbalize(spans.get(k), voice);
            if (retrivedIMGcounter != retrivedIMGlength || k == spans.size()) {
                ImageFrame.getContentPane().removeAll();
                ImageFrame.dispatchEvent(new WindowEvent(ImageFrame, WindowEvent.WINDOW_CLOSING));
                GoogleEarthActive(true);
            }
        }
        GoogleEarthActive(true);
        ImageFrame.getContentPane().removeAll();
        ImageFrame.dispatchEvent(new WindowEvent(ImageFrame, WindowEvent.WINDOW_CLOSING));
    }
    public static void verbalize(ArrayList<String> spans, Voice voice) {
        for (int k = 0; k < spans.size() && k < 3; k++) {
            Boolean found1 = false;
            Boolean found2 = false;
            for (int l = 0; l < Locations.size() && !found2; l++) {
                if (spans.get(k).toLowerCase().contains(Locations.get(l).toLowerCase())) {
                    if (found1) {
                        found2 = true;
                        GoogleEarth(Locations.get(l));
                    }
                    found1 = true;
                }
            }
            if (found1 && !found2) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                }
                for (int y = 0; y < Locations.size() && found1; y++) {
                    if (spans.get(k).toLowerCase().contains(Locations.get(y).toLowerCase())) {
                        GoogleEarth(Locations.get(y));
                        found1 = false;
                    }
                }
            }
            verbalize(spans.get(k), voice);
        }
    }
    public static boolean GoogleEarthActive() {
        try {
            String tString = "";
            BufferedReader br = new BufferedReader(new FileReader("GoogleEarthStatus.txt"));
            if (br != null & !br.equals("")) {
                tString = br.readLine();
            }
            if (tString.contains("True")) {
                googleEarthStatus = true;
                return true;
            } else {
                googleEarthStatus = false;
                return false;
            }
        } catch (Exception ignore) {
        }
        return false;
    }
    public static void GoogleEarthActive(Boolean setGEarthStatus) {
        if (setGEarthStatus) {
            try {
                PrintWriter writer = new PrintWriter("GoogleEarthStatus.txt");
                writer.println("True");
                writer.close();
            } catch (Exception ignore) {
            }
        } else {
            try {
                PrintWriter writer = new PrintWriter("GoogleEarthStatus.txt");
                writer.println("False");
                writer.close();
            } catch (Exception ignore) {
            }
        }
    }
    private static void waitRandomShort() throws InterruptedException {
        new cls();
        Random rand = new Random();
        for (int i = 0; i < 45; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(75 + rand.nextInt(100));
        }
        System.out.printf("\n");
    }

    private static void waitRandom() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(500);
        for (int i = 0; i < 40; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
        }
        System.out.printf("\n");
    }

    private static void waitRandomLong() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(15000);
        random += 1000;
        for (int i = 0; i < 40; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
        }
        System.out.printf("\n");
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
