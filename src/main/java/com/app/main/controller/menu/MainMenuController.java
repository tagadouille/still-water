package com.app.main.controller.menu;

import com.app.main.controller.levelEditor.LevelEditorController;
import com.app.main.view.levelEditor.LevelEditorView;

import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    public void toSettings(){
        MenuSwitcher.switchScene("Settings.fxml");
    }

    public void toGamemode(){
        MenuSwitcher.switchScene("Gamemode.fxml");
    }

    public void toLevelEditor(){
        LevelEditorView editor = new LevelEditorView();
        LevelEditorController controller = LevelEditorController.buildLevelEditorController(editor);
        MenuSwitcher.switchScene(editor);
    }

    public void quit(){
        System.exit(0);
    }
}
