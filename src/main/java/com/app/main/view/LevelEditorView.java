package com.app.main.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class LevelEditorView extends Scene {

    private static final HBox root = new HBox();

    private Button chooseBackgroundBtn;
    private Button chooseObstacleBtn;

    private ImageView backgroundPreview;
    private ImageView obstaclePreview;

    private Button nextBtn;
    private Button cancelBtn;

    public LevelEditorView() {

        super(root);
        root.setAlignment(Pos.CENTER);
        root.setPrefHeight(720);
        root.setPrefWidth(1280);

        // Initialisation of the differents containers :
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
}
