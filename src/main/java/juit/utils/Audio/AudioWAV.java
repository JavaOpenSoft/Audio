package juit.utils.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;



public class AudioWAV {
    File file;
    AudioInputStream audioInputStream;
    Clip clip = AudioSystem.getClip();
    public AudioWAV(String File) throws LineUnavailableException, IOException {
        if(!new File(File).isDirectory()) {
            if (new File(File).exists()) {
                try {
                    this.audioInputStream = AudioSystem.getAudioInputStream(new File(File));
                    this.clip.open(audioInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(!new File(File).exists())throw new FileNotFoundException("Error: File not found");
        }
        else throw new IOException("Error: The file provided is a directory, not a file.");

    }
    public void setFile(String File){
        this.file = new File(File);
    }

    public File getFile(){
        return file;
    }
    public void playAudio(long length,boolean playAudio) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        this.audioInputStream = AudioSystem.getAudioInputStream(file);
        this.clip.open(audioInputStream);
        if(playAudio){
            play();
        }
    }
    public Clip getClip(){
        return clip;
    }
    public void play() throws InterruptedException {
        clip.start();
        do {
            Thread.sleep(15);
        } while (clip.isRunning());
        clip.drain();
    }
    public void start(){
        clip.start();
    }
    public boolean isAudioPlaying(){
        return clip.isRunning();
    }
    public void drain(){
        clip.drain();
    }
    public int getLengthOfMedia() {
        return clip.getFrameLength();
    }
    public void stop(){
        clip.stop();
    }
    public void setSecondsPlayed(float secondsPlayed){
        clip.setMicrosecondPosition((long) (secondsPlayed*1000000));
    }
    public void wait(int second) throws InterruptedException {
        Thread.sleep(second* 100L);
    }
}
