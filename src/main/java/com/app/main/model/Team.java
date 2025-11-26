package com.app.main.model;

import java.util.ArrayList;
import java.util.List;

public final class Team {

    private final List<Cell> army;

    public final GradientGrid gradient;

    public final Color color;
    public final int teamId;

    private int targetX, targetY;

    public Team(int id, Color c, int mapWidth, int mapHeight) {
        this.teamId = id;
        this.color = c;
        this.army = new ArrayList<>();
        this.gradient = new GradientGrid(mapWidth, mapHeight);
    }
    
    public static class Cell{
        public int x, y;

        public int energy;
        public int teamId;
        public final Color team;
        
        public Cell(int x, int y, int teamId, Color team){
            this.x = x;
            this.y = y;
            this.team = team;
            this.teamId = teamId;
            this.energy = 100;
        }
    }

    public void setTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    public void addCell(Cell c) {
        this.army.add(c);
    }
    
    public List<Cell> getArmy() {
        return army;
    }

    public void updateArmy(Cell[][] globalGrid) {
        gradient.calculgradient(targetX, targetY);

        for (Cell myCell : army) {
            if (myCell.energy > 0) {
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
            if (occupant.teamId == this.teamId) {
                heal(myCell, occupant);
            } else {
                attack(occupant);
            }
        }
    }

    public void attack(Cell target){
        target.energy -= 10;

        if(target.energy < 0) target.energy = 0;
    }

    public void heal(Cell healer, Cell receiver){

        int minenergy = 20;

        int maxenergy= 80;

        if(healer.energy > minenergy && receiver.energy < maxenergy){
            healer.energy --;
            receiver.energy ++;
        }
    }

}