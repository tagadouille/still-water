package com.app.main.controller.menu;

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
