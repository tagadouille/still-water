package com.app.main.view.levelEditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import com.app.main.model.GameManager;
import com.app.main.view.ParticleView;

/**
 * The TeamCanvas represents the Canvas where the team spawn point will
 * be displayed.
 * 
 * @see TeamEditorView
 * @author Dai Elias
 */
public final class TeamCanvas extends Canvas {

    /**
     * The TeamRectangle class represents graphicallly the
     * spawn point of the team. The area of spawn.
     */
    public final static class TeamRectangle {

        private double x, y;
        private double sizeX;
        private double sizeY;

        private final Color color;

        private TeamRectangle(double x, double y, double sizeX, double sizeY, Color color){

            this.x = x;
            this.y = y;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.color = color;
        }

        /* Getters an setters */

        public double getSizeX() {
            return sizeX;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getSizeY() {
            return sizeY;
        }

        public void setX(double x) {
            this.x = Math.min(GameManager.GRID_DIM, Math.max(0, x));
        }

        public void setY(double y){
            this.y = Math.min(GameManager.GRID_DIM, Math.max(0, y));
        }

        public void setSizeX(double sizeX){
            this.sizeX = Math.min(GameManager.GRID_DIM, Math.max(0, sizeX));
        }

        public void setSizeY(double sizeY){
            this.sizeY = Math.min(GameManager.GRID_DIM, Math.max(0, sizeY));
        }

    }

    private final List<TeamRectangle> rectangles = new ArrayList<>();

    private Image background;

    /**
     * The constructor of the class that initialize the Canvas
     */
    public TeamCanvas(){
        super(GameManager.GRID_DIM, GameManager.GRID_DIM);
    }

    /**
     * @return a list of all the TeamRectangle that are displayed
     * on the Canvas
     */
    public List<TeamRectangle> getRectangles() {
        return rectangles;
    }

    /**
     * For initialize the background of the canvas which is the obstacle map
     * @param obstacles the obstacle map where true mean that there's an obstacle and false
     * that thre's not;
     */
    public void setBackground(boolean[][] obstacles){

        if (obstacles == null || obstacles.length == 0 || obstacles[0].length == 0) {
            throw new IllegalArgumentException("The array can't be empty");
        }

        int width = obstacles[0].length;

        WritableImage image = new WritableImage(width, obstacles.length);
        var pixelWriter = image.getPixelWriter();

        for (int i = 0; i < obstacles.length; i++) {

            if(obstacles[i].length != width){
                throw new IllegalArgumentException("All the row of the array must have the same length");
            }
            for (int j = 0; j < obstacles[i].length; j++) {

                Color color = null;

                if(obstacles[i][j]){
                    color = Color.BLACK;
                }
                else{
                    color = Color.WHITE;
                }
   
                pixelWriter.setColor(j, i, color);
            }
        }

        this.background = image;
        draw();
    }

    /**
     * This method if for adding a TeamRectangle to the canvas
     * @param sizeX the size of the rectangle on the x axis
     * @param sizeY the size of the rectangle on the y axis
     * @return true if the rectangle is add, false if not.
     * @see TeamRectangle
     */
    public boolean addTeamRectangle(int sizeX, int sizeY){

        if(sizeX * sizeY != GameManager.NB_CELL){
            throw new IllegalArgumentException("The surface of the rectangle must be equal to GameManager.NB_CELL");
        }

        if(com.app.main.model.core.Color.values().length - 1 < rectangles.size() + 1){
            return false;
        }

        Color tmp = ParticleView.getTeamColor(com.app.main.model.core.Color.values()[rectangles.size()]);
        
        Color color = new Color(tmp.getRed(), tmp.getGreen(), tmp.getBlue(), tmp.getOpacity() * 0.75);

        TeamRectangle rectangle = new TeamRectangle(GameManager.GRID_DIM/2, GameManager.GRID_DIM/2, sizeX, sizeY, color);
        rectangles.add(rectangle);
        draw();

        return true;
    }

    /**
     * Remove a TeamRectangle of the canvas.
     * @param teamRectangle the team rectangle to remove
     */
    public void removeTeamRectangle(TeamRectangle teamRectangle){

        if(rectangles.size() != 0 && teamRectangle != null){
            rectangles.remove(teamRectangle);
            draw();
        }
    }

    /**
     * For updating the canvas
     */
    public void draw() {

        GraphicsContext gc = this.getGraphicsContext2D();

        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        if(background != null){
            gc.drawImage(background, 0, 0, getWidth(), getHeight());
        }

        for (TeamRectangle s : rectangles) {
            gc.setFill(s.color);
            gc.fillRect(s.x, s.y, s.sizeX, s.sizeY);
        }
    }
}
