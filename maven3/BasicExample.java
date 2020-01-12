import org.bytedeco.javacpp.*;
import org.bytedeco.leptonica.*;
import org.bytedeco.tesseract.*;

import java.io.PrintWriter;

import static org.bytedeco.leptonica.global.lept.*;
import static org.bytedeco.tesseract.global.tesseract.*;

public class BasicExample {
    public static void main(String[] args) {
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(null, "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        PIX image = pixRead(args[0]);
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        //    System.out.println("OCR output:\n" + outText.getString());

        try {
            PrintWriter writer = new PrintWriter(args[1], "UTF-8");
            writer.write(outText.getString());
            writer.close();
        } catch (Exception e) {
            e.getMessage();
        }

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
    }
}