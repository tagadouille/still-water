package com.app.main.controller;

import java.util.Comparator;
import java.util.Random;

import com.app.main.model.core.Team;
import com.app.main.util.BotStrategy;
import com.app.main.util.Controller;

import javafx.geometry.Point2D;

public final class BotController implements Controller{

    private Team team;
    private Team enemy;

    private Team[] allTeam = new Team[0];

    private BotStrategy strategy;

    private final int mapWidth;
    private final int mapHeight;

    private Point2D targetPos;

    private int speed = 2;

    /**
     * Duration of the strategy in milliseconds
     */
    private long strategyDuration = 5000;
    private long strategyStart = 0;

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
        this.targetPos = new Point2D(mapWidth / 2, mapHeight / 2);

        strategyStart = System.currentTimeMillis();
    }

    public Point2D getTargetPos() {
        return targetPos;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public Team getEnemy() {
        return enemy;
    }

    public Team getTeam() {
        return team;
    }

    public Team[] getAllTeam() {
        return allTeam;
    }

    public void setTargetPos(Point2D targetPos) {
        if(targetPos == null){
            throw new IllegalArgumentException("The target point can't be null");
        }
        this.targetPos = targetPos;
    }

    public void setAllTeam(Team[] allTeam) {
        if(allTeam == null){
            throw new IllegalArgumentException("The team array can't be null");
        }
        this.allTeam = allTeam;
    }


    @Override
    public void setTeam(Team team) {

        if(team == null){
            throw new IllegalArgumentException("The team can't be null");
        }
        this.team = team;
    }

    public void setEnemy(Team enemy) {

        if(enemy == null){
            throw new IllegalArgumentException("The enemy team can't be null");
        }
        this.enemy = enemy;
    }

    @Override
    //TODO ajouter des niveaux de difficultés 
    public void update() {
        if (team == null) return;

        if(strategyStart + strategyDuration <= System.currentTimeMillis()){
            enemy = null;
            strategy = null;

            strategyStart += strategyDuration;
        }
        
        // Choose randomly a new strategy
        if(strategy == null && enemy == null){

            Random rand = new Random();
            int n = rand.nextInt(3);

            if(n == 0){
                strategy = new OppositeStrategy();
            }else if(n == 1){
                strategy = new RandomStrategy();
            }else{
                strategy = new StickStrategy();
            }

            //Choose if the bot target : the weakest or the stronger
            if(rand.nextInt() == 2){
                findEnnemyByPower((x, y) -> {
                    if (x > y) return true;
                return false;
                });
            }else{
                findEnnemyByPower((x, y) -> {
                    if (x <= y) return true;
                    return false;
                });
            }
            
            strategy.applyStrategy(this);
        }

        for (int i = 0; i < speed; i++) {
            moveCursor();
        }
    }

    private void moveCursor(){

        if(targetPos.getX() < team.getTargetX()){
            team.setTarget(team.getTargetX() - speed, team.getTargetY());
        }
        else if(targetPos.getX() > team.getTargetX()){
            team.setTarget(team.getTargetX() + speed, team.getTargetY());
        }

        if(targetPos.getY() < team.getTargetY()){
            team.setTarget(team.getTargetX(), team.getTargetY() - speed);
        }
        else if(targetPos.getY() > team.getTargetY()){
            team.setTarget(team.getTargetX(), team.getTargetY() + speed);
        }
    }

    private void findEnnemyByPower(Comparer comp){
        int greaterMass = 0;
        Team bigEnemy = null;

        for (int i = 0; i < allTeam.length; i++) {
            
            if(allTeam[i] != team && comp.compare(allTeam[i].getArmy().size(), greaterMass)){
                greaterMass = allTeam[i].getArmy().size();
                bigEnemy = allTeam[i];
            }
        }
        this.enemy = bigEnemy;
    }

    private interface Comparer{

        /**
         * Tell if the comparaison between x and y is true
         * For instance we can use it for tell if x is greater than y
         * @param x
         * @param y
         * @return true if the comparaison is true, false otherwise
         */
        public boolean compare(int x, int y);
    }
}