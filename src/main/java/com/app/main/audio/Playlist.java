package com.app.main.audio;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Modern audio playlist manager.
 * Supports sequential or shuffled playback, volume control, loop, and playing a specific track.
 * 
 * @author Dai Elias
 */
public final class Playlist {

    private final Audio[] playlist;
    private float volume;
    private boolean shuffle;
    private boolean loop;
    private ScheduledExecutorService executor;
    private int currentIndex = 0;
    private Audio currentAudio;

    /**
     * Constructs a Playlist.
     *
     * @param playlist array of Audio objects
     * @param volume initial volume (0.0 → 1.0)
     * @param shuffle true to play in random order
     * @param loop true to loop playlist indefinitely
     */
    public Playlist(Audio[] playlist, float volume, boolean shuffle, boolean loop) {
        this.playlist = playlist;
        this.volume = Math.max(0f, Math.min(volume, 1f));
        this.shuffle = shuffle;
        this.loop = loop;
        applyVolumeToAll();
    }

    /** Returns the playlist array. */
    public Audio[] getPlaylist() {
        return playlist;
    }

    /** Gets the current volume. */
    public float getVolume() {
        return volume;
    }

    /** Sets the playlist volume and applies it to all tracks. */
    public void setVolume(float volume) {
        this.volume = Math.max(0f, Math.min(volume, 1f));
        applyVolumeToAll();
    }

    /** Applies the current volume to all audio tracks. */
    private void applyVolumeToAll() {
        for (Audio a : playlist) {
            a.setVolume(volume);
        }
    }

    /** Starts playback of the playlist (sequential or shuffle). */
    public void play() {
        playNext(-1);
    }

    /**
     * Plays a specific track by index.
     *
     * @param index index of the track in the playlist
     */
    public void play(int index) {
        if (index < 0 || index >= playlist.length){
            return;
        }
        playNext(index);
    }

    /** Internal method to handle playback logic. */
    private void playNext(int forcedIndex) {
        stop(); // stop any existing playback

        executor = Executors.newSingleThreadScheduledExecutor();
        currentIndex = (forcedIndex >= 0) ? forcedIndex : 0;

        Runnable playTask = new Runnable() {
            private final Random random = new Random();

            @Override
            public void run() {
                if (playlist.length == 0) {
                    stop();
                    return;
                }

                // Choose next track
                if (forcedIndex >= 0) {
                    currentIndex = forcedIndex;
                } else if (shuffle) {
                    currentIndex = random.nextInt(playlist.length);
                } else if (currentIndex >= playlist.length) {
                    if (loop) {
                        currentIndex = 0;
                    } else {
                        stop();
                        return;
                    }
                }

                currentAudio = playlist[currentIndex];
                currentAudio.setVolume(volume);
                currentAudio.play();

                // Schedule next check
                executor.schedule(this, 500, TimeUnit.MILLISECONDS);

                // Move to next track if sequential and not forced
                if (!shuffle && forcedIndex < 0) {
                    currentIndex++;
                }
            }
        };

        executor.submit(playTask);
    }

    /** Stops the playlist and the currently playing track. */
    public void stop() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
            executor = null;
        }

        if (currentAudio != null && currentAudio.isPlaying()) {
            currentAudio.stop();
        }
    }

    /** Enables or disables looping. */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /** Enables or disables shuffle mode. */
    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }
}
