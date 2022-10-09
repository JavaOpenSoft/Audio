package jadt.utils.audio.recorder;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class RecordStream {
    private float sampleRate = 16000;
    private int sampleSizeInBits = 8;
    private int channels = 2;
    private boolean signed = true;
    private boolean bigEndian = true;
    private File file;
    private TargetDataLine line;
    private AudioFileFormat.Type fileType;
    private boolean isVerboseOutputEnabled = false;


    public boolean isVerboseOutputEnabled() {
        return isVerboseOutputEnabled;
    }
    public void setVerboseOutputEnabled(boolean verboseOutputEnabled) {
        isVerboseOutputEnabled = verboseOutputEnabled;
    }
    public AudioFormat getAudioFormat() {
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    public File getRecordedSoundFile() {
        return file;
    }

    public TargetDataLine getTargetDataLine() {
        return line;
    }

    public void setTargetDataLine(TargetDataLine line) {
        this.line = line;
    }

    public AudioFileFormat.Type getFileType() {
        return fileType;
    }

    public void setFileType(AudioFileFormat.Type fileType) {
        this.fileType = fileType;
    }

    public void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            if(isVerboseOutputEnabled)System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            if(isVerboseOutputEnabled)System.out.println("Start recording...");

            // start recording
            AudioSystem.write(ais, fileType, file);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    public void stopRecording() {
        line.stop();
        line.close();
        if (isVerboseOutputEnabled)System.out.println("Finished");
    }
    public float getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(float sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getSampleSizeInBits() {
        return sampleSizeInBits;
    }

    public void setSampleSizeInBits(int sampleSizeInBits) {
        this.sampleSizeInBits = sampleSizeInBits;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public boolean isBigEndian() {
        return bigEndian;
    }

    public void setBigEndian(boolean bigEndian) {
        this.bigEndian = bigEndian;
    }
}
