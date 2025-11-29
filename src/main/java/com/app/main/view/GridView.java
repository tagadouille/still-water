package com.app.main.view;

import com.app.main.model.GameManager;
import com.app.main.model.Team;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Classe étendant StackPane qui représente la vue de la
 * zone de jeu où sont affichés les particules
 * @author Dai Elias
 */
public final class GridView extends StackPane {

    private Canvas canvas;
    private int size;
    private GameManager gameManager;

    /**
     * Constructeur pour créer la zone de jeu
     * @param size la taille de la zone de jeu
     */
    public GridView(int size) {
        super();
        if(size <= 0){
            throw new IllegalArgumentException("The size of the game zone can't be nagative of null");
        }
        this.size = size;
        this.canvas = new Canvas(size, size);
        this.getChildren().add(canvas);

        startGameLoop();

        canvas.setOnMouseMoved(e -> {
            
        });
        canvas.requestFocus();
    }

    /**
     * Méthode permettant le fonctionnement de la boucle du jeu
     */
    private void startGameLoop() {

        AnimationTimer gameLoop = new AnimationTimer() {
            int fps = 60;
            double intervalMaj = 1000000000.0 / fps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long actualTime;

            @Override
            public void handle(long now) {
                actualTime = System.nanoTime();
                delta += (actualTime - lastTime) / intervalMaj;
                lastTime = actualTime;

                if(delta >= 1){

                    update();
                    render(canvas.getGraphicsContext2D());

                    delta--;
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
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Team team : gameManager.getTeams()) {
            //! Utiliser le multi-threading
            ParticleView.renderParticles(gc, team, size);
        }

    }
}
