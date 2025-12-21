package com.app.main.controller.levelEditor;

import java.util.ArrayList;
import java.util.List;

import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.model.GameManager;
import com.app.main.view.levelEditor.ObstacleEditorView;
import com.app.main.view.levelEditor.TeamEditorView;
import com.app.main.view.levelEditor.TeamEditorView.EditTeamBox;

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

        this.rectangleSizePossibility = rectanglesForSurface(GameManager.NB_CELL);

        buttonBehavior();
        editTeamBoxBehavior();
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
            ObstacleEditorController obstacleEditorController = ObstacleEditorController.buildEditorController(obstacleEditorView, fileWrapper);

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

        teamBox.getVerifyBtn().setOnAction((e) -> {
            //TODO
        });

        //TODO teamBox.getTeamSlider()
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
    private static int[][] rectanglesForSurface(int surface) {

        if (surface <= 0) {
            throw new IllegalArgumentException("The surface must be a positive int");
        }

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
