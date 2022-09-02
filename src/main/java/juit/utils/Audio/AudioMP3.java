package juit.utils.Audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.security.InvalidParameterException;

public class AudioMP3 {
    Media media;
    MediaPlayer player;
    int Volume = 0;
    String FilePathWithName;
    boolean AutoPlay;
    public AudioMP3(String FilePathWithName) {
        this.FilePathWithName = FilePathWithName;
    }
    public AudioMP3(String FilePathWithName,boolean AutoPlay) {
        this.FilePathWithName = FilePathWithName;
        this.AutoPlay = AutoPlay;
    }
    public void startPlaying() {
        this.media = new Media(this.FilePathWithName);
        this.player = new MediaPlayer(media);
        player.setAutoPlay(this.AutoPlay);
    }
    private void startPlaying(String FilePathWithName, boolean Autoplay) {
        this.media = new Media(FilePathWithName);
        this.player = new MediaPlayer(media);
        player.setAutoPlay(Autoplay);
    }
    public void startPlaying(String FilePathWithName) {
        this.media = new Media(FilePathWithName);
        this.player = new MediaPlayer(media);
    }
    public void stopPlaying() {
        this.player.stop();
    }
    public void startPlayingAgain() {
        this.player.play();
    }
    public void isAutoPlay(boolean Autoplay) {
        this.player.setAutoPlay(Autoplay);
    }
    public String getMediaPath()
    {
        return new String(this.player.getMedia().getSource());
    }
    public int ConvertMillisToNano(int in)
    {
        int temp = in;
        temp *= 1000;
        return temp;
    }
    public int getStartTime(String Type)
    {
        int temp = 0;
        if(Type.equals("HOURS"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toHours()));
        if(Type.equals("MINUTES"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toMinutes()));
        if(Type.equals("SECONDS"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toSeconds()));
        if(Type.equals("MILLISECOND"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toMillis()));
        if(Type.equals("MICROSECONDS"))temp = Integer.parseInt(String.valueOf(this.player.getStartTime().toMillis()))*1000;
        if(Type.equals("NANOSECONDS"))temp = ConvertMillisToNano(Integer.parseInt(String.valueOf(this.player.getStartTime().toMillis())));
        return temp;
    }

    public int getTimeOnPause(){
        return Integer.parseInt(String.valueOf(this.player.getOnStopped()));
    }
    public int getTotalTime(){
        return Integer.parseInt(String.valueOf(this.player.getTotalDuration()));
    }
    public void setVolume(short Volume){
        if(Volume == 0)isMute(true);
        if(Volume <= 100) this.player.setVolume(Volume);
        if(Volume < 0) this.player.setVolume(Volume*-1);
        if(Volume < -100)throw new InvalidParameterException("The Number Is too small");
        if(Volume > 100)throw new InvalidParameterException("The Number Is too big");
        this.Volume = Volume;
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
    public String getVolumePercentageLocally() {
        return String.valueOf(this.Volume)+'%';
    }
    public String getVolumePercentage() {
        return String.valueOf(getVolume())+'%';
    }
    public int getVolumeLocally()
    {
        return this.Volume;
    }
    public int getVolume()
    {
     return Integer.parseInt(String.valueOf(this.player.getVolume()));
    }
    public void isMute(boolean bool)
    {
        this.player.setMute(bool);
    }
    
}
