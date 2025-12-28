package com.app.main.controller.menu;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.app.main.audio.GamePlaylist;
import com.app.main.controller.ControllerInit;
import com.app.main.controller.playercontroller.MouseController;
import com.app.main.model.GameLevel;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.util.GameLevelLoader;
import com.app.main.view.GameScene;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class LevelMenuController implements ControllerInit{

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
            GameLevel gameLevel = GameLevelLoader.load("levels/" + nameoffile + ".json");

            GameManager gameManager = GameManager.createFromJSON(gameLevel);

            Controller[] controllers = initializeControllers(gameLevel, gameManager.getTeams());

            for (int i = 0; i < controllers.length; i++) {
                
                if(controllers[i] == null){
                    System.out.println("Il est null");
                }
                System.out.println(controllers + " " + i);

            }

            //Load the image :
            Image levelBackground = new Image(Files.newInputStream(Paths.get("levelimages/"+ gameLevel.backgroundImageFilename)));
            
            GamePlaylist.playLevelAudio();

            MenuSwitcher.switchScene(
                GameScene.buildGameScene(gameManager, controllers, levelBackground)
            );

        } catch (Exception e ) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int initializePlayerControllers(Controller[] controllers, Team[] loadedTeams) {
        controllers[0] = MouseController.createMouseController(loadedTeams[0]);
        return 1;
    }

}
