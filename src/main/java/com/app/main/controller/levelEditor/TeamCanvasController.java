package com.app.main.controller.levelEditor;

import com.app.main.view.levelEditor.TeamCanvas;
import com.app.main.view.levelEditor.TeamCanvas.TeamRectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class TeamCanvasController {

    private final TeamCanvas teamCanvas;

    private TeamRectangle selectedRectangle = null;
    private double offsetX, offsetY;

    private final boolean[][] obstacles;

    private TeamCanvasController(TeamCanvas canvas, boolean[][] obstacles){

        this.teamCanvas = canvas;
        this.obstacles = obstacles;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dectection of the click on a team rectangle :
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            for (TeamRectangle s : canvas.getRectangles()) {
                if (e.getX() >= s.getX() && e.getX() <= s.getX() + s.getSizeX() &&
                    e.getY() >= s.getY() && e.getY() <= s.getY() + s.getSizeY()) {

                    selectedRectangle = s;
                    offsetX = e.getX() - s.getX();
                    offsetY = e.getY() - s.getY();
                    break;
                }
            }
        });

        // Team rectangle drag :
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (selectedRectangle != null) {
                selectedRectangle.setX(e.getX() - offsetX);
                selectedRectangle.setY(e.getY() - offsetY);
                canvas.draw(gc);
            }
        });

        // Mouse release :
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            selectedRectangle = null;
        });
    }

    public static TeamCanvasController buildCanvasController(TeamCanvas canvas, boolean[][] obstacles){

        if(canvas == null){
            throw new IllegalArgumentException("The TeamCanvas can't be null");
        }

        return new TeamCanvasController(canvas, obstacles);
    }

    public void addTeamRectangle(){

        if(!teamCanvas.addTeamRectangle()){
            Alert add = new Alert(AlertType.WARNING, "You can't add more teams 🤣🫵", ButtonType.CLOSE);
            add.setHeaderText("Something append 🥀💔");
            add.showAndWait();
        }
    }

    public void removeTeamRectangle(){
        teamCanvas.removeTeamRectangle();
    }

    public boolean verifyOverlapping(){

        //TODO

        // Team overlapping to obstacles : 

        // Team overlapping to other teams :
        return false;
    }
}
