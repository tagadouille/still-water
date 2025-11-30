package com.app.main.controller.menu;

public class GamemodeController {
    
    public void solo(){
        MenuSwitcher.switchScene("MatchType.fxml");
    }

    public void goBack(){
        MenuSwitcher.switchScene("MainMenu.fxml");
    }
}
