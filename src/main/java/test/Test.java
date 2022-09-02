package test;

import javafx.application.Application;
import juit.utils.Audio.AudioWAV;
import juit.utils.Audio.AudioMP3;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws LineUnavailableException, InterruptedException, IOException {
        new AudioMP3("/Users/rishon/Downloads/errorSound.mp3").start();

        new AudioWAV("/Users/rishon/errorSound.wav").play();

    }
}
