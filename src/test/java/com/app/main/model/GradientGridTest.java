package com.app.main.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.app.main.model.core.GradientGrid;

public class GradientGridTest {

    @Test
    public void testPropagationSimple() {

        boolean[][] obstacles = new boolean[5][5];
        GradientGrid grid = GradientGrid.createGradientGrid(5, 5, obstacles);

        grid.calculgradient(2, 2);

        assertEquals(0, grid.getDistance(2, 2));

        assertEquals(1, grid.getDistance(2, 3)); 
        assertEquals(1, grid.getDistance(1, 2));

        assertEquals(2, grid.getDistance(0, 0));
    }

    @Test
    public void testObstacleAvoidance() {

        boolean[][] obstacles = new boolean[3][3];
        GradientGrid grid = GradientGrid.createGradientGrid(3, 3, obstacles);
        
        grid.setObstacle(0, 1, true); 

        grid.calculgradient(0, 0);

        assertEquals(2, grid.getDistance(0, 2));
    }
    
    @Test
    public void testSafetyCheck() {
        boolean[][] obstacles = new boolean[5][5];
        GradientGrid grid = GradientGrid.createGradientGrid(5, 5, obstacles);

        grid.calculgradient(-5, -5);

        assertEquals(GradientGrid.INFINITE_DISTANCE, grid.getDistance(0, 0));
    }

    @Test
    public void testNoDiagonalsForGradient() {
        
        boolean[][] obstacles = new boolean[3][3];
        GradientGrid grid = GradientGrid.createGradientGrid(3, 3, obstacles);

        grid.calculgradient(1, 1);

        assertEquals(1, grid.getDistance(1, 0), "Axe HAUT incorrect");

        assertEquals(1, grid.getDistance(1, 2), "Axe BAS incorrect");

        assertEquals(1, grid.getDistance(0, 1), "Axe GAUCHE incorrect");

        assertEquals(1, grid.getDistance(2, 1), "Axe DROITE incorrect");

        assertEquals(1, grid.getDistance(0, 0), "Le coin Haut-Gauche doit être à distance 2");

        assertEquals(1, grid.getDistance(2, 0), "Le coin Haut-Droite doit être à distance 2");

        assertEquals(1, grid.getDistance(0, 2), "Le coin Bas-Gauche doit être à distance 2");

        assertEquals(1, grid.getDistance(2, 2), "Le coin Bas-Droite doit être à distance 2");
    }
}