package com.app.main.model.core;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Gère la carte des distances (Heatmap / Flow Field) pour une équipe donnée.
 * <p>
 * Cette classe implémente l'algorithme de pathfinding décrit dans le sujet Liquid War :
 * un calcul de plus court chemin par gradient (BFS) depuis une cible unique vers toute la carte.
 * </p>
 * <p>
 * [cite_start]Voir sujet : [cite: 22, 23, 28, 29]
 * </p>
 */
public final class GradientGrid {

    /** Largeur et Hauteur de la grille. */
    public int height, width;

    /** * Matrice des distances. 
     * distances[x][y] contient le nombre de pas nécessaires pour atteindre la cible.
     */
    private final int[][] distances;

    /** * Matrice des obstacles.
     * obstacle[x][y] est true si la case est un mur infranchissable.
     */
    private final boolean[][] obstacle;

    /** Valeur représentant une case non visitée ou inatteignable. */
    public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;

    /** Valeur pour le debug (non utilisé dans le calcul pur mais utile pour l'affichage). */
    public static final int WALL = -1;

    /** * Les 4 directions de déplacement possibles (Haut, Bas, Gauche, Droite).
     * Pas de diagonales pour l'instant (Connexité 4).
     */
    private static final int[][] DIRECTIONS = {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}
    };

    /**
     * Initialise une nouvelle grille de gradient.
     *
     * @param width Largeur de la carte.
     * @param height Hauteur de la carte.
     */
    private GradientGrid(int width, int height, boolean[][] sharedObstacles) {
        this.width = width;
        this.height = height;
        this.distances = new int[height][width];
        this.obstacle = sharedObstacles;
        
        reset();
    }

    /**
     * La Fabrique Statique
     * 
     * @param width
     * @param height
     * @return
     */
    public static GradientGrid createGradientGrid(int width, int height, boolean [][] sharedObstacles){
        return new GradientGrid(width, height, sharedObstacles);
    }

    /**
     * Définit ou supprime un obstacle à une position donnée.
     * Les obstacles bloquent la propagation du gradient.
     *
     * @param x Coordonnée X.
     * @param y Coordonnée Y.
     * @param isObstacle true pour placer un mur, false pour libérer.
     */
    public void setObstacle(int x, int y, boolean isObstacle) {
        if (isValid(x, y)) {
            obstacle[x][y] = isObstacle;
        }
    }


    /**
     * Vérifie si les coordonnées sont à l'intérieur des limites de la carte.
     * @param x Coordonnée X.
     * @param y Coordonnée Y.
     * @return true si valide, false si hors map.
     */
    public boolean isValid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Réinitialise toutes les distances à {@link #INFINITE_DISTANCE}.
     * À appeler avant chaque recalcul du gradient.
     */
    public void reset(){
        for(int i = 0; i < width ; i++){
            for(int j = 0; j < height; j++){
                distances[i][j] = INFINITE_DISTANCE;
            }
        }
    }

    /**
     * Récupère la distance brute d'une case par rapport à la cible.
     * @param x Coordonnée X.
     * @param y Coordonnée Y.
     * @return La distance en nombre de cases, ou {@link #INFINITE_DISTANCE}.
     */
    public int getDistance(int x, int y) {
        if (isValid(x, y)) {
            return distances[x][y];
        }
        return INFINITE_DISTANCE;
    }

    /**
     * Algorithme principal : Parcours en Largeur (BFS).
     * <p>
     * Calcule le plus court chemin depuis la cible (targetX, targetY) vers TOUTES
     * les autres cases accessibles de la carte.
     * </p>
     * <ol>
     * <li>Initialise la cible à 0.</li>
     * <li>Propage la valeur i+1 aux voisins des cases de valeur i.</li>
     * </ol>
     *
     * @param targetX Coordonnée X de la cible (objectif).
     * @param targetY Coordonnée Y de la cible (objectif).
     */
    public void calculgradient(int targetx, int targety){

        reset();

        if(!isValid(targetx, targety) || obstacle[targetx][targety]){
            return;
        }

        Queue<Point> file = new ArrayDeque<>();

        distances[targetx][targety] = 0;
        file.add(new Point(targetx, targety));

        while(!file.isEmpty()){
            Point p = file.poll();
            int currentDist = distances[p.x()][p.y()];

            for(int [] dir : DIRECTIONS){
                int nx = p.x() + dir[0];
                int ny = p.y() + dir[1];

                if(isValid(nx, ny) && !obstacle[nx][ny]){

                    if(distances[nx][ny] == INFINITE_DISTANCE){

                        distances[nx][ny] = currentDist + 1;

                        file.add(new Point(nx, ny));
                    }
                }
            }
        }
    }
}
