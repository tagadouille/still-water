package com.app.main.model.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team redTeam;
    private Team blueTeam;
    private boolean[][] obstacles;

    @BeforeEach
    void setUp() {
        // On crée une petite map 10x10 sans obstacles pour le test
        obstacles = new boolean[10][10];
        redTeam = Team.CreateRedTeam(10, 10, obstacles);
        blueTeam = Team.CreateBlueTeam(10, 10, obstacles);
    }

    @Test
    void testTeamIdentity() {
        // On vérifie que les deux équipes sont bien distinctes
        assertEquals(Color.RED, redTeam.getTeam());
        assertEquals(Color.BLUE, blueTeam.getTeam());
        assertNotEquals(redTeam, blueTeam);
    }

    @Test
    void testAddCell() {
        Cell cell = Cell.CreateCell(1, 1, Color.RED);
        redTeam.addCell(cell);
        
        assertEquals(1, redTeam.getArmy().size());
        assertTrue(redTeam.getArmy().contains(cell));
    }

    @Test
    void testAttackLogicBetweenTeams() {
        // 1. Création des combattants
        // Un soldat de l'équipe ROUGE
        Cell attacker = Cell.CreateCell(0, 0, Color.RED);
        redTeam.addCell(attacker);

        // Une victime de l'équipe BLEUE
        Cell victim = Cell.CreateCell(0, 1, Color.BLUE);
        blueTeam.addCell(victim);
        
        // 2. Configuration de l'énergie
        attacker.setEnergy(50);
        victim.setEnergy(5); // Énergie critique (<= 5 signifie conversion imminente)

        // 3. Action : L'équipe Rouge attaque la cellule de l'équipe Bleue
        redTeam.attack(attacker, victim);

        // 4. Vérifications
        // L'attaquant gagne de l'énergie (+5)
        assertEquals(55, attacker.getEnergy(), "L'attaquant rouge devrait gagner 5 PV");
        
        // La victime reste à 5 PV (seuil minimum défini dans ton code)
        assertEquals(5, victim.getEnergy(), "La victime bleue devrait être à 5 PV");

        // CRUCIAL : La victime bleue doit changer d'allégeance vers l'équipe rouge (this.team)
        assertEquals(Color.RED, victim.getNextTeam(), "La cellule bleue doit être convertie à l'équipe Rouge");
        assertNotEquals(victim.getCurrentTeam(), victim.getNextTeam(), "L'équipe actuelle et future doivent être différentes");
    }

    @Test
    void testHealLogicSameTeam() {
        // Le soin ne marche qu'entre membres de la même équipe
        Cell healer = Cell.CreateCell(0, 0, Color.RED);
        Cell receiver = Cell.CreateCell(0, 1, Color.RED);
        
        redTeam.addCell(healer);
        redTeam.addCell(receiver);
        
        healer.setEnergy(90); // Presque plein
        receiver.setEnergy(30); // Besoin de soin

        // Action : Soin
        redTeam.heal(healer, receiver);

        // Vérification : Transfert d'énergie (+1 / -1)
        assertEquals(89, healer.getEnergy());
        assertEquals(31, receiver.getEnergy());
    }
}