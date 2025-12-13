package com.app.main.view;

import com.app.main.model.core.Team;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class is for display a cursor of a team
 * 
 * @author Dai Elias
 */
public final class TeamCursor {
    
    /**
     * Display the cursor of the team with the same color as the team
     * @param gc the graphics context where the cursor is displayed
     * @param team the team that used for display it cursor
     */
    public static void displayTeamCursor(GraphicsContext gc, Team team){

        double posX = team.getTargetX() * GameScene.getxFactor();
        double posY = team.getTargetY() * GameScene.getyFactor();

        Color color = ParticleView.getTeamColor(team);

        double brightLim = 0.90;
        double augmLim = 0.15;

        gc.setStroke(
            new Color(
                Math.min(color.getRed() + augmLim , brightLim),
                Math.min(color.getGreen() + augmLim , brightLim), 
                Math.min(color.getBlue() + augmLim , brightLim),
                color.getOpacity()
            )
        );
        gc.setLineWidth(3);
        double size = 10 * GameScene.getxFactor();

        gc.strokeOval(posX - size/2, posY - size/2, size, size);
    }
}
