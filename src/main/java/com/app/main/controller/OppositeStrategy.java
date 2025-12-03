package com.app.main.controller;

import com.app.main.model.core.Team;
import com.app.main.util.BotStrategy;

import javafx.geometry.Point2D;

/**
 * This class implements @see BotStrategy and describe a strategy
 * where the bot put his cursor at the opposite player cursor
 */
public final class OppositeStrategy implements BotStrategy{

    @Override
    public void applyStrategy(BotController botController) {

        initialisation(botController);

        if(botController.getEnemy() == null){
            return;
        }

        Team team = botController.getTeam();

        Point2D currentPos = new Point2D(team.getTargetX(), team.getTargetY());
        Point2D enemyPos = new Point2D(botController.getEnemy().getTargetX(), botController.getEnemy().getTargetY());

        double targetX = (2*enemyPos.getX()) - currentPos.getX();
        double targetY = (2*enemyPos.getY()) - currentPos.getY();

        targetX = Math.min(targetX, botController.getMapWidth() - 6);
        targetY = Math.min(targetY, botController.getMapHeight() - 6);

        targetX = Math.max(targetX, 5);
        targetY = Math.max(targetY, 5);

        botController.setTargetPos(new Point2D(targetX, targetY));
    }

    private void initialisation(BotController botController){
        int greaterMass = 0;
        Team bigEnemy = null;

        Team[] allTeam = botController.getAllTeam();
        Team team = botController.getTeam();

        for (int i = 0; i < allTeam.length; i++) {
            
            if(allTeam[i] != team && allTeam[i].getArmy().size() > greaterMass){
                greaterMass = allTeam[i].getArmy().size();
                bigEnemy = allTeam[i];
            }
        }
        botController.setEnemy(bigEnemy);
    }
    
}
