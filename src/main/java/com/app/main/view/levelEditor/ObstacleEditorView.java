package com.app.main.view.levelEditor;

import com.app.main.util.mapGenerator.MapGenerator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The ObstacleEditorView is the second page of the level editor. 
 * It allows the user to edit the obstacle image that he choose before.
 * He have to use a slider to determine which pixel of the image
 * will represents an obstacles according to the brightness of the pixel.
 * The user can see a preview with the ObstaclePreview Canvas.
 * 
 * @see MapGenerator
 * @see ObstaclePreview
 * @author Dai Elias
 */
public final class ObstacleEditorView extends Scene implements LevelEditorConstant{

    private HBox root;
    
    private ObstaclePreview obstaclePreview;

    private Slider thresholdSlider;
    private Button backButton;
    private Button nextButton;

    /**
    * The construtor of the class that initialize
    * the components and do the layout of the UI
    */
    public ObstacleEditorView() {
        super(new HBox());

        if(this.getRoot() instanceof HBox){
            root = (HBox) this.getRoot();
            root.setAlignment(Pos.CENTER);
            root.setPrefHeight(720);
            root.setPrefWidth(1280);
            root.getStyleClass().add("levelEdit");
        }

        // Initialization of the differents containers : 
        VBox mainVBox = new VBox();
        mainVBox.setAlignment(Pos.TOP_CENTER);
        mainVBox.setPrefSize(657, 720);
        mainVBox.setSpacing(10);

        Text title = new Text("Edit the obstacles of the map");
        title.setFont(Font.font("System Bold", 30));
        title.setFill(Color.WHITE);

        obstaclePreview = new ObstaclePreview();

        // Threshold :
        Text thresholdText = new Text("Obstacles threshold :");
        thresholdText.setFont(Font.font(25));
        thresholdText.setFill(Color.WHITE);

        thresholdInit();

        // Buttons :
        HBox buttonBox = buttonInit();
        
        mainVBox.getChildren().addAll(
                title,
                obstaclePreview,
                thresholdText,
                thresholdSlider,
                buttonBox
        );

        root.getChildren().add(mainVBox);
    }

    /* Some helpers for initializing the components */

    private void thresholdInit(){

        thresholdSlider = new Slider();
        thresholdSlider.setPrefSize(658, 71);
        thresholdSlider.setBlockIncrement(5);
        thresholdSlider.setShowTickMarks(true);
        thresholdSlider.setShowTickLabels(true);

        thresholdSlider.setMin(0);
        thresholdSlider.setMax(100);
    }

    private HBox buttonInit(){

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        backButton = new Button("Go Back");
        backButton.setFont(BUTTON_FONT);

        nextButton = new Button("Next");
        nextButton.setFont(BUTTON_FONT);

        buttonBox.getChildren().addAll(backButton, nextButton);

        return buttonBox;
    }

    /* Getters */

    public ObstaclePreview getObstaclePreview() {
        return obstaclePreview;
    }

    public Slider getThresholdSlider() {
        return thresholdSlider;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getNextButton() {
        return nextButton;
    }
}

