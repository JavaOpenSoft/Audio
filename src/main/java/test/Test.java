package test;

import juit.utils.Audio.AudioWAV;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws LineUnavailableException, InterruptedException, IOException {
        AudioWAV audioWav = new AudioWAV("/Users/rishon/errorSound.wav");
        System.out.println(audioWav.getLengthOfMedia());
        audioWav.play();

    }
}
