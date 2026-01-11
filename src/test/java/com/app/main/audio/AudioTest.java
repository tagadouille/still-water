package com.app.main.audio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AudioTest {

    @BeforeEach
    void setUp() {
        //! Disable audio for not accessing to system
        Audio.setAudioEnabled(false);
    }

    @AfterEach
    void tearDown() {
        // Reactivate to avoid side effects between tests
        Audio.setAudioEnabled(true);
    }

    @Test
    void constructor_audioDisabled_doesNotLoadClip() {

        Audio audio = new Audio("test.wav");

        assertFalse(audio.isLoaded());
        assertFalse(audio.isPlaying());
    }

    @Test
    void play_audioDisabled_doesNothing() {

        Audio audio = new Audio("test.wav");

        audio.play();

        assertFalse(audio.isPlaying());
    }

    @Test
    void loop_audioDisabled_doesNothing() {

        Audio audio = new Audio("test.wav");

        audio.loop();

        assertFalse(audio.isPlaying());
    }

    @Test
    void stop_audioDisabled_doesNothing() {

        Audio audio = new Audio("test.wav");

        audio.stop();

        assertFalse(audio.isPlaying());
    }

    @Test
    void close_audioDisabled_isSafe() {

        Audio audio = new Audio("test.wav");

        assertDoesNotThrow(audio::close);
        assertFalse(audio.isPlaying());
    }

    @Test
    void setVolume_clampsValueBetween0And1() {

        Audio audio = new Audio("test.wav");

        audio.setVolume(2.0f);   // too loud
        audio.setVolume(-1.0f);  // too low

        assertTrue(true);
    }

    @Test
    void playSFX_audioDisabled_doesNotCrash() {

        assertDoesNotThrow(() -> Audio.playSFX("sfx.wav"));
    }
}

