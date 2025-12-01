package com.app.main.model.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une équipe (un joueur) dans le jeu Liquid War.
 * <p>
 * Chaque équipe possède :
 * <ul>
 * <li>Une couleur unique ({@link Color}).</li>
 * <li>Une liste de particules ({@link Cell}) constituant son armée.</li>
 * <li>Une carte de gradient ({@link GradientGrid}) propre à elle, calculant les chemins vers sa cible.</li>
 * </ul>
 * </p>
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
        return new Team(Color.BLUE, width, height, sharedObstacles);
    }

    /**
     * Représente une particule individuelle (un pixel) sur la carte.
     * Une cellule possède de l'énergie (PV) et appartient à une équipe.
     */
    public static class Cell{

        /** Position X, Y actuelle sur la grille. */
        private Point position;

        /** * Niveau d'énergie de la cellule (0 à 100). 
         * Détermine sa résistance et la brillance de sa couleur.
         */
        private int energy;

        /** L'équipe à laquelle la cellule appartient actuellement. */
        private Color currentTeam;

        /** * Indique la prochaine équipe de la cellule si elle a été convertie.
         * Si {@code null}, la cellule reste dans son équipe actuelle.
         * Si non null, elle est considérée comme "morte" pour ce tour et attend le transfert.
         */
        private Color nextTeam = null;
        
        /**
         * Crée une nouvelle cellule.
         * @param x Position X initiale.
         * @param y Position Y initiale.
         * @param team L'équipe d'appartenance.
         */
        private Cell(Point position, Color team){
            this.position = new Point(position.x(), position.y());
            this.currentTeam = team;
            this.energy = 100;
        }

        public int getEnergy() {
            return energy;
        }
        public void setEnergy(int energy) {
            if(energy < 0 || energy > 100){
                throw new IllegalArgumentException("The energy must be between 0 and 100");
            }
            this.energy = energy;
        }
        public Point getPosition() {
            return position;
        }
        public Color getNextTeam() {
            return nextTeam;
        }
        public Color getCurrentTeam() {
            return currentTeam;
        }
        public void setCurrentTeam(Color currentTeam) {
            this.currentTeam = currentTeam;
        }
        public void setNextTeam(Color nextTeam) {
            this.nextTeam = nextTeam;
        }

        //TODO vérif si la position est valide
        public static Cell CreateCell(Point position, Color team){
            if(position == null){
                throw new IllegalArgumentException("The position can't be null");
            }
            return new Cell(position, team);
        }



        /**
         * Vérifie si la cellule est stable dans son équipe.
         * @return {@code true} si la cellule n'est pas en cours de conversion (nextteam est null).
         */
        public boolean isAlive(){
            return nextTeam == null;
        }
    }
    public void setCellPosition(Cell cell, Point newPosition) {
        if(this.gradient.isValid(newPosition.x(), newPosition.y())){
            cell.position = newPosition;
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
     * <ol>
     * <li>Recalcule le gradient (carte des distances) vers la cible actuelle.</li>
     * <li>Déplace chaque cellule valide vers son meilleur voisin.</li>
     * </ol>
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
    private void moveOneCell(Cell myCell, Cell[][] globalGrid) {

        Point nextPos = gradient.BestNeighbors(myCell.position.x(), myCell.position.y());

        if (nextPos == null) return;

        Cell occupant = globalGrid[nextPos.y()][nextPos.x()];

        if (occupant == null) {
            globalGrid[myCell.position.y()][myCell.position.x()] = null;
            this.setCellPosition(myCell, nextPos);
            globalGrid[nextPos.y()][nextPos.x()] = myCell;
        }
        else {
            if (occupant.currentTeam == this.team) {
                heal(myCell, occupant);
            } else {
                attack(myCell, occupant);
            }
        }
    }

    /**
     * Logique d'attaque (Combat).
     * Transfère de l'énergie de la victime vers l'attaquant.
     * Si la victime tombe à 0 énergie, elle est marquée pour conversion ({@code nextteam}).
     *
     * @param attacker La cellule qui attaque.
     * @param target La cellule ennemie ciblée.
     */
    public void attack(Cell attacker, Cell target){

        if(!target.isAlive()) return;

        target.energy -= 5;
        attacker.energy += 5;

        if(target.energy <= 0){
            target.energy = 0;
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