package jadt.utils.audio;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;

public class AudioPlayer {
    public AudioPlayer() {}
    public AudioAAC getAACDecoder(){
        return new AudioAAC();
    }
    public AudioWAV getWavDecoder() throws LineUnavailableException {
        return  new AudioWAV();
    }
    public AudioMP3 getMp3Decoder() {
        return new AudioMP3();
    }
    public AudioOGG getOGGDecoder(){
        return new AudioOGG();
    }
    public AudioMIDI getMIDIDecoder() throws MidiUnavailableException {
        return new AudioMIDI();
    }

}
