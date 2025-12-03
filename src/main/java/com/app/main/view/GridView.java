package com.app.main.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.app.main.controller.playercontroller.MouseController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Classe étendant StackPane qui représente la vue de la
 * zone de jeu où sont affichés les particules
 * @author Dai Elias
 */
public final class GridView extends StackPane {

    private Canvas canvas;
    private double width;
    private double height;
    private GameManager gameManager;
    private int currentFps = 0;

    private GridView(double width, double height, GameManager gameManager) {
        super();
        
        this.gameManager = gameManager;
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.getChildren().add(canvas);

        startGameLoop();

        MouseController mouseController = MouseController.createMouseController(canvas, gameManager.getTeams()[0]);

        canvas.requestFocus();
    }

    /**
     * Fabrique statique pour créer la zone de jeu
     * @param size la taille de la zone de jeu
     * @param gameManager le GameManager
     */
    public static GridView createGridView(double width, double height, GameManager gameManager){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The size of the game zone can't be negative or null");
        }
        if(gameManager == null){
            throw new IllegalArgumentException("The GameManager can't be null");
        }
        return new GridView(width, height, gameManager);
    }

    /**
     * Méthode permettant le fonctionnement de la boucle du jeu
     */
    private void startGameLoop() {

        AnimationTimer gameLoop = new AnimationTimer() {
            // Pour faire tourner le jeu à max 60 FPS
            int fps = 60;
            double intervalMaj = 1000000000.0 / fps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long actualTime;

            // Pour afficher les FPS
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

                // Affichage des FPS toutes les secs
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
     * Mets à jour le modèle
     */
    private void update() {
        gameManager.update();
    }

    /**
     * Mets à jour la vue
     * @param gc le graphics context du canva
     */
    private void render(GraphicsContext gc) {
        try{
            gc.drawImage(new Image(Files.newInputStream(Paths.get("src/main/resources/com/app/image/john_pork.jpg"))), 0, 0, width, width);
        }catch(IOException e){

        }
        
        for (Team team : gameManager.getTeams()) {
            //! Utiliser le multi-threading
            ParticleView.renderParticles(gc, team, width, height);
        }

        //!Affichage des fps provisoire
        gc.setFill(Color.YELLOW);
        gc.setFont(new Font("comic sans ms", 15));
        gc.fillText("FPS: " + currentFps, 10, 20);
    }
}
