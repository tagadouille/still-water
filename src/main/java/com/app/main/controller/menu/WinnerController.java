package com.app.main.controller.menu;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * The WinnerController is the controller
 * for the winning page of the game
 * 
 * @author Dai Elias
 */
public class WinnerController {
    @FXML
    private Text text;

    /**
     * Change the text when the winner is annonced
     * @param color the name of the color
     */
    public void updateWinner(String color){
        text.setText("The winner is " + color);
    }

    /**
     * Go to the main menu
     */
    public void goToMain(){
        MenuSwitcher.switchScene("MainMenu.fxml");
    }

    /**
     * Go to the gamemode page
     */
    public void goToGamemode(){
        MenuSwitcher.switchScene("Gamemode.fxml");
    }
}
