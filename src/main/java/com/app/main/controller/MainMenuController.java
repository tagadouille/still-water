package com.app.main.controller;

import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    public void toSettings(){
        MenuSwitcher.switchScene("Settings.fxml");
    }

    public void toGamemode(){
        MenuSwitcher.switchScene("Gamemode.fxml");
    }

    public void quit(){
        System.exit(0);
    }
}
