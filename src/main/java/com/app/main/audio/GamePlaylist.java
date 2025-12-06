package com.app.main.audio;

public final class GamePlaylist {
    private static final String SOUND_PATH = "src/main/resources/audio";

    private static Playlist playlist = new Playlist(new Audio[]{
        new Audio(SOUND_PATH + "bad-piggies-drip.wav"),
        new Audio(SOUND_PATH + "Flowering-Night.wav")

    }, (float) 0.5, false, false);

    public static Playlist getPlaylist() {
        return playlist;
    }
}
