package com.app.main.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.app.main.model.Team.Cell;

public class TeamTest {

    Cell attacker = Cell.CreateCell(new Point(0,0), Color.RED);
    Cell victim = Cell.CreateCell(new Point(0,1), Color.BLUE);
    Team redTeam = Team.CreateRedTeam(10, 10);

    @BeforeEach
    public void reset(){
        attacker = Cell.CreateCell(new Point(0,0), Color.RED);
        victim = Cell.CreateCell(new Point(0,1), Color.BLUE);
        redTeam = Team.CreateRedTeam(10, 10);
    }
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
        Cell c = Cell.CreateCell(new Point(5, 5), Color.RED);
        assertEquals(5, c.getPosition().x());
        assertEquals(100, c.getEnergy());
    }

    @Test
    public void testAttackLogic() {
        
        attacker.setEnergy(50);
        victim.setEnergy(50);

        // Action
        redTeam.attack(attacker, victim);

        // Vérification (Vol d'énergie)
        assertEquals(55, attacker.getEnergy(), "L'attaquant doit gagner 5 PV");
        assertEquals(45, victim.getEnergy(), "La victime doit perdre 5 PV");
    }

    @Test
    public void testHealLogic() {
        Team redTeam = Team.CreateRedTeam(10, 10);
        Cell healer = Cell.CreateCell(new Point(0,0), Color.RED);
        Cell receiver = Cell.CreateCell(new Point(0,1), Color.RED);

        // Cas valide pour le soin
        healer.setEnergy(30);   // > 20
        receiver.setEnergy( 70); // < 80

        redTeam.heal(healer, receiver);

        assertEquals(29, healer.getEnergy(), "Le soigneur perd 1 PV");
        assertEquals(71, receiver.getEnergy(), "Le receveur gagne 1 PV");
    }

    @Test
    public void testConversionLogic() {

        // La victime est faible
        victim.setEnergy(5);
        attacker.setEnergy(50);

        // L'attaque doit tuer la victime (5 - 5 = 0)
        redTeam.attack(attacker, victim);

        assertEquals(0, victim.getEnergy());
        assertFalse(victim.isAlive(), "La victime ne doit plus être 'vivante' (stable)");
        assertEquals(Color.RED, victim.getNextTeam(), "La victime doit être marquée pour rejoindre les Rouges");
    }
}