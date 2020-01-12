import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Verbalize {
    public static class cls {
        public cls() {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_Q);
            robot.keyRelease(KeyEvent.VK_Q);
            robot.keyRelease(KeyEvent.VK_ALT);
        } catch (Exception ignore) {
        }
        String phrase = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("phrase.txt"));
            if (br != null & !br.equals("")) {
                phrase = br.readLine();
            }
        } catch (Exception ignore) {
        }
        new cls();
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        System.setProperty("mbrola.base", "lib\\mbr301d");
        voice = voiceManager.getVoice("mbrola_us1");
        voice.allocate();
        new cls();
        System.out.println(phrase);
        try {
            if (args[0] != null) System.out.println("\n" + args[0]);
        } catch (Exception ignore) {
        }
        try {
            PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
            writer.println("Talking");
            writer.close();
        } catch (Exception ignore) {
        }
        try {
            voice.speak(phrase);
        } catch (Exception ignore) {
        }
        try {
            PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
            writer.println("Not Talking");
            writer.close();
            TimeUnit.SECONDS.sleep(1);
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
        } catch (Exception ignore) {
        }
        System.exit(0);
    }
}

