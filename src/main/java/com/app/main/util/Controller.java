package com.app.main.util;

import com.app.main.model.core.Team;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public interface Controller {
    
    /**
     * Attache ce contrôleur à une équipe.
     * @param team L'équipe à contrôler.
     */
    void setTeam(Team team);

    /**
     * Méthode appelée à chaque tour de boucle (si besoin de mise à jour continue).
     * Pour la souris c'est événementiel, pour le clavier/IA c'est continu.
     */
    void update();

    default void setupInput(Scene scene, Canvas canvas) {}
}
