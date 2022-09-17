package jadt.utils.audio;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.File;
import java.util.Objects;

public class AudioMIDI {
    public final int LOOP_FOREVER = Sequencer.LOOP_CONTINUOUSLY;
    private Sequencer midiPlayer = MidiSystem.getSequencer();
    private Sequence sequence;
    private File MIDI;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AudioMIDI audioMIDI)) return false;
        return LOOP_FOREVER == audioMIDI.LOOP_FOREVER && Objects.equals(midiPlayer, audioMIDI.midiPlayer) && Objects.equals(sequence, audioMIDI.sequence) && Objects.equals(MIDI, audioMIDI.MIDI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LOOP_FOREVER, midiPlayer, sequence, MIDI);
    }

    @Override
    public String toString() {
        return "AudioMIDI{" +
                "LOOP_FOREVER=" + LOOP_FOREVER +
                ", midiPlayer=" + midiPlayer +
                ", sequence=" + sequence +
                ", MIDI=" + MIDI +
                '}';
    }
    public AudioMIDI() throws MidiUnavailableException {
    }
    public AudioMIDI(String MIDI_File) throws MidiUnavailableException{
        this.MIDI = new File(MIDI_File);
    }
    public void play(){
        try {
            if (midiPlayer==null) {
                System.err.println("Sequencer device not supported");
                return;
            }
            midiPlayer.open(); // Open device
            // Create sequence, the File must contain MIDI file data.
            this.sequence = MidiSystem.getSequence(this.MIDI);
            midiPlayer.setSequence(sequence); // load it into sequencer
        }
        catch (MidiUnavailableException | InvalidMidiDataException | IOException ex) {
            ex.printStackTrace();
        }
    }
    public void playRepeatedly(int numberOfTimesToBePlayed) throws MidiUnavailableException {
        midiPlayer.setLoopCount(numberOfTimesToBePlayed);
        midiPlayer.open();
        midiPlayer.start();
    }
    public void close(){
        midiPlayer.close();
    }
    public void stop(){
        midiPlayer.stop();
    }
    public void setTempoInBPM(float bpm){
        midiPlayer.setTempoInBPM(bpm);
    }
    public void setTempoInMPQ(float mpq){
        midiPlayer.setTempoInMPQ(mpq);
    }
    public void setTempoFactor(float factor){
        midiPlayer.setTempoFactor(factor);
    }
    public void muteSound(){
        Track[] track = sequence.getTracks();
        midiPlayer.setTrackMute(track[0].size(), true);
    }
    public void unmuteSound() {
        Track[] track = sequence.getTracks();
        midiPlayer.setTrackMute(track[0].size(), false);
    }
    public float getLength(){
        return (float) (midiPlayer.getMicrosecondLength()/10000000);
    }
    public boolean isOpen(){
        return midiPlayer.isOpen();
    }
    public void setPosition(int seconds){
        midiPlayer.setMicrosecondPosition((long)seconds/10000000);
    }
    public void setTickPosition(long ticks){
        midiPlayer.setTickPosition(ticks);
    }

}
