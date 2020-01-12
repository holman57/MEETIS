
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


public class Main {

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

        String phrase = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("phrase.txt"));
            if (br != null & !br.equals("")) {
                phrase = br.readLine();
            }
        } catch (Exception e) {
            e.getMessage();
        }

        new cls();
        MaryTTSRemote maryTTSRemote = new MaryTTSRemote("cmu-slt-hsmm");

        try {
            PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
            writer.println("Talking");
            writer.close();
        } catch (Exception e) {
            e.getMessage();
        }

        if (args.length > 0) {
            System.out.println(args[0]);
            maryTTSRemote.say(args[0]);
        } else {
            System.out.println(phrase);
            maryTTSRemote.say(phrase);
        }

        try {
            PrintWriter writer = new PrintWriter("MeetisTalking.txt", "UTF-8");
            writer.println("Not Talking");
            writer.close();
            TimeUnit.MILLISECONDS.sleep(100);
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
        } catch (Exception e) {
            e.getMessage();
        }


    //    System.exit(0);

    }

}
