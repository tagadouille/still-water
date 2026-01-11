package com.app.main.controller.levelEditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.app.main.Game;
import com.app.main.audio.GamePlaylist;
import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.controller.playercontroller.ControllerInit;
import com.app.main.model.GameLevel;
import com.app.main.model.GameManager;
import com.app.main.util.ImageUtil;
import com.app.main.util.Controller;
import com.app.main.util.GameLevelLoader;
import com.app.main.view.GameScene;
import com.app.main.view.levelEditor.LevelEditorView;
import com.app.main.view.levelEditor.LevelListView;
import com.app.main.view.levelEditor.ObstacleEditorView;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 * The LevelEditorController is basically the controller of
 * the LevelEditorView. It define the behavior of the buttons
 * of the UI
 * 
 * @see LevelEditorView
 * @author Dai Elias
 */
public final class LevelEditorController {
    
    private LevelEditorView levelEditorView;
    private FileWrapper fileWrapper = new FileWrapper();

    private LevelEditorController(LevelEditorView levelEditorView){
        this.levelEditorView = levelEditorView;

        // Definition of the behavior of each components :
        imageSelectBehavior();
        buttonBehavior();

        LevelListView list = new LevelListView();
        list.setPrefWidth(220);
        list.setOnLevelSelected(path -> {
            try {
                GameLevel gameLevel = GameLevelLoader.load(path.toString());

                GameManager gameManager = GameManager.createFromJSON(gameLevel);

                Controller[] controllers = ControllerInit.initializeControllers(gameLevel, gameManager.getTeams());

                System.out.println(gameLevel.backgroundImageFilename);

                Image background = new Image(Files.newInputStream(Paths.get("editorimages/"+ gameLevel.backgroundImageFilename)));

                GamePlaylist.playLevelAudio();

                MenuSwitcher.switchScene(
                    GameScene.buildGameScene(gameManager, controllers, background)
                );

            } catch (Exception e) {
                System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
                e.printStackTrace();
            }
        });
        levelEditorView.getRootContainer().getChildren().add(0, list);
    }

    /**
     * The static fabric of the class that initialize it components and set the behavior
     * of the button of the LevelEditorView
     * @param levelEditorView the view of the first page of the level editor
     * @return an instance of LevelEditorController
     */
    public static LevelEditorController buildLevelEditorController(LevelEditorView levelEditorView){

        if(levelEditorView == null){
            throw new IllegalArgumentException("The levelEditorView can't be null");
        }
        return new LevelEditorController(levelEditorView);
    }

    /* Setting the behavior of each button : */

    private void imageSelectBehavior(){

        FileChooser imageChooser = new FileChooser();
        
        levelEditorView.getChooseBackgroundBtn().setOnAction((e) -> 
        {
            File bg = imageChooser.showOpenDialog(Game.getPrimaryStage());

            // Test the validity
            if(!ImageUtil.isValidImage(bg)){
                if(bg != null){
                    LevelEditorView.showInvalidImage();
                }
            }
            else{
                try{
                    levelEditorView.getBackgroundPreview().setImage(new Image(new FileInputStream(bg)));

                    fileWrapper.setBackgroundImage(bg);
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
            if(!ImageUtil.isValidImage(obs)){
                if(obs != null){
                    LevelEditorView.showInvalidImage();
                }
            }
            else{
                try{
                    levelEditorView.getObstaclePreview().setImage(new Image(new FileInputStream(obs)));
                    fileWrapper.setObstacleImage(new Image(new FileInputStream(obs)));
                }
                catch(IOException exp){

                }
            }
        });
    }

    private void buttonBehavior(){

        levelEditorView.getNextBtn().setOnAction((e) -> {

            if(fileWrapper.allGood()){

                //Modify the background image and rediTODO Modifier le fichier image pour le truncate
                try{
                    fileWrapper.setObstacleImage(ImageUtil.resizeImage(fileWrapper.getObstacleImage(), GameManager.GRID_DIM, GameManager.GRID_DIM));
                }
                catch(IOException exp){
                    return;
                }

                ObstacleEditorView obstacleEditorView = new ObstacleEditorView();
                ObstacleEditorController.buildEditorController(obstacleEditorView, fileWrapper);

                MenuSwitcher.switchScene(obstacleEditorView);
            }
            else{
                LevelEditorView.showMustChoose();
            }
        });

        levelEditorView.getCancelBtn().setOnAction((e) -> MenuSwitcher.switchScene("MainMenu.fxml"));
    }
}
