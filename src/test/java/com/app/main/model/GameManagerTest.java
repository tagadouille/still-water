package com.app.main.model;

import com.app.main.model.core.Color;
import com.app.main.model.core.Point;
import com.app.main.model.core.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gameManager;
    private boolean[][] obstacles;
    private Team[] teams;
    private Point[][] spawns;

    @BeforeEach
    void setUp() {
        // On respecte la dimension fixe du GameManager pour éviter les erreurs
        int dim = GameManager.GRID_DIM; 
        obstacles = new boolean[dim][dim];

        // Création de 2 équipes
        teams = new Team[2];
        teams[0] = Team.CreateRedTeam(dim, dim, obstacles);
        teams[1] = Team.CreateBlueTeam(dim, dim, obstacles);

        // Définition des spawns (Petits rectangles pour le test)
        spawns = new Point[2][2];
        // Team 1 Spawn (Area 10x10 = 100 cells)
        spawns[0][0] = new Point(0, 0);
        spawns[0][1] = new Point(10, 10);
        
        // Team 2 Spawn (Area 10x10 = 100 cells)
        spawns[1][0] = new Point(20, 20);
        spawns[1][1] = new Point(30, 30);
    }

    @Test
    void testFactorySuccess() {
        // On utilise la factory TEST pour ignorer la règle des 8000 cellules
        gameManager = GameManager.gameManagerFactoryTest(obstacles, teams, spawns, GameManager.GRID_DIM);
        
        assertNotNull(gameManager);
        assertEquals(2, gameManager.getTeams().length);
        assertNotNull(gameManager.getGlobalGrid());
    }

    @Test
    void testInitCellsPlacement() {
        gameManager = GameManager.gameManagerFactoryTest(obstacles, teams, spawns, GameManager.GRID_DIM);

        // Vérifie qu'une cellule a bien été placée aux coordonnées du spawn
        assertNotNull(gameManager.getGlobalGrid()[0][0], "Il devrait y avoir une cellule en 0,0");
        assertEquals(Color.RED, gameManager.getGlobalGrid()[0][0].getCurrentTeam());
        
        assertNotNull(gameManager.getGlobalGrid()[20][20], "Il devrait y avoir une cellule en 20,20");
        assertEquals(Color.BLUE, gameManager.getGlobalGrid()[20][20].getCurrentTeam());
    }

    @Test
    void testUpdateForces() {
        gameManager = GameManager.gameManagerFactoryTest(obstacles, teams, spawns, GameManager.GRID_DIM);
        
        double[] forces = gameManager.getForces();
        
        // On a créé deux zones de 10x10 = 100 cellules chacune.
        // Total = 200. Ratio = 100/200 = 0.5 chacun.
        assertEquals(0.5, forces[0], 0.01);
        assertEquals(0.5, forces[1], 0.01);
    }

    @Test
    void testInvalidConfiguration() {
        // Teste si le GameManager rejette une config invalide (ex: null team)
        teams[0] = null;
        
        assertThrows(IllegalArgumentException.class, () -> {
            GameManager.gameManagerFactoryTest(obstacles, teams, spawns, GameManager.GRID_DIM);
        });
    }
}