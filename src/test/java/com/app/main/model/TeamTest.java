// package com.app.main.model;

// import static org.junit.jupiter.api.Assertions.*;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.app.main.model.core.Team;
// import com.app.main.model.core.Team.Cell;
// import com.app.main.model.core.Color;

// public class TeamTest {

//     // On a besoin d'un tableau d'obstacles factice pour créer les équipes
//     boolean[][] emptyObstacles = new boolean[10][10];

//     Team redTeam;
//     Cell attacker;
//     Cell victim;

//     @BeforeEach
//     public void setup() {
//         // Initialisation propre avant chaque test
//         redTeam = Team.CreateRedTeam(10, 10, emptyObstacles);
//         attacker = Cell.CreateCell(0, 0, Color.RED);
//         victim = Cell.CreateCell(0, 1, Color.BLUE);
//     }

//     @Test
//     public void testFactoryMethods() {
//         boolean[][] obs = new boolean[100][100];

//         // Test de la création via les fabriques statiques
//         Team red = Team.CreateRedTeam(100, 100, obs);
//         Team blue = Team.CreateBlueTeam(100, 100, obs);
//         Team custom = Team.CreateTeam(Color.GREEN, 50, 50, new boolean[50][50]);

//         assertEquals(Color.RED, red.getTeam(), "La fabrique rouge doit créer une équipe rouge");
//         assertEquals(Color.BLUE, blue.getTeam(), "La fabrique bleue doit créer une équipe bleue");
//         assertEquals(Color.GREEN, custom.getTeam(), "La fabrique générique doit respecter la couleur");
        
//         // Vérification de la création de Cellule via la fabrique
//         Cell c = Cell.CreateCell(5, 5, Color.RED);
//         assertEquals(5, c.getX(), c.getY()); // Utilisation de .getPosition().x()
//         assertEquals(100, c.getEnergy());     // Utilisation du Getter
//     }

//     @Test
//     public void testAttackLogic() {
//         attacker.setEnergy(50);
//         victim.setEnergy(50);

//         // Action
//         redTeam.attack(attacker, victim);

//         // Vérification (Vol d'énergie)
//         assertEquals(55, attacker.getEnergy(), "L'attaquant doit gagner 5 PV");
//         assertEquals(45, victim.getEnergy(), "La victime doit perdre 5 PV");
//     }

//     @Test
//     public void testHealLogic() {
//         // Pour le soin, il faut que les deux soient de la même équipe
//         Cell healer = Cell.CreateCell(0, 0, Color.RED);
//         Cell receiver = Cell.CreateCell(0, 1, Color.RED);

//         // Cas valide pour le soin
//         healer.setEnergy(30);   // > 20
//         receiver.setEnergy(70); // < 80

//         redTeam.heal(healer, receiver);

//         assertEquals(29, healer.getEnergy(), "Le soigneur perd 1 PV");
//         assertEquals(71, receiver.getEnergy(), "Le receveur gagne 1 PV");
//     }

//     @Test
//     public void testConversionLogic() {
//         // La victime est faible
//         victim.setEnergy(5);
//         attacker.setEnergy(50);

//         // L'attaque doit tuer la victime (5 - 5 = 0)
//         redTeam.attack(attacker, victim);

//         assertEquals(0, victim.getEnergy());
//         assertFalse(victim.isAlive(), "La victime ne doit plus être 'vivante' (stable)");
        
//         // Vérification que la prochaine équipe est bien celle de l'attaquant (RED)
//         assertEquals(Color.RED, victim.getNextTeam(), "La victime doit être marquée pour rejoindre les Rouges");
//     }
// }