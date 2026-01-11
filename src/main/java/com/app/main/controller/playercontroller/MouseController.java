package com.app.main.controller.playercontroller;

import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.view.GameScene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 * Contrôleur basé sur la souris pour une équipe.
 * <p>
 * Écoute les mouvements de souris sur le <code>Canvas</code> et met à jour la
 * position cible de la {@link Team} associée. Fournit une fabrique statique
 * pour la création et vérifie les paramètres.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public final class MouseController implements Controller{

    /** Équipe contrôlée. */
    public Team team;
    /** Position courante de la souris (coordonnées canvas). */
    public double mousex, mousey;

    private MouseController(Team team){
        this.team = team;
    }

    /**
     * Crée un contrôleur souris lié à l'équipe donnée.
     *
     * @param team l'équipe à contrôler (ne doit pas être {@code null})
     * @return une instance de {@code MouseController}
     * @throws IllegalArgumentException si {@code team} est {@code null}
     */
    public static MouseController createMouseController(Team team){
        if(team == null){
            throw new IllegalArgumentException("The team can't be null");
        }
        return new MouseController(team);
    }
    
    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    /** Méthode de mise à jour périodique — aucun comportement ici. */
    @Override
    public void update() {}

    /**
     * Configure l'écoute des mouvements de souris sur le canvas.
     * <p>
     * Calcule la cible en coordonnées de grille en divisant par les facteurs
     * fournit par {@link GameScene} puis appelle {@code team.setTarget(x,y)}.
     * </p>
     */
    @Override
    public void setupInput(Scene scene, Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            this.mousex = e.getX();
            this.mousey = e.getY();

            if (team != null) {
                int targetX = (int) (this.mousex / GameScene.getxFactor());
                int targetY = (int) (this.mousey / GameScene.getyFactor());

                team.setTarget(targetX, targetY);
            }
        });
    }
}
