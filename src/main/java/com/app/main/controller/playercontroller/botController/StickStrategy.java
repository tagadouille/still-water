package com.app.main.controller.playercontroller.botController;

import com.app.main.model.core.Team;
import com.app.main.util.BotStrategy;

import javafx.geometry.Point2D;

/**
 * This class extends @see BotStrategy and describe a 
 * strategy where the bot will try to put is cursor
 * to the current position of one of his enemy
 * 
 * @author Dai Elias
 */
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
