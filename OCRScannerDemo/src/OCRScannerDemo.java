import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader;
import net.sourceforge.javaocr.scanner.PixelImage;

public class OCRScannerDemo {
    private static final long serialVersionUID = 1L;
    private boolean debug = true;
    private Image image;
    private OCRScanner scanner;

    public OCRScannerDemo() {
        scanner = new OCRScanner();
    }

    /**
     * Load demo training images.
     *
     * @param trainingImageDir The directory from which to load the images.
     */
    public void loadTrainingImages(String trainingImageDir) {
        /*     if (debug) {
            System.err.println("loadTrainingImages(" + trainingImageDir + ")");
        }
   if (!trainingImageDir.endsWith(File.separator)) {
            trainingImageDir += File.separator;
        try {
            scanner.clearTrainingImages();
            TrainingImageLoader loader = new TrainingImageLoader();
            HashMap<Character, ArrayList<TrainingImage>> trainingImageMap = new HashMap<Character, ArrayList<TrainingImage>>();
            if (debug) {
                System.err.println("ascii.png");
            }
            loader.load(
                    trainingImageDir + "ascii.png",
                    new CharacterRange('!', '~'),
                    trainingImageMap);
            if (debug) {
                System.err.println("hpljPica.jpg");
            }
            loader.load(
                    trainingImageDir + "hpljPica.jpg",
                    new CharacterRange('!', '~'),
                    trainingImageMap);
            if (debug) {
                System.err.println("digits.jpg");
            }
            loader.load(
                    trainingImageDir + "digits.jpg",
                    new CharacterRange('0', '9'),
                    trainingImageMap);
            if (debug) {
                System.err.println("adding images");
            }
            scanner.addTrainingImages(trainingImageMap);
            if (debug) {
                System.err.println("loadTrainingImages() done");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(2);
        }
    }*/
    }

    public void process(String imageFilename) {
        if (debug) {
            System.err.println("process(" + imageFilename + ")");
        }
        try {
            image = ImageIO.read(new File(imageFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image == null) {
            System.err.println("Cannot find image file: " + imageFilename);
            return;
        }
        PixelImage pixelImage = new PixelImage(image);
        pixelImage.toGrayScale(true);
        pixelImage.filter();

        System.out.println(imageFilename + ":");
        String text = scanner.scan(image, 0, 0, 0, 0, null);
        System.out.println("[" + text + "]");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please specify one or more image filenames.");
            System.exit(1);
        }
        //  String trainingImageDir = System.getProperty("TRAINING_IMAGE_DIR");
        /*if (trainingImageDir == null) {
            System.err.println("Please specify -DTRAINING_IMAGE_DIR=<dir> on "
                    + "the java command line.");
            return;
        }*/
        OCRScannerDemo demo = new OCRScannerDemo();


        demo.process(args[0]);

        System.out.println("done.");
    }

    private static final Logger LOG = Logger.getLogger(OCRScannerDemo.class.getName());
}
