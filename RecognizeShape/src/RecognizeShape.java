import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Box extends JComponent {
    public void paint(Graphics g) {
        g.drawRect(30, 30, 200, 200);
    }
}
class Dot extends JPanel {
    private Random rand = new Random();
    private Color color = new Color(1f, 0f, 0f, 1f);
    private int loop = 0;
    private float fAlpha = 1.0f;
    final private float increment = 0.1f;
    private int size = 10;
    private int offset = 5;
    private int x;
    private int y;
    public Dot(Coordinates coordinates) {
        //    System.out.println("Dot Created");
        this.x = coordinates.x;
        this.y = coordinates.y;
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                color = new Color(1f, 0f, 0f, fAlpha);
                repaint();
                fAlpha = fAlpha - increment;
                loop++;
                size--;
                //    System.out.println("timer " + loop + " fAlpha: " + fAlpha);
                if (loop % 2 == 0) offset++;
                if (loop > 8) color = new Color(1f, 0f, 0f, 0f);
                if (loop == 9) ((Timer) evt.getSource()).stop();
            }
        };
        new Timer(rand.nextInt(2000), taskPerformer).start();
    }
    @Override
    public void paint(Graphics g) {
        setBackground(new Color(0f, 0f, 0f, 0f));
        //    super.paint(g);
        g.setColor(color);
        g.fillOval(x - 10 + offset, y - 10 + offset, size, size);
        getParent().repaint();
    }
}
class Region {
    int x;
    int y;
    int width;
    int height;
    public Region() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }
    public Region(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public void copy(Region region) {
        this.x = region.x;
        this.y = region.y;
        this.width = region.width;
        this.height = region.height;
    }
}
class Coordinates {
    int x;
    int y;
    public Coordinates() {
        x = 0;
        y = 0;
    }
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void north() { this.y--; }
    public void east() { this.x++; }
    public void south() { this.y++; }
    public void west() { this.x--; }
    public void copy(Coordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(x:").append(x).append(", y:").append(y).append(")");
        return sb.toString();
    }
}

class Cursor extends JPanel {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int windowWidth = gd.getDisplayMode().getWidth();
    int windowHeight = gd.getDisplayMode().getHeight();
    public int tolerance = 25;
    public int[] colorSteps = new int[4];
    public int colorStep;
    public Region region = new Region();
    public static BufferedImage image;
    public Coordinates currentNode = new Coordinates();
    public Coordinates previousNode = new Coordinates();
    public Coordinates displayNode = new Coordinates();
    public String direction = "North";
    public Random rand = new Random();

    public Cursor(Frame frame, JLayeredPane jp, Coordinates coordinates) {
        try {
            image = ImageIO.read(new File("screenshot.jpg"));
        } catch (Exception e) {
            System.out.println("ScreenShot FAILED READ");
            System.out.println(e.getMessage());
        }
        currentNode.x = coordinates.x;
        currentNode.y = coordinates.y;
/*
        jp.add(new Dot(currentNode), 0);
        frame.pack();
*/
        if (coordinates.x > coordinates.y) {
            direction = "South";
            currentNode.y += (windowHeight / 10);
            System.out.println(direction);
        } else if (coordinates.x < coordinates.y) {
            direction = "East";
            System.out.println(direction);
        }
        rgbValue(currentNode);
        int nextColor;
        int previousColor;
        do {
            previousNode.copy(currentNode);
            previousColor = rgbValue(currentNode);
        //    System.out.println(currentNode.toString());
            if (direction.contains("South") && currentNode.y > (windowHeight - (windowHeight / 10))){
                direction = "Stop";
                System.out.println("Stop");
            }
            if (direction.contains("East") && currentNode.x == windowWidth) {
                direction = "Stop";
                System.out.println("Stop");
            }
            switch (direction) {
                case "South": {
                    currentNode.south();
                    nextColor = rgbValue(currentNode);
                    if (previousColor != nextColor){
                        jp.add(new Dot(currentNode), 0);
                        frame.pack();
                        System.out.println("Dot");
                    }
                    System.out.println(currentNode.toString() + ": Color: " + nextColor);
                    break;
                }
                case "East": {
                    currentNode.east();
                    nextColor = rgbValue(currentNode);
                    if (previousColor != nextColor){
                        jp.add(new Dot(currentNode), 0);
                        frame.pack();
                        System.out.println("Dot");
                    }
                    System.out.println(currentNode.toString() + ": Color: " + nextColor);
                    break;
                }
                case "Stop": {
                    direction = "Stop";
                    break;
                }
            }
        } while (!direction.equals("Stop"));
    }
    public Cursor(Frame f, Region region, JLayeredPane jp, Coordinates coordinates) throws IOException {
        this.region.copy(region);
        image = ImageIO.read(new File("ssRegion.jpg"));
        currentNode.x = region.width / 2;
        currentNode.y = region.height / 2;
        colorSteps[0] = rgbValue(currentNode);
        colorSteps[1] = rgbValue(currentNode);
        randomDirection();
        System.out.printf(direction + " :");
        do {
            displayNode.x = currentNode.x + region.x;
            displayNode.y = currentNode.y + region.y;
            if (rand.nextInt(3) == 2) {
                jp.add(new Dot(displayNode), 0);
                f.pack();
            }
            previousNode.copy(currentNode);
            switch (direction) {
                case "North": {
                    currentNode.north();
                    if (validBound(region)) {
                        currentNode.north();
                        int nextColor = rgbValue(currentNode);
                        if (nextColor < colorSteps[0] + tolerance &&
                                nextColor > colorSteps[0] - tolerance) {
                            addColor(nextColor);
                        } else {
                            System.out.println("rgbValue different " + currentNode.toString());
                            currentNode.south();
                            if (rgbValue(currentNode) == rgbValue(currentNode.x - 1, currentNode.y) &&
                                    rgbValue(currentNode) == rgbValue(currentNode.x + 1, currentNode.y)) {
                                switch (rand.nextInt(2)) {
                                    case 0: {
                                        direction = "East";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                    case 1: {
                                        direction = "West";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                }
                            } else {
                                if (rgbValue(currentNode) == rgbValue(currentNode.x + 1, currentNode.y)) {
                                    direction = "West";
                                    System.out.printf(direction + " :");
                                } else if (rgbValue(currentNode) == rgbValue(currentNode.x - 1, currentNode.y)) {
                                    direction = "East";
                                    System.out.printf(direction + " :");
                                } else {
                                    randomDirection();
                                    System.out.printf(direction + " :");
                                }
                            }
                        }
                    } else {
                        int nextColor = rgbValue(currentNode);
                        if (nextColor < colorSteps[0] + tolerance &&
                                nextColor > colorSteps[0] - tolerance) {
                            addColor(nextColor);
                        } else {
                            System.out.println("rgbValue different " + currentNode.toString());
                            currentNode.south();
                            if (rgbValue(currentNode) == rgbValue(currentNode.x - 1, currentNode.y) &&
                                    rgbValue(currentNode) == rgbValue(currentNode.x + 1, currentNode.y)) {
                                switch (rand.nextInt(2)) {
                                    case 0: {
                                        direction = "East";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                    case 1: {
                                        direction = "West";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                }
                            } else {
                                if (rgbValue(currentNode) == rgbValue(currentNode.x + 1, currentNode.y)) {
                                    direction = "West";
                                    System.out.printf(direction + " :");
                                } else if (rgbValue(currentNode) == rgbValue(currentNode.x - 1, currentNode.y)) {
                                    direction = "East";
                                    System.out.printf(direction + " :");
                                } else {
                                    randomDirection();
                                    System.out.printf(direction + " :");
                                }
                            }
                        }
                    }
                    break;
                }
                case "East": {
                    currentNode.east();
                    if (validBound(region)) {
                        int nextColor = rgbValue(currentNode);
                        if (nextColor < colorSteps[0] + tolerance &&
                                nextColor > colorSteps[0] - tolerance) {
                            addColor(nextColor);
                        } else {
                            currentNode.west();
                            System.out.println("rgbValue different " + currentNode.toString());
                            if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y + 1) &&
                                    rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y - 1)) {
                                switch (rand.nextInt(2)) {
                                    case 0: {
                                        direction = "North";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                    case 1: {
                                        direction = "South";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                }
                            } else {
                                if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y - 1)) {
                                    direction = "North";
                                    System.out.printf(direction + " :");
                                } else if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y + 1)) {
                                    direction = "South";
                                    System.out.printf(direction + " :");
                                } else {
                                    randomDirection();
                                    System.out.printf(direction + " :");
                                }
                            }
                        }
                    } else {
                        int nextColor = rgbValue(currentNode);
                        if (nextColor < colorSteps[0] + tolerance &&
                                nextColor > colorSteps[0] - tolerance) {
                            addColor(nextColor);
                        } else {
                            currentNode.west();
                            System.out.println("rgbValue different " + currentNode.toString());
                            if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y + 1) &&
                                    rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y - 1)) {
                                switch (rand.nextInt(2)) {
                                    case 0: {
                                        direction = "North";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                    case 1: {
                                        direction = "South";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                }
                            } else {
                                if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y - 1)) {
                                    direction = "North";
                                    System.out.printf(direction + " :");
                                } else if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y + 1)) {
                                    direction = "South";
                                    System.out.printf(direction + " :");
                                } else {
                                    randomDirection();
                                    System.out.printf(direction + " :");
                                }
                            }
                        }
                    }
                    break;
                }
                case "South": {
                    currentNode.south();
                    if (validBound(region)) {
                        int nextColor = rgbValue(currentNode);
                        if (nextColor < colorSteps[0] + tolerance &&
                                nextColor > colorSteps[0] - tolerance) {
                            addColor(nextColor);
                        } else {
                            System.out.println("rgbValue different " + currentNode.toString());
                            currentNode.north();
                            if (rgbValue(currentNode) == rgbValue(currentNode.x - 1, currentNode.y) &&
                                    rgbValue(currentNode) == rgbValue(currentNode.x + 1, currentNode.y)) {
                                switch (rand.nextInt(2)) {
                                    case 0: {
                                        direction = "East";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                    case 1: {
                                        direction = "West";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                }
                            } else {
                                if (rgbValue(currentNode) == rgbValue(currentNode.x - 1, currentNode.y)) {
                                    direction = "West";
                                    System.out.printf(direction + " :");
                                } else if (rgbValue(currentNode) == rgbValue(currentNode.x + 1, currentNode.y)) {
                                    direction = "East";
                                    System.out.printf(direction + " :");
                                } else {
                                    randomDirection();
                                    System.out.printf(direction + " :");
                                }
                            }
                        }
                    } else {
                        int nextColor = rgbValue(currentNode);
                        if (nextColor < colorSteps[0] + tolerance &&
                                nextColor > colorSteps[0] - tolerance) {
                            addColor(nextColor);
                        } else {
                            System.out.println("rgbValue different " + currentNode.toString());
                            currentNode.north();
                            if (rgbValue(currentNode) == rgbValue(currentNode.x - 1, currentNode.y) &&
                                    rgbValue(currentNode) == rgbValue(currentNode.x + 1, currentNode.y)) {
                                switch (rand.nextInt(2)) {
                                    case 0: {
                                        direction = "East";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                    case 1: {
                                        direction = "West";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                }
                            } else {
                                if (rgbValue(currentNode) == rgbValue(currentNode.x - 1, currentNode.y)) {
                                    direction = "West";
                                    System.out.printf(direction + " :");
                                } else if (rgbValue(currentNode) == rgbValue(currentNode.x + 1, currentNode.y)) {
                                    direction = "East";
                                    System.out.printf(direction + " :");
                                } else {
                                    randomDirection();
                                    System.out.printf(direction + " :");
                                }
                            }
                        }
                    }
                    break;
                }
                case "West": {
                    currentNode.west();
                    if (validBound(region)) {
                        int nextColor = rgbValue(currentNode);
                        if (nextColor < colorSteps[0] + tolerance &&
                                nextColor > colorSteps[0] - tolerance) {
                            addColor(nextColor);
                        } else {
                            currentNode.east();
                            System.out.println("rgbValue different " + currentNode.toString());
                            if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y + 1) &&
                                    rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y - 1)) {
                                switch (rand.nextInt(2)) {
                                    case 0: {
                                        direction = "North";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                    case 1: {
                                        direction = "South";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                }
                            } else {
                                if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y - 1)) {
                                    direction = "North";
                                    System.out.printf(direction + " :");
                                } else if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y + 1)) {
                                    direction = "South";
                                    System.out.printf(direction + " :");
                                } else {
                                    randomDirection();
                                    System.out.printf(direction + " :");
                                }
                            }
                        }
                    } else {
                        int nextColor = rgbValue(currentNode);
                        if (nextColor < colorSteps[0] + tolerance &&
                                nextColor > colorSteps[0] - tolerance) {
                            addColor(nextColor);
                        } else {
                            currentNode.east();
                            System.out.println("rgbValue different " + currentNode.toString());
                            if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y + 1) &&
                                    rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y - 1)) {
                                switch (rand.nextInt(2)) {
                                    case 0: {
                                        direction = "North";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                    case 1: {
                                        direction = "South";
                                        System.out.printf(direction + " :");
                                        break;
                                    }
                                }
                            } else {
                                if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y - 1)) {
                                    direction = "North";
                                    System.out.printf(direction + " :");
                                } else if (rgbValue(currentNode) == rgbValue(currentNode.x, currentNode.y + 1)) {
                                    direction = "South";
                                    System.out.printf(direction + " :");
                                } else {
                                    randomDirection();
                                    System.out.printf(direction + " :");
                                }
                            }
                        }
                    }
                    break;
                }
                case "Stop": {
                    direction = "Stop";
                    break;
                }
            }
        } while (!direction.equals("Stop"));
        System.out.printf(direction + " :");
    }

    public boolean validBound(Region region) {
        boolean goodBound = true;
        if (currentNode.x < 0 || currentNode.x > region.width - 1) goodBound = false;
        if (currentNode.y < 0 || currentNode.y > region.height - 1) goodBound = false;
        return goodBound;
    }

    public void addColor(int newColor) {
        for (int i = 0; i < colorSteps.length; i++) {
            if (i + 1 < colorSteps.length) {
                colorSteps[i + 1] = colorSteps[i];
            }
        }
        colorSteps[0] = newColor;
    }

    public int rgbValue(Coordinates coordinates) {
        try {
            int clr = image.getRGB(coordinates.x, coordinates.y);
            int red = (clr & 0x00ff0000) >> 16;
            int green = (clr & 0x0000ff00) >> 8;
            int blue = clr & 0x000000ff;
            return red + blue + green;
        } catch (Exception e) {
            return 2000;
        }
    }

    public int rgbValue(int x, int y) {
        try {
            int clr = image.getRGB(x, y);
            int red = (clr & 0x00ff0000) >> 16;
            int green = (clr & 0x0000ff00) >> 8;
            int blue = clr & 0x000000ff;
            return red + blue + green;
        } catch (Exception e) {
            return 2000;
        }
    }

    public void randomDirection() {
        switch (rand.nextInt(4)) {
            case 0: {
                direction = "North";
                break;
            }
            case 1: {
                direction = "East";
                break;
            }
            case 2: {
                direction = "South";
                break;
            }
            case 3: {
                direction = "West";
                break;
            }
        }
    }
}

public class RecognizeShape {

    static JFrame frame2 = new JFrame();

    public static void main(String[] args) {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int windowWidth = gd.getDisplayMode().getWidth();
        int windowHeight = gd.getDisplayMode().getHeight();

        int screenSizeAverage = ((windowHeight + windowWidth) / 110);

        Font meetisFont = new Font("Arial", Font.BOLD, screenSizeAverage);
        Font meetisFont2 = new Font("Consolas", Font.BOLD, screenSizeAverage * 2);
        Font averagedFont = new Font("Arial", Font.BOLD, screenSizeAverage);
        Font listFont = new Font("Arial", Font.BOLD, (screenSizeAverage * 3 / 5 * 5 / 4));

        BufferedImage meetisLogo = null;
        try {
            meetisLogo = ImageIO.read(new File("src\\resources\\meetis_logo_wht_glow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel meetisIcon = new JLabel(new ImageIcon(meetisLogo));

        frame2.setPreferredSize(new Dimension(windowWidth, windowHeight));
        frame2.setUndecorated(true);
        frame2.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.0f));
        frame2.isAlwaysOnTop();
        frame2.setLayout(null);
        frame2.add(meetisIcon);
        meetisIcon.setLocation(windowWidth * 4 / 5, windowHeight / 2);
        meetisIcon.setSize(277, 170);

        LinesComponent comp = new LinesComponent();
        comp.setSize(windowWidth, windowHeight);
        comp.setLocation(0,0);
        frame2.add(comp);

        JLayeredPane jp = new JLayeredPane();
        jp.setSize(new Dimension(windowWidth, windowHeight));
        jp.setLayout(new BorderLayout());
        frame2.add(jp);

        frame2.pack();
        frame2.setVisible(true);

        takeScreenShot();

        while (true) {
            zZ(500);
            int x1 = (windowWidth / 2);
            int x2 = x1;
            int y1 = 0;
            int y2 = windowHeight;
            Coordinates coord = new Coordinates(x1, y1);
            comp.addLine(x1, y1, x2, y2, Color.red);
            frame2.repaint();
            frame2.add(new Cursor(frame2, jp, coord));
            x1 = 0;
            x2 = windowWidth;
            y1 = (windowHeight / 2);
            y2 = y1;
            coord.x = x1;
            coord.y = y1;
            comp.addLine(x1, y1, x2, y2, Color.red);
            frame2.repaint();
            frame2.add(new Cursor(frame2, jp, coord));
        }

/*        while (true) {
            zZZ(1);
            int x1 = (int) (Math.random()*windowWidth);
            int x2 = (int) (Math.random()*windowWidth);
            int y1 = (int) (Math.random()*windowHeight);
            int y2 = (int) (Math.random()*windowHeight);
            //Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
            comp.addLine(x1, y1, x2, y2, Color.red);

            frame2.repaint();
        }*/


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

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
        }
        System.out.println();
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
    public static void takeScreenShot() {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "jpg", new File("screenshot.jpg"));
        } catch (Exception e) {
            System.out.println("FAILED to take SCREEN SHOT");
            System.out.println(e.getMessage());
        }
    }
    public static boolean checkIfPixleWhite(int x, int y) {
        boolean isWhite = false;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("screenshot.jpg"));
        } catch (Exception e) {
            System.out.println("ScreenShot FAILED READ");
            System.out.println(e.getMessage());
        }
        int clr = image.getRGB(x, y);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;
        if (red + blue + green > 700) {
            isWhite = true;
        }
        return isWhite;
    }
    public static boolean checkIfPixleBlack(int x, int y) {
        boolean isBlack = false;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("screenshot.jpg"));
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
            image = ImageIO.read(new File("screenshot.jpg"));
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
}
