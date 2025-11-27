package com.app.main.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GradientGridTest {

    @Test
    public void testPropagationSimple() {
        // Grille 5x5, cible au centre (2,2)
        GradientGrid grid = GradientGrid.createGradientGrid(5, 5);
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
        GradientGrid grid = GradientGrid.createGradientGrid(3, 3);
        
        // Mur en (0,1) bloquant le chemin direct vers (0,0) depuis (0,2)
        grid.setObstacle(0, 1, true);

        // Calcul depuis la cible (0,0)
        grid.calculgradient(0, 0);

        // Distance directe (si pas de mur) serait 2. Avec détour -> 4.
        assertEquals(4, grid.getDistance(0, 2));
    }

    @Test
    public void testGetBestNeighbor() {
        GradientGrid grid = GradientGrid.createGradientGrid(3, 3);
        grid.calculgradient(2, 2); // Cible en bas à droite

        // On est en (0,0), distance 4
        Point best = grid.BestNeighbors(0, 0);
        
        assertNotNull(best, "Un voisin doit être trouvé");
        
        // Le voisin doit réduire la distance (passer de 4 à 3)
        int distVoisin = grid.getDistance(best.x(), best.y());
        assertEquals(3, distVoisin);
    }
    
    @Test
    public void testSafetyCheck() {
        GradientGrid grid = GradientGrid.createGradientGrid(5, 5);
        
        // Test avec cible hors limites
        grid.calculgradient(-5, -5);
        
        // Rien ne doit avoir changé (tout reste à l'infini)
        assertEquals(GradientGrid.INFINITE_DISTANCE, grid.getDistance(0, 0));
    }
}