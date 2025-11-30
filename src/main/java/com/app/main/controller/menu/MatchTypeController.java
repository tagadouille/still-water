package com.app.main.controller.menu;

import com.app.main.model.GameManager;
import com.app.main.model.core.Point;
import com.app.main.model.core.Team;
import com.app.main.view.GameScene;

public class MatchTypeController {

    public void play(){
        //! Provisoire : 
        boolean[][] obstacles = new boolean[GameManager.GRID_DIM][GameManager.GRID_DIM];
        Team[] teams = new Team[]{
            Team.CreateBlueTeam(GameManager.GRID_DIM, GameManager.GRID_DIM, obstacles), 
            Team.CreateRedTeam(GameManager.GRID_DIM, GameManager.GRID_DIM, obstacles)
        };
        GameManager gameManager = GameManager.gameManagerFactory(
            obstacles,
            teams,
            new Point[][]{
                {new Point(0, 0), new Point(100, 144)},
                {new Point(336, 380), new Point(GameManager.GRID_DIM, GameManager.GRID_DIM)}
            }
        );
        teams[1].setTarget(0, 0);
        MenuSwitcher.switchScene(new GameScene(gameManager));
    }

    public void goBack(){
        MenuSwitcher.switchScene("Gamemode.fxml");
    }
}
