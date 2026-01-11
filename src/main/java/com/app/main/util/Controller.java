package com.app.main.util;

import com.app.main.model.core.Team;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

/**
 * Interface générique pour un contrôleur d'équipe.
 * <p>
 * Un contrôleur gère l'entrée (souris, clavier, IA) et met à jour la cible
 * d'une {@link Team}. Les implémentations peuvent attacher des handlers
 * d'entrée via {@link #setupInput(Scene, Canvas)} et être mises à jour
 * régulièrement via {@link #update()}.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public interface Controller {
    
    /**
     * Attache ce contrôleur à une équipe.
     *
     * @param team L'équipe à contrôler.
     */
    void setTeam(Team team);

    /**
     * Méthode appelée à chaque tour de boucle (si besoin de mise à jour continue).
     * <p>
     * Pour la souris l'entrée est événementielle, tandis que pour le clavier
     * ou une IA la mise à jour peut être continue.
     * </p>
     */
    void update();

    /**
     * Méthode optionnelle pour attacher les handlers d'entrée à la scène
     * et au canvas (souris, clavier, etc.).
     *
     * @param scene la scène JavaFX
     * @param canvas le canvas utilisé pour le rendu
     */
    default void setupInput(Scene scene, Canvas canvas) {}
}
