package com.app.main.audio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PlaylistTest {

    @AfterEach
    void tearDown() {
        // Sécurité : éviter des threads orphelins
        Audio.setAudioEnabled(false);
    }

    @Test
    void constructor_appliesVolumeToAllAudios() {

        Audio a1 = mock(Audio.class);
        Audio a2 = mock(Audio.class);

        new Playlist(new Audio[]{a1, a2}, 0.7f, false, false);

        verify(a1).setVolume(0.7f);
        verify(a2).setVolume(0.7f);
    }

    @Test
    void setVolume_updatesAllAudios() {

        Audio a1 = mock(Audio.class);
        Audio a2 = mock(Audio.class);

        Playlist playlist = new Playlist(new Audio[]{a1, a2}, 0.3f, false, false);

        playlist.setVolume(1.5f); // clamp à 1.0

        verify(a1).setVolume(1.0f);
        verify(a2).setVolume(1.0f);
    }

    @Test
    void play_withEmptyPlaylist_doesNothing() {

        Playlist playlist = new Playlist(new Audio[]{}, 0.5f, false, false);

        assertDoesNotThrow(() -> playlist.play());
    }

    @Test
    void playOnlyOne_invalidIndex_doesNothing() {

        Audio audio = mock(Audio.class);
        Playlist playlist = new Playlist(new Audio[]{audio}, 0.5f, false, false);

        playlist.playOnlyOne(-1, false);
        playlist.playOnlyOne(5, false);

        verify(audio, never()).play();
    }

    @Test
    void playOnlyOne_validIndex_playsAudio() {

        Audio audio = mock(Audio.class);
        when(audio.isPlaying()).thenReturn(false);

        Playlist playlist = new Playlist(new Audio[]{audio}, 0.5f, false, false);

        playlist.playOnlyOne(0, false);

        verify(audio, timeout(200).atLeastOnce()).setVolume(0.5f);
        verify(audio, timeout(200)).play();
    }

    @Test
    void play_startsPlayback() throws InterruptedException {

        Audio audio = mock(Audio.class);
        when(audio.isPlaying()).thenReturn(false);

        Playlist playlist = new Playlist(new Audio[]{audio}, 0.5f, false, false);

        playlist.play();

        Thread.sleep(50);

        verify(audio).play();
    }

    @Test
    void stop_interruptsPlaybackAndStopsAudio() throws InterruptedException {

        Audio audio = mock(Audio.class);
        when(audio.isPlaying()).thenReturn(true);

        Playlist playlist = new Playlist(new Audio[]{audio}, 0.5f, false, false);

        playlist.play();
        Thread.sleep(50);

        playlist.stop();

        verify(audio).stop();
    }

    @Test
    void shuffleMode_playsAtLeastOneAudio() throws InterruptedException {
        Audio a1 = mock(Audio.class);
        Audio a2 = mock(Audio.class);

        when(a1.isPlaying()).thenReturn(false);
        when(a2.isPlaying()).thenReturn(false);

        Playlist playlist = new Playlist(
                new Audio[]{a1, a2},
                0.5f,
                true,
                false
        );

        playlist.play();
        Thread.sleep(50);
        playlist.stop();

        // Au moins un des deux a été joué
        verify(a1, atLeastOnce()).play();
        verify(a2, atLeastOnce()).play();
    }

}
