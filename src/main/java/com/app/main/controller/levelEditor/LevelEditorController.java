package com.app.main.controller.levelEditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.app.main.Game;
import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.view.levelEditor.LevelEditorView;
import com.app.main.view.levelEditor.ObstacleEditorView;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class LevelEditorController {
    
    private LevelEditorView levelEditorView;
    private FileWrapper fileWrapper = new FileWrapper();

    private LevelEditorController(LevelEditorView levelEditorView){
        this.levelEditorView = levelEditorView;

        // Definition of the behavior of each components :
        imageSelectBehavior();
        buttonBehavior();
    }

    public static LevelEditorController buildLevelEditorController(LevelEditorView levelEditorView){

        if(levelEditorView == null){
            throw new IllegalArgumentException("The levelEditorView can't be null");
        }
        return new LevelEditorController(levelEditorView);
    }

    private void imageSelectBehavior(){

        FileChooser imageChooser = new FileChooser();
        
        levelEditorView.getChooseBackgroundBtn().setOnAction((e) -> 
        {
            File bg = fileWrapper.backgroundImage;

            bg = imageChooser.showOpenDialog(Game.getPrimaryStage());

            // Test the validity
            if(!isValidImage(bg)){
                showInvalidImage();
                fileWrapper.decrementNbDef();
            }
            else{
                try{
                    levelEditorView.getBackgroundPreview().setImage(new Image(new FileInputStream(bg)));
                    fileWrapper.incrementNbDef();
                    fileWrapper.backgroundImage = bg;
                }
                catch(IOException exp){

                }
            }
        }
        );

        levelEditorView.getChooseObstacleBtn().setOnAction((e) -> 
        {
            File obs = imageChooser.showOpenDialog(Game.getPrimaryStage());

            // Test the validity
            if(!isValidImage(obs)){
                showInvalidImage();
                fileWrapper.decrementNbDef();
            }
            else{
                try{
                    levelEditorView.getObstaclePreview().setImage(new Image(new FileInputStream(obs)));
                    fileWrapper.incrementNbDef();
                    fileWrapper.obstacleImage = obs;
                }
                catch(IOException exp){

                }
            }
        });
    }

    /**
     * Inform if the file is a valid javafx Image
     * @param file the file to test
     * @return true if it's the case, false if not
     */
    public static boolean isValidImage(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            Image image = new Image(fis);

            return !image.isError() && image.getWidth() > 0 && image.getHeight() > 0;

        } catch (IOException e) {
            return false;
        }
    }

    private void showInvalidImage(){

        Alert wrongImage = new Alert(Alert.AlertType.ERROR, "The image that has been choose is incorrect 💀🙏✌️🎋💔", ButtonType.OK);
        wrongImage.setHeaderText("☣️☣️☣️Something wents wrong..☣️☣️☣️");
        wrongImage.showAndWait();
        
    }

    private void buttonBehavior(){

        levelEditorView.getNextBtn().setOnAction((e) -> {

            if(fileWrapper.allGood()){
                ObstacleEditorView obstacleEditorView = new ObstacleEditorView();
                ObstacleEditorController obstacleEditorController = ObstacleEditorController.buildEditorController(obstacleEditorView, fileWrapper);

                MenuSwitcher.switchScene(obstacleEditorView);
            }
            else{
                showMustChoose();
            }
        });

        levelEditorView.getCancelBtn().setOnAction((e) -> MenuSwitcher.switchScene("MainMenu.fxml"));
    }

    private void showMustChoose(){

        Alert mustChoose = new Alert(AlertType.WARNING, "You must choose the two image before going to the next step 🗿👍", ButtonType.OK);
        mustChoose.setHeaderText("Something appends ;)");
        mustChoose.showAndWait();
    }
}
