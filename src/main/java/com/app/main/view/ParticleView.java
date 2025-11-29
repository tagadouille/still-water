package com.app.main.view;

import com.app.main.model.Team;
import com.app.main.model.Team.Cell;

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
    public static void renderParticles(GraphicsContext gc, Team team, int screenSize){

        Color color = getTeamColor(team);

        for (Cell cell : team.getArmy()) {

            // Rendre la couleur plus ou moins foncé selon le niveau d'énergie de la cellule
            double brightnessFactor = cell.getEnergy() / 100;

            Color cellColor = new Color(
                color.getRed() * brightnessFactor,
                color.getGreen() * brightnessFactor,
                color.getBlue() * brightnessFactor,
                color.getOpacity()
            );

            double factor = screenSize / 480; // Facteur multiplicatif pour l'affichage

            gc.setFill(cellColor);
            gc.fillRect(
                cell.getX() * factor,
                cell.getY() * factor,
                1 * factor,
                1 * factor);
        }
    }

    private static Color getTeamColor(Team team){
        com.app.main.model.Color color = team.getTeam();
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
