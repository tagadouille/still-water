package com.app.main.controller;

import com.app.main.view.GameInfoView;

public class GameInfoViewController {
    
    GameInfoView gameInfoView;

    private GameInfoViewController(GameInfoView gameinfo){
        this.gameInfoView = gameinfo;

        gameinfo.getQuitButton().setOnAction((e) -> System.exit(0));
    }

    public static GameInfoViewController creaInfoViewController(GameInfoView gameInfoView){

        if(gameInfoView == null){
            throw new IllegalArgumentException("The GameInfoView can't be null");
        }

        return new GameInfoViewController(gameInfoView);
    }
}
