package com.app.main.controller;

import com.app.main.view.GameScene;

public class MatchTypeController {

    public void play(){
        MenuSwitcher.switchScene(new GameScene());
    }

    public void goBack(){
        MenuSwitcher.switchScene("Gamemode.fxml");
    }
}
