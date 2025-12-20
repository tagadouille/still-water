package com.app.main.view.levelEditor;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TeamEditorView extends Scene{

    private HBox root;

    private Button placeTeamButton;
    private Button backButton;
    private TextField fileNameField;
    private Button saveButton;

    public TeamEditorView(){
        super(new HBox());

        if(this.getRoot() instanceof HBox){
            root = (HBox) this.getRoot();
            root.setAlignment(Pos.CENTER);
            root.setPrefHeight(720);
            root.setPrefWidth(1280);
        }

        // Initialization of the differents containers : 
        VBox mainVBox = new VBox(5);
        mainVBox.setAlignment(Pos.TOP_CENTER);
        mainVBox.setPrefSize(478, 720);

        Text title = new Text("Place each team spawn points");
        title.setFont(Font.font("System Bold", 28));

        Canvas canvas = new Canvas(480, 480); //TODO Team canva

        // Buttons
        buttonInit();

        // Save area
        HBox saveBox = saveInit();

        // ===== Assemble layout =====
        mainVBox.getChildren().addAll(
                title,
                canvas,
                placeTeamButton,
                backButton,
                saveBox
        );

        root.getChildren().add(mainVBox);
    }

    private void buttonInit(){
        placeTeamButton = new Button("Place a team");
        placeTeamButton.setFont(Font.font("Rubik Bold Italic", 25));

        backButton = new Button("Go Back");
        backButton.setFont(Font.font("Rubik Bold Italic", 25));
    }

    private HBox saveInit(){
        HBox saveBox = new HBox(10);
        saveBox.setAlignment(Pos.CENTER);

        fileNameField = new TextField();
        fileNameField.setPromptText("Enter the name of the file");
        fileNameField.setFont(Font.font(21));

        saveButton = new Button("Save");
        saveButton.setFont(Font.font("Rubik Bold Italic", 25));

        saveBox.getChildren().addAll(fileNameField, saveButton);

        return saveBox;
    }
}
