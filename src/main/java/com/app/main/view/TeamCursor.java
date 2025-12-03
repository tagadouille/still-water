package com.app.main.view;

import com.app.main.model.core.Team;

import javafx.scene.canvas.GraphicsContext;

public class TeamCursor {
    
    public static void displayTeamCursor(GraphicsContext gc, Team team){

        double posX = team.getTargetX() * GameScene.getxFactor();
        double posY = team.getTargetY() * GameScene.getyFactor();

        gc.setStroke(ParticleView.getTeamColor(team).brighter().brighter().brighter());
        gc.setLineWidth(3);
        gc.strokeOval(posX, posY, 10 * GameScene.getxFactor(), 10 * GameScene.getxFactor());
    }
}
