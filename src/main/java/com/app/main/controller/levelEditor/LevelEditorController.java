package com.app.main.controller.levelEditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.app.main.Game;
import com.app.main.audio.GamePlaylist;
import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.controller.playercontroller.MouseController;
import com.app.main.controller.playercontroller.botController.BotController;
import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.view.GameScene;
import com.app.main.view.levelEditor.LevelEditorView;
import com.app.main.view.levelEditor.LevelListView;
import com.app.main.view.levelEditor.ObstacleEditorView;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class LevelEditorController {
    
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
                GameManager gameManager = GameManager.createFromJSON(path.toString());

                Team[] loadedTeams = gameManager.getTeams();
                int nbTeams = loadedTeams.length;

                Controller[] controllers = new Controller[nbTeams];

                controllers[0] = MouseController.createMouseController(loadedTeams[0]);

                for (int i = 1; i < nbTeams; i++) {
                    controllers[i] = new BotController(
                        gameManager.getWidth(),
                        gameManager.getHeight(),
                        loadedTeams[i]
                    );
                }

                GamePlaylist.playLevelAudio();

                MenuSwitcher.switchScene(
                    new GameScene(gameManager, controllers)
                );

            } catch (Exception e) {
                System.err.println("Erreur lors du chargement du niveau : " + e.getMessage());
                e.printStackTrace();
            }
        });
        levelEditorView.getRootContainer().getChildren().add(0, list);
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
                if(bg != null){
                    showInvalidImage();
                }
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
                if(obs != null){
                    showInvalidImage();
                }
                fileWrapper.decrementNbDef();
            }
            else{
                try{
                    levelEditorView.getObstaclePreview().setImage(new Image(new FileInputStream(obs)));
                    fileWrapper.incrementNbDef();
                    fileWrapper.obstacleImage = new Image(new FileInputStream(obs));
                    fileWrapper.obstacleImageFile = obs;
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

                //Modify the background image and rediTODO Modifier le fichier image pour le truncate
                try{
                    fileWrapper.obstacleImage = resizeImage(fileWrapper.obstacleImage, GameManager.GRID_DIM, GameManager.GRID_DIM);
                }
                catch(IOException exp){
                    return;
                }

                ObstacleEditorView obstacleEditorView = new ObstacleEditorView();
                ObstacleEditorController.buildEditorController(obstacleEditorView, fileWrapper);

                MenuSwitcher.switchScene(obstacleEditorView);
            }
            else{
                showMustChoose();
            }
        });

        levelEditorView.getCancelBtn().setOnAction((e) -> MenuSwitcher.switchScene("MainMenu.fxml"));
    }

    //TODO mettre ça autre part
    public static Image resizeImage(Image image, double width, double height) throws FileNotFoundException {

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);

        return imageView.snapshot(null, null);
    }


    private void showMustChoose(){

        Alert mustChoose = new Alert(AlertType.WARNING, "You must choose the two image before going to the next step 🗿👍", ButtonType.OK);
        mustChoose.setHeaderText("Something appends ;)");
        mustChoose.showAndWait();
    }
}
