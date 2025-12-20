package com.app.main.controller.levelEditor;

import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.view.levelEditor.LevelEditorView;
import com.app.main.view.levelEditor.ObstacleEditorView;

public final class ObstacleEditorController {
    
    ObstacleEditorView obstacleEditorView;
    FileWrapper fileWrapper;

    private ObstacleEditorController(ObstacleEditorView obstacleEditorView, FileWrapper fileWrapper){
        this.obstacleEditorView = obstacleEditorView;
        this.fileWrapper = fileWrapper;

        buttonBehavior();
        sliderBehavior();
        previewInit();
    }

    public static ObstacleEditorController buildEditorController(ObstacleEditorView obstacleEditorView, FileWrapper fileWrapper){

        // Verification of the validity of the parameters
        if(obstacleEditorView == null){
            throw new IllegalArgumentException("The ObstacleEditorView parameter can't be null");
        }

        verifyFileWrapper(fileWrapper);

        return new ObstacleEditorController(obstacleEditorView, fileWrapper);
    }

    private static void verifyFileWrapper(FileWrapper fileWrapper){

        if(fileWrapper == null){
            throw new IllegalArgumentException("The FileWrapper parameter can't be null");
        }

        if(!LevelEditorController.isValidImage(fileWrapper.backgroundImage)){
            throw new IllegalArgumentException("The background image of the file wrapper is not a valid file");
        }

        if(!LevelEditorController.isValidImage(fileWrapper.obstacleImage)){
            throw new IllegalArgumentException("The obstacle image of the file wrapper is not a valid file");
        }
    }

    private void buttonBehavior(){

        obstacleEditorView.getBackButton().setOnAction((e) -> {
            LevelEditorView editor = new LevelEditorView();
            LevelEditorController controller = LevelEditorController.buildLevelEditorController(editor);
            MenuSwitcher.switchScene(editor);
        });

        obstacleEditorView.getNextButton().setOnAction((e) -> {
            //TODO
        });
    }

    private void previewInit(){
        obstacleEditorView.getObstaclePreview().setObstacles(fileWrapper.obstacleImage);
        obstacleEditorView.getObstaclePreview().updateObstacles(0);
    }

    private void sliderBehavior(){
        obstacleEditorView.getThresholdSlider().valueProperty().addListener((obs, oldV, newV) -> 
        {
            int threshold = newV.intValue();
            obstacleEditorView.getObstaclePreview().updateObstacles(threshold);
        });
    }
}
