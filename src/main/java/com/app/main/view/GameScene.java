package com.app.main.view;

import com.app.main.controller.GameInfoViewController;
import com.app.main.model.GameManager;
import com.app.main.util.Controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

/**
 * Class extend Scene for representing the view of the game
 * @author Dai Elias
 */
public final class GameScene extends Scene {

    private static int width = 1280;
    private static int height = 720;

    /**Scaling factor of the particles */
    private static double yFactor;
    private static double xFactor;

    /**
     * Constructor of the class for initiating the view
     * 
     * @param gameManager the gameManager
     * @param controllers an array of all the controllers that will be used by the players. If the first
     * element is null it's a player with a mouse.
     */
    public GameScene(GameManager gameManager, Controller[] controllers) {
        super(new HBox(), width, height);

        //Calculation for the proper canva size and the proper factor
        int canvaWidth = (int) Math.floor(width * 0.7);
        int canvaHeight = height;

        if(canvaHeight > height){
            canvaHeight = height;
        }

        xFactor = canvaWidth / (double) GameManager.GRID_DIM;
        yFactor = canvaHeight / (double) GameManager.GRID_DIM;

        GridView gridview = GridView.createGridView(canvaWidth, canvaHeight, gameManager, controllers);
        
        int rightPaneWidth = (int) Math.max(0, (int) width - canvaWidth);

        for (Controller c : controllers) {
            if (c != null) {
                c.setupInput(this, gridview.getCanvas());
            }
        }

        GameInfoView gameInfoView = new GameInfoView(rightPaneWidth);
        GameInfoViewController gameInfoViewController = GameInfoViewController.creaInfoViewController(gameInfoView);

        gridview.addObserver(gameInfoView);
        gridview.addObserver(gameInfoViewController);

        ((HBox) this.getRoot()).setSpacing(0);
        ((HBox) this.getRoot()).setPadding(Insets.EMPTY);
        ((HBox) this.getRoot()).getChildren().addAll(gridview, gameInfoView);
    }

    public static double getScreenWidth() {
        return width;
    }

    public static double getScreenHeight() {
        return height;
    }

    public static double getxFactor() {
        return xFactor;
    }

    public static double getyFactor() {
        return yFactor;
    }
}
