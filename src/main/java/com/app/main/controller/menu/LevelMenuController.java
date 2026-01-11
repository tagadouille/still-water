package com.app.main.controller.menu;
import com.app.main.util.LevelLoader;

import javafx.fxml.FXML;

/**
 * Contrôleur pour le menu de sélection des niveaux.
 * <p>
 * Fournit des méthodes FXML pour charger différents niveaux et prépare
 * la scène de jeu en chargeant le niveau, les contrôleurs et l'image de fond.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public class LevelMenuController {

    @FXML
    public void tolevel1() {
        LevelLoader.loadLevel("levels/lvl1.json", "levelimages/");
    }

    @FXML
    public void tolevel2(){
        LevelLoader.loadLevel("levels/lvl2.json", "levelimages/");
    }

    @FXML
    public void tolevel3(){
        LevelLoader.loadLevel("levels/lvl3.json", "levelimages/");
    }

    @FXML
    public void tolevel4(){
        LevelLoader.loadLevel("levels/lvl4.json", "levelimages/");
    }

    @FXML
    public void tolevel5(){
        LevelLoader.loadLevel("levels/lvl5.json", "levelimages/");
    }

}
