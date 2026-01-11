package com.app.main.controller.playercontroller;

import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameLevel;
import com.app.main.model.GameLevel.TeamConfig;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;

import javafx.scene.input.KeyCode;

/**
 * The class ControllerInit must be used to initialize controller
 * for the game
 * 
 * @author Dai Elias
 */
public final class ControllerInit {

    private static final KeyCode[][] KEY_MAP = {
        {KeyCode.Z, KeyCode.S, KeyCode.D, KeyCode.Q},
        {KeyCode.I, KeyCode.K, KeyCode.L, KeyCode.J},
        {KeyCode.UP, KeyCode.DOWN, KeyCode.RIGHT, KeyCode.LEFT}
    };

    /**
     * Initialize the controllers for a level
     * @param gameLevel a GameLevel
     * @param loadedTeams the teams that have been load by a level loader
     * @return the controllers that has been initialize
     */
    public static Controller[] initializeControllers(GameLevel gameLevel, Team[] loadedTeams){


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
        int nbPlayer = 0;
        Controller[] controllers = new Controller[nbTeams];

        // Loading of bot controllers and verifications :
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
            else{
                nbPlayer++;
            }
        }
        // Loading of players controller :
        int pid = 0;

        for (int i = 0; i < controllers.length; i++) {
            
            if(gameLevel.getTeamsInfo().get(i).isPlayer){

                if(nbPlayer == 1){
                    controllers[i] = MouseController.createMouseController(loadedTeams[0]);
                    return controllers;
                }
                else{

                    if(pid < 3){
                        controllers[i] = KeyboardController .createKeyboardController(
                            loadedTeams[i],
                            GameManager.GRID_DIM, GameManager.GRID_DIM,
                            KEY_MAP[pid][0], KEY_MAP[pid][1], KEY_MAP[pid][2], KEY_MAP[pid][3]
                        );
                    }
                    else{
                        controllers[i] = MouseController.createMouseController(loadedTeams[i]);
                    }  
                    pid++;
                }
            }
        }
        return controllers;
    }
}
