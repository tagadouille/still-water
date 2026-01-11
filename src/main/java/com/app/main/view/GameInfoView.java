package com.app.main.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * GameInfoView is a class that extends VBox for representing
 * the part of the game view where the elements for the game sequence
 * (number of FPS, team repartition, etc..) are displayed
 * 
 * @author Dai Elias
 */
public final class GameInfoView extends VBox{

    /**
     * Pane that display the repartition of the teams forces
     */
    private VBox forcesRepartiton = new VBox();

    /**
     * Pane that display the number of fps, the timer
     * and the quit button
     */
    private HBox infoBox = new HBox();

    private Text fps = new Text("FPS : 0");
    private Text timer = new Text("0:00");

    private Button quitButton = new Button("Quit");

    /**
     * Constructor of the class for initialize the main components of the class
     * @param width the width of the GameInfoView
     */
    public GameInfoView(int width){
        super();
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, getInsets())));
        this.setSpacing(10);
        HBox.setHgrow(this, Priority.ALWAYS);

        this.setPrefWidth(width);
        this.setMinWidth(width);
        this.setMaxWidth(width);

        this.setSpacing(20);
        
        this.initialiseInfoBox();

        forcesRepartiton.setSpacing(10);

        ImageView img = new ImageView();

        try{
            img = new ImageView(new Image(Files.newInputStream(Paths.get("src/main/resources/com/app/image/stillWater.png"))));
        }catch(IOException e){
            
        }
        img.setFitWidth(width);
        img.setPreserveRatio(true);

        // For forcing the image to be at the bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(infoBox, forcesRepartiton, spacer, img);
    }

    private void initialiseInfoBox(){

        VBox textBox = new VBox(10);

        fps.setFont(new Font("comic sans ms", 30));
        fps.setFill(Color.WHITE);
        timer.setFont(fps.getFont());
        timer.setFill(Color.WHITE);

        textBox.getChildren().addAll(fps, timer);

        // For forcing the button to be at the left
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        quitButton.setPrefSize(100, 40);
        quitButton.setStyle("-fx-font-size: 22px;");

        infoBox.getChildren().addAll(textBox, spacer, quitButton);
    }

    /* Getters : */

    public Button getQuitButton() {
        return quitButton;
    }

    public Text getFps() {
        return fps;
    }

    public Text getTimer() {
        return timer;
    }

    public VBox getForcesRepartiton() {
        return forcesRepartiton;
    }
}
