import jadt.utils.audio.AudioWAV;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class Main extends AudioWAV {
    static AudioWAV audioWAV;

    static {
        try {
            audioWAV = new AudioWAV();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public Main() throws LineUnavailableException {
    }

    public Main(String WAV_File) throws LineUnavailableException, IOException {
        super(WAV_File);
    }

    public static void main(String[] args) {
        System.out.println(audioWAV.getFilePath());
    }

}
