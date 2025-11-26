package com.app.main.model;

import java.util.ArrayList;
import java.util.List;

public final class Team {

    private final List<Cell> army;
    public final GradientGrid gradient;
    public final Color team;
    private int targetX, targetY;

    public Team(Color team, GradientGrid gradientGrid) {
        this.team = team;
        this.army = new ArrayList<>();
        this.gradient = gradientGrid;
    }

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
    
    public List<Cell> getArmy() {
        return army;
    }

    public void addCell(Cell c) {
        this.army.add(c);
    }

    public static class Cell{
        public int x, y;
        public int energy;
        public Color currentTeam;
        public Color nextTeam = null;
        
        public Cell(int x, int y, Color team){
            this.x = x;
            this.y = y;
            this.currentTeam = team;
            this.energy = 100;
        }

        public boolean isAlive(){
            return nextTeam == null;
        }
    }

    public void updateArmy(Cell[][] globalGrid) {
        gradient.calculgradient(targetX, targetY);

        for (Cell myCell : army) {
            if (myCell.energy > 0 && myCell.isAlive()) {
                moveOneCell(myCell, globalGrid);
            }
        }
    }

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
            if (occupant.currentTeam == this.team) {
                heal(myCell, occupant);
            } else {
                attack(myCell, occupant);
            }
        }
    }

    public void attack(Cell attacker, Cell target){

        if(!target.isAlive()) return;

        target.energy -= 5;
        attacker.energy += 5;

        if(target.energy <= 0){
            target.energy = 0;
            target.nextTeam = this.team;
        }
    }

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