package com.app.main.model.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Représente une équipe (un joueur) dans le jeu Liquid War.
 * 
 * Chaque équipe possède :
 * 
 * Une couleur unique ({@link Color}).
 * Une liste de particules ({@link Cell}) constituant son armée.
 * Une carte de gradient ({@link GradientGrid}) propre à elle, calculant les chemins vers sa cible.
 * 
 * 
 * 
 * @author Mohamed Ibrir
 */
public final class Team {

    /** Liste des cellules vivantes appartenant à cette équipe. */
    private List<Cell> army;

    /** * La grille de gradient utilisée pour le pathfinding de cette équipe.
     * Elle est recalculée à chaque tour en fonction de la position de la cible.
     */
    public final GradientGrid gradient;

    /** La couleur (identité) de l'équipe. */
    public final Color team;

    /** Coordonnées de la cible (curseur souris) vers laquelle l'armée se dirige. */
    private int targetX, targetY;

    /**
     * Initialise une nouvelle équipe.
     *
     * @param team      La couleur identifiant l'équipe.
     * @param mapWidth  Largeur de la carte (pour initialiser le gradient).
     * @param mapHeight Hauteur de la carte (pour initialiser le gradient).
     */
    private Team(Color team, int mapWidth, int mapHeight, boolean [][] sharedObstacles) {
        this.team = team;
        this.army = new ArrayList<>();
        this.gradient = GradientGrid.createGradientGrid(mapWidth, mapHeight, sharedObstacles);
    }

    /**
     * @return La liste modifiable des cellules de l'armée.
     */
    public List<Cell> getArmy() {
        return army;
    }

    public void setArmy(List<Cell> newArmy){
        this.army = newArmy;
    }

    /**
     * Définit la nouvelle cible vers laquelle toutes les particules de l'équipe doivent se diriger.
     * Généralement appelé lors d'un mouvement de souris.
     *
     * @param x Coordonnée X de la cible.
     * @param y Coordonnée Y de la cible.
     */
    public void setTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    public int getTargetX() {
        return targetX;
    }
    public int getTargetY() {
        return targetY;
    }

    public Color getTeam() {
        return team;
    }

    /**
     * La Fabrique Statique
     * 
     * @param c
     * @param width
     * @param height
     * @return
     */
    public static Team CreateTeam(Color team, int width, int height, boolean [][] sharedObstacles){
        if(team == null){
            throw new IllegalArgumentException("The team color can't be null");
        }
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The width and height must be positive");
        }
        if(sharedObstacles == null){
            throw new IllegalArgumentException("The sharedObstacles array can't be null");
        }
        if(sharedObstacles.length != width || sharedObstacles[0].length != height){
            throw new IllegalArgumentException("The sharedObstacles array dimensions must match the width and height");
        }
        return new Team(team, width, height, sharedObstacles);
    }

    /**
     * La Fabrique Statique Red team
     * 
     * @param width
     * @param height
     * @return
     */
    public static Team CreateRedTeam(int width, int height, boolean [][] sharedObstacles){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The width and height must be positive");
        }
        if(sharedObstacles == null){
            throw new IllegalArgumentException("The sharedObstacles array can't be null");
        }
        return new Team(Color.RED, width, height, sharedObstacles);
    }

    /**
     * La Fabrique Statique Green team
     * 
     * @param width
     * @param height
     * @return
     */
    public static Team CreateGreenTeam(int width, int height, boolean [][] sharedObstacles){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The width and height must be positive");
        }
        if(sharedObstacles == null){
            throw new IllegalArgumentException("The sharedObstacles array can't be null");
        }
        return new Team(Color.GREEN, width, height, sharedObstacles);
    }

    /**
     * La Fabrique Statique Purple team
     * 
     * @param width
     * @param height
     * @return
     */
    public static Team CreatePurpleTeam(int width, int height, boolean [][] sharedObstacles){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The width and height must be positive");
        }
        if(sharedObstacles == null){
            throw new IllegalArgumentException("The sharedObstacles array can't be null");
        }
        return new Team(Color.PURPLE, width, height, sharedObstacles);
    }


    /**
     * La Fabrique Statique Blue team
     * 
     * @param width
     * @param height
     * @return
     */
    public static Team CreateBlueTeam(int width, int height, boolean [][] sharedObstacles){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("The width and height must be positive");
        }
        if(sharedObstacles == null){
            throw new IllegalArgumentException("The sharedObstacles array can't be null");
        }
        return new Team(Color.BLUE, width, height, sharedObstacles);
    }

    public void setCellPosition(Cell cell, int newX, int newY) {
        if(this.gradient.isValid(newX, newY)){
            cell.x = newX;
            cell.y = newY;
        }else{
            throw new IllegalArgumentException("The new position is invalid");
        }
    }

    /**
     * Ajoute une nouvelle cellule à l'armée.
     * @param c La cellule à ajouter.
     */
    public void addCell(Cell c) {
        this.army.add(c);
    }

    /**
     * Met à jour l'état de toute l'armée pour une frame de jeu.
     * 
     * Recalcule le gradient (carte des distances) vers la cible actuelle.
     * Déplace chaque cellule valide vers son meilleur voisin.
     * 
     *
     * @param globalGrid La grille globale du jeu contenant toutes les particules (pour gérer les collisions).
     */
    public void updateArmy(Cell[][] globalGrid) {
        gradient.calculgradient(targetX, targetY);

        for (Cell myCell : army) {
            if (myCell.energy > 0 && myCell.isAlive()) {
                moveOneCell(myCell, globalGrid);
            }
        }
    }

    /**
     * Gère le déplacement ou l'interaction d'une cellule unique.
     *
     * @param myCell La cellule qui tente de bouger.
     * @param globalGrid La grille de référence pour vérifier l'occupation des cases.
     */
    private synchronized void moveOneCell(Cell myCell, Cell[][] globalGrid){
        int x = myCell.x;
        int y = myCell.y;
        int myDist = gradient.getDistance(x, y);
        
        List<Point> mainDirsFree = new ArrayList<>();
        List<Point> acceptDirsFree = new ArrayList<>();
        List<Cell> enemiesOnMain = new ArrayList<>();
        List<Cell> enemiesOnAcceptDirs = new ArrayList<>();
        List<Cell> friendsOnMain = new ArrayList<>();
        
        for (Direction dir : Direction.ALL) {
            int nx = x + dir.x;
            int ny = y + dir.y;

            if (!gradient.isValid(nx, ny) || gradient.getDistance(nx, ny) == GradientGrid.INFINITE_DISTANCE) continue;

            int distVoisin = gradient.getDistance(nx, ny);
            Cell occupant = globalGrid[ny][nx]; 

            // CLASSIFICATION DE LA DIRECTION
            boolean isMain = (distVoisin < myDist);
            boolean isAcceptable = (distVoisin == myDist);

            if (occupant == null) {
                // Case Libre
                if (isMain) mainDirsFree.add(new Point(nx, ny));
                else if (isAcceptable) acceptDirsFree.add(new Point(nx, ny));
            } else {
                // Case Occupée
                if (isMain) {
                    if (occupant.currentTeam != this.team) enemiesOnMain.add(occupant);
                    else friendsOnMain.add(occupant);
                }else if(isAcceptable) if(myCell.currentTeam == occupant.currentTeam)friendsOnMain.add(occupant);
                else friendsOnMain.add(occupant);
            }
        }
        
        synchronized(globalGrid){
            // 1. Si une direction principale est libre
            if (!mainDirsFree.isEmpty()) {
                Collections.shuffle(mainDirsFree);
                moveCellTo(myCell, mainDirsFree.get(0), globalGrid);
                return;
            }

            // 2. & 3. Si une direction acceptable est libre
            if (!acceptDirsFree.isEmpty()) {
                Collections.shuffle(mainDirsFree);
                moveCellTo(myCell, acceptDirsFree.get(0), globalGrid);
                return;
            }

            // 4. Si direction principale occupée par ennemi
            if (!enemiesOnMain.isEmpty()) {
                attack(myCell, enemiesOnMain.get(0));
                return;
            }

            //5. Si direction acceptable occupée par ennemi
            if(!enemiesOnAcceptDirs.isEmpty()){
                attack(myCell, enemiesOnAcceptDirs.get(0));
                return;
            }

            // 6. Si direction principale occupée par ami
            if (!friendsOnMain.isEmpty()) {
                heal(myCell, friendsOnMain.get(0));
                return;
            }
            // 7. Sinon rien
        }
    }

    // Petite méthode helper pour ne pas dupliquer le code de déplacement
    private void moveCellTo(Cell c, Point p, Cell[][] grid) {
        grid[c.y][c.x] = null;
        setCellPosition(c, p.x(), p.y()); // Ta méthode existante
        grid[p.y()][p.x()] = c;
    }

    /**
     * Logique d'attaque (Combat).
     * Transfère de l'énergie de la victime vers l'attaquant.
     * Si la victime tombe à 5 énergie, elle est marquée pour conversion ({@code nextteam}).
     *
     * @param attacker La cellule qui attaque.
     * @param target La cellule ennemie ciblée.
     */
    public void attack(Cell attacker, Cell target){

        if(!target.isAlive()) return;

        target.energy -= 5;
        attacker.energy += 5;

        if(target.energy <= 5){
            target.energy = 5;
            target.nextTeam = this.team;
        }
    }

    /**
     * Logique de soin (Support).
     * Équilibre l'énergie entre deux alliés selon des seuils définis.
     *
     * @param healer La cellule qui donne de l'énergie.
     * @param receiver La cellule qui reçoit.
     */
    public void heal(Cell healer, Cell receiver){

        if(!receiver.isAlive()) return;

        int minenergy = 20;

        int maxenergy= 80;

        if(healer.energy > minenergy && receiver.energy < maxenergy){
            healer.energy --;
            receiver.energy ++;
        }
    }

}