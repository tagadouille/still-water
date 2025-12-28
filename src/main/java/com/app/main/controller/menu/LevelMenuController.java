package com.app.main.controller.menu;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.app.main.audio.GamePlaylist;
import com.app.main.controller.playercontroller.MouseController;
import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.view.GameScene;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class LevelMenuController {

    @FXML
    public void tolevel1() {
        loader("lvl1");
    }

    @FXML
    public void tolevel2(){
        loader("lvl2");
    }

    @FXML
    public void tolevel3(){
        loader("lvl3");
    }

    @FXML
    public void tolevel4(){
        loader("lvl4");
    }

    @FXML
    public void tolevel5(){
        loader("lvl5");
    }

    private void loader(String nameoffile){
        try {

            // Load the level : 
            GameManager gameManager = GameManager.createFromJSON("levels/" + nameoffile + ".json");

            Team[] loadedTeams = gameManager.getTeams();
            int nbTeams = loadedTeams.length;

            Controller[] controllers = new Controller[nbTeams];

            controllers[0] = MouseController.createMouseController(loadedTeams[0]);

            for (int i = 1; i < nbTeams; i++) {
                controllers[i] = new BotController(
                    gameManager.getWidth(), 
                    gameManager.getHeight(), 
                    loadedTeams[i]
                );
            }

            //Load the image :
            Image levelBackground = levelImageLoader(nameoffile);
            
            GamePlaylist.playLevelAudio();

            MenuSwitcher.switchScene(
                GameScene.buildGameScene(gameManager, controllers, levelBackground)
            );

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Image levelImageLoader(String nameoffile) {
        
        String[] imageType = {"png", "jpg", "jpeg", "gif", "bmp"};

        String imgPath = nameoffile;

        for (int i = 0; i < imageType.length; i++) {
            
            String tmp = "levelimages/" + imgPath + "." + imageType[i];

            if(new File(tmp).exists()){
                imgPath = tmp;
                break;
            }
        }

        try {
            Image ret = new Image(Files.newInputStream(Paths.get(imgPath)));
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

}
