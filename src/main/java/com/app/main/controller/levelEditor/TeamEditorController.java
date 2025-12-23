package com.app.main.controller.levelEditor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.model.GameLevel;
import com.app.main.model.GameManager;
import com.app.main.util.mapGenerator.FileGenerator;
import com.app.main.view.levelEditor.ObstacleEditorView;
import com.app.main.view.levelEditor.TeamEditorView;
import com.app.main.view.levelEditor.TeamEditorView.EditTeamBox;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class TeamEditorController {
    
    private final TeamEditorView teamEditorView;
    private final TeamCanvasController teamCanvasController;

    private final FileWrapper fileWrapper;
    private final boolean[][] obstacles;

    private final int[][] rectangleSizePossibility;

    private TeamEditorController(TeamEditorView teamEditorView, FileWrapper fileWrapper, boolean[][] obstacles){

        this.fileWrapper = fileWrapper;
        this.teamEditorView = teamEditorView;
        this.teamCanvasController = TeamCanvasController.buildCanvasController(teamEditorView.getTeamCanvas(), obstacles);
        this.obstacles = obstacles;

        this.rectangleSizePossibility = rectanglesForSurface();

        buttonBehavior();
        editTeamBoxBehavior();
        saveBehavior();
    }

    public static TeamEditorController buildEditorController(TeamEditorView teamEditorView, FileWrapper fileWrapper, boolean[][] obstacles){

        if(teamEditorView == null){
            throw new IllegalArgumentException("TeamEditorView can't be null");
        }
        ObstacleEditorController.verifyFileWrapper(fileWrapper);

        if(obstacles == null){
            throw new IllegalArgumentException("The obstacles double array can't be null");
        }

        return new TeamEditorController(teamEditorView, fileWrapper, obstacles);
    }

    private void buttonBehavior(){

        teamEditorView.getGoBackBtn().setOnAction((e) -> {
            ObstacleEditorView obstacleEditorView = new ObstacleEditorView();
            ObstacleEditorController.buildEditorController(obstacleEditorView, fileWrapper);

            MenuSwitcher.switchScene(obstacleEditorView);
        });

        teamEditorView.getPlaceTeamBtn().setOnAction((e) -> {
            teamCanvasController.addTeamRectangle();
        });
    }

    private void editTeamBoxBehavior(){

        EditTeamBox teamBox = teamEditorView.getEditTeamBox();

        teamBox.getRemoveTeamBtn().setOnAction((e) -> {
            teamCanvasController.removeTeamRectangle();
        });

        teamBox.getTeamSlider().valueProperty().addListener((obs, oldV, newV) -> 
        {
            int possibilty = Math.min(rectangleSizePossibility.length - 1, newV.intValue());
            int[] size = rectangleSizePossibility[possibilty];

            teamCanvasController.updateRectangleSize(size[0], size[1]);
        });
    }

    private void saveBehavior(){

        teamEditorView.getEditTeamBox().getSaveBtn().setOnAction((e) -> {

            String filename = teamEditorView.getEditTeamBox().getFileNameField().getText();

            // Verify the validity of filename :
            if(filename == null || filename.isBlank() || filename.matches(".*[\\\\/:*?\"<>|].*")){

                Alert warning = new Alert(AlertType.WARNING, "The filename that you entered is not correct 💀👍💔", ButtonType.CLOSE);

                warning.showAndWait();
                return;
            }

            // Verification of the validity of the spawn point of each team : 
            if(!teamCanvasController.verifyOverlapping()){
                Alert warning = new Alert(
                    AlertType.WARNING,
                    "A team spawn point can't overlapp to another. And can't overlapp an obstacle",
                    ButtonType.CLOSE);
                warning.setHeaderText("The spawn points of the team are incorrect 💀✌️");
                warning.showAndWait();
                return;
            }
            
            // Save :
            GameLevel gameLevel = new GameLevel(obstacles, teamCanvasController.getTeamConfig());

            try{
                FileGenerator.createFileGenerator(gameLevel).createfile(filename);

                // Copy of the background level image at the path where the level were saved :
                copyFile(fileWrapper.backgroundImage, Path.of("levels")); //TODO changer le chemin
            }
            catch(IOException ex){
                Alert error = new Alert(AlertType.ERROR, "Can't save the file 🏗️💔", ButtonType.CLOSE);
                error.setHeaderText("An error occured 😱😨😭🙏");
                error.showAndWait();

                return;
            }

            Alert finish = new Alert(AlertType.INFORMATION, "You're level has been save !🔥🔥🔥 Be proud 🗿✌️", ButtonType.YES);
            finish.setHeaderText("The save is succesful ! 😎✌️");
            finish.showAndWait();
            MenuSwitcher.switchScene("MainMenu.fxml");
        });
    }

    //TODO Déplacer quelque part

    public static void copyFile(File file, Path dstFolder) throws IOException {

        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("The source file doesn't exit");
        }

        // Creation if the folder if it doesn't exist
        Files.createDirectories(dstFolder);

        Path destination = dstFolder.resolve(file.getName());

        // Copy of the file
        Files.copy(
            file.toPath(),
            destination,
            StandardCopyOption.REPLACE_EXISTING
        );
    }

    /**
     * Calculate all the possibilities of sizes for a
     * rectangle of a given surface
     * @param surface the surface of the rectangle
     * @return all the possibilities of sizes where each case is one possibilty
     * where the first element of the sub-array is the first side and the second
     * element is the other side.
     * 
     * <h4> Example :</h4>
     * 
     * <pre>
     int surface = 12;
     int[][] rectangles = rectanglesForSurface(surface);

     for (int[] r : rectangles) {
         System.out.println(r[0] + " x " + r[1]);
     }

     output : 
     1 x 12
     12 x 1
     2 x 6
     6 x 2
     3 x 4
     4 x 3
     * </pre>
     */
    static int[][] rectanglesForSurface() {

        int surface = GameManager.NB_CELL;

        List<int[]> result = new ArrayList<>();

        // Browse all the divisors of the surface :
        for (int i = 1; i * i <= surface; i++) {

            if (surface % i == 0) {
                int a = i;
                int b = surface / i;

                // Main orientation
                result.add(new int[]{a, b});

                // Rotation East/West vs North/South
                if (a != b) {
                    result.add(new int[]{b, a});
                }
            }
        }
        // Conversion in array of array
        return result.toArray(new int[result.size()][]);
    }
}
