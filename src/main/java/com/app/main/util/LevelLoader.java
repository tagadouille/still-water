package com.app.main.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.app.main.audio.GamePlaylist;
import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.controller.playercontroller.ControllerInit;
import com.app.main.model.GameLevel;
import com.app.main.model.GameManager;
import com.app.main.view.GameScene;

import javafx.scene.image.Image;

/**
 * Classe utilitaire pour le chargement des niveaux de jeu.
 * 
 * Fournit une méthode statique pour charger un niveau depuis un
 * fichier JSON, initialiser le GameManager et les contrôleurs,
 * charger l'image de fond, puis afficher la scène de jeu.
 * 
 * @author Mohamed Ibrir
 */
public final class LevelLoader {

    /**
     * Constructeur privé pour empêcher l'instanciation de la classe utilitaire 
     * (à priori on a pas besoin d'objet issue de cette class).
     */
    private LevelLoader(){}
    
    /**
     * Charge le niveau depuis le JSON, initialise le GameManager et les
     * contrôleurs, charge l'image de fond puis affiche la scène de jeu.
     *
     * @param nameoffile nom du fichier (sans extension) dans le dossier "levels"
     */
    public static void loadLevel(String levelPath, String imagePath) {
        try {
            // Load the level : 
            GameLevel gameLevel = GameLevelLoader.load(levelPath);

            GameManager gameManager = GameManager.createFromJSON(gameLevel);

            Controller[] controllers = ControllerInit.initializeControllers(gameLevel, gameManager.getTeams());

            //Load the image :

            Image levelBackground = new Image(Files.newInputStream(Paths.get(imagePath+ gameLevel.backgroundImageFilename)));
            // Image levelBackground = new Image(Files.newInputStream(Paths.get("levelimages/"+ gameLevel.backgroundImageFilename)));
            
            GamePlaylist.playLevelAudio();

            MenuSwitcher.switchScene(
                GameScene.buildGameScene(gameManager, controllers, levelBackground)
            );

        } catch (Exception e ) {
            System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
