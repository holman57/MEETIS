import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(args[0]));
        } catch (Exception e) {
            System.out.println("ScreenShot FAILED READ");
            System.out.println(e.getMessage());
        } // symmetric image 88 by 88
        for (int i = 0; i < image.getWidth(); i += 11) {
            for (int j = 0; j < image.getHeight(); j += 11) {
                if (checkYouTubeBlue(i, j, args[0])){
                    System.out.println("x:" + i + " y:" + j);
                }
            }

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
    public static boolean checkIfPixleWhite(int x, int y, String filepath) {
        boolean isWhite = false;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filepath));
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
    public static boolean checkYouTubeBlue(int x, int y, String filepath) {
        //  Red:106      Green:171      Blue:223    =   500
        BufferedImage image = null;
        boolean youTubeBlue = false;
        try {
            image = ImageIO.read(new File(filepath));
        } catch (Exception e) {
            System.out.println("ScreenShot FAILED READ");
            System.out.println(e.getMessage());
        }
        int clr = image.getRGB(x, y);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;
        if (red + blue + green < 525 && red + green + blue > 475) {
            youTubeBlue = true;
        }
        return youTubeBlue;
    }
}
