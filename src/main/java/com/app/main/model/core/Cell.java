package com.app.main.model.core;

/**
 * Représente une particule individuelle (un pixel) sur la carte.
 * Une cellule possède de l'énergie (PV) et appartient à une équipe.
 */
public class Cell{
    
    /** Position X, Y actuelle sur la grille. */
    private int x, y;

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
    private Cell(int x, int y, Color team){
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public static Cell CreateCell(int x, int y, Color team){
        if(team == null){
            throw new IllegalArgumentException("The team color can't be null");
        }
        return new Cell(x, y, team);
    }
    /**
     * Vérifie si la cellule est stable dans son équipe.
     * @return {@code true} si la cellule n'est pas en cours de conversion (nextteam est null).
     */
    public boolean isAlive(){
        return nextTeam == null;
    }
}