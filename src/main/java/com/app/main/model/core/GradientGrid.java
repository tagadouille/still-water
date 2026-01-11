package com.app.main.model.core;

/**
 * Gère la carte des distances (Heatmap / Flow Field) pour une équipe donnée.
 * Cette classe implémente l'algorithme de pathfinding décrit dans le sujet Liquid War :
 * un calcul de plus court chemin par gradient (BFS) depuis une cible unique vers toute la carte.
 * 
 * @author Mohamed Ibrir
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

    private final int[] queueX;
    private final int[] queueY;

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

        this.queueX = new int[width * height];
        this.queueY = new int[width * height];
        
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
     * 
     * Calcule le plus court chemin depuis la cible (targetX, targetY) vers TOUTES
     * les autres cases accessibles de la carte.
     * Initialise la cible à 0.
     * Propage la valeur i+1 aux voisins des cases de valeur i.
     * Version Améliorée : Gère les murs ET utilise des tableaux primitifs pour la performance.
     *
     * @param targetX Coordonnée X de la cible (objectif).
     * @param targetY Coordonnée Y de la cible (objectif).
     */
    public void calculgradient(int targetx, int targety){

        reset();

        if(!isValid(targetx, targety)){
            return;
        }

        if (obstacle[targetx][targety]) {
            int[] validTarget = findNearestNonObstacle(targetx, targety);
            
            if (validTarget != null) {
                targetx = validTarget[0];
                targety = validTarget[1];
            } else {
                return;
            }
        }

        int head = 0;
        int tail = 0;

        distances[targetx][targety] = 0;

        queueX[tail] = targetx;
        queueY[tail] = targety;
        tail++;

        while(head < tail){

            int cx = queueX[head];
            int cy = queueY[head];
            head++;

            int currentDist = distances[cx][cy];

            for(Direction dir : Direction.ALL){
                int nx = cx + dir.x;
                int ny = cy + dir.y;

                if(isValid(nx, ny) && !obstacle[nx][ny]){

                    if(distances[nx][ny] == INFINITE_DISTANCE){
                        
                        distances[nx][ny] = currentDist + 1;

                        queueX[tail] = nx;
                        queueY[tail] = ny;
                        tail++;
                    }
                }
            }
        }
    }
    
    /**
     * Trouve la case vide la plus proche (BFS local).
     * Optimisé pour ne pas utiliser d'objets Point.
     * @return int[]{x, y} ou null
     */
    private int[] findNearestNonObstacle(int startX, int startY) {
        
        int[] qX = new int[width * height];
        int[] qY = new int[width * height];
        boolean[][] visited = new boolean[width][height];
        
        int head = 0;
        int tail = 0;
        
        qX[tail] = startX;
        qY[tail] = startY;
        tail++;
        visited[startX][startY] = true;

        while (head < tail) {
            int cx = qX[head];
            int cy = qY[head];
            head++;

            // Trouvé !
            if (!obstacle[cx][cy]) {
                return new int[]{cx, cy};
            }

            for (Direction dir : Direction.ALL) {
                int nx = cx + dir.x;
                int ny = cy + dir.y;

                if (isValid(nx, ny) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    qX[tail] = nx;
                    qY[tail] = ny;
                    tail++;
                }
            }
        }
        return null; 
    }

}
