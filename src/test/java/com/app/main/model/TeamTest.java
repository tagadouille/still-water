package com.app.main.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.app.main.model.Team.Cell;

public class TeamTest {

    @Test
    public void testFactoryMethods() {
        // Test de la création via les fabriques statiques
        Team red = Team.CreateRedTeam(100, 100);
        Team blue = Team.CreateBlueTeam(100, 100);
        Team custom = Team.CreateTeam(Color.GREEN, 50, 50);

        assertEquals(Color.RED, red.team, "La fabrique rouge doit créer une équipe rouge");
        assertEquals(Color.BLUE, blue.team, "La fabrique bleue doit créer une équipe bleue");
        assertEquals(Color.GREEN, custom.team, "La fabrique générique doit respecter la couleur");
        
        // Vérification de la création de Cellule via la fabrique
        Cell c = Cell.CreateCell(5, 5, Color.RED);
        assertEquals(5, c.x);
        assertEquals(100, c.energy);
    }

    @Test
    public void testAttackLogic() {
        // 1. Création via Factory
        Team redTeam = Team.CreateRedTeam(10, 10);
        
        // 2. Création des cellules via Factory
        Cell attacker = Cell.CreateCell(0, 0, Color.RED);
        Cell victim = Cell.CreateCell(0, 1, Color.BLUE);
        
        attacker.energy = 50;
        victim.energy = 50;

        // Action
        redTeam.attack(attacker, victim);

        // Vérification (Vol d'énergie)
        assertEquals(55, attacker.energy, "L'attaquant doit gagner 5 PV");
        assertEquals(45, victim.energy, "La victime doit perdre 5 PV");
    }

    @Test
    public void testHealLogic() {
        Team redTeam = Team.CreateRedTeam(10, 10);
        Cell healer = Cell.CreateCell(0, 0, Color.RED);
        Cell receiver = Cell.CreateCell(0, 1, Color.RED);

        // Cas valide pour le soin
        healer.energy = 30;   // > 20
        receiver.energy = 70; // < 80

        redTeam.heal(healer, receiver);

        assertEquals(29, healer.energy, "Le soigneur perd 1 PV");
        assertEquals(71, receiver.energy, "Le receveur gagne 1 PV");
    }

    @Test
    public void testConversionLogic() {
        Team redTeam = Team.CreateRedTeam(10, 10);
        
        Cell attacker = Cell.CreateCell(0, 0, Color.RED);
        Cell victim = Cell.CreateCell(0, 1, Color.BLUE);

        // La victime est faible
        victim.energy = 5; 
        attacker.energy = 50;

        // L'attaque doit tuer la victime (5 - 5 = 0)
        redTeam.attack(attacker, victim);

        assertEquals(0, victim.energy);
        assertFalse(victim.isAlive(), "La victime ne doit plus être 'vivante' (stable)");
        assertEquals(Color.RED, victim.nextteam, "La victime doit être marquée pour rejoindre les Rouges");
    }
}