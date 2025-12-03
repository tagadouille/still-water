package com.app.main.controller;

import com.app.main.model.core.Team;
import com.app.main.util.Controller;

public final class BotController implements Controller {

    private Team team;

    private final int mapWidth;
    private final int mapHeight;

    private double currentX;
    private double currentY;

    private double dx;
    private double dy;

    /**
     * Crée un bot avec une trajectoire aléatoire.
     * 
     * @param mapWidth Largeur de la carte.
     * @param mapHeight Hauteur de la carte.
     */
    public BotController(int mapWidth, int mapHeight, Team team) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        this.team = team;

        this.currentX = 5; 
        this.currentY =5;

        this.dx = (Math.random() * 4) - 2;
        this.dy = (Math.random() * 4) - 2;
    }

    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public void update() {
        if (team == null) return;

        
        
        team.setTarget((int) currentX, (int) currentY);
    }
}