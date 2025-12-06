package com.app.main.audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Simple audio manager built on top of javax.sound.sampled.Clip.
 * Supports loading, playing, looping, stopping and volume control.
 * Audio can also be globally disabled (e.g. during JavaFX tests).
 * 
 * @author Dai Elias
 */
public final class Audio {

    private static boolean audioEnabled = true;

    private Clip clip;
    private FloatControl volumeControl;
    private float volume = 0.5f;
    private boolean playing = false;

    public Audio(String file){
        this.load(file);
    }

    /** 
    * Enables or disables audio globally (useful for tests).
    * @param enabled true if enable, false for disable
    */
    public static void setAudioEnabled(boolean enabled) {
        audioEnabled = enabled;
    }

    /** 
     * Returns whether the clip is loaded. 
     * @return true if the clip is loaded
     */
    public boolean isLoaded() {
        return clip != null;
    }

    /** 
     * Returns whether the audio is currently playing.
     * @return true if the audio is playing, false otherwise
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
    * Loads an audio file from the resources folder.
    * @param filename the filename
    */
    public void load(String fileName) {
        if (!audioEnabled)
            return;

        try {
           
            URL url = getClass().getResource("/com/app/audio/" + fileName);
            if (url == null) {
                System.err.println("Audio file not found: " + fileName);
                return;
            }

            AudioInputStream stream = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(stream);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                applyVolume();
            }

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    playing = false;
                }
            });

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Audio system not available, disabling audio.");
            audioEnabled = false;
        }
    }

    /** 
    * Plays the loaded audio once.
    */
    public void play() {
        if (clip != null && audioEnabled) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
            playing = true;
        }
    }

    /** 
    *Loops the audio continuously. 
    */
    public void loop() {
        if (clip != null && audioEnabled) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            playing = true;
        }
    }

    /** 
     * Stops playback (can be resumed later). 
     */
    public void stop() {
        if (clip != null && clip.isRunning() && audioEnabled) {
            clip.stop();
            playing = false;
        }
    }

    /** 
     * Fully closes and releases the audio clip. 
    */
    public void close() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.close();
            clip = null;
            playing = false;
        }
    }

    /** 
     * Sets the volume in the range 0.0 → 1.0.
    */
    public void setVolume(float v) {
        this.volume = Math.max(0f, Math.min(v, 1f));
        applyVolume();
    }

    /** 
     * Converts linear volume to decibels and applies it.
     */
    private void applyVolume() {
        if (volumeControl != null) {
            // Convert linear gain to dB
            float gainDB = (float) (20 * Math.log10(Math.max(volume, 0.0001)));
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            volumeControl.setValue(Math.max(min, Math.min(gainDB, max)));
        }
    }

    /**
     * Static helper for quick SFX playback.
     * Creates a temporary Audio instance, plays it, and auto-frees afterwards.
     * 
     * @param file the file to play
     */
    public static void playSFX(String file) {
        Audio audio = new Audio(file);
        audio.play();
    }
}
