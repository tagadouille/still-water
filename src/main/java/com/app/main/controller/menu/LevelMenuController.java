package com.app.main.controller.menu;

import com.app.main.audio.GamePlaylist;
import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.view.GameScene;

import javafx.fxml.FXML;

public class LevelMenuController {


    @FXML
    public void tolevel1() {
        loader("levels/lvl1.json");
    }

    @FXML
    public void tolevel2(){
        loader("levels/lvl2.json");
    }

    @FXML
    public void tolevel3(){
        loader("levels/lvl3.json");
    }

    @FXML
    public void tolevel4(){
        loader("levels/lvl4.json");
    }

    @FXML
    public void tolevel5(){
        loader("levels/lvl5.json");
    }

    private void loader(String nameoffile){
        try {
            GameManager gameManager = GameManager.createFromJSON(nameoffile);

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

            GamePlaylist.playLevelAudio();

            MenuSwitcher.switchScene(
                new GameScene(gameManager, controllers)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
