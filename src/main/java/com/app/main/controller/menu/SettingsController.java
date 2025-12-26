package com.app.main.controller.menu;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

/**
 * The SettingsController is the controller class for
 * the settings page of the game
 */
public class SettingsController {
    @FXML
    private ChoiceBox screenResolution;

    /**
     * To go back to the main menu
     */
    public void goBack(){
        MenuSwitcher.switchScene("MainMenu.fxml");
    }
}
