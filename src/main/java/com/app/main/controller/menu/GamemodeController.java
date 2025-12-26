package com.app.main.controller.menu;

/**
 * The GamemodeController is the controller class
 * of the gamemode page of the game
 */
public class GamemodeController {
    
    public void solo(){
        MenuSwitcher.switchScene("LevelMenu.fxml");
    }

    /**
     * to go to the main menu
     */
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
