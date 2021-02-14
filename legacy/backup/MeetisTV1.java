import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class MEETIS extends JFrame {
    static ArrayList<String> Data_Points;
    static ArrayList<String> Locations;
    static ArrayList<String> keywords;
    static ArrayList<String> twitterTrends;
    static ArrayList<String> bounceIMG;
    static ArrayList<String> bounceSpan;
    static boolean talking = false;
    static boolean googleEarthStatus = false;
    static boolean twitterStatus = false;
    static String HashBar = "3LegDog?";
    static String Status = "";
    static String googleEarthPosition = "";
    static JFrame frame2 = new JFrame();
    static BufferedImage meetisLogo = null;
    static JLabel meetisIcon = null;
    static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static int windowWidth = gd.getDisplayMode().getWidth();
    static int windowHeight = gd.getDisplayMode().getHeight();
    static int screenSizeAverage = ((windowHeight + windowWidth) / 110);
    static Font meetisFont = new Font("Arial", Font.BOLD, screenSizeAverage);
    static Font meetisFont2 = new Font("Consolas", Font.BOLD, screenSizeAverage * 2);
    static Font averagedFont = new Font("Arial", Font.BOLD, screenSizeAverage);
    static Font listFont = new Font("Arial", Font.BOLD, (screenSizeAverage * 3 / 5 * 5 / 4));
    public static void main(String[] args) {
     try {
            robot_TOGGLE_STREAM_NEW_SCRATCH(280);
        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }
        packMeetisIcon();
        zZZ(3);
        frontWindowAlwaysOnTop();
        zZZ(5);



        Thread UpdateThread = new Thread(() -> {
            do {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("MeetisTalking.txt"));
                    if (br != null & !br.equals("")) {
                        String checkTalking = br.readLine();
                        if (checkTalking.equalsIgnoreCase("Talking")) {
                            talking = true;
                            try {
                                Random rand1 = new Random();
                                meetisLogo = ImageIO.read(new File("src\\resources\\meetis_logo_wht_glow_talk.png"));
                                meetisIcon.setIcon(new ImageIcon(meetisLogo));
                                meetisIcon.updateUI();
                                frame2.repaint();
                                TimeUnit.MILLISECONDS.sleep(50 + rand1.nextInt(600));
                                meetisLogo = ImageIO.read(new File("src\\resources\\meetis_logo_wht_glow.png"));
                                meetisIcon.setIcon(new ImageIcon(meetisLogo));
                                meetisIcon.updateUI();
                                frame2.repaint();
                                TimeUnit.MILLISECONDS.sleep(50 + rand1.nextInt(900));
                            } catch (Exception ignore) {
                            }
                        }
                        if (checkTalking.equalsIgnoreCase("Not Talking")) {
                            talking = false;
                            try {
                                meetisLogo = ImageIO.read(new File("src\\resources\\meetis_logo_wht_glow.png"));
                                meetisIcon.setIcon(new ImageIcon(meetisLogo));
                                meetisIcon.updateUI();
                                frame2.repaint();
                            } catch (Exception ignore) {
                            }
                        }
                    }
                    br.close();
                } catch (Exception ignore) {
                }
            } while (true);
        });
        UpdateThread.start();
        Thread GoogleEarthThread = new Thread(() -> {
            do {
                if (GoogleEarthActive()) {
                    try {
                        String tempGooglePosition = googleEarthPosition;
                        TimeUnit.SECONDS.sleep(2);
                        BufferedReader br = new BufferedReader(new FileReader("Google_Earth_Position.txt"));
                        if (br != null & !br.equals("")) {
                            googleEarthPosition = br.readLine();
                        }
                        if (!tempGooglePosition.equalsIgnoreCase(googleEarthPosition) && googleEarthPosition != "") {
                            Robot r = new Robot();
                            zZ(500);
                            r.mouseMove(27, 160);
                            r.mousePress(InputEvent.BUTTON1_MASK);
                            r.mouseRelease(InputEvent.BUTTON1_MASK);
                            zZ(500);
                            r.mouseMove(109, 97);
                            r.mousePress(InputEvent.BUTTON1_MASK);
                            r.mouseRelease(InputEvent.BUTTON1_MASK);
                            zZ(500);
                            r.mousePress(InputEvent.BUTTON1_MASK);
                            r.mouseRelease(InputEvent.BUTTON1_MASK);
                            zZ(500);
                            r.keyPress(KeyEvent.VK_CONTROL);
                            r.keyPress(KeyEvent.VK_A);
                            zZ(300);
                            r.keyRelease(KeyEvent.VK_A);
                            r.keyRelease(KeyEvent.VK_CONTROL);
                            zZ(500);
                            r.keyPress(KeyEvent.VK_DELETE);
                            r.keyRelease(KeyEvent.VK_DELETE);
                            zZ(500);
                            r.mousePress(InputEvent.BUTTON1_MASK);
                            r.mouseRelease(InputEvent.BUTTON1_MASK);
                            zZ(200);
                            r.keyPress(KeyEvent.VK_CONTROL);
                            r.keyPress(KeyEvent.VK_A);
                            zZ(500);
                            r.keyRelease(KeyEvent.VK_A);
                            r.keyRelease(KeyEvent.VK_CONTROL);
                            zZ(500);
                            r.keyPress(KeyEvent.VK_DELETE);
                            r.keyRelease(KeyEvent.VK_DELETE);
                            zZ(500);
                            StringSelection stringSelection = new StringSelection("");
                            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                            StringSelection selection = new StringSelection(googleEarthPosition);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(selection, selection);
                            r.keyPress(KeyEvent.VK_CONTROL);
                            r.keyPress(KeyEvent.VK_V);
                            zZ(200);
                            r.keyRelease(KeyEvent.VK_CONTROL);
                            r.keyRelease(KeyEvent.VK_V);
                            zZ(500);
                            r.keyPress(KeyEvent.VK_DOWN);
                            r.keyRelease(KeyEvent.VK_DOWN);
                            zZ(500);
                            r.keyPress(KeyEvent.VK_ENTER);
                            r.keyRelease(KeyEvent.VK_ENTER);
                            zZ(500);
                            r.keyPress(KeyEvent.VK_ENTER);
                            r.keyRelease(KeyEvent.VK_ENTER);
                            zZZ(3);
                            r.mouseMove(972, 117);
                            zZ(500);
                            r.mousePress(InputEvent.BUTTON1_MASK);
                            r.mouseRelease(InputEvent.BUTTON1_MASK);
                            zZZ(3);
                            takeScreenShot();
                            zZ(500);
                            if (checkIfPixleWhite(423,78)) {
                                r.mouseMove(427, 98);
                                zZ(500);
                                r.mousePress(InputEvent.BUTTON1_MASK);
                                r.mouseRelease(InputEvent.BUTTON1_MASK);
                            }
                        }
                    } catch (Exception ignore) {
                    }
                } else {
                    try {
                        zZZ(5);
                    } catch (Exception ignore) {
                    }
                }
            } while (true);
        });
        GoogleEarthThread.start();
        do {
            ArrayList listTitle = new ArrayList();
            try {
                runTwitter();//-------------------------------------------------------------------------
            } catch (InterruptedException e) {//----               _____(  Twitter  )_____
            } catch (AWTException e) {//------------               _______________________
            }
            boolean motion;
            try {
                Random random1 = new Random();
                moveMeetisIcon(250, 100 + random1.nextInt(150));
                writeToMeetisFramework("MeetisTalking.txt", "Not Talking");
                zZZ(2);
                robot_Click(490, 15, 5);
                robot_F5(40);
                robot_Click(659, 136, 3);



                zZZ(5);
                motion = false;
                takeScreenShot();
                int motionArray1 = colorOfPixel(350, 217);
                int motionArray2 = colorOfPixel(600, 317);
                int motionArray3 = colorOfPixel(750, 417);
                int motionArray4 = colorOfPixel(900, 217);
                System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                zZZ(5);
                takeScreenShot();
                if (motionArray1 != colorOfPixel(350, 217)) motion = true;
                if (motionArray2 != colorOfPixel(600, 317)) motion = true;
                if (motionArray3 != colorOfPixel(750, 417)) motion = true;
                if (motionArray4 != colorOfPixel(900, 217)) motion = true;
                System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                System.out.println("         Motion: " + motion);

                if (!motion)
                    robot_Click(550, 500, 5);

                motion = false;
                takeScreenShot();
                motionArray1 = colorOfPixel(350, 217);
                motionArray2 = colorOfPixel(600, 317);
                motionArray3 = colorOfPixel(750, 417);
                motionArray4 = colorOfPixel(900, 217);
                System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                zZZ(5);
                takeScreenShot();
                if (motionArray1 != colorOfPixel(350, 217)) motion = true;
                if (motionArray2 != colorOfPixel(600, 317)) motion = true;
                if (motionArray3 != colorOfPixel(750, 417)) motion = true;
                if (motionArray4 != colorOfPixel(900, 217)) motion = true;
                System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                System.out.println("         Motion: " + motion);

                if (!motion)
                    do {
                        motion = false;
                        takeScreenShot();
                        motionArray1 = colorOfPixel(350, 217);
                        motionArray2 = colorOfPixel(600, 317);
                        motionArray3 = colorOfPixel(750, 417);
                        motionArray4 = colorOfPixel(900, 217);
                        System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                        zZZ(3);
                        takeScreenShot();
                        if (motionArray1 != colorOfPixel(350, 217)) motion = true;
                        if (motionArray2 != colorOfPixel(600, 317)) motion = true;
                        if (motionArray3 != colorOfPixel(750, 417)) motion = true;
                        if (motionArray4 != colorOfPixel(900, 217)) motion = true;
                        System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                        System.out.println("         Motion: " + motion);
                        motion = false;
                        takeScreenShot();
                        motionArray1 = colorOfPixel(350, 217);
                        motionArray2 = colorOfPixel(600, 317);
                        motionArray3 = colorOfPixel(750, 417);
                        motionArray4 = colorOfPixel(900, 217);
                        System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                        zZZ(3);
                        takeScreenShot();
                        if (motionArray1 != colorOfPixel(350, 217)) motion = true;
                        if (motionArray2 != colorOfPixel(600, 317)) motion = true;
                        if (motionArray3 != colorOfPixel(750, 417)) motion = true;
                        if (motionArray4 != colorOfPixel(900, 217)) motion = true;
                        System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                        System.out.println("         Motion: " + motion);

                            try {
                                robot_Click(490, 15, 2);
                                robot_F5(40);
                                robot_Click(659, 136, 5);
                            } catch (Exception ignore) {
                            }

                    } while (!motion);



                Runtime rt1 = Runtime.getRuntime();
                Process proc1 = null;
                try {
                    proc1 = rt1.exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar Count.jar"});
                } catch (Exception e) {}
                try{
                    PrintWriter writer = new PrintWriter("MEETIS_RUNTIME.txt");
                    writer.println(ManagementFactory.getRuntimeMXBean().getName());
                    System.out.println("Runtime: ");
                    System.out.println(ManagementFactory.getRuntimeMXBean().getName());
                    writer.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    zZZ(5);
                } catch (Exception e) { }
                String everything = null;
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader("Count_Log.txt"));
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                Random random = new Random();
                moveMeetisIcon(200 + random.nextInt(500), 215);
                everything = getString(everything, br);
                System.out.println(everything);
                String[] parts = everything.split("@");
                String part1 = parts[0];
                try {
                    proc1 = rt1.exec("taskkill /F /PID " + part1);
                    proc1 = rt1.exec("taskkill /f /im cmd.exe") ;
                } catch (Exception e) {}
                try {
                    zZZ(1);
                    proc1 = rt1.exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar MEETIS.jar"});
                    zZZ(8);
                   // frontWindowAlwaysOnTop();
                    zZZ(1);
                    Robot r = new Robot();
                    r.keyPress(KeyEvent.VK_ALT);
                    r.keyPress(KeyEvent.VK_A);
                    zZ(200);
                    r.keyRelease(KeyEvent.VK_A);
                    r.keyRelease(KeyEvent.VK_ALT);
                    zZZ(1);

                } catch (Exception e) {}
                try {
                    br = new BufferedReader(new FileReader("MEETIS_RUNTIME.txt"));
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                everything = getString(everything, br);
                System.out.println(everything);
                String[] parts1 = everything.split("@");
                String part2 = parts1[0];
                try {
                        Random rand = new Random();
                        zZZZ(5 + rand.nextInt(15));

                    proc1 = rt1.exec("taskkill /F /PID " + part2);
                    proc1 = rt1.exec("taskkill /f /im cmd.exe");

                } catch (Exception e) {}
                writeToMeetisFramework("MeetisStatus.txt", "Ready");
            } catch (Exception ignore) {
            }
            zZZ(8);
            robot_Click(551, 500, 2);
            robot_Click(288, 12, 3);
            GoogleEarthActive(true);
            zZZ(15);//--------------------------------------------------------------------------
            runProbe();//---------------------                    ( Probe )*
            waitTillReady();
            writeToMeetisFramework("MeetisStatus.txt", "Crawl");
            writeToMeetisFramework("MeetisTalking.txt", "Not Talking");
            googleEarthStatus = false;
            Random rand = new Random();
            if (rand.nextInt(3) == 2)
                try {
                    moveMeetisIcon(375, 281);
                    zZZ(10);
                    robot_TOGGLE_STREAM_NEW(60);
                } catch (AWTException e) {
                }
        } while (true);



    }

    private static void robot_TOGGLE_STREAM(int seconds) throws AWTException{
        Robot r = new Robot();
        zZZ(1);

        //robot_Click(81, 747, 3);
   /*     robot_Click(835, 746, 2);
        robot_Click(139, 722, 1);
        robot_Click(632, 503, 2);
        robot_Click(980, 447, 1);*/


        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_CONTROL);

        /*r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_SHIFT);
        r.keyPress(KeyEvent.VK_ALT);
        r.keyPress(KeyEvent.VK_Z);
        zZ(500);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_SHIFT);
        r.keyRelease(KeyEvent.VK_ALT);
        r.keyRelease(KeyEvent.VK_Z);
*/
        System.out.println("wait");
        zZZ(seconds);
/*

        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_SHIFT);
        r.keyPress(KeyEvent.VK_ALT);
        r.keyPress(KeyEvent.VK_Z);
        zZ(500);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_SHIFT);
        r.keyRelease(KeyEvent.VK_ALT);
        r.keyRelease(KeyEvent.VK_Z);
        zZ(500);
*/

/*        robot_Click(835, 746, 2);
        robot_Click(139, 722, 1);
        robot_Click(632, 503, 2);
        robot_Click(980, 447, 1);*/

        //robot_Click(81, 747, 1);

        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_CONTROL);

    }
    private static void robot_TOGGLE_STREAM_NEW (int seconds) throws AWTException{
        Robot r = new Robot();
        zZZ(1);

        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_CONTROL);

        System.out.println("wait");
        zZZ(seconds);

        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_CONTROL);

        ArrayList listTitle = new ArrayList();
        readLoocalToArray("Data_Points.txt", listTitle);
        restartSEQUENCE(listTitle);

        zZZ(4);
/*        robot_Click(930, 265, 4);
        robot_Click(483, 248, 2);
        robot_Click(537, 11, 2);*/
        robot_F11(3);
        robot_Click(769, 14, 2);

    }
    private static void robot_TOGGLE_STREAM_NEW_SCRATCH (int seconds) throws AWTException{
        Robot r = new Robot();
        zZZ(1);

        try {
            new ProcessBuilder("cmd", "/c", "startOBS.bat").inheritIO().start().waitFor();

        //    Process process = new ProcessBuilder("cmd", "C:\\Program Files\\obs-studio\\bin\\64bit\\obs64.exe").start();
        //    Process p = Runtime.getRuntime().exec("C:\\Program Files\\obs-studio\\bin\\64bit\\obs64.exe");
        } catch (Exception e) {
            e.printStackTrace();
        }


/*        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
*/
/*
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_CONTROL);*/
/*

        ArrayList listTitle = new ArrayList();
        readLoocalToArray("Data_Points.txt", listTitle);
        restartSEQUENCE(listTitle);

        System.out.println("wait");
        zZZ(seconds);

        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_I);
        r.keyRelease(KeyEvent.VK_CONTROL);

        zZZ(4);
        robot_Click(930, 265, 4);
        robot_Click(483, 248, 2);
        robot_Click(537, 11, 2);
        robot_F11(3);
        robot_Click(769, 14, 2);
*/

    }
    private static void runTwitter() throws AWTException, InterruptedException {
        Robot r = new Robot();
        GoogleEarthActive(false);
        writeToMeetisFramework("MeetisStatus.txt", "Attack Twitter");
        writeToMeetisFramework("MeetisTalking.txt", "Not Talking");
        zZ(500);
        moveMeetisIcon(750, 400);
        String exePath = "lib\\selenium\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
        WebDriver driver = new ChromeDriver();
        driver.get("https://twitter.com/MeetisSpeaks");
        zZZ(10);
        r.mouseMove(961, 196);
        zZZ(1);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZ(500);
        r.mouseMove(986, 114);
        zZ(500);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZ(500);
        r.mouseMove(954, 19);
        zZ(500);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZZ(1);
        StringSelection selection = new StringSelection("MeetisSpeaks");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        zZ(200);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
        zZ(200);
        r.keyPress(KeyEvent.VK_TAB);
        r.keyRelease(KeyEvent.VK_TAB);
        zZ(200);
        selection = new StringSelection(HashBar);
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        zZ(200);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        zZ(200);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
        zZ(200);
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
        zZZ(4);
        r.mouseMove(1004, 45);
        zZZ(1);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZZ(1);
        r.mouseMove(949, 235);
        zZZ(1);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZZ(1);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZZ(1);
        r.mouseMove(996, 241);
        zZZ(1);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZZ(1);
        r.mouseMove(30, 26);
        zZZ(1);
        moveMeetisIcon(450, 350);
        zZZ(1);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZZ(7);
        r.mouseMove(942, 759);
        zZZ(1);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZZ(1);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(215,0)");
        zZZ(10);
        moveMeetisIcon(50, 215);
        zZZ(1);
        boolean doneWithTwitter = false;
        do {
            int i = 0;
            StringBuilder sb = new StringBuilder();
            Random rand = new Random();
            int randomAmount = 5 + rand.nextInt(50);
            do {
                System.out.println(i + "---------------------------------------------");
                try {
                    do {
                        zZZ(1);
                    } while (Talking());
                    js.executeScript("document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].scrollIntoView();");
                    js.executeScript("window.scrollBy(0,-80)");
                    String twitterHeader = js.executeScript("return document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].parentElement.parentElement.innerText").toString();
                    System.out.println(twitterHeader);
                    String tHead = twitterHeader.replaceAll("Verified", "");
                    twitterHeader = tHead.replaceAll("account", "");
                    String[] breakApart = twitterHeader.split(" ");
                    boolean gate3 = true;
                    for (int j = 0; j < breakApart.length; j++) {
                        if (breakApart[j].contains("@") && gate3){
                            String[] sAry = breakApart[j].split("");
                            boolean gate1 = false;
                            for (int k = 0; k < sAry.length; k++) {
                                if (sAry[k].equals("@")) gate1 = true;
                                if (gate1){
                                    sb.append(sAry[k]);
                                }
                            }
                            sb.setLength(sb.length() - 1);
                            sb.append(". ");
                            String[] sAry1 = breakApart[j + 2].split("");
                            boolean gate2 = true;
                            for (int k = 0; k < sAry1.length; k++) {
                                if (sAry1[k].equals("m")) {
                                    sb.append(" minutes");
                                    gate2 = false;
                                } else if (sAry1[k].equals("h")){
                                    sb.append(" hours");
                                    gate2 = false;
                                } else if (sAry1[k].equals("d")){
                                    sb.append(" days");
                                    gate2 = false;
                                } else if (sAry1[k].equals("s")){
                                    sb.append(" seconds");
                                    gate2 = false;
                                }
                                if (gate2){
                                    sb.append(sAry1[k]);
                                }
                            }
                            sb.append(" ago. ");
                            gate3 = false;
                        }
                    }
                    sb.append(js.executeScript("return document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].innerText;").toString());
                } catch (Exception ignore) {
                }
                rand = new Random();
                if (rand.nextInt(2) == 1) {
                    verbalize(sb.toString());
                    do {
                        zZZ(1);
                    } while (Talking());
                }
                sb.setLength(0);
                String fullHTMLnode = "";
                String htmlNode = null;
                String Description = null;
                boolean foundGIF = false;
                boolean foundVideo = false;
                boolean foundDescription = false;
                try {
                    htmlNode = js.executeScript(
                            "var iframe = document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].parentElement.parentElement.children[2].children[0].children[0];" +
                                    "var innerDoc = (iframe.contentDocument) ? iframe.contentDocument : iframe.contentWindow.document;" +
                                    "return innerDoc.getElementsByClassName(\"u-block TwitterCardsGrid-col--spacerTop SummaryCard-destination\")[0].innerText;"
                    ).toString();
                    if (htmlNode.contains("youtube")){
                        foundVideo = true;
                    }
                } catch (Exception ignore) {
                    System.out.println("Failed parse [" + i + "] " + htmlNode);
                }
                try {
                    Description = js.executeScript(
                            "var iframe = document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].parentElement.parentElement.children[2].children[0].children[0];" +
                                    "var innerDoc = (iframe.contentDocument) ? iframe.contentDocument : iframe.contentWindow.document;" +
                                    "return innerDoc.getElementsByClassName(\"tcu-resetMargin u-block TwitterCardsGrid-col--spacerTop tcu-textEllipse--multiline\")[0].innerText;"
                    ).toString();
                    if (htmlNode != null){
                        foundDescription = true;
                    }
                } catch (Exception ignore) {
                    System.out.println("Failed parse [" + i + "] " + htmlNode);
                }
                try {
                    htmlNode = js.executeScript("return document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].parentElement.parentElement.children[2].innerHTML").toString();
                    fullHTMLnode = js.executeScript("return document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].parentElement.parentElement.innerHTML").toString();
                    System.out.println(htmlNode);
                } catch (Exception ignore) {
                }
                zZZ(1);
                try {
                    String[] nodes = htmlNode.split("<");
                    for (int j = 0; j < nodes.length; j++) {
                        if (nodes[j].contains("AdaptiveMedia")) {// && nodes[j].contains("allow-expansion")
                            foundVideo = true;
                        }
                    }
                    if (fullHTMLnode.contains("gif")) foundGIF = true;
                    if (!foundGIF) {
                        System.out.println("Found AdaptiveMedia --------------------- *");
                        boolean motion;
                        boolean clickMotion = true;
                        //js.executeScript("window.scrollBy(0,50)");
                        int timeStop = 30;
                        do {
                            motion = false;
                            takeScreenShot();
                            int motionArray1 = colorOfPixel(400, 317);
                            int motionArray2 = colorOfPixel(550, 317);
                            int motionArray3 = colorOfPixel(750, 317);
                            int motionArray4 = colorOfPixel(900, 317);
                            System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                            zZZ(3);
                            takeScreenShot();
                            if (motionArray1 != colorOfPixel(400, 317)) motion = true;
                            if (motionArray2 != colorOfPixel(550, 317)) motion = true;
                            if (motionArray3 != colorOfPixel(750, 317)) motion = true;
                            if (motionArray4 != colorOfPixel(900, 317)) motion = true;
                            System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                            System.out.println("         Motion: " + motion);
                            timeStop--;
                            if (motion && clickMotion) {
                                try {
                                    //js.executeScript("window.scrollBy(0,100)");
                                    r.mouseMove(619, 320);
                                    TimeUnit.SECONDS.sleep(1);
                                    r.mousePress(InputEvent.BUTTON1_MASK);
                                    TimeUnit.MILLISECONDS.sleep(1);
                                    r.mouseRelease(InputEvent.BUTTON1_MASK);
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (Exception ignore) {
                                }
                                robot_Move(100, 700);
                                clickMotion = false;
                            }
                        } while (motion && timeStop > 0);
                        System.out.println("Motion Broken --------------------- *");
                        if (foundDescription){
                            js.executeScript(
                                    "var iframe = document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].parentElement.parentElement.children[2].children[0].children[0];" +
                                            "var innerDoc = (iframe.contentDocument) ? iframe.contentDocument : iframe.contentWindow.document;" +
                                            "innerDoc.getElementsByClassName(\"TwitterCardsGrid-col--spacerTop\")[0].scrollIntoView();"
                            );
                            js.executeScript("window.scrollBy(0,-150)");
                            verbalize(Description);
                            do {
                                zZZ(2);
                            } while (Talking());
                            String youtube = js.executeScript(
                                    "var iframe = document.getElementsByClassName(\"TweetTextSize  js-tweet-text tweet-text\")[" + i + "].parentElement.parentElement.children[2].children[0].children[0];" +
                                            "var innerDoc = (iframe.contentDocument) ? iframe.contentDocument : iframe.contentWindow.document;" +
                                            "return innerDoc.getElementsByClassName(\"tcu-resetMargin u-block TwitterCardsGrid-col--spacerTop tcu-textEllipse--multiline\")[1].innerText;"
                            ).toString();
                            System.out.println(youtube);
                            if (youtube.contains(youtube)){
                                TimeUnit.SECONDS.sleep(1);
                                robot_Click(619, 290, 1);
                                robot_Move(200, 700);
                                timeStop = 200;
                                do {
                                    motion = false;
                                    takeScreenShot();
                                    int motionArray1 = colorOfPixel(400, 317);
                                    int motionArray2 = colorOfPixel(550, 317);
                                    int motionArray3 = colorOfPixel(750, 317);
                                    int motionArray4 = colorOfPixel(900, 317);
                                    System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                                    zZZ(7);
                                    takeScreenShot();
                                    if (motionArray1 != colorOfPixel(400, 317)) motion = true;
                                    if (motionArray2 != colorOfPixel(550, 317)) motion = true;
                                    if (motionArray3 != colorOfPixel(750, 317)) motion = true;
                                    if (motionArray4 != colorOfPixel(900, 317)) motion = true;
                                    System.out.println(motionArray1 + " - " + motionArray2 + " - " + motionArray3 + " - " + motionArray4);
                                    System.out.println("         Motion: " + motion);
                                    timeStop--;
                                } while (motion && timeStop > 0);
                            }
                        }
                    }
                } catch (Exception ignore) {
                }
                meetisUpdateGUI();
                i++;

            } while (i < randomAmount);

            if (!(rand.nextInt(2) == 2)) {
                zZZ(2);
                robot_Click(187, 644, 1);
                robot_F5(12);
                robot_Home(5);
            } else {
                doneWithTwitter = true;
            }
            zZZ(1);
        } while (!doneWithTwitter);
        zZZ(2);
        robot_Move(59, 175, 1);
        robot_Click(59, 175, 1);
        r.keyPress(KeyEvent.VK_F11);
        r.keyRelease(KeyEvent.VK_F11);
        zZ(500);
        r.mouseMove(999, 7);
        zZ(500);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZZ(2);
        writeToMeetisFramework("MeetisStatus.txt", "Ready");
    }

    private static void robot_Home(int seconds) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_HOME);
            r.keyRelease(KeyEvent.VK_HOME);
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignore) {
        }
    }

    private static void robot_F5(int seconds) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_F5);
            r.keyRelease(KeyEvent.VK_F5);
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignore) {
        }
    }

    private static void runProbe() {
        //driver.get("https://earth.google.com/web/");
        zZZ(4);
        moveMeetisIcon(720, 200);
        GoogleEarthActive(true);
        writeToMeetisFramework("MeetisStatus.txt", "Probe Data Points");
        writeToMeetisFramework("MeetisTalking.txt", "Not Talking");
        executeCommand("java -jar Probe.jar true");
        zZZ(3);
        robot_Click(598, 580, 1);
        frontOpacityActive();
        frontWindowAlwaysOnTop();
        zZZ(1);
        robot_Click(903, 263, 1);
        robot_Move(100, 507);
    }
    private static void frontOpacityActive() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_WINDOWS);
            r.keyPress(KeyEvent.VK_A);
            r.keyRelease(KeyEvent.VK_WINDOWS);
            r.keyRelease(KeyEvent.VK_A);
        } catch (Exception ignore) {
        }
    }
    private static void frontOpacityActive(int wait) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_WINDOWS);
            r.keyPress(KeyEvent.VK_A);
            r.keyRelease(KeyEvent.VK_WINDOWS);
            r.keyRelease(KeyEvent.VK_A);
            TimeUnit.SECONDS.sleep(wait);
        } catch (Exception ignore) {
        }
    }
    private static void executeCommand(String command) {
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "cmd", "/k", command});
        } catch (Exception ignore) {
        }
    }
    private static void robot_Enter() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception ignore) {
        }
    }
    private static void robot_Enter(int wait) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
            TimeUnit.SECONDS.sleep(wait);
        } catch (Exception ignore) {
        }
    }
    private static void robot_CtrlV() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_V);
        } catch (Exception ignore) {
        }
    }
    private static void robot_CtrlV(int wait) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_V);
            TimeUnit.SECONDS.sleep(wait);
        } catch (Exception ignore) {
        }
    }
    private static void robot_CtrlC() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_C);
        } catch (Exception ignore) {
        }
    }
    private static void robot_CtrlC(int wait) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_C);
            TimeUnit.SECONDS.sleep(wait);
        } catch (Exception ignore) {
        }
    }
    private static void robot_Tab() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_TAB);
            r.keyRelease(KeyEvent.VK_TAB);
        } catch (Exception ignore) {
        }
    }
    private static void robot_Tab(int wait) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_TAB);
            r.keyRelease(KeyEvent.VK_TAB);
            TimeUnit.SECONDS.sleep(wait);
        } catch (Exception ignore) {
        }
    }
    private static Coordinates scanLineColor(int x1, int x2, int y, int colorRGB) {
        int hitCount = 0;
        Coordinates coordinates = new Coordinates();
        for (int i = x1; i < x2 && hitCount < 4; i++) {
            int color = colorOfPixel(i, y);
            if (color > colorRGB - 5 && color < colorRGB + 5){
                coordinates.setXvalue(i);
                coordinates.setYvalue(y);
                hitCount++;
            }
        }
        System.out.println("(x:" + coordinates.x + " y:" + coordinates.y + ") found config button xfire");
        return coordinates;
    }
    private static String generateTagsFromDataPoints(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder("");
        ArrayList<String> tArray = new ArrayList<>();
        for (int i = 0, j = 0; i < list.size(); i++, j++) {
            switch (j) {
                case 0: {
                    break;
                }
                case 1: {
                    String[] sTemp = list.get(i).split(" ");
                    for (int k = 0; k < sTemp.length; k++) {
                        if (list.get(i).length() > 2){
                            tArray.add(sTemp[k]);
                        }
                    }
                    break;
                }
                case 2: {
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
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            sb.append(tArray.get(rand.nextInt(tArray.size()))).append(", ");
        }
        sb.append(tArray.get(rand.nextInt(tArray.size())));
        return sb.toString();
    }
    private static String generateDescriptionFromDataPoints(ArrayList arrayList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0; i < arrayList.size(); i++, j++) {
            switch (j) {
                case 0: {
                    break;
                }
                case 1: {
                    sb.append(arrayList.get(i)).append(" ");
                    break;
                }
                case 2: {
                    sb.append(arrayList.get(i)).append(" ");
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
        return sb.toString();
    }
    private static String generateTitleFromDataPoints(ArrayList arrayList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0; i < arrayList.size() && sb.length() < 70; i++, j++) {
            switch (j) {
                case 0: {
                    break;
                }
                case 1: {
                    String[] sTemp = arrayList.get(i).toString().split(" ");
                    for (int k = 0; k < sTemp.length && k < 3; k++) {
                        sb.append(sTemp[k]).append(" ");
                    }
                    break;                }
                case 2: {
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
        return sb.toString();
    }
    private static void readLoocalToArray(String local, ArrayList arrayRefrence) {
        BufferedReader bufferedReader = null;
        String line = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(local));
            while ((line = bufferedReader.readLine()) != null) {
                arrayRefrence.add(line);
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readDataPoints(ArrayList<String> list) {
        BufferedReader bufferedReader;
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader("Data_Points.txt"));
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (String aList : list) {
            System.out.println(aList);
        }
    }
    private static void robot_Delete() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_DELETE);
            r.keyRelease(KeyEvent.VK_DELETE);
        } catch (Exception ignore) {
        }
    }
    private static void robot_Delete(int wait) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_DELETE);
            r.keyRelease(KeyEvent.VK_DELETE);
            TimeUnit.SECONDS.sleep(wait);
        } catch (Exception ignore) {
        }
    }
    private static void robot_CtrlA() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_A);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_A);
        } catch (Exception ignore) {
        }
    }
    private static void robot_CtrlA(int wait) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_A);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_A);
            TimeUnit.SECONDS.sleep(wait);
        } catch (Exception ignore) {
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
    private static void frontWindowAlwaysOnTop() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_ALT);
            r.keyPress(KeyEvent.VK_T);
            zZ(1);
            r.keyRelease(KeyEvent.VK_T);
            r.keyRelease(KeyEvent.VK_ALT);
            r.keyRelease(KeyEvent.VK_CONTROL);
        } catch (Exception ignore) {
        }
    }
    private static void packMeetisIcon() {
        try {
            meetisLogo = ImageIO.read(new File("src\\resources\\meetis_logo_wht_glow.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        meetisIcon = new JLabel(new ImageIcon(meetisLogo));
        frame2.setPreferredSize(new Dimension(windowWidth, windowHeight));
        frame2.setUndecorated(true);
        frame2.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.0f));
        frame2.isAlwaysOnTop();
        frame2.setLayout(null);
        frame2.add(meetisIcon);
        meetisIcon.setLocation(750, 400);
        meetisIcon.setSize(277, 170);
        frame2.setVisible(true);
        frame2.pack();
        frame2.toFront();
        frame2.repaint();
    }
    private static void packMeetisIcon(int x, int y) {
        try {
            meetisLogo = ImageIO.read(new File("src\\resources\\meetis_logo_wht_glow.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JLabel meetisIcon = new JLabel(new ImageIcon(meetisLogo));
        frame2.setPreferredSize(new Dimension(windowWidth, windowHeight));
        frame2.setUndecorated(true);
        frame2.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.0f));
        frame2.isAlwaysOnTop();
        frame2.setLayout(null);
        frame2.add(meetisIcon);
        meetisIcon.setLocation(x, y);
        meetisIcon.setSize(277, 170);
        frame2.setVisible(true);
        frame2.pack();
        frame2.toFront();
        frame2.repaint();
    }
    public static void moveMeetisIcon(int x, int y) {
        meetisIcon.setLocation(x, y);
//        meetisIcon.updateUI();
        frame2.repaint();
    }
    private static void meetisUpdateGUI() {
        frame2.isAlwaysOnTop();
        frame2.pack();
        frame2.toFront();
        frame2.repaint();
    }
    public static void pause() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception ignore) {
        }
    }
    public static void verbalize(String phrase) {
        while (Talking()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception ignore) {
            }
        }
        String http = "";
        String[] breakMe = phrase.split(" ");
        for (int i = 0; i < breakMe.length; i++) {
            if (breakMe[i].contains("http")) {
                http = breakMe[i];
                breakMe[i] = null;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < breakMe.length; i++) {
            if (breakMe[i] != null) {
                sb.append(breakMe[i]);
                sb.append(" ");
            }
        }
        String removedHyperlink = sb.toString();
        phrase = removedHyperlink.replaceAll("[^A-Za-z0-9@!$%&()., ]", " ");
        writeToMeetisFramework("phrase.txt", phrase);
        try {
            if (http.length() > 1) {
                Runtime.getRuntime().exec(new String[]{
                        "cmd", "/c", "start", "cmd", "/k", "java -jar Verbalize.jar "
                  /*      + "\"" + phrase + "\"" + " "*/ + "\"" + http + "\""
                });
            } else {
                Runtime.getRuntime().exec(new String[]{
                        "cmd", "/c", "start", "cmd", "/k", "java -jar Verbalize.jar"
                 //       + "\"" + phrase + "\""
                });
            }
        } catch (Exception ignore) {
        }
    }
    public static void copyStringIntoClipboard(String phrase){
        StringSelection stringSelection = new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        StringSelection selection = new StringSelection(phrase);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
    public static boolean Talking(){
        String tString = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("MeetisTalking.txt"));
            if (br != null & !br.equals("")) {
                tString = br.readLine();
            }
        } catch (Exception ignore) {
        }
        if (tString.equalsIgnoreCase("Talking")){
            return true;
        } else {
            return false;
        }
    }
    public static void takeScreenShot() {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "jpg", new File("ScreenShot\\screenshot.jpg"));
        } catch (Exception e) {
            System.out.println("FAILED to take SCREEN SHOT");
            System.out.println(e.getMessage());
        }
    }
    public static boolean checkIfPixleWhite(int x, int y) {
        boolean isWhite = false;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("ScreenShot\\screenshot.jpg"));
        } catch (Exception e) {
            System.out.println("ScreenShot FAILED READ");
            System.out.println(e.getMessage());
        }
        int clr = image.getRGB(x, y);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;
        if (red + blue + green > 650) {
            isWhite = true;
        }
        return isWhite;
    }
    public static boolean checkIfPixleBlack(int x, int y) {
        boolean isBlack = false;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("ScreenShot\\screenshot.jpg"));
        } catch (Exception e) {
            System.out.println("ScreenShot FAILED READ");
            System.out.println(e.getMessage());
        }
        int clr = image.getRGB(x, y);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;
        if (red + blue + green < 50) {
            isBlack = true;
        }
        return isBlack;
    }
    public static int colorOfPixel(int x, int y) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("ScreenShot\\screenshot.jpg"));
        } catch (Exception e) {
            System.out.println("ScreenShot FAILED READ");
            System.out.println(e.getMessage());
        }
        int clr = image.getRGB(x, y);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;
        return red + green + blue;
    }
/*    private static boolean waitForJSandJQueryToLoad(WebDriver driver) {
        return (Boolean) ((JavascriptExecutor) driver).executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
    }*/
    private static void writeToMeetisFramework(String fileName, String writeLineUpdate) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            writer.println(writeLineUpdate);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void waitTillReady() {
        do {
            try {
                TimeUnit.SECONDS.sleep(5);
                try {
                } catch (Exception ignore) {
                }
            } catch (InterruptedException e) {
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader("MeetisStatus.txt"));
                if (br != null & !br.equals(""))
                    Status = br.readLine();
                br.close();
            } catch (Exception e) {
            }
        } while (!Status.equalsIgnoreCase("Ready"));
    }
    public static boolean loadProgress(JProgressBar progressBar) {
        if (progressBar.getValue() < 100) {
            Random rand = new Random();
            int random = rand.nextInt(5);
            int progress = progressBar.getValue() + random;
            progressBar.setValue(progress);
            return true;
        } else
            return false;
    }
    private static boolean testConnection() throws MalformedURLException, IOException {
        final URL url = new URL("http://www.lukeholman.net/meetis.php");
        final URLConnection connection;
        connection = url.openConnection();
        connection.connect();
        return true;
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
    private static List<String> trimHTTP(List<String> twitterTrends) {
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
    private static void waitRandomShort() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(200);
        for (int i = 0; i < 40; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
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
    private static int findX() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        return gd.getDisplayMode().getWidth();
    }
    private static int findCenterX() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int windowWidth = gd.getDisplayMode().getWidth();
        return (windowWidth / 2);
    }
    private static int findY() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        return gd.getDisplayMode().getHeight();
    }
    private static int findCenterY() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int windowHeight = gd.getDisplayMode().getHeight();
        return (windowHeight / 2);
    }
    private static Dimension findMidline() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int windowWidth = gd.getDisplayMode().getWidth();
        int windowHeight = gd.getDisplayMode().getHeight();
        return new Dimension((windowWidth / 2), (windowHeight / 2));
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
    public static void TwitterActive(Boolean setTwitterStatus) {
        if (setTwitterStatus) {
            try {
                PrintWriter writer = new PrintWriter("TwitterStatus.txt");
                writer.println("True");
                writer.close();
            } catch (Exception ignore) {
            }
        } else {
            try {
                PrintWriter writer = new PrintWriter("TwitterStatus.txt");
                writer.println("False");
                writer.close();
            } catch (Exception ignore) {
            }
        }
    }
    public static boolean TwitterActive() {
        try {
            String tString = "";
            BufferedReader br = new BufferedReader(new FileReader("TwitterStatus.txt"));
            if (br != null & !br.equals("")) {
                tString = br.readLine();
            }
            if (tString.contains("True")) {
                twitterStatus = true;
                return true;
            } else {
                twitterStatus = false;
                return false;
            }
        } catch (Exception ignore) {
        }
        return false;
    }
    public static void closeCommandPrompt() {
        try {
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
        } catch (Exception ignore) {
        }
    }
    public static void robot_Click(int x, int y){
        try {
            Robot r = new Robot();
            r.mouseMove(x, y);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
        } catch (Exception ignore) {
        }
    }
    public static void robot_Click(int x, int y, int wait){
        try {
            Robot r = new Robot();
            r.mouseMove(x, y);
            TimeUnit.MILLISECONDS.sleep(100);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            TimeUnit.SECONDS.sleep(wait);
        } catch (Exception ignore) {
        }
    }
    public static void robot_Move(int x, int y){
        try {
            Robot r = new Robot();
            r.mouseMove(x, y);
        } catch (Exception ignore) {
        }
    }
    public static void robot_Move(int x, int y, int seconds){
        try {
            Robot r = new Robot();
            r.mouseMove(x, y);
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignore) {
        }
    }
    private static String getString(String everything, BufferedReader br) {
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return everything;
    }
    private static void restartSEQUENCE(ArrayList arrayList) throws AWTException {

        zZZ(2);

        robot_Click(611, 15, 5);
        robot_Click(228, 84, 5);
        robot_Click(960, 93, 5);
        robot_Click(787, 254, 6);
        robot_Click(80, 290, 5);
        robot_F11(5);
        robot_Click(355, 655, 4);
        robot_Click(355, 655, 4);
        Robot r = new Robot();
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZ(500);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZ(500);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_A);
        zZ(300);
        r.keyRelease(KeyEvent.VK_A);
        r.keyRelease(KeyEvent.VK_CONTROL);
        zZ(500);
        r.keyPress(KeyEvent.VK_DELETE);
        r.keyRelease(KeyEvent.VK_DELETE);
        zZ(500);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZ(200);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_A);
        zZ(500);
        r.keyRelease(KeyEvent.VK_A);
        r.keyRelease(KeyEvent.VK_CONTROL);
        zZ(500);
        r.keyPress(KeyEvent.VK_DELETE);
        r.keyRelease(KeyEvent.VK_DELETE);
        zZ(500);
        StringSelection stringSelection = new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        StringSelection selection = new StringSelection("MEETIS " + generateTitleFromDataPoints(arrayList));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        zZ(200);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
        robot_Click(324, 695, 1);
        robot_Click(324, 695, 1);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZ(500);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZ(500);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_A);
        zZ(300);
        r.keyRelease(KeyEvent.VK_A);
        r.keyRelease(KeyEvent.VK_CONTROL);
        zZ(500);
        r.keyPress(KeyEvent.VK_DELETE);
        r.keyRelease(KeyEvent.VK_DELETE);
        zZ(500);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        zZ(200);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_A);
        zZ(500);
        r.keyRelease(KeyEvent.VK_A);
        r.keyRelease(KeyEvent.VK_CONTROL);
        zZ(500);
        r.keyPress(KeyEvent.VK_DELETE);
        r.keyRelease(KeyEvent.VK_DELETE);
        zZ(500);
        stringSelection = new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        selection = new StringSelection("MEETIS @MeetisSpeaks " +
                generateDescriptionFromDataPoints(arrayList));
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        zZ(200);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
        zZZ(2);
        robot_Click(501, 90, 2);
    }

    private static void robot_F11(int seconds) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_F11);
            r.keyRelease(KeyEvent.VK_F11);
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignore) {
        }
    }

}
