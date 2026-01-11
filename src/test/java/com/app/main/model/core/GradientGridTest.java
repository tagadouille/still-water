package com.app.main.model.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GradientGridTest {

    private GradientGrid gradientGrid;
    private boolean[][] obstacles;
    private final int width = 10;
    private final int height = 10;

    @BeforeEach
    void setUp() {
        // Initialisation d'une grille 10x10 vide
        obstacles = new boolean[width][height];
        gradientGrid = GradientGrid.createGradientGrid(width, height, obstacles);
    }

    @Test
    void testInitialization() {
        // Vérifie que tout est initialisé à INFINITE
        assertEquals(GradientGrid.INFINITE_DISTANCE, gradientGrid.getDistance(0, 0));
        assertEquals(width, gradientGrid.width);
        assertEquals(height, gradientGrid.height);
    }

    @Test
    void testCalculGradientSimple() {
        // Cible en (0,0)
        gradientGrid.calculgradient(0, 0);

        // La cible doit être à 0
        assertEquals(0, gradientGrid.getDistance(0, 0));
        
        // Un voisin direct doit être à 1
        assertEquals(1, gradientGrid.getDistance(1, 0));
        assertEquals(1, gradientGrid.getDistance(0, 1));
        
        // Un voisin en diagonale ou plus loin doit être à 1 ou 2 selon ton implémentation de Direction
        // (En supposant 8 directions, c'est 1, sinon 2)
        assertTrue(gradientGrid.getDistance(1, 1) > 0);
    }

    @Test
    void testObstacleBlocking() {
        // 1. On place un mur en (5,5)
        gradientGrid.setObstacle(5, 5, true);
        
        // 2. On lance le calcul depuis (0,0)
        gradientGrid.calculgradient(0, 0);
        
        // 3. Vérification :
        // La case (5,5) étant un mur, le gradient ne doit pas "entrer" dedans.
        // Sa distance doit donc rester à la valeur par défaut (INFINITE).
        assertEquals(GradientGrid.INFINITE_DISTANCE, gradientGrid.getDistance(5, 5), 
            "Un obstacle ne doit pas recevoir de valeur de distance (doit rester INFINITE)");

        // Vérifions qu'une case juste à côté (5,4) a bien reçu une distance (car elle est vide)
        // (Elle n'est pas inaccessible, juste le mur l'est)
        assertNotEquals(GradientGrid.INFINITE_DISTANCE, gradientGrid.getDistance(5, 4),
            "Les cases voisines libres doivent être calculées normalement");
    }

    @Test
    void testTargetOnObstacleCorrection() {
        // 1. On place un mur en (5,5)
        gradientGrid.setObstacle(5, 5, true);

        // 2. On lance le calcul SUR le mur
        gradientGrid.calculgradient(5, 5);

        // 3. Vérification : 
        // L'algorithme a dû déplacer la cible (distance 0) sur une case voisine.
        // On cherche si UN des voisins (dans le carré 3x3 autour) est devenu la cible.
        
        boolean foundNewTarget = false;
        
        // On scanne autour de (5,5)
        for (int x = 4; x <= 6; x++) {
            for (int y = 4; y <= 6; y++) {
                // On ignore le mur lui-même
                if (x == 5 && y == 5) continue;
                
                // Si on trouve une distance de 0, c'est que la cible s'est déplacée ici 
                if (gradientGrid.getDistance(x, y) == 0) {
                    foundNewTarget = true;
                }
            }
        }

        assertTrue(foundNewTarget, "La cible aurait dû être déplacée vers une case voisine libre.");
        
        // Vérifie aussi que le reste de la map est accessible (pas INFINITE)
        assertNotEquals(GradientGrid.INFINITE_DISTANCE, gradientGrid.getDistance(0, 0));
    }
}