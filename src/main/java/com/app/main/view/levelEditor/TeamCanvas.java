package com.app.main.view.levelEditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import com.app.main.view.ParticleView;

public final class TeamCanvas extends Canvas {

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
            this.x = Math.min(TeamCanvas.WIDTH, Math.max(0, x));
        }

        public void setY(double y){
            this.y = Math.min(TeamCanvas.HEIGHT, Math.max(0, y));
        }

        public void setSizeX(double sizeX){
            this.sizeX = Math.min(TeamCanvas.WIDTH, Math.max(0, sizeX));
        }

        public void setSizeY(double sizeY){
            this.sizeY = Math.min(TeamCanvas.HEIGHT, Math.max(0, sizeY));
        }

    }

    public static final double WIDTH = 480;
    public static final double HEIGHT = 480;

    private final List<TeamRectangle> rectangles = new ArrayList<>();

    private Image background;

    public TeamCanvas(){
        super(WIDTH, HEIGHT);
    }

    public List<TeamRectangle> getRectangles() {
        return rectangles;
    }

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
        draw(getGraphicsContext2D());
    }

    public boolean addTeamRectangle(int sizeX, int sizeY){

        if(com.app.main.model.core.Color.values().length <= rectangles.size()){
            return false;
        }

        Color tmp = ParticleView.getTeamColor(com.app.main.model.core.Color.values()[rectangles.size()]);
        
        Color color = new Color(tmp.getRed(), tmp.getGreen(), tmp.getBlue(), tmp.getOpacity() * 0.75);

        TeamRectangle rectangle = new TeamRectangle(0, 0, sizeX, sizeY, color);
        rectangles.add(rectangle);
        draw(getGraphicsContext2D());

        return true;
    }

    public void removeTeamRectangle(){

        if(rectangles.size() != 0){
            rectangles.remove(rectangles.size() - 1);
            draw(getGraphicsContext2D());
        }
    }

    public void draw(GraphicsContext gc) {
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
