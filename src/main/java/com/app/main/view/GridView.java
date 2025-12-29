package com.app.main.view;

import java.util.ArrayList;
import java.util.List;

import com.app.main.controller.TimerHandler;
import com.app.main.controller.VictoryHandler;
import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.model.core.Color;
import com.app.main.util.Controller;
import com.app.main.util.Observable;
import com.app.main.util.Observer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

/**
 * Classe which extends StackPane who represent the view of the game zone where the particules
 * are displayed
 * @author Dai Elias
 */
final class GridView extends StackPane implements Observable{

    private Canvas canvas;
    private double width;
    private double height;

    private GameManager gameManager;
    private int currentFps = 0;
    private Controller[] controllers;

    private TimerHandler timer;

    private AnimationTimer gameLoop;

    private Image levelBackground;

    List<Observer> observers = new ArrayList<>();

    private GridView(double width, double height, GameManager gameManager, Controller[] controllers, Image levelBackground) {
        super();
        
        this.gameManager = gameManager;
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.getChildren().add(canvas);

        this.levelBackground = levelBackground;

        this.controllers = controllers;

        // Initialisation of the team array of the bots
        for (Controller controller : controllers) {

            if(controller instanceof BotController){
                BotController bc = (BotController) controller;
                bc.setAllTeam(gameManager.getTeams());
            }
        }
        this.timer = new TimerHandler();

        startGameLoop();
        canvas.requestFocus();
    }

    /**
     * Static factory for create the game zone
     * @param width the width of the canva
     * @param height the height of the canva
     * @param gameManager the GameManager
     * @param controllers an array of all the controllers that will be used by the players. If the first
     * element is null it's a player with a mouse.
     */
    static GridView createGridView(double width, double height, GameManager gameManager, Controller[] controllers, Image levelBackground){ 

        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The size of the game zone can't be negative or null");
        }

        GameScene.verifyInfo(gameManager, controllers);

        return new GridView(width, height, gameManager, controllers, levelBackground);
    }

    GameManager getGameManager() {
        return gameManager;
    }

    int getCurrentFps() {
        return currentFps;
    }

    TimerHandler getTimer() {
        return timer;
    }

    Canvas getCanvas() {
        return canvas;
    }

    /**
     * For the gameloop functionnement
     */
    private void startGameLoop() {

        gameLoop = new AnimationTimer() {
            // For run the game at max 60 FPS
            int fps = 60;
            double intervalMaj = 1000000000.0 / fps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long actualTime;

            // for display the FPS
            long fpsTimer = System.currentTimeMillis();
            int frameCount = 0;

            @Override
            public void handle(long now) {
                actualTime = System.nanoTime();
                delta += (actualTime - lastTime) / intervalMaj;
                lastTime = actualTime;

                if(delta >= 1){

                    update();
                    render(canvas.getGraphicsContext2D());

                    frameCount++;

                    delta--;
                }

                // Update of the FPS every secs
                if(System.currentTimeMillis() - fpsTimer >= 1000){
                    currentFps = frameCount;
                    frameCount = 0;
                    fpsTimer += 1000;
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Update the model
     */
    private void update() {
        Color c = VictoryHandler.determineWinnerByDominance(gameManager);

        if(c != Color.NO_COLOR){
            this.notifyObservers(this, c, "winner");
            gameLoop.stop();
        }
        this.notifyObservers(this, null, "info");

        if(timer.isTimePassed()){

            c = VictoryHandler.determineWinnerByPower(gameManager);
            this.notifyObservers(this, c, "winner");
            gameLoop.stop();
            
        }

        for(Controller controller : controllers){
            controller.update();
        }
        gameManager.update();
    }

    /**
     * Update the view
     * @param gc the GraphicsContext of the canva
     */
    private void render(GraphicsContext gc) {

        gc.clearRect(0, 0, width, height);

        if(levelBackground != null) {
            gc.drawImage(levelBackground, 0, 0, width, height);
        }
        else{
            gc.setFill(javafx.scene.paint.Color.WHITE);
            gc.fillRect(0, 0, width, height);
        }
        
        for (Team team : gameManager.getTeams()) {
            ParticleView.renderParticles(gc, team, width, height);
        }
        for (Team team : gameManager.getTeams()) {
            TeamCursor.displayTeamCursor(gc, team);
        }
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
