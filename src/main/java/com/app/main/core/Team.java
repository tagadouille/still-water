package com.app.main.core;

import java.util.ArrayList;
import java.util.List;

import com.app.main.util.GradientGrid;

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
        //TODO CAS 2 : La case est occupée 
        else {
            if (occupant.teamId == this.teamId) {
                //TODO C'est un AMI : On partage de l'énergie
            } else {
                //TODO C'est un ENNEMI : On attaque
            }
        }
    }

}