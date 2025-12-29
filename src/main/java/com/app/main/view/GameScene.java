package com.app.main.view;

import com.app.main.controller.GameInfoViewController;
import com.app.main.model.GameManager;
import com.app.main.util.Controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    private GameScene(GameManager gameManager, Controller[] controllers, Image levelBackground) {
        super(new HBox(), width, height);

        //Calculation for the proper canva size and the proper factor
        int canvaWidth = (int) Math.floor(width * 0.7);
        int canvaHeight = height;

        if(canvaHeight > height){
            canvaHeight = height;
        }

        xFactor = canvaWidth / (double) GameManager.GRID_DIM;
        yFactor = canvaHeight / (double) GameManager.GRID_DIM;

        GridView gridview = GridView.createGridView(canvaWidth, canvaHeight, gameManager, controllers, levelBackground);
        
        int rightPaneWidth = (int) Math.max(0, (int) width - canvaWidth);

        for (Controller c : controllers) {
            if (c != null) {
                c.setupInput(this, gridview.getCanvas());
            }
        }

        GameInfoView gameInfoView = new GameInfoView(rightPaneWidth);
        GameInfoViewController gameInfoViewController = GameInfoViewController.creaInfoViewController(gameInfoView);

        gridview.addObserver(gameInfoViewController);

        ((HBox) this.getRoot()).setSpacing(0);
        ((HBox) this.getRoot()).setPadding(Insets.EMPTY);
        ((HBox) this.getRoot()).getChildren().addAll(gridview, gameInfoView);
    }

    /**
     * Static fabric that create a new instance of GameScene
     * @param gameManager the gameManager
     * @param controllers all the players controllers
     * @param levelBackground the background of the level
     * @return a new instance of GameScene
     */
    public static GameScene buildGameScene(GameManager gameManager, Controller[] controllers, Image levelBackground){

        verifyInfo(gameManager, controllers);

        return new GameScene(gameManager, controllers, levelBackground);
    }

    /**
     * Static fabric that create a new instance of GameScene but with no background image
     * @param gameManager the gameManager
     * @param controllers all the players controllers
     * @return a new instance of GameScene
     */
    public static GameScene buildGameScene(GameManager gameManager, Controller[] controllers){

        verifyInfo(gameManager, controllers);

        return new GameScene(gameManager, controllers, null);
    }

    /**
     * Verify the validity of the parameters for creating a view for a game
     * @param gameManager gameManager
     * @param controllers all the players controllers
     * @param levelBackground the background of the level
     */
    public static void verifyInfo(GameManager gameManager, Controller[] controllers){

        if(gameManager == null){
            throw new IllegalArgumentException("The GameManager can't be null");
        }

        if(controllers == null){
            throw new IllegalArgumentException("The controller array can't be null");
        }

        if(controllers.length == 0){
            throw new IllegalArgumentException("The controllers can't be empty");
        }

        if(gameManager.getTeams().length != controllers.length){
            throw new IllegalArgumentException("The number of team must be the same as the number of controller");
        }

        for (int i = 0; i < controllers.length; i++) {

            if(controllers[i] == null){
                throw new IllegalArgumentException("A controller can't be null in the array of controller");
            }
        }
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
