package com.app.main.controller;

/**
 * 
 * @author Dai Elias
 */
public final class TimerHandler {
    
    private static final long ONE_SEC = 1000;
    private static final long GAME_DURATION = ((ONE_SEC * 60) * 2) + ONE_SEC / 2;

    private long whenStarted;

    public TimerHandler(){
        this.whenStarted = System.currentTimeMillis();
    }

    /**
     * Tell if the time is passed and the game must finish
     * @return true if it's the case, false otherwise
     */
    public boolean isTimePassed(){
        return whenStarted + GAME_DURATION <= System.currentTimeMillis();
    }

    /**
     * @return the remaining time
     */
    public String getTimeRemaining() {

        long timePassed = System.currentTimeMillis() - whenStarted;
        long secRemaining = (GAME_DURATION - timePassed) / ONE_SEC;

        if (secRemaining < 0){
            secRemaining = 0;
        }    

        long minutes = secRemaining / 60;
        long seconds = secRemaining % 60;

        return String.format("%d:%02d", minutes, seconds);
    }

}
