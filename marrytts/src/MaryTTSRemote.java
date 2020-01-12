import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.modules.synthesis.Voice;
import marytts.util.data.audio.AudioPlayer;

import javax.sound.sampled.AudioInputStream;
import java.util.ArrayList;


public class MaryTTSRemote {
    private MaryInterface marytts;
    private AudioPlayer ap;

    public MaryTTSRemote(String voiceName) {
        try {
            marytts = new LocalMaryInterface();
            marytts.setVoice(voiceName);
            ap = new AudioPlayer();
        } catch (MaryConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    public void say(String input) {
        AudioInputStream audio = null;
        try {
            audio = marytts.generateAudio(input);

            ap.setAudio(audio);
            ap.start();
        } catch (SynthesisException e) {
            System.err.println("Error saying phrase.");
        }

    }

}