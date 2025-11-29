package com.app.main.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Classe étendant VBox permettant de représenter
 * la partie de la vue du jeu où sont affichés les
 * éléments relatif au déroulement de la partie 
 * (nombre de FPS, répartition des équipes, points gagnés, etc..)
 * @author Dai Elias
 */
public final class GameInfoView extends VBox{

    /**
     * Constructeur de la classe permettant d'initialiser ses
     * principaux composants
     */
    public GameInfoView(){
        super();
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, getInsets())));
        this.setSpacing(10);
        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
