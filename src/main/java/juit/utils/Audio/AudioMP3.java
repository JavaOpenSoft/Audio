package juit.utils.Audio;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.security.InvalidParameterException;

public class AudioMP3 extends Application{

    private Media media;
    private MediaPlayer player;
    private String FilePathWithName;
    private File currentFile;
    private boolean AutoPlay;

    public Media getMedia() {
        return media;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public AudioMP3(String FilePathWithName) {
        this.FilePathWithName = FilePathWithName;
        this.currentFile = new File(FilePathWithName);
    }
    public void start(){

        Platform.startup(new Runnable() {
            @Override
            public void run() {
                media = new Media(Paths.get(FilePathWithName).toUri().toString());
                player = new MediaPlayer(media);
                player.setAutoPlay(true);
            }
        });
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public void stop() {
        player.setAutoPlay(false);
        this.player.stop();
    }
    public void setFile(String FilePathWithName){
        this.FilePathWithName = FilePathWithName;
        this.media = new Media(Paths.get(FilePathWithName).toUri().toString());
        this.player = new MediaPlayer(media);
        this.currentFile = new File(this.FilePathWithName);
    }
    public String getFilePath() {
        return this.FilePathWithName;
    }
    public File getFile() {
        return this.currentFile;
    }
    public void stopPlaying() {
        this.player.stop();
    }
    public void startPlayingAgain() {
        this.player.play();
    }
    public boolean isAutoPlaying() {
        return this.AutoPlay;
    }
    public String getMediaPath()
    {
        return new String(this.player.getMedia().getSource());
    }
    public int ConvertMillisToNano(int in)
    {
        return in *= 1000;
    }
    public int getStartTime(String Type)
    {
        int temp = 0;
        if(Type.equals("Hours"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toHours()));
        if(Type.equals("Minutes"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toMinutes()));
        if(Type.equals("Seconds"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toSeconds()));
        if(Type.equals("Milliseconds"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toMillis()));
        if(Type.equals("Microseconds"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toMillis()))*1000;
        if(Type.equals("Nanoseconds"))temp = ConvertMillisToNano(Integer.parseInt(String.valueOf(this.player.getStartTime().toMillis())));
        return temp;
    }

    public int getTimeOnPause(){
        return Integer.parseInt(String.valueOf(this.player.getOnStopped()));
    }
    public int getTotalTime(){
        return Integer.parseInt(String.valueOf(this.player.getTotalDuration()));
    }
    public void setVolume(byte VolumePercentage){
        if(VolumePercentage == 0)setMute(true);
        if(VolumePercentage <= 100 && VolumePercentage > 0) this.player.setVolume(VolumePercentage);
        if(VolumePercentage < 0 && VolumePercentage >= -100) this.player.setVolume(VolumePercentage*-1);
        if(VolumePercentage > 100||VolumePercentage < -100 )throw new InvalidParameterException("The Volume Percentage should be between 0 and 100. The current value provided is "+ VolumePercentage);
    }
    public int getTimeOnHalt() {
        return Integer.parseInt(String.valueOf(this.player.getOnHalted()));
    }
    public int getTimeOnPlaying() {
        return Integer.parseInt(String.valueOf(this.player.getOnPlaying()));
    }
    public int getLengthOfMedia() {
        return Integer.parseInt(String.valueOf(this.player.getOnEndOfMedia()));
    }
    public String getVolumePercentage() {
        return String.valueOf(getVolume())+'%';
    }
    public int getVolume()
    {
     return Integer.parseInt(String.valueOf(this.player.getVolume()));
    }
    public void setMute(boolean bool)
    {
        this.player.setMute(bool);
    }
    
}
