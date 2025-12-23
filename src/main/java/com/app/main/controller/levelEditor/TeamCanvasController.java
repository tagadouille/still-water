package com.app.main.controller.levelEditor;

import java.util.ArrayList;
import java.util.List;

import com.app.main.model.GameLevel.TeamConfig;
import com.app.main.model.core.Color;
import com.app.main.model.core.Point;
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

        teamCanvas.setBackground(obstacles);

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

                // Determine if the position is legal
                if(
                    e.getX() - offsetX >= 0 && e.getX() - offsetX < canvas.getWidth() &&
                    e.getY() - offsetY >= 0 && e.getY() - offsetY < canvas.getHeight()
                ){
                    selectedRectangle.setX(e.getX() - offsetX);
                    selectedRectangle.setY(e.getY() - offsetY);
                    canvas.draw(gc);
                }
                
            }
        });
    }

    public static TeamCanvasController buildCanvasController(TeamCanvas canvas, boolean[][] obstacles){

        if(canvas == null){
            throw new IllegalArgumentException("The TeamCanvas can't be null");
        }

        return new TeamCanvasController(canvas, obstacles);
    }

    public void addTeamRectangle(){

        int[] size = TeamEditorController.rectanglesForSurface()[0];

        if(!teamCanvas.addTeamRectangle(size[0], size[1])){
            Alert add = new Alert(AlertType.WARNING, "You can't add more teams 🤣🫵", ButtonType.CLOSE);
            add.setHeaderText("Something append 🥀💔");
            add.showAndWait();
            return;
        }
        this.selectedRectangle = teamCanvas.getRectangles().getLast();
    }

    public void removeTeamRectangle(){
        teamCanvas.removeTeamRectangle();
    }

    public boolean verifyOverlapping() {

        // Team overlapping with obstacles
        for (TeamRectangle r : teamCanvas.getRectangles()) {

            int startY = (int) Math.floor(r.getY());
            int endY   = (int) Math.ceil(r.getY() + r.getSizeY());
            int startX = (int) Math.floor(r.getX());
            int endX   = (int) Math.ceil(r.getX() + r.getSizeX());

            for (int i = startY; i < endY; i++) {
                for (int j = startX; j < endX; j++) {

                    if (i < 0 || j < 0 || i >= obstacles.length || j >= obstacles[i].length) {
                        return false;
                    }

                    if (obstacles[i][j]) {
                        return false;
                    }
                }
            }
        }

        // Team overlapping with other teams
        var rects = teamCanvas.getRectangles();

        for (int i = 0; i < rects.size(); i++) {
            TeamRectangle a = rects.get(i);

            for (int j = i + 1; j < rects.size(); j++) {
                TeamRectangle b = rects.get(j);

                boolean overlap =
                        a.getX() < b.getX() + b.getSizeX() &&
                        a.getX() + a.getSizeX() > b.getX() &&
                        a.getY() < b.getY() + b.getSizeY() &&
                        a.getY() + a.getSizeY() > b.getY();

                if (overlap) {
                    return false;
                }
            }
        }
        return true;
    }

    public void updateRectangleSize(int sizeX, int sizeY){

        if(this.selectedRectangle != null){
            selectedRectangle.setSizeX(sizeX);
            selectedRectangle.setSizeY(sizeY);
            teamCanvas.draw(teamCanvas.getGraphicsContext2D());
        }
    }

    public List<TeamConfig> getTeamConfig(){

        List<TeamConfig> ret = new ArrayList<>();
        
        for (int i = 0; i < teamCanvas.getRectangles().size(); i++) {
            
            TeamRectangle rectangle = teamCanvas.getRectangles().get(i);

            // Definition of the spawn area
            Point[] spawnArea = new Point[2];

            spawnArea[0] = new Point((int) rectangle.getX(), (int) (rectangle.getY()));
            spawnArea[1] = new Point(
                (int) (rectangle.getX() + rectangle.getSizeX()),
                (int) (rectangle.getY() + rectangle.getSizeY())
            );

            ret.add(new TeamConfig(Color.values()[i], spawnArea));
        }

        return ret;
    }
}
