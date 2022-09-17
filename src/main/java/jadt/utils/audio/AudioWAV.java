package jadt.utils.audio;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.*;

public class AudioWAV {
    Logger logger = Logger.getLogger(AudioWAV.class.getName());
    private File file;
    private AudioInputStream audioInputStream;
    private Clip clip = AudioSystem.getClip();
    public final int LOOP_FOREVER = Clip.LOOP_CONTINUOUSLY;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AudioWAV audioWAV)) return false;
        return LOOP_FOREVER == audioWAV.LOOP_FOREVER && Objects.equals(file, audioWAV.file) && Objects.equals(audioInputStream, audioWAV.audioInputStream) && Objects.equals(clip, audioWAV.clip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, audioInputStream, clip, LOOP_FOREVER);
    }
    public AudioWAV() throws LineUnavailableException {

    }

    @Override
    public String toString() {
        return "AudioWAV{" +
                "file=" + file +
                ", audioInputStream=" + audioInputStream +
                ", clip=" + clip.toString() +
                ", LOOP_FOREVER=" + LOOP_FOREVER +
                '}';
    }
    /**
     * The path of the WAV file is passed though this parameter
     * @param WAV_File
    * */
    public AudioWAV(String WAV_File) throws LineUnavailableException, IOException {
        if(new File(WAV_File).exists())this.file = new File(WAV_File);
        String fileName = file.toString();
        int index = fileName.lastIndexOf('.');
        if(index > 0 && file.exists()) {
            String extension = fileName.substring(index + 1);
            if(!extension.equals("wav"))throw new IllegalArgumentException("File provided is not an wav File.");
        }
        if(file.isFile()) {
            if (file.exists()) {
                try {
                    this.file = new File(WAV_File);
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
    /**
     * Sets the audio file to the Path of the new audio file specified
     * The path of the WAV file is passed though this parameter
     * @param File
     * */
    public void setFile(String File) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
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
    /**
     * @return the current WAV file provided if it exists and is not a directory.
     * */
    public File getWAVFile() throws FileNotFoundException {
        if(file == null) throw new IllegalStateException("File passed is null.");
        if(!file.exists()) throw new FileNotFoundException("The file is not found.");
        if (file.isDirectory()) throw new FileNotFoundException("The file is a directory, not a file.");
        return file;
    }
    /**
     * @return  the javax.sound.sampled.Clip Class used to play the WAV files.
     **/
    public Clip getClip(){
        return clip;
    }
    /**
     * @return  the javax.sound.sampled.clip Class used to play the WAV files.
     * * */
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
    /**
     * @return  the File path of the WAV file
     * */
    public String getFilePath(){
        if(file == null)logger.warning("File is null");

        return file.getAbsolutePath();
    }
    /**
     * @return  the WAV file name
     * * */
    public String getFileName(){
        return file.getName();
    }
}
