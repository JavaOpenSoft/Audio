package juit.utils.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;



public class AudioWAV {
    File file;
    AudioInputStream audioInputStream;
    Clip clip = AudioSystem.getClip();

    public AudioWAV(String File) throws LineUnavailableException{
        try{
            this.audioInputStream = AudioSystem.getAudioInputStream(new File(File));
            this.clip.open(audioInputStream);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void playAudio(String filePathWithName,int length,boolean playAudio) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.file = new File(filePathWithName);
        this.audioInputStream = AudioSystem.getAudioInputStream(file);
        this.clip.open(audioInputStream);
        if(playAudio){
            clip.start();
            for(int i = 0;i < length;i++){}
        }
    }
    public Clip getClip(String filePathWithName,int length) throws LineUnavailableException {
        return  AudioSystem.getClip();
    }
    public void start(){
        clip.start();
    }
    public void stop(){
        clip.stop();
    }
    public void setSecondsPlayed(float secondsPlayed){
        clip.setMicrosecondPosition((long) (secondsPlayed*1000000));
    }
    public void wait(int second) throws InterruptedException {
        Thread.sleep(second* 1000000L);
    }
}
