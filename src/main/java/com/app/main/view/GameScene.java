package com.app.main.view;

import com.app.main.model.GameManager;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Classe étendant Scene permettant de représenter la vue
 * du jeu
 * @author Dai Elias
 */
public final class GameScene extends Scene {

    private static double width = 1280;
    private static double height = 720;
    private static int resolution = 720; //Resolution de la grille de jeu

    private static final HBox root = new HBox(10);

    /**
     * Constructeur de la classe permettant
     * d'initialiser la vue
     * 
     * @param gameManager le gameManager
     */
    public GameScene(GameManager gameManager) {
        super(root, width, height);

        StackPane gridview = new GridView(resolution, gameManager);
        VBox gameInfoView = new GameInfoView();

        root.getChildren().addAll(gridview, gameInfoView);
    }
}
