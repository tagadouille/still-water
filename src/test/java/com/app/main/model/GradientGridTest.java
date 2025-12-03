package com.app.main.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.app.main.model.core.GradientGrid;
import com.app.main.model.core.Point;

public class GradientGridTest {

    @Test
    public void testPropagationSimple() {
        // Grille 5x5, cible au centre (2,2)
        boolean[][] obstacles = new boolean[5][5];
        GradientGrid grid = GradientGrid.createGradientGrid(5, 5, obstacles);
        
        grid.calculgradient(2, 2);

        // La cible doit être à 0
        assertEquals(0, grid.getDistance(2, 2));

        // Les voisins directs doivent être à 1
        assertEquals(1, grid.getDistance(2, 3)); 
        assertEquals(1, grid.getDistance(1, 2));

        // Un coin (0,0) doit être à distance 4
        assertEquals(4, grid.getDistance(0, 0));
    }

    @Test
    public void testObstacleAvoidance() {
        // Contournement de mur
        boolean[][] obstacles = new boolean[3][3];
        GradientGrid grid = GradientGrid.createGradientGrid(3, 3, obstacles);
        
        // Mur en (0,1) bloquant le chemin direct vers (0,0) depuis (0,2)
        // Attention : setObstacle met à jour le tableau partagé ET l'objet
        grid.setObstacle(0, 1, true); 

        // Calcul depuis la cible (0,0)
        grid.calculgradient(0, 0);

        // Distance directe (si pas de mur) serait 2. Avec détour -> 4.
        assertEquals(4, grid.getDistance(0, 2));
    }

    @Test
    public void testSafetyCheck() {
        boolean[][] obstacles = new boolean[5][5];
        GradientGrid grid = GradientGrid.createGradientGrid(5, 5, obstacles);
        
        // Test avec cible hors limites
        grid.calculgradient(-5, -5);
        
        // Rien ne doit avoir changé (tout reste à l'infini par défaut)
        assertEquals(GradientGrid.INFINITE_DISTANCE, grid.getDistance(0, 0));
    }
}