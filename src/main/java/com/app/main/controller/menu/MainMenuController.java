package com.app.main.controller.menu;

import com.app.main.controller.levelEditor.LevelEditorController;
import com.app.main.view.levelEditor.LevelEditorView;

import javafx.fxml.FXML;

/**
 * MainMenuController is the controller of the main menu of the game
 * and add behavior to the buttons
 * 
 * @author Dai Elias
 */
public class MainMenuController {

    @FXML
    /**
     * Go to the settings page
     */
    public void toSettings(){
        MenuSwitcher.switchScene("Settings.fxml");
    }

    /**
     * Go to the gamemode page
     */
    public void toGamemode(){
        MenuSwitcher.switchScene("Gamemode.fxml");
    }

    /**
     * Go to the levelEditor page
     */
    public void toLevelEditor(){
        LevelEditorView editor = new LevelEditorView();
        LevelEditorController.buildLevelEditorController(editor);
        MenuSwitcher.switchScene(editor);
    }

    /**
     * Quit the game
     */
    public void quit(){
        System.exit(0);
    }
}
