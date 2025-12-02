package com.app.main.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.app.main.controller.MouseController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Classe which extends StackPane who represent the view of the game zone where the particules
 * are displayed
 * @author Dai Elias
 */
public final class GridView extends StackPane {

    private Canvas canvas;
    private double width;
    private double height;
    private GameManager gameManager;
    private int currentFps = 0;
    Controller[] controllers;

    private GridView(double width, double height, GameManager gameManager, Controller[] controllers) {
        super();
        
        this.gameManager = gameManager;
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.getChildren().add(canvas);

        startGameLoop();
        this.controllers = controllers;

        if(this.controllers[0] == null){
            this.controllers[0] = MouseController.createMouseController(canvas, gameManager.getTeams()[0]);
        }

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
    public static GridView createGridView(double width, double height, GameManager gameManager, Controller[] controllers){ 
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The size of the game zone can't be negative or null");
        }
        if(gameManager == null){
            throw new IllegalArgumentException("The GameManager can't be null");
        }
        if(controllers == null){
            throw new IllegalArgumentException("The controller array can't be null");
        }
        if(controllers.length == 0){
            throw new IllegalArgumentException("The controllers can't be empty");
        }
        Controller[] newControllers = new Controller[controllers.length];

        for (int i = 0; i < controllers.length; i++) {

            if(i != 0 && controllers[i] == null){
                throw new IllegalArgumentException("A controller can't be null in the array of controller");
            }
            newControllers[i] = controllers[i];
        }
        return new GridView(width, height, gameManager, newControllers);
    }

    /**
     * For the gameloop functionnement
     */
    private void startGameLoop() {

        AnimationTimer gameLoop = new AnimationTimer() {
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
        /*try{
            gc.drawImage(new Image(Files.newInputStream(Paths.get("src/main/resources/com/app/image/john_pork.jpg"))), 0, 0, width, width);
        }catch(IOException e){

        }*/

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        
        for (Team team : gameManager.getTeams()) {
            ParticleView.renderParticles(gc, team, width, height);
        }

        //!Affichage des fps provisoire
        gc.setFill(Color.YELLOW);
        gc.setFont(new Font("comic sans ms", 15));
        gc.fillText("FPS: " + currentFps, 10, 20);
    }
}
