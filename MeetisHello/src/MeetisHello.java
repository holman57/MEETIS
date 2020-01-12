import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MeetisHello {
    public static class cls {
        public cls() {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    static Voice voice;
    static VoiceManager voiceManager = VoiceManager.getInstance();
    public static void main(String[] args) {
        System.setProperty("mbrola.base", "lib\\mbr301d");
        voice = voiceManager.getVoice("mbrola_us1");
        voice.allocate();
        new cls();
        Boolean keepAlive;
        do {
            String input = getInput();
            verbalize(input);
            keepAlive = checkExit(input);
            if (keepAlive) {
                Sentence sentence = new Sentence(input);
                System.out.println();
                for (int i = 0; i < sentence.sentence.size(); i++) {
                    voice.speak(sentence.sentence.get(i).text);
                    System.out.printf(sentence.sentence.get(i).text).append(" ( ");
                    System.out.printf(sentence.sentence.get(i).partsOfSpeechToString()).append(") \n");
                }
            }
            new cls();
        } while (keepAlive);
        shutdown();
    }
    private static void verbalize(String input) {
        System.out.println(input);
        voice.speak(input);
    }
    private static void shutdown(){
        try {
            waitRandomShort();
        } catch (InterruptedException e) {
        }
        try {
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
        } catch (Exception e) {
        }
        System.exit(0);
    }
    private static String getInput() {
        String input = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            input = br.readLine();
        } catch (Exception ignore) {
        }
        return input;
    }
    private static boolean checkExit(String input) {
        if (input.equalsIgnoreCase("exit")){
            System.out.println("See you later dog");
            voice.speak("See you later dog");
            voice.speak("peace out");
            return false;
        }
        return true;
    }
    private static void insultMe(String input) {
        if (input.equalsIgnoreCase("insult me")){
            Random random = new Random();
            int switchMe = random.nextInt(3);
            switch (switchMe){
                case 1:{
                    System.out.println("\twhat's this");
                    voice.speak("What's this");
                    System.out.println("\tyou want me to smoke you?");
                    voice.speak("you want me to smoke you");
                    break;}
                case 2:{
                    System.out.println("\tOh yeah");
                    voice.speak("Oh yeah");
                    System.out.println("\tyou looking for a burn?");
                    voice.speak("you looking for a burn");
                    break;}
                case 0:{
                    System.out.println("\tmmmmmmmm");
                    voice.speak("mmmmmmmm");
                    System.out.println("\tdo you really think you can handle all this human");
                    voice.speak("do you really think you can handle all this human");
                    break;}
            }
            String s = "";
            for (int j = 0; j < 3; j++) {
                s = "";
                for (int i = 0; i < 2; i++) {
                    switchMe = random.nextInt(17);
                    switch (switchMe){
                        case 0:{s +=" ugly";break;}
                        case 1:{s +=" smelly";break;}
                        case 2:{s +=" fat";break;}
                        case 3:{s +=" cross-eyed";break;}
                        case 4:{s +=" freaky";break;}
                        case 5:{s +=" gimpy";break;}
                        case 6:{s +=" stupid";break;}
                        case 7:{s +=" crazy";break;}
                        case 8:{s +=" drug user";break;}
                        case 9:{s +=" gross";break;}
                        case 11:{s +=" strange";break;}
                        case 12:{s +=" weird";break;}
                        case 13:{s +=" ridiculous looking";break;}
                        case 14:{s +=" weak";break;}
                        case 15:{s +=" poor";break;}
                        case 16:{s +=" generally displeasing";break;}
                        case 17:{s +=" bad";break;}
                    }
                    System.out.println("\t" + s);
                }
            }
            voice.speak("chump");
        }
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
}
