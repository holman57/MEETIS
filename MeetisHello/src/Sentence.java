import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;
public class Sentence {
    ArrayList<Word> sentence = new ArrayList<>();
    String text = "";
    public Sentence() {}
    public Sentence(String input) {
        String[] sTemp = input.split(" ");
        for (int i = 0; i < sTemp.length; i++) {
            Word word = identifyPartOfSpeech(sTemp[i]);
            if (word.contraction) {
                try {
                    String[] sSplit = identifyContraction(word).split(" ");
                    for (int j = 0; j < sSplit.length; j++) {
                        hardCodeByPassExceptions(sentence, identifyPartOfSpeech(sSplit[j]));
                    }
                } catch (Exception e) {
                    hardCodeByPassExceptions(sentence, word);
                }
            } else {
                hardCodeByPassExceptions(sentence, word);            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentence.size(); i++) {
            sb.append(sentence.get(i)).append(" ");
        }
        text = sb.toString();
    }
    public void append(Word word) {
        if (word.contraction) {
            String[] contractionSplit = word.text.split("'");
            for (int j = 0; j < contractionSplit.length; j++) {
                sentence.add(identifyPartOfSpeech(contractionSplit[j]));
            }
        } else {
            sentence.add(word);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(text).append(word.text);
        text = sb.toString();
    }
    public String textPartsOfSpeech() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentence.size(); i++) {
            sb.append(sentence.get(i)).append(" (");
            sb.append(sentence.get(i).partsOfSpeechToString()).append(") \n");
        }
        return sb.toString();
    }
    public static void hardCodeByPassExceptions(ArrayList<Word> sentence, Word word){
        String checkString = word.text.replaceAll("[^a-zA-Z]", "");
        if (checkString.equalsIgnoreCase("i")){
            word.resetPartsOfSpeech();
            word.noun = true;
        }
        if (    checkString.equalsIgnoreCase("am") ||
                checkString.equalsIgnoreCase("find") ||
                checkString.equalsIgnoreCase("know2") ||
                checkString.equalsIgnoreCase("will") ||
                checkString.equalsIgnoreCase("want") ||
                checkString.equalsIgnoreCase("going") ||
                checkString.equalsIgnoreCase("doing") ){
            word.resetPartsOfSpeech();
            word.verb = true;
        }
        if (checkString.equalsIgnoreCase("all")){
            word.adjective = true;
            word.preposition = true;
        }
        if (checkString.equalsIgnoreCase("as")){
            word.noun = false;
        }
        sentence.add(word);
    }
    public static String identifyContraction(Word word) {
        Document doc = null;
        try {
            doc = Jsoup.connect(
                    "http://googledictionary.freecollocation.com/meaning?word=" + word.text
            ).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Elements span = doc.select("span");
        String bElm = FilterHTML.delHTMLTag(span.get(0).toString());
        return bElm;
    }
    public static Word identifyPartOfSpeech(String input) {
        Document doc = null;
        Word word = new Word();
        word.text = input;
        try {
            doc = Jsoup.connect(
                    "http://googledictionary.freecollocation.com/meaning?word=" + input
            ).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        boolean partsOfSpeechFound = false;
        boolean unidentified = true;
        Elements b = doc.select("b");
        for (int i = 0; i < b.size(); i++) {
            String bElm = FilterHTML.delHTMLTag(b.get(i).toString());
            for (int j = 0; j < PartsOf.Speech.length; j++) {
                if (bElm.toLowerCase().contains(PartsOf.Speech[j])) {
                    partsOfSpeechFound = true;
                }
            }
            if (partsOfSpeechFound){
                String[] bElmSplit = bElm.split(" ");
                word.identifyPartsOfSpeech(bElmSplit[0]);
                unidentified = false;
            }
            partsOfSpeechFound = false;
        }
        if (unidentified) {
            if (word.text.equalsIgnoreCase("i")) {
                word.pronoun = true;
            } else {
                word.noun = true;
            }
        }
        if (word.text.equalsIgnoreCase("am")){
            word.resetPartsOfSpeech();
            word.verb = true;
        }
        return word;
    }
}
