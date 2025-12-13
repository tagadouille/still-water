package com.app.main.controller.menu;

import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.view.GameScene;

import javafx.fxml.FXML;

public class LevelMenuController {


    @FXML
    public void tolevel1() {
        try {
            GameManager gameManager = GameManager.createFromJSON("levels/lvl1.json");

            Team[] loadedTeams = gameManager.getTeams();
            int nbTeams = loadedTeams.length;

            Controller[] controllers = new Controller[nbTeams];

            controllers[0] = null;

            for (int i = 1; i < nbTeams; i++) {
                controllers[i] = new BotController(
                    gameManager.getWidth(), 
                    gameManager.getHeight(), 
                    loadedTeams[i]
                );
            }

            MenuSwitcher.switchScene(
                new GameScene(gameManager, controllers)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tolevel2(){
        try {
            GameManager gameManager = GameManager.createFromJSON("levels/lvl2.json");

            Team[] loadedTeams = gameManager.getTeams();
            int nbTeams = loadedTeams.length;

            Controller[] controllers = new Controller[nbTeams];

            controllers[0] = null;

            for (int i = 1; i < nbTeams; i++) {
                controllers[i] = new BotController(
                    gameManager.getWidth(), 
                    gameManager.getHeight(), 
                    loadedTeams[i]
                );
            }

            MenuSwitcher.switchScene(
                new GameScene(gameManager, controllers)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tolevel3(){
        try {
            GameManager gameManager = GameManager.createFromJSON("levels/lvl3.json");

            Team[] loadedTeams = gameManager.getTeams();
            int nbTeams = loadedTeams.length;

            Controller[] controllers = new Controller[nbTeams];

            controllers[0] = null;

            for (int i = 1; i < nbTeams; i++) {
                controllers[i] = new BotController(
                    gameManager.getWidth(), 
                    gameManager.getHeight(), 
                    loadedTeams[i]
                );
            }

            MenuSwitcher.switchScene(
                new GameScene(gameManager, controllers)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tolevel4(){
        try {
            GameManager gameManager = GameManager.createFromJSON("levels/lvl4.json");

            Team[] loadedTeams = gameManager.getTeams();
            int nbTeams = loadedTeams.length;

            Controller[] controllers = new Controller[nbTeams];

            controllers[0] = null;

            for (int i = 1; i < nbTeams; i++) {
                controllers[i] = new BotController(
                    gameManager.getWidth(), 
                    gameManager.getHeight(), 
                    loadedTeams[i]
                );
            }

            MenuSwitcher.switchScene(
                new GameScene(gameManager, controllers)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tolevel5(){
        try {
            GameManager gameManager = GameManager.createFromJSON("levels/lvl5.json");

            Team[] loadedTeams = gameManager.getTeams();
            int nbTeams = loadedTeams.length;

            Controller[] controllers = new Controller[nbTeams];

            controllers[0] = null;

            for (int i = 1; i < nbTeams; i++) {
                controllers[i] = new BotController(
                    gameManager.getWidth(), 
                    gameManager.getHeight(), 
                    loadedTeams[i]
                );
            }

            MenuSwitcher.switchScene(
                new GameScene(gameManager, controllers)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
