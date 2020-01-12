import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
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
        g.fillOval(x - 5 + offset, y - 5 + offset, size, size);
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
    public void west() {
        this.x--;
    }
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

    public Cursor(
            Frame f,
            Region region,
            JLayeredPane jp,
            Coordinates coordinates) throws IOException {
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

public class Main extends JPanel {
    static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static int windowWidth = gd.getDisplayMode().getWidth();
    static int windowHeight = gd.getDisplayMode().getHeight();

    public static void main(String[] args) {
        Region region = new Region(10, 300, 500, 500);
        takeScreenShot(region);
        BufferedImage imageRegion = null;
        try {
            imageRegion = ImageIO.read(new File("ssRegion.jpg"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JLabel imageLable = new JLabel(new ImageIcon(imageRegion));
        JFrame f = new JFrame();
        JLayeredPane jp = new JLayeredPane();
        jp.setSize(new Dimension(windowWidth, windowHeight));
        jp.add(imageLable, 1);
        jp.setLayout(new BorderLayout());
        f.add(jp);
        f.setPreferredSize(new Dimension(windowWidth, windowHeight));
        f.setUndecorated(true);
        f.setBackground(new Color(0.1f, 0.1f, 0.1f, 0.0f));
        f.setLayout(new BorderLayout());
        imageLable.setLocation(region.x, region.y);
        imageLable.setSize(imageRegion.getWidth(), imageRegion.getHeight());
        f.setVisible(true);
        f.pack();
        f.toFront();
        f.repaint();
        System.out.println(
                "Cursor (x:" + (region.x + region.width / 2) +
                        ", y:" + (region.y + region.height / 2) + ")"
        );
        Coordinates coordinates = new Coordinates(
                region.x + region.width / 2,
                region.y + region.height / 2
        );
        try {
            new Cursor(f, region, jp, coordinates);
        } catch (Exception e) {
            e.printStackTrace();
        }

        zZZ(55555);
    }

    public static void zZZ(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    public static void zZ(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }

    public static void takeScreenShot(Region region) {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(region.x, region.y, region.width, region.height));
            ImageIO.write(image, "jpg", new File("ssRegion.jpg"));
        } catch (Exception e) {
            System.out.println("FAILED to take SCREEN SHOT");
            System.out.println(e.getMessage());
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

    public static void takeScreenShot(int sequenceNumber) {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "jpg", new File("ScreenShot\\sc" + sequenceNumber + ".jpg"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
