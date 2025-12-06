package com.app.main.controller;

import java.time.Clock;

/**
 * A Class for handling the time for the game
 * @author Dai Elias
 */
public final class TimerHandler {
    
   private static final long ONE_SEC = 1000;
    private static final long GAME_DURATION = ((ONE_SEC * 60) * 2) + ONE_SEC / 2;

    private final Clock clock;
    private final long whenStarted;


    public TimerHandler(){
        this(Clock.systemUTC());
    }

    public TimerHandler(Clock clock) {
        this.clock = clock;
        this.whenStarted = clock.millis();
    }

    /**
     * Tell if the time is passed and the game must finish
     * @return true if it's the case, false otherwise
     */
    public boolean isTimePassed(){
        return whenStarted + GAME_DURATION <= clock.millis();
    }

    /**
     * @return the remaining time
     */
    public String getTimeRemaining() {

        long timePassed = clock.millis() - whenStarted;
        long secRemaining = (GAME_DURATION - timePassed) / ONE_SEC;

        if (secRemaining < 0){
            secRemaining = 0;
        }    

        long minutes = secRemaining / 60;
        long seconds = secRemaining % 60;

        return String.format("%d:%02d", minutes, seconds);
    }

}
