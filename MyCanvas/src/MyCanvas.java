import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
public class MyCanvas {
    JLabel view;
    BufferedImage surface;
    Random random = new Random();
    public MyCanvas() {
        surface = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 600, 400);
        g.setColor(Color.BLACK);
        g.drawLine(10, 20, 350, 380);
        g.dispose();
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addNewElement();
            }
        };
        Timer timer = new Timer(200, listener);
        timer.start();
    }
    public void addNewElement() {
        boolean drawArc = random.nextBoolean();
        int x = random.nextInt(60);
        int y = random.nextInt(40);
        Graphics g = surface.getGraphics();
        if (drawArc) {
            g.setColor(Color.BLUE);
            int xx = random.nextInt(60);
            int yy = random.nextInt(40);
            drawArc(x, y, xx, yy, g);
        } else {
            drawNode(x, y, g);
        }
        g.dispose();
        view.repaint();
    }
    public static void main(String[] args) {
        MyCanvas canvas = new MyCanvas();
        JFrame frame = new JFrame();
        int vertexes = 0;
        vertexes = 10;
        int canvasSize = vertexes * vertexes;
        frame.setSize(canvasSize, canvasSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(canvas.view);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    public void drawNode(int x, int y, Graphics g) {
        // Treat each location as a 10x10 block. If position 1,1 then go to (5,5) - If position 3,5 then go to (25, 45) eg: (x*10)-5, (y*10)-5
        int xLoc = (x * 10) - 5;
        int yLoc = (y * 10) - 5;
        g.setColor(Color.white);
        g.fillOval(xLoc, yLoc, 8, 8);
        g.drawOval(xLoc, yLoc, 8, 8);
    }
    public void drawArc(int x, int y, int xx, int yy, Graphics g) {
        int xLoc = (x * 10) - 5;
        int yLoc = (y * 10) - 5;
        int xxLoc = (xx * 10) - 5;
        int yyLoc = (yy * 10) - 5;
        g.drawLine(xLoc, yLoc, xxLoc, yyLoc);
    }
}