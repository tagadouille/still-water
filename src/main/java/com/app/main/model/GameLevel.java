package com.app.main.model;

import java.util.List;

import com.app.main.model.core.Point;
import com.app.main.model.core.Color;

/**
 * Représente un niveau de jeu.
 * 
 * Contient la grille d'obstacles, les dimensions du niveau, la configuration
 * des équipes et le nom de fichier optionnel de l'image de fond.
 * 
 * 
 * @author Mohamed Ibrir
 */
public final class GameLevel {

    /**
     * Matrice d'obstacles. Première dimension = `width`, seconde dimension = `height`.
     * Une case vaut {@code true} si elle contient un obstacle, {@code false} sinon.
     */
    public final boolean[][] obstacles;

    /** Largeur du niveau (nombre d'éléments dans la première dimension de {@link #obstacles}). */
    public final int width;
    /** Hauteur du niveau (nombre d'éléments dans la seconde dimension de {@link #obstacles}). */
    public final int height;

    /** Configuration des équipes présentes dans le niveau. */
    public final List<TeamConfig> teamsInfo;

    /** Nom de fichier de l'image de fond du niveau ou {@code null} s'il n'y en a pas. */
    public final String backgroundImageFilename;

    /**
     * Construit un niveau sans image de fond.
     *
     * @param obstacles matrice d'obstacles (doit être rectangulaire)
     * @param teamsInfo liste des configurations d'équipe pour ce niveau
     */
    public GameLevel(boolean[][] obstacles, List<TeamConfig> teamsInfo) {
        this.obstacles = obstacles;
        this.width = obstacles.length;
        this.height = obstacles[0].length;
        this.teamsInfo = teamsInfo;
        this.backgroundImageFilename = null;
    }

    /**
     * Construit un niveau avec une image de fond.
     *
     * @param obstacles matrice d'obstacles (doit être rectangulaire)
     * @param teamsInfo liste des configurations d'équipe pour ce niveau
     * @param backgroundImageFilename nom de fichier de l'image de fond (peut être {@code null})
     */
    public GameLevel(boolean[][] obstacles, List<TeamConfig> teamsInfo, String backgroundImageFilename) {
        this.obstacles = obstacles;
        this.width = obstacles.length;
        this.height = obstacles[0].length;
        this.teamsInfo = teamsInfo;
        this.backgroundImageFilename = backgroundImageFilename;
    }

    /** Retourne la hauteur du niveau. */
    public int getHeight() {
        return height;
    }

    /** Retourne la matrice d'obstacles. */
    public boolean[][] getObstacles() {
        return obstacles;
    }

    /** Retourne la liste des configurations d'équipe. */
    public List<TeamConfig> getTeamsInfo() {
        return teamsInfo;
    }

    /** Retourne la largeur du niveau. */
    public int getWidth() {
        return width;
    }

    /**
     * Configuration d'une équipe dans le niveau.
     * 
     * Contient la couleur de l'équipe, les positions de spawn et un indicateur
     * si l'équipe est contrôlée par un joueur.
     * 
     */
    public final static class TeamConfig {
        /** Couleur associée à l'équipe. */
        public final Color color;
        /** Tableau de points décrivant la zone/positions de spawn de l'équipe. */
        public final Point[] spawnArea;
        /** {@code true} si cette équipe est contrôlée par un joueur humain. */
        public final boolean isPlayer;

        /**
         * Crée une configuration d'équipe.
         *
         * @param color couleur de l'équipe
         * @param spawnArea positions de spawn (tableau de {@link Point})
         * @param isPlayer indicateur si l'équipe est jouée par un humain
         */
        public TeamConfig(Color color, Point[] spawnArea, boolean isPlayer) {
            this.color = color;
            this.spawnArea = spawnArea;
            this.isPlayer = isPlayer;
        }
    }
}
