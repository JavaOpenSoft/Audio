import javazoom.jl.decoder.JavaLayerException;
import jadt.utils.Audio.AudioMP3;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AudioTest {
    static AudioMP3 audioWAV;

    static {
        try {
            audioWAV = new AudioMP3("/Users/rishon/errorSound.mp3");
        } catch (IOException | JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        audioWAV.play();
    }
}
