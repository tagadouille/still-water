package com.app.main.controller;

import com.app.main.model.core.Team;
import com.app.main.util.BotStrategy;

import javafx.geometry.Point2D;

public class StickStrategy implements BotStrategy{

    @Override
    public void applyStrategy(BotController botController) {
        
        Team enemy = botController.getEnemy();

        if(enemy == null){
            return;
        }
        botController.setTargetPos(new Point2D(enemy.getTargetX(), enemy.getTargetY()));
    }
    
}
