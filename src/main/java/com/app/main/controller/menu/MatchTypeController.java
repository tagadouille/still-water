package com.app.main.controller.menu;

import java.util.Random;

import com.app.main.audio.GamePlaylist;
import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Point;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.view.GameScene;

public class MatchTypeController {

    public void play(){
        //! Provisoire : 
        /*boolean[][] obstacles = new boolean[GameManager.GRID_DIM][GameManager.GRID_DIM];
        Team[] teams = new Team[]{
            Team.CreateBlueTeam(GameManager.GRID_DIM, GameManager.GRID_DIM, obstacles), 
            Team.CreateRedTeam(GameManager.GRID_DIM, GameManager.GRID_DIM, obstacles)
        };
        GameManager gameManager = GameManager.gameManagerFactoryTest(
            obstacles,
            teams,
            new Point[][]{
                {new Point(0, 0), new Point(100, 144)},
                {new Point(460, 460), new Point(GameManager.GRID_DIM, GameManager.GRID_DIM)}
            },
            GameManager.GRID_DIM
        );
        GamePlaylist.playLevelAudio();

        MenuSwitcher.switchScene(
            new GameScene(gameManager,
            new Controller[]{
                null, 
                new BotController(gameManager.getWidth(), gameManager.getHeight(), gameManager.getTeams()[1])})
        );*/
    }

    public void goBack(){
        MenuSwitcher.switchScene("Gamemode.fxml");
    }
}
