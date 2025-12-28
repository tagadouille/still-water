package com.app.main.util;

import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameLevel;
import com.app.main.model.GameLevel.TeamConfig;
import com.app.main.model.core.Team;

/**
 * The interface ControllerInit must be used to initialize controller
 * for the game
 * 
 * @author Dai Elias
 */
public interface ControllerInit {
    
    /**
     * Initialize the  player controllers
     * @param controllers the controllers to initialize
     */
    public void initializePlayerControllers(Controller[] controllers, Team[] loadedTeams);

    /**
     * Initialize the controllers for a level
     * @param gameLevel a GameLevel
     * @param loadedTeams the teams that have been load by a level loader
     * @return the controllers that has been initialize
     */
    public default Controller[] initializeControllers(GameLevel gameLevel, Team[] loadedTeams){


        // Verifications :
        if(gameLevel == null){
            throw new IllegalArgumentException("The GameLevel can't be null");
        }

        if(loadedTeams == null){
            throw new IllegalArgumentException("The loadedTeams parameter can't be null");
        }

        if(loadedTeams.length != gameLevel.getTeamsInfo().size()){
            throw new IllegalArgumentException("The length of loadedTeams must be the same as gameLevel.getTeamsInfo().size()");
        }

        int nbTeams = loadedTeams.length;

        Controller[] controllers = new Controller[nbTeams];

        initializePlayerControllers(controllers, loadedTeams);

        // Loading of the bot controllers :
        for (int i = 0; i < nbTeams; i++) {

            TeamConfig teamConfig = gameLevel.getTeamsInfo().get(i);

            if(teamConfig == null){
                throw new IllegalArgumentException("The team config of the game level can't be null");
            }

            if(teamConfig.color != loadedTeams[i].getTeam()){
                throw new IllegalArgumentException("The color of a TeamConfig and a loaded team must be the same");
            }

            if(!teamConfig.isPlayer){

                controllers[i] = new BotController(loadedTeams[i]);
            }
            
        }

        return controllers;

    }
}
