package com.app.main.model.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team redTeam;
    private Team blueTeam;
    private boolean[][] obstacles;

    @BeforeEach
    void setUp() {
        obstacles = new boolean[10][10];
        redTeam = Team.CreateRedTeam(10, 10, obstacles);
        blueTeam = Team.CreateBlueTeam(10, 10, obstacles);
    }

    @Test
    void testTeamCreation() {
        assertEquals(Color.RED, redTeam.getTeam());
        assertNotNull(redTeam.getArmy());
        assertTrue(redTeam.getArmy().isEmpty());
    }

    @Test
    void testAddCell() {
        Team.Cell cell = Team.Cell.CreateCell(1, 1, Color.RED);
        redTeam.addCell(cell);
        
        assertEquals(1, redTeam.getArmy().size());
        assertEquals(cell, redTeam.getArmy().get(0));
    }

    @Test
    void testAttackLogic() {
        // Mise en place : 1 Attaquant Rouge, 1 Victime Bleue
        Team.Cell attacker = Team.Cell.CreateCell(0, 0, Color.RED);
        Team.Cell victim = Team.Cell.CreateCell(0, 1, Color.BLUE);
        
        // Énergie initiale
        attacker.setEnergy(50);
        victim.setEnergy(10); // Faible énergie

        // Action : Attaque
        redTeam.attack(attacker, victim);

        // Vérification : Transfert d'énergie (+5 / -5)
        assertEquals(55, attacker.getEnergy(), "L'attaquant gagne 5 PV");
        assertEquals(5, victim.getEnergy(), "La victime perd 5 PV");

        // Vérification : Conversion (Victime à 5 PV ou moins -> nextTeam devient Rouge)
        assertEquals(Color.RED, victim.getNextTeam(), "La victime doit être convertie");
    }

    @Test
    void testHealLogic() {
        // Mise en place : 2 Rouges
        Team.Cell healer = Team.Cell.CreateCell(0, 0, Color.RED);
        Team.Cell receiver = Team.Cell.CreateCell(0, 1, Color.RED);
        
        healer.setEnergy(90); // Riche
        receiver.setEnergy(30); // Pauvre

        // Action : Soin
        redTeam.heal(healer, receiver);

        // Vérification : Transfert d'énergie (+1 / -1)
        assertEquals(89, healer.getEnergy());
        assertEquals(31, receiver.getEnergy());
    }
}