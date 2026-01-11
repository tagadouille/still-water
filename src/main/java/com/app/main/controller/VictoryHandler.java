package com.app.main.controller;

import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.model.core.Color;

/**
 * Class that must be used to determine who's the winner
 * @author Dai Elias
 */
public final class VictoryHandler {

    /**
     * Determine the winner by finding the last one standing
     * @return the color of the winner, Color.NO_COLOR if there's no winner
     */
    public static Color determineWinnerByDominance(GameManager gameManager){
        
        int nb0 = 0;
        for (int i = 0; i < gameManager.getForces().length; i++) {
            if(gameManager.getForces()[i] == 0){
                nb0++;
            }
        }

        Team winner = null;

        if(nb0 == gameManager.getForces().length - 1 ){
            for (int i = 0; i < gameManager.getForces().length; i++) {
                if(gameManager.getForces()[i] != 0){
                    winner = gameManager.getTeams()[i];
                }
            }
            if(winner != null){
                return winner.getTeam();
            }
        }
        return Color.NO_COLOR;  
    }

    /**
     * Determine the winner by the most powerful army.
     * Use this method when the time is up
     * @return the color of the winner, Color.NO_COLOR if there's no winner
     */
    public static Color determineWinnerByPower(GameManager gameManager){

        double maxForces = 0.0;
        Team winner = null;

        for (int i = 0; i < gameManager.getForces().length; i++) {

            if(gameManager.getForces()[i] > maxForces){
                maxForces = gameManager.getForces()[i];
                winner = gameManager.getTeams()[i];
            }
        }

        if(winner != null){
            return winner.getTeam();
        }
        return Color.NO_COLOR;
    }
}
