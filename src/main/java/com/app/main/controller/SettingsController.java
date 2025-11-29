package com.app.main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SettingsController {
    @FXML
    ChoiceBox screenResolution;

    public void goBack(){
        MenuSwitcher.switchScene("MainMenu.fxml");
    }
}
