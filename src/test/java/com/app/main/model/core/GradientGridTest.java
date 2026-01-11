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
        // On place un mur en (1,0) et (1,1) et (0,1) pour bloquer (0,0) sauf diagonale ?
        // Bloquons simplement une ligne
        gradientGrid.setObstacle(5, 5, true);
        
        gradientGrid.calculgradient(0, 0);
        
        // La case obstacle ne doit pas être visitée (reste INFINITE ou non traitable)
        // Note: selon ton algo, si c'est un mur, on ne met pas à jour sa distance
        // Donc si on demande la distance D'UN MUR, ça dépend de ton implémentation getDistance.
        // Mais un point DERRIERE un mur doit avoir un chemin plus long.
    }

    @Test
    void testTargetOnObstacleCorrection() {
        // C'est ta fonctionnalité "Mailles améliorée"
        // On met un mur en (5,5)
        obstacles[5][5] = true;
        // On s'assure qu'il y a du vide à côté (5,6)
        obstacles[5][6] = false;

        // On demande le gradient avec la CIBLE SUR LE MUR
        gradientGrid.calculgradient(5, 5);

        // L'algo doit avoir trouvé le voisin (5,6) ou autre et propagé depuis là.
        // Donc (5,6) doit être à 0 (nouvelle cible)
        assertEquals(0, gradientGrid.getDistance(5, 6)); 
        
        // Et le reste doit être rempli (pas INFINITE)
        assertNotEquals(GradientGrid.INFINITE_DISTANCE, gradientGrid.getDistance(0, 0));
    }
}