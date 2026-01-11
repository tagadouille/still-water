package com.app.main.controller;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimerHandlerTest {

    /**
     * For simulating the clock
     */
    private final class MutableClock extends Clock {
        private Instant now;
        private final ZoneId zone;

        public MutableClock(Instant now, ZoneId zone) {
            this.now = now;
            this.zone = zone;
        }

        public void addSeconds(long seconds) {
            now = now.plusSeconds(seconds);
        }

        @Override
        public ZoneId getZone() {
            return zone;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return new MutableClock(now, zone);
        }

        @Override
        public Instant instant() {
            return now;
        }
    }
    // valeur connue: GAME_DURATION = 120500 ms => 120.5s => affichage "2:00" initial
    @Test
    public void testInitialAndAfter30Seconds() {
        Instant start = Instant.ofEpochMilli(1_000_000L);
        MutableClock clock = new MutableClock(start, ZoneId.systemDefault());

        TimerHandler timer = new TimerHandler(clock);

        assertEquals("2:00", timer.getTimeRemaining());

        clock.addSeconds(30);
        assertEquals("1:30", timer.getTimeRemaining());

        clock.addSeconds(60);
        assertEquals("0:30", timer.getTimeRemaining());
    }

    @Test
    public void testTimePassedAndZeroClamped() {
        Instant start = Instant.ofEpochMilli(2_000_000L);
        MutableClock clock = new MutableClock(start, ZoneId.systemDefault());

        TimerHandler timer = new TimerHandler(clock);

        clock.addSeconds(130);
        assertTrue(timer.isTimePassed());
        assertEquals("0:00", timer.getTimeRemaining());
    }
}
