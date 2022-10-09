package jadt.utils.audio;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import javax.sound.sampled.*;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.mp4.MP4Container;
import net.sourceforge.jaad.mp4.MP4Exception;
import net.sourceforge.jaad.mp4.api.*;

public class AudioAAC {
    private File audioFile;
    private AACPlayer aacPlayer;
    public AudioAAC() {

    }
    public AudioAAC(String fileName) {
        this.audioFile = new File(fileName);
        aacPlayer = new AACPlayer(audioFile);
    }
    public void play() {
        aacPlayer.play();
    }
    public void stop() {
        aacPlayer.stop();
    }
    public void pause() {
        aacPlayer.pause();
    }
    public void playForever(){
        aacPlayer.enableLoop();
    }
    public void resume(){
        aacPlayer.resume();
    }
    public void playRepeatedly(){
        aacPlayer.enableRepeat();
    }
    public File getAudioFile(){
        return audioFile;
    }
}
class AACPlayer{

        private boolean loop;
        private boolean repeat;
        private Thread  playback;
        private boolean paused;
        private boolean muted;
        private boolean interrupted;
        private File[]  files;

        /**
         * creates a new Instance of AACPlayer with a set of Files to be played back.
         * @param files Filelist to playback.
         */
        public AACPlayer(File[] files)
        {
            // init these fields
            loop        = false;
            repeat      = false;
            paused      = false;
            muted       = false;
            interrupted = false;


            List<File> validFiles = new LinkedList<>();

            for (File temp: files)
            {
                try
                {
                    MP4Container cont = new MP4Container(new RandomAccessFile(temp, "r"));
                    Movie movie = cont.getMovie();
                    List<Track> includedTracks = movie.getTracks();

                    if (!includedTracks.isEmpty())
                        validFiles.add(temp);
                    else
                        System.err.println("no tracks found in " + temp.getName() + ". Skipping this one.");
                }
                catch(IOException e)
                {
                    System.err.println("FileNotFound, skipping " + temp.getName());
                }
            }

            this.files = new File[validFiles.size()];
            for (int i=0; i < validFiles.size(); i++)
                this.files[i] = (File) validFiles.get(i);
        }

        /**
         * Player with only one File in List.
         * @param file
         */
        public AACPlayer(File file)
        {
            this (new File[] {file});
        }

        /**
         * Instances a new Player with one File, Path given as String.
         * @param pathToFile
         */
        public AACPlayer(String pathToFile)
        {
            this (new File (pathToFile));
        }

        private void initThread()
        {
            interrupted = false;
            playback = new Thread(() ->
            {
                // local vars
                byte[]          b;
                AudioTrack      track;
                AudioFormat     af;
                SourceDataLine  line;
                Decoder         dec;
                Frame           frame;
                SampleBuffer    buf;
                int             currentTrack;
                MP4Container    cont;
                Movie           movie;

                try
                {
                    for (currentTrack = 0; currentTrack < files.length; currentTrack++)
                    {
                        cont    = new MP4Container(new RandomAccessFile(files[currentTrack], "r"));
                        movie   = cont.getMovie();
                        track = null;
                        for (Track elem : movie.getTracks()) {
                            if(elem instanceof AudioTrack) {
                                track   = (AudioTrack) elem;
                                break;
                            }
                        }
                        if(track == null)
                            throw new MP4Exception("No audiotracks");
                        af      = new AudioFormat(track.getSampleRate(), track.getSampleSize(), track.getChannelCount(), true, true);
                        line    = AudioSystem.getSourceDataLine(af);
                        line.open();
                        line.start();

                        dec     = new Decoder(track.getDecoderSpecificInfo());

                        buf = new SampleBuffer();

                        playback:
                        while(!interrupted && track.hasMoreFrames())
                        {
                            frame = track.readNextFrame();
                            dec.decodeFrame(frame.getData(), buf);
                            b = buf.getData();
                            if (!muted)
                                line.write(b, 0, b.length);

                            while (paused)
                            {
                                Thread.sleep(500);

                                if (interrupted)
                                    break playback;
                            }
                        }

                        line.drain();
                        line.close();

                        if (interrupted)
                        {
                            Thread.currentThread().interrupt();
                            return;
                        }

                        if (loop)
                            currentTrack--;
                        else if (repeat && (currentTrack == files.length -1))
                            currentTrack = -1;
                    }
                }
                catch (LineUnavailableException | IOException | InterruptedException e)
                {
                    e.printStackTrace();
                }
            });
        }

        /**
         * Starts Playback of given File(s) with the first file.
         */
        public void play()
        {
            // check if playback is still running
            if (playback != null && playback.isAlive())
            {
                System.err.println("it plays yet, before you start again, stop it.");
                return;
            }

            initThread();           // init new Thread
            playback.start();       // and start it
        }

        /**
         * Stops playback
         */
        public void stop()
        {
            interrupted = true;         // set own interrupted flag
        }

        /**
         * Pauses playback.
         * Can be resumed at paused position with method resume.
         */
        public void pause()
        {
            paused = true;
        }

        /**
         * resumes playback on paused position.
         * If playback isn't paused, nothing happens.
         */
        public void resume()
        {
            paused = false;
        }

        /**
         * mutes playback (in background the file is still processed, but the audio data not given
         * to the AudioSystem.
         *
         * @return true if player is muted after call
         */
        public boolean toggleMute()
        {
            this.muted = !this.muted;
            return this.isMuted();
        }

        /**
         * Enables loop of current file.
         */
        public void enableLoop()
        {
            loop = true;
        }

        /**
         * Disables loop of current file.
         */
        public void disableLoop()
        {
            loop = false;
        }

        /**
         * Enabled repeated playback of whole file list.
         */
        public void enableRepeat()
        {
            repeat = true;
        }

        /**
         * Disables repeated playback of whole filelist.
         */
        public void disableRepeat()
        {
            repeat = false;
        }

        /**
         * Checks the state of playback.
         * @return true if playback thread is still alive
         */
        public boolean isPlaying()
        {
            if (playback != null)
                return playback.isAlive();
            else
                return false;
        }

        /**
         * Returns the mute state
         * @return true if player is muted right now
         */
        public boolean isMuted()
        {
            return this.muted;
        }

}
