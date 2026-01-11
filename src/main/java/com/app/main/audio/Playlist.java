package com.app.main.audio;

import java.util.Random;

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

    private int currentIndex = 0;
    
    private Audio currentAudio;
    
    private final Random random = new Random();

    private Thread playbackThread;

    /**
     * The constructor of the class for initialize the main fields
     * @param playlist the playlist
     * @param volume the volume of all the musics
     * @param shuffle if the music is played randomly for play method
     * @param loop for playing the playlist in loop
     */
    public Playlist(Audio[] playlist, float volume, boolean shuffle, boolean loop) {
        this.playlist = playlist;
        this.volume = Math.max(0f, Math.min(volume, 1f));
        this.shuffle = shuffle;
        this.loop = loop;
        applyVolumeToAll();
    }

    public Audio[] getPlaylist() {
        return playlist;
    }

    public float getVolume() {
        return volume;
    }
    
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0f, Math.min(volume, 1f));
        applyVolumeToAll();
    }

    private void applyVolumeToAll() {
        for (Audio a : playlist) {
            a.setVolume(volume);
        }
    }

    /**
     * For playing the playlist
     */
    public void play() {
        playNext(-1);
    }

    /**
     * For playing the playlist by start by a music of an certain index
     * @param index the index of the music
     */
    public void play(int index){
        playNext(index);
    }

    /**
     * For playing only one sound of the playlist
     * @param index the index of the music
     * @param loop if it'll be played on loop or no
     */
    public void playOnlyOne(int index, boolean loop) {

        if (index < 0 || index >= playlist.length){
            return;
        }

        stop();

        playbackThread = Thread.startVirtualThread(() -> {

            do {
                currentAudio = playlist[index];
                currentAudio.setVolume(volume);
                currentAudio.play();

                // attendre la fin du son
                while (currentAudio.isPlaying()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        return; // stop() a été appelé
                    }
                }
            } while (loop);
        });
    }

    private void playNext(int forcedIndex) {
        stop(); // Stop any currently playing track

        if (playlist.length == 0) return;

        playbackThread = Thread.startVirtualThread(() -> {
            int localIndex = (forcedIndex >= 0) ? forcedIndex : currentIndex;

            while (true) {

                if (forcedIndex >= 0) {
                    localIndex = forcedIndex;
                }
                else if (shuffle) {
                    localIndex = random.nextInt(playlist.length);
                }
                else if (localIndex >= playlist.length) {
                    if (loop){
                        localIndex = 0;
                    }
                    else break;
                }

                currentIndex = localIndex;
                currentAudio = playlist[currentIndex];
                currentAudio.setVolume(volume);
                currentAudio.play();

                // Wait until the current track finishes
                while (currentAudio.isPlaying()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                // Move to next track if sequential and not forced
                if (!shuffle && forcedIndex < 0) {
                    localIndex++;
                }

                // Reset forcedIndex after first forced play
                if(forcedIndex != -1){
                    localIndex = -1;
                }
            }
        });
    }

    /**
     * Stop the current music defininetely
     */
    public void stop() {
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
            playbackThread = null;
        }

        if (currentAudio != null && currentAudio.isPlaying()) {
            currentAudio.stop();
        }
    }
}
