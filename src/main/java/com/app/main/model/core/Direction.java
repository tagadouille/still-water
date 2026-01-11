package com.app.main.model.core;

import java.util.List;

/**
 * Directions possibles de déplacement sur la grille.
 * <p>
 * Chaque valeur énumérée fournit un décalage en X et Y permettant de calculer
 * une nouvelle position à partir d'un {@link Point} existant.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public enum Direction {
    /** Déplacement vers le haut (delta X = -1, delta Y = 0). */
    HAUT(-1, 0),
    /** Déplacement vers le bas (delta X = 1, delta Y = 0). */
    BAS(1, 0),
    /** Déplacement vers la gauche (delta X = 0, delta Y = -1). */
    GAUCHE(0, -1),
    /** Déplacement vers la droite (delta X = 0, delta Y = 1). */
    DROITE(0, 1),
    /** Déplacement en bas-gauche (delta X = 1, delta Y = -1). */
    BASGAUCHE(1, -1),
    /** Déplacement en bas-droite (delta X = 1, delta Y = 1). */
    BASDROITE(1, 1),
    /** Déplacement en haut-gauche (delta X = -1, delta Y = -1). */
    HAUTGAUCHE(-1, -1),
    /** Déplacement en haut-droite (delta X = -1, delta Y = 1). */
    HAUTDROITE(-1, 1);

    /** Déplacement en X appliqué par cette direction. */
    public final int x, y;
    
    private Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    /** Liste immuable de toutes les directions (utile pour itération). */
    public static final List<Direction> ALL = List.of(values());

    /**
     * Applique la direction au point fourni et retourne le nouveau point.
     *
     * @param p point source
     * @return nouveau point résultant de l'application du décalage
     */
    public Point applyTo(Point p) {
        return new Point(p.x() + this.x, p.y() + this.y);
    }
}
