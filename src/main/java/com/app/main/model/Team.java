package com.app.main.model;

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
    private final List<Cell> army;

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
    public Team(Color team, int mapWidth, int mapHeight) {
        this.team = team;
        this.army = new ArrayList<>();
        this.gradient = new GradientGrid(mapWidth, mapHeight);
    }

    /**
     * Représente une particule individuelle (un pixel) sur la carte.
     * Une cellule possède de l'énergie (PV) et appartient à une équipe.
     */
    public static class Cell{

        /** Position X, Y actuelle sur la grille. */
        public int x, y;

        /** * Niveau d'énergie de la cellule (0 à 100). 
         * Détermine sa résistance et la brillance de sa couleur.
         */
        public int energy;

        /** L'équipe à laquelle la cellule appartient actuellement. */
        public Color currentteam;

        /** * Indique la prochaine équipe de la cellule si elle a été convertie.
         * Si {@code null}, la cellule reste dans son équipe actuelle.
         * Si non null, elle est considérée comme "morte" pour ce tour et attend le transfert.
         */
        public Color nextteam = null;
        
        /**
         * Crée une nouvelle cellule.
         * @param x Position X initiale.
         * @param y Position Y initiale.
         * @param team L'équipe d'appartenance.
         */
        public Cell(int x, int y, Color team){
            this.x = x;
            this.y = y;
            this.currentteam = team;
            this.energy = 100;
        }

        /**
         * Vérifie si la cellule est stable dans son équipe.
         * @return {@code true} si la cellule n'est pas en cours de conversion (nextteam est null).
         */
        public boolean isAlive(){
            return nextteam == null;
        }
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

    /**
     * Ajoute une nouvelle cellule à l'armée.
     * @param c La cellule à ajouter.
     */
    public void addCell(Cell c) {
        this.army.add(c);
    }
    
    /**
     * @return La liste modifiable des cellules de l'armée.
     */
    public List<Cell> getArmy() {
        return army;
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

        Point nextPos = gradient.BestNeighbors(myCell.x, myCell.y);

        if (nextPos == null) return;

        int nx = nextPos.x();
        int ny = nextPos.y();

        Cell occupant = globalGrid[nx][ny];

        if (occupant == null) {
            globalGrid[myCell.x][myCell.y] = null;
            myCell.x = nx;
            myCell.y = ny;
            globalGrid[nx][ny] = myCell;
        }
        else {
            if (occupant.currentteam == this.team) {
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
            target.nextteam = this.team;
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

        int minenergy = 20;

        int maxenergy= 80;

        if(healer.energy > minenergy && receiver.energy < maxenergy){
            healer.energy --;
            receiver.energy ++;
        }
    }

}