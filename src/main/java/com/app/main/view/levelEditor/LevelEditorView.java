package com.app.main.view.levelEditor;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The LevelEditorView represents the first page of the level editor.
 * The purpose of it is to initialize the 2 file that are images that are
 * used for a level : the background and the obstacles map. The background is just
 * an image used for the background of the level and the obstacle one is for determine
 * where are the obstacles.
 * 
 * @author Dai Elias
 */
public final class LevelEditorView extends Scene {

    private Button chooseBackgroundBtn;
    private Button chooseObstacleBtn;

    private ImageView backgroundPreview;
    private ImageView obstaclePreview;

    private Button nextBtn;
    private Button cancelBtn;

    private HBox root;

    /**
     * The construtor of the class that initialize
     * the components and do the layout of the UI
     */
    public LevelEditorView() {

        super(new HBox());

        if(this.getRoot() instanceof HBox){
            root = (HBox) this.getRoot();
            root.setAlignment(Pos.CENTER);
            root.setPrefHeight(720);
            root.setPrefWidth(1280);
        }
        

        // Initialization of the differents containers :
        VBox mainVBox = new VBox(15);
        mainVBox.setAlignment(Pos.TOP_CENTER);
        mainVBox.setPrefSize(633, 720);

        VBox topVBox = new VBox(15);
        topVBox.setAlignment(Pos.TOP_CENTER);
        topVBox.setPrefSize(634, 252);

        // The title
        initTitlePane(topVBox);

        VBox bottomVBox = new VBox(10);
        bottomVBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomVBox.setPrefSize(634, 410);

        // Preview Pane :
        Text previewText = new Text("Preview");
        previewText.setFont(Font.font("Arial Bold", 32));

        HBox previewBox = initPreviewPane();

        // Buttons for canceling or go to the next step
        nextBtn = new Button("Next");
        nextBtn.setFont(Font.font(26));

        cancelBtn = new Button("Cancel");
        cancelBtn.setFont(Font.font(26));

        bottomVBox.getChildren().addAll(
                previewText,
                previewBox,
                nextBtn,
                cancelBtn
        );

        mainVBox.getChildren().addAll(topVBox, bottomVBox);
        root.getChildren().add(mainVBox);
    }

    /* Some helpers for initializing the components */

    private void initTitlePane(VBox topVBox){

        Text title = new Text("Level Editor");
        title.setFont(Font.font("Arial Bold", 45));

        Pane spacer = new Pane();
        spacer.setPrefSize(200, 200);

        chooseBackgroundBtn = new Button("Choose the background");
        chooseBackgroundBtn.setFont(Font.font("Rubik Bold Italic", 26));

        chooseObstacleBtn = new Button("Choose the obstacle map");
        chooseObstacleBtn.setFont(Font.font("Rubik Bold Italic", 26));

        topVBox.getChildren().addAll(
                title,
                spacer,
                chooseBackgroundBtn,
                chooseObstacleBtn
        );
    }

    private HBox initPreviewPane(){

        HBox previewBox = new HBox(15);
        previewBox.setAlignment(Pos.CENTER);

        backgroundPreview = new ImageView();
        backgroundPreview.setFitWidth(240);
        backgroundPreview.setFitHeight(240);
        backgroundPreview.setPreserveRatio(false);
        backgroundPreview.setPickOnBounds(true);

        obstaclePreview = new ImageView();
        obstaclePreview.setFitWidth(240);
        obstaclePreview.setFitHeight(240);
        obstaclePreview.setPreserveRatio(false);
        obstaclePreview.setPickOnBounds(true);

        previewBox.getChildren().addAll(backgroundPreview, obstaclePreview);

        return previewBox;
    }

    /* Getters */

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public ImageView getBackgroundPreview() {
        return backgroundPreview;
    }

    public ImageView getObstaclePreview() {
        return obstaclePreview;
    }

    public Button getNextBtn() {
        return nextBtn;
    }

    public Button getChooseBackgroundBtn() {
        return chooseBackgroundBtn;
    }

    public Button getChooseObstacleBtn() {
        return chooseObstacleBtn;
    }

    /**
     * Display a warning Alert that informs the user that he
     * doesn't choose 2 images.
     */
    public static void showMustChoose(){

        Alert mustChoose = new Alert(AlertType.WARNING, "You must choose the two image before going to the next step 🗿👍", ButtonType.OK);
        mustChoose.setHeaderText("Something happends ;)");
        mustChoose.showAndWait();
    }

    /**
     * Display an error Alert that informs the user that the
     * file that he choosed is not a valid image
     */
    public static void showInvalidImage(){

        Alert wrongImage = new Alert(Alert.AlertType.ERROR, "The image that has been choose is incorrect 💀🙏✌️🎋💔", ButtonType.OK);
        wrongImage.setHeaderText("☣️☣️☣️Something wents wrong..☣️☣️☣️");
        wrongImage.showAndWait();
        
    }
}
