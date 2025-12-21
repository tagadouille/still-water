package com.app.main.view.levelEditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
            this.sizeY = Math.min(TeamCanvas.WIDTH, Math.max(0, sizeY));
        }
    }

    public static final double WIDTH = 480;
    public static final double HEIGHT = 480;

    private final List<TeamRectangle> rectangles = new ArrayList<>();

    public TeamCanvas(){
        super(WIDTH, HEIGHT);
    }

    public List<TeamRectangle> getRectangles() {
        return rectangles;
    }

    public boolean addTeamRectangle(){

        if(com.app.main.model.core.Color.values().length <= rectangles.size()){
            return false;
        }

        Color tmp = ParticleView.getTeamColor(com.app.main.model.core.Color.values()[rectangles.size()]);
        
        Color color = new Color(tmp.getRed(), tmp.getGreen(), tmp.getBlue(), tmp.getOpacity() * 0.75);

        //TODO Algo de renvoie de taille de rectangle
        TeamRectangle rectangle = new TeamRectangle(0, 0, HEIGHT, BASELINE_OFFSET_SAME_AS_HEIGHT, color);
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

        for (TeamRectangle s : rectangles) {
            gc.setFill(s.color);
            gc.fillRect(s.x, s.y, s.sizeX, s.sizeY);
        }
    }
}
