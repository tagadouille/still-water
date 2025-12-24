package com.app.main.controller.levelEditor;

import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.view.levelEditor.LevelEditorView;
import com.app.main.view.levelEditor.ObstacleEditorView;
import com.app.main.view.levelEditor.TeamEditorView;

/**
 * The ObstacleEditorController define the behavior of each interactable
 * component of the ObstacleEditorView
 * 
 * @see ObstacleEditorView
 * @author Dai Elias
 */
final class ObstacleEditorController {
    
    private final ObstacleEditorView obstacleEditorView;
    private final FileWrapper fileWrapper;

    private boolean[][] obstacles;

    private ObstacleEditorController(ObstacleEditorView obstacleEditorView, FileWrapper fileWrapper){
        this.obstacleEditorView = obstacleEditorView;
        this.fileWrapper = fileWrapper;

        buttonBehavior();
        sliderBehavior();
        previewInit();
    }

    /**
     * The static fabric of the class for creating a new instance of the class
     * @param obstacleEditorView the ObstacleEditorView
     * @param fileWrapper the fileWrapper that contains the obstacle map and the background of the level
     * @return a new instance of the class
     */
    public static ObstacleEditorController buildEditorController(ObstacleEditorView obstacleEditorView, FileWrapper fileWrapper){

        // Verification of the validity of the parameters
        if(obstacleEditorView == null){
            throw new IllegalArgumentException("The ObstacleEditorView parameter can't be null");
        }

        FileWrapper.verifyFileWrapper(fileWrapper);

        return new ObstacleEditorController(obstacleEditorView, fileWrapper);
    }

    /* Method for setting the behavior of the components */

    private void buttonBehavior(){

        obstacleEditorView.getBackButton().setOnAction((e) -> {
            LevelEditorView editor = new LevelEditorView();
            LevelEditorController.buildLevelEditorController(editor);
            MenuSwitcher.switchScene(editor);
        });

        obstacleEditorView.getNextButton().setOnAction((e) -> {
            TeamEditorView editorView = new TeamEditorView();
            TeamEditorController.buildEditorController(editorView, fileWrapper, obstacles);
            MenuSwitcher.switchScene(editorView);
        });
    }

    private void previewInit(){
        obstacleEditorView.getObstaclePreview().setObstacles(fileWrapper.getObstacleImage());
        obstacleEditorView.getObstaclePreview().updateObstacles(0);
        obstacles = obstacleEditorView.getObstaclePreview().getObstacles();
    }

    private void sliderBehavior(){
        obstacleEditorView.getThresholdSlider().valueProperty().addListener((obs, oldV, newV) -> 
        {
            int threshold = newV.intValue();
            obstacleEditorView.getObstaclePreview().updateObstacles(threshold);
            obstacles = obstacleEditorView.getObstaclePreview().getObstacles();
        });
    }
}
