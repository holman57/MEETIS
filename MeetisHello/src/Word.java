public class Word {
    boolean noun = false;
    boolean pronoun = false;
    boolean adjective = false;
    boolean determiner = false;
    boolean verb = false;
    boolean adverb = false;
    boolean preposition = false;
    boolean conjunction = false;
    boolean interjection = false;
    boolean contraction = false;
    boolean symbol = false;
    String text = "";
    boolean unidentified = false;
    public void clearText(){
        text = "";
    }
    public Word(){}
    public Word(String word){
        Word wordClone = Sentence.identifyPartOfSpeech(word);
        for (int i = 0; i < PartsOf.Speech.length; i++) {
            switch (PartsOf.Speech[i]) {
                case "noun":{if (wordClone.noun) noun = true;break;}
                case "pronoun":{if (wordClone.pronoun) pronoun = true;break;}
                case "adjective":{if (wordClone.adjective) adjective = true;break;}
                case "determiner":{if (wordClone.determiner) determiner = true;break;}
                case "verb":{if (wordClone.verb) verb = true;break;}
                case "adverb":{if (wordClone.adverb) adverb = true;break;}
                case "preposition":{if (wordClone.preposition) preposition = true;break;}
                case "conjunction":{if (wordClone.conjunction) conjunction = true;break;}
                case "interjection":{if (wordClone.interjection) interjection = true;break;}
                case "contraction":{if (wordClone.contraction) contraction = true;break;}
                case "symbol":{if (wordClone.symbol) symbol = true;break;}
            }
        }
        if (wordClone.unidentified) unidentified = true;
        text = wordClone.text;
    }
    public void identifyPartsOfSpeech(String unidentifiedWord){
        for (int i = 0; i < PartsOf.Speech.length; i++) {
            switch (unidentifiedWord.toLowerCase().trim()) {
                case "noun":{noun = true;break;}
                case "pronoun":{pronoun = true;break;}
                case "adjective":{adjective = true;break;}
                case "determiner":{determiner = true;break;}
                case "verb":{verb = true;break;}
                case "adverb":{adverb = true;break;}
                case "preposition":{preposition = true;break;}
                case "conjunction":{conjunction = true;break;}
                case "interjection":{interjection = true;break;}
                case "contraction":{contraction = true;break;}
                case "symbol":{symbol = true;break;}
            }
        }
        unidentified = true;
        for (int i = 0; i < PartsOf.Speech.length; i++) {
            if (unidentifiedWord.toLowerCase().trim().contains(PartsOf.Speech[i].toLowerCase())){
             unidentified = false;
            }
        }
        if (unidentified) noun = true;
    }
    public int partsOfSpeechCount(){
        int count = 0;
        for (int i = 0; i < PartsOf.Speech.length; i++) {
            switch (i) {
                case 0:{if (noun) count++;break;}
                case 1:{if (pronoun) count++;break;}
                case 2:{if (adjective) count++;break;}
                case 3:{if (determiner) count++;break;}
                case 4:{if (verb) count++;break;}
                case 5:{if (adverb) count++;break;}
                case 6:{if (preposition) count++;break;}
                case 7:{if (conjunction) count++;break;}
                case 8:{if (interjection) count++;break;}
                case 9:{if (contraction) count++;break;}
                case 10:{if (symbol) count++;break;}
            }
        }
        return count;
    }
    public void resetPartsOfSpeech(){
        for (int i = 0; i < PartsOf.Speech.length; i++) {
            switch (i) {
                case 0:{if (noun) noun = false;break;}
                case 1:{if (pronoun) pronoun = false;break;}
                case 2:{if (adjective) adjective = false;break;}
                case 3:{if (determiner) determiner = false;break;}
                case 4:{if (verb) verb = false;break;}
                case 5:{if (adverb) adverb = false;break;}
                case 6:{if (preposition) preposition = false;break;}
                case 7:{if (conjunction) conjunction = false;break;}
                case 8:{if (interjection) interjection = false;break;}
                case 9:{if (contraction) contraction = false;break;}
                case 10:{if (symbol) symbol = false;break;}
            }

        }
    }
    public String partsOfSpeechToString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PartsOf.Speech.length; i++) {
            switch (i) {
                case 0:{if (noun) sb.append("Noun ");break;}
                case 1:{if (pronoun) sb.append("Pronoun ");break;}
                case 2:{if (adjective) sb.append("Adjective ");break;}
                case 3:{if (determiner) sb.append("Determiner ");break;}
                case 4:{if (verb) sb.append("Verb ");break;}
                case 5:{if (adverb) sb.append("Adverb ");break;}
                case 6:{if (preposition) sb.append("Preposition ");break;}
                case 7:{if (conjunction) sb.append("Conjunction ");break;}
                case 8:{if (interjection) sb.append("Interjection ");break;}
                case 9:{if (contraction) sb.append("Contraction ");break;}
                case 10:{if (symbol) sb.append("Symbol ");break;}
            }
        }
        return sb.toString();
    }
}
