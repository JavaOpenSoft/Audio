package jadt.utils.Audio;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AudioWAV {
    private File file;
    private AudioInputStream audioInputStream;
    private Clip clip = AudioSystem.getClip();
    public final int LOOP_FOREVER = Clip.LOOP_CONTINUOUSLY;
    public AudioWAV(String File) throws LineUnavailableException, IOException {
        this.file = new File(File);
        String fileName = file.toString();
        int index = fileName.lastIndexOf('.');
        if(index > 0 && file.exists()) {
            String extension = fileName.substring(index + 1);
            if(!extension.equals("wav"))throw new IllegalArgumentException("File provided is not an wav File.");
        }
        if(file.isFile()) {
            if (file.exists()) {
                try {
                    this.file = new File(File);
                    this.audioInputStream = AudioSystem.getAudioInputStream(file);
                    this.clip.open(audioInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(!file.exists())throw new FileNotFoundException("Error: File not found");
        }
        else if(file.isDirectory()) throw new IOException("Error: The file provided is a directory, not a file.");

    }
    public void setFile(String File) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.file = new File(File);
        this.audioInputStream = AudioSystem.getAudioInputStream(file);
        this.clip.open(audioInputStream);
    }
    public File getFile(){
        return file;
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
    public void playRepeatedly(int numberOfTimesToBePlayed) throws InterruptedException {
        clip.loop(numberOfTimesToBePlayed-1);
        play();
        Thread.sleep(getLengthOfMedia());
    }
    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }
    public void close() throws IOException {
        audioInputStream.close();
        clip.close();
    }
    public void deleteFile() {
        boolean temp = this.file.delete();
    }
    public String getFilePath(){
        return file.getAbsolutePath();
    }
    public String getFileName(){
        return file.getName();
    }
}
