package com.app.main.controller.menu;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WinnerController {
    @FXML
    private Text text;

    public void updateWinner(String color){
        text.setText("The winner is " + color);
    }

    public void goToMain(){
        MenuSwitcher.switchScene("MainMenu.fxml");
    }

    public void goToGamemode(){
        MenuSwitcher.switchScene("Gamemode.fxml");
    }
}
