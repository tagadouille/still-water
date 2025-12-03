package com.app.main.controller;

import java.util.Random;

import com.app.main.util.BotStrategy;

import javafx.geometry.Point2D;

public class RandomStrategy implements BotStrategy{

    @Override
    public void applyStrategy(BotController botController) {

        Random r = new Random();

        double px = r.nextDouble(botController.getMapWidth());
        double py = r.nextDouble(botController.getMapHeight());

        botController.setTargetPos(new Point2D(px, py));
    }
    
}
