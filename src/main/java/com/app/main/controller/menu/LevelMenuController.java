package com.app.main.controller.menu;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.app.main.audio.GamePlaylist;
import com.app.main.controller.playercontroller.ControllerInit;
import com.app.main.model.GameLevel;
import com.app.main.model.GameManager;
import com.app.main.util.Controller;
import com.app.main.util.GameLevelLoader;
import com.app.main.view.GameScene;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

/**
 * Contrôleur pour le menu de sélection des niveaux.
 * <p>
 * Fournit des méthodes FXML pour charger différents niveaux et prépare
 * la scène de jeu en chargeant le niveau, les contrôleurs et l'image de fond.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
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

    /**
     * Charge le niveau depuis le JSON, initialise le GameManager et les
     * contrôleurs, charge l'image de fond puis affiche la scène de jeu.
     *
     * @param nameoffile nom du fichier (sans extension) dans le dossier "levels"
     */
    private void loader(String nameoffile){
        try {

            // Load the level : 
            GameLevel gameLevel = GameLevelLoader.load("levels/" + nameoffile + ".json");

            GameManager gameManager = GameManager.createFromJSON(gameLevel);

            Controller[] controllers = ControllerInit.initializeControllers(gameLevel, gameManager.getTeams());

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

    /**
     * Initialise les contrôleurs pour les joueurs (implémentation par défaut
     * : le premier joueur utilise la souris).
     */
    @Override
    public void initializePlayerControllers(Controller[] controllers, Team[] loadedTeams) {
        controllers[0] = MouseController.createMouseController(loadedTeams[0]);
    }

}
