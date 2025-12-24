package com.app.main.view.levelEditor;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The TeamEditorView is the last page of the level editor that allows the user
 * to place the spawn point of each team and dimension it and save the level.
 * 
 * @author Dai Elias
 */
public final class TeamEditorView extends Scene {

    private HBox root;
    
    private Button placeTeamBtn;
    private Button goBackBtn;

    private TeamCanvas teamCanvas;

    private EditTeamBox editTeamBox;

    /**
    * The construtor of the class that initialize
    * the components and do the layout of the UI
    */
    public TeamEditorView(){
        
        super(new HBox());

        if(this.getRoot() instanceof HBox){
            root = (HBox) this.getRoot();
            root.setAlignment(Pos.CENTER);
            root.setPrefHeight(720);
            root.setPrefWidth(1280);
        }

        // Initialization of the differents panels :
        VBox leftVBox = initializeLeftVBox();
        editTeamBox = new EditTeamBox();

        root.getChildren().addAll(leftVBox, editTeamBox);
    }

    /* Helper for initialize the components */

    private VBox initializeLeftVBox(){

        VBox leftVBox = new VBox(5);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        leftVBox.setPrefSize(480, 720);

        Text leftTitle = new Text("Place each team spawn points");
        leftTitle.setFont(Font.font("System Bold", 28));

        teamCanvas = new TeamCanvas();

        placeTeamBtn = new Button("Place a team");
        placeTeamBtn.setFont(Font.font("Rubik Bold Italic", 25));

        goBackBtn = new Button("Go Back");
        goBackBtn.setFont(Font.font("Rubik Bold Italic", 25));

        leftVBox.getChildren().addAll(
                leftTitle,
                teamCanvas,
                placeTeamBtn,
                goBackBtn
        );

        return leftVBox;
    }

    /* Getters */

    public TeamCanvas getTeamCanvas() {
        return teamCanvas;
    }

    public Button getGoBackBtn() {
        return goBackBtn;
    }

    public Button getPlaceTeamBtn() {
        return placeTeamBtn;
    }

    public EditTeamBox getEditTeamBox() {
        return editTeamBox;
    }

    /**
     * Display an error Alert that informs the user
     * that the save of the level file is a failure
     */
    public static void showCannotSave(){
        Alert error = new Alert(AlertType.ERROR, "Can't save the file 🏗️💔", ButtonType.CLOSE);
        error.setHeaderText("An error occured 😱😨😭🙏");
        error.showAndWait();
    }

    /**
     * Display a warning Alert that inform the user that
     * the spawns points of the teams are invalid
     */
    public static void showTeamInvalid(){
        Alert warning = new Alert(
            AlertType.WARNING,
            "There must be at least 2 team spoint point. 🤓☝️ A team spawn point can't overlapp to another. And can't overlapp an obstacle",
            ButtonType.CLOSE);
        warning.setHeaderText("The spawn points of the team are incorrect 💀✌️");
        warning.showAndWait();
    }

    /**
     * Display a warning Alert that inform the user that
     * the filename that he entered is not correct
     */
    public static void showFileNameIncorrect(){
        Alert warning = new Alert(AlertType.WARNING, "The filename that you entered is not correct 💀👍💔", ButtonType.CLOSE);

        warning.showAndWait();
    }

    /**
     * Display a information Alert that inform the user
     * that the save is successful
     */
    public static void showSaveSuccess(){
        Alert finish = new Alert(AlertType.INFORMATION, "You're level has been save !🔥🔥🔥 Be proud 🗿✌️", ButtonType.YES);
        finish.setHeaderText("The save is succesful ! 😎✌️");
        finish.showAndWait();
    }

    /**
     * The EditTeamBox represents the right panel of the TeamEditorView.
     * In this panel there are the components for edit a team spawn point and save the level.
     * 
     * @see TeamEditorView
     * @author Dai Elias
     */
    public class EditTeamBox extends VBox{

        private Slider teamSlider;
        private Button removeTeamBtn;

        private TextField fileNameField;
        private Button saveBtn;

        /**
        * The construtor of the class that initialize
        * the components and do the layout of the UI
        */
        public EditTeamBox(){
            
            super(15);
            this.setAlignment(Pos.CENTER);
            this.setPrefSize(480, 720);

            Text rightTitle = new Text("Edit the selected spawn point :");
            rightTitle.setFont(Font.font("System Bold", 28));

            editPartInit();

            // ===== Save area =====
            HBox saveBox = saveAreaInit();

            Text warningText = new Text(
                    "Save only if you click on \"verify\" and if you are sure of what you did"
            );
            warningText.setFont(Font.font("System Italic", 16));

            this.getChildren().addAll(
                    rightTitle,
                    teamSlider,
                    removeTeamBtn,
                    saveBox,
                    warningText
            );
        }

        /* Getters */

        public Slider getTeamSlider() {
            return teamSlider;
        }

        public Button getSaveBtn() {
            return saveBtn;
        }

        public TextField getFileNameField() {
            return fileNameField;
        }

        public Button getRemoveTeamBtn() {
            return removeTeamBtn;
        }

        /* Helpers for initialize the components of the UI */

        private void editPartInit(){

            teamSlider = new Slider();
            teamSlider.setShowTickMarks(true);

            removeTeamBtn = new Button("Remove the selected team");
            removeTeamBtn.setFont(Font.font("Rubik Bold Italic", 25));
        }

        private HBox saveAreaInit(){

            HBox saveBox = new HBox(10);
            saveBox.setAlignment(Pos.CENTER);

            fileNameField = new TextField();
            fileNameField.setPromptText("Enter the name of the file");
            fileNameField.setFont(Font.font(21));

            saveBtn = new Button("Save");
            saveBtn.setFont(Font.font("Rubik Bold Italic", 25));

            saveBox.getChildren().addAll(fileNameField, saveBtn);

            return saveBox;
        }
    }
}
