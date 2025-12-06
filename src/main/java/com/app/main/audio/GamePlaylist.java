package com.app.main.audio;

import java.util.Random;

/**
 * This class represents the playlist of the game.
 * With that class we can play some musics @see Playlist
 * 
 * @author Dai Elias
 */
public final class GamePlaylist {

    private static final Playlist playlist = new Playlist(new Audio[]{
        new Audio( "bad-piggies-drip.wav"),
        new Audio( "Flowering-Night.wav"),
        new Audio( "level.wav")

    }, (float) 0.5, false, true);

    public static Playlist getPlaylist() {
        return playlist;
    }

    /**
     * Play a level music randomly
     */
    public static void playLevelAudio(){
        playlist.playOnlyOne(new Random().nextInt(2) + 1, true);
    }
}
