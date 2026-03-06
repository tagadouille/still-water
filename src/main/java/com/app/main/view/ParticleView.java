package com.app.main.view;

import com.app.main.model.core.Team;
import com.app.main.model.core.Cell;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe permettant de rendre les particules
 * 
 * @author Dai Elias
 */
public final class ParticleView {
    
    /**
     * Permet d'afficher les cellules d'une équipes
     * @param gc le graphics context sur lequel affiché les cellules
     * @param team l'équipe dont les cellules seront affichés
     * @param screenSize la taille de l'écran
     */
    public static void renderParticles(GraphicsContext gc, Team team, double width, double height){

        Color color = getTeamColor(team);

        for (Cell cell : team.getArmy()) {

            // Make the color more or less dark in function of the energy of the cell
            double brightnessFactor = cell.getEnergy() / 100.0;

            brightnessFactor = Math.min(brightnessFactor, 1.0);
            brightnessFactor = Math.max(brightnessFactor, 0.0);


            Color cellColor = new Color(
                color.getRed() * brightnessFactor,
                color.getGreen() * brightnessFactor,
                color.getBlue() * brightnessFactor,
                color.getOpacity()
            );


            double x = cell.getX() * GameScene.getxFactor();
            double y = cell.getY() * GameScene.getyFactor(); 
                
            double w = GameScene.getxFactor();
            double h = GameScene.getyFactor();

            // Truncate the position if needed
            if(x + w > width){
                w = width - x;
            }
            if(y + h > height){
                h = height -y;
            }

            gc.setFill(cellColor);
            gc.fillRect(x, y, w, h);
        }
    }

    /**
     * Give the javafx color of a team for the view
     * @param team the team for get it color
     * @return the javafx color of the team
     */
    public static Color getTeamColor(Team team){
        com.app.main.model.core.Color color = team.getTeam();

        return getTeamColor(color);
    }

    /**
     * Give the javafx color of a team color for the view
     * @param color the color team for get it color
     * @return the javafx color of the team
     */
    public static Color getTeamColor(com.app.main.model.core.Color color){

        switch (color) {
            case RED:
                return Color.RED;
            case PURPLE:
                return Color.PURPLE;
            case BLUE:
                return Color.BLUE;
            default:
                return Color.GREEN;
        }
    }
}
