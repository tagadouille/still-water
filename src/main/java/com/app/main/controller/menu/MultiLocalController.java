package com.app.main.controller.menu;

import com.app.main.audio.GamePlaylist;
import com.app.main.controller.playercontroller.KeyboardController;
import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Color;
import com.app.main.model.core.Point;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.view.GameScene;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;

/**
 * Contrôleur pour la création de parties locales (duo, trio, squad).
 * <p>
 * Prépare les équipes, les contrôleurs (joueurs et bots), puis lance
 * la scène de jeu correspondante.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public class MultiLocalController {

    @FXML
    public void duo() {
        launchCustomGame(2);
    }

    @FXML
    public void trio() {
        launchCustomGame(3);
    }

    @FXML
    public void squad() {
        launchCustomGame(4);
    }

    /**
     * Initialise et lance une partie locale personnalisée avec le nombre
     * d'équipes spécifié.
     *
     * @param nbTeams nombre d'équipes à créer
     */
    private void launchCustomGame(int nbTeams) {
        try {
            
            boolean[][] obstacles = new boolean[GameManager.GRID_DIM][GameManager.GRID_DIM]; 

            Team[] teams = new Team[nbTeams];
            Point[][] spawns = new Point[nbTeams][2];

            Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.PURPLE};

            for (int i = 0; i < nbTeams; i++) {
                teams[i] = Team.CreateTeam(colors[i % colors.length], GameManager.GRID_DIM, GameManager.GRID_DIM, obstacles);

                spawns[i] = getSpawnForTeam(i, GameManager.GRID_DIM);
            }

            GameManager gm = GameManager.gameManagerFactory(obstacles, teams, spawns);

            Controller[] controllers = new Controller[nbTeams];

            controllers[0] = KeyboardController.createKeyboardController(
                teams[0], GameManager.GRID_DIM, GameManager.GRID_DIM, 
                KeyCode.Z, KeyCode.S, KeyCode.D, KeyCode.Q
            );

            if (nbTeams >= 2) {
                controllers[1] = KeyboardController.createKeyboardController(
                    teams[1], GameManager.GRID_DIM, GameManager.GRID_DIM, 
                    KeyCode.I, KeyCode.K, KeyCode.L, KeyCode.J
                );
            }

            // --- LE RESTE (Bots) ---
            for (int i = 2; i < nbTeams; i++) {
                controllers[i] = new BotController(teams[i]);
            }

            GamePlaylist.playLevelAudio();

            MenuSwitcher.switchScene(GameScene.buildGameScene(gm, controllers));

        } catch (Exception e) {
            System.err.println("Erreur création partie custom : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retourne une paire de points de spawn pour l'équipe d'index donné.
     * Les positions sont choisies par défaut en fonction de l'index (coins et centres).
     *
     * @param index indice de l'équipe
     * @param dim dimension de la grille
     * @return tableau de deux {@link Point} représentant la zone de spawn
     */
    private Point[] getSpawnForTeam(int index, int dim) {
        int w = 100;
        int h = 80;
        int margin = 10;

        Point p1, p2;

        switch (index) {
            case 0: // Haut-Gauche (J1)
                p1 = new Point(margin, margin);
                p2 = new Point(margin + w, margin + h);
                break;
            case 1: // Bas-Droit (J2)
                p1 = new Point(dim - margin - w, dim - margin - h);
                p2 = new Point(dim - margin, dim - margin);
                break;
            case 2: // Haut-Droit (Bot 1)
                p1 = new Point(dim - margin - w, margin);
                p2 = new Point(dim - margin, margin + h);
                break;
            case 3: // Bas-Gauche (Bot 2)
                p1 = new Point(margin, dim - margin - h);
                p2 = new Point(margin + w, dim - margin);
                break;
            default: // Au centre (Secours)
                p1 = new Point(dim/2, dim/2);
                p2 = new Point(dim/2 + w, dim/2 + h);
                break;
        }
        return new Point[]{p1, p2};
    }
    
}