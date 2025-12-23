package com.app.main.controller.menu;

public class GamemodeController {
    
    public void solo(){
        MenuSwitcher.switchScene("LevelMenu.fxml");
    }

    public void goBack(){
        MenuSwitcher.switchScene("MainMenu.fxml");
    }

    public void local(){
         MenuSwitcher.switchScene("Multilocal.fxml"); //! pour l'instant
    }

     public void online(){
        //! Réseau
    }
}
