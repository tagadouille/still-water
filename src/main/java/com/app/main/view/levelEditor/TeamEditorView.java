package com.app.main.view.levelEditor;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class TeamEditorView extends Scene {

    private HBox root;
    
    private Button placeTeamBtn;
    private Button goBackBtn;

    private TeamCanvas teamCanvas;

    private EditTeamBox editTeamBox;

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

    public class EditTeamBox extends VBox{

        private Slider teamSlider;
        private Button removeTeamBtn;
        private Button verifyBtn;

        private TextField fileNameField;
        private Button saveBtn;

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
                    verifyBtn,
                    saveBox,
                    warningText
            );
        }

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

        public Button getVerifyBtn() {
            return verifyBtn;
        }

        private void editPartInit(){

            teamSlider = new Slider();
            teamSlider.setShowTickMarks(true);

            removeTeamBtn = new Button("Remove the selected team");
            removeTeamBtn.setFont(Font.font("Rubik Bold Italic", 25));

            verifyBtn = new Button("Verify");
            verifyBtn.setFont(Font.font("Lucida Console", 25));
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
