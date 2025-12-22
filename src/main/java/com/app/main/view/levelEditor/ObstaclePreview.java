package com.app.main.view.levelEditor;

import com.app.main.util.mapGenerator.MapGenerator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ObstaclePreview extends Canvas{
    
    private boolean[][] obstacles;
    private int[][] grayMap;

    public ObstaclePreview(){
        super(480, 480);
    }

    public void setObstacles(Image obstacleImage){
        
        if(obstacleImage == null){
            throw new IllegalArgumentException("The obstacle image can't be null");
        }
        grayMap = MapGenerator.getGrayMap(obstacleImage);
    }

    public boolean[][] getObstacles() {
        return obstacles;
    }

    public void updateObstacles(int threshold){
        obstacles = MapGenerator.getLevelMap(grayMap, threshold);
        render(this.getGraphicsContext2D());
    }

    private void render(GraphicsContext gc){

        for (int i = 0; i < obstacles.length; i++) {
            for (int j = 0; j < obstacles[i].length; j++) {
                
                if(obstacles[i][j]){
                    gc.setFill(Color.BLACK);
                }
                else{
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(j, i, 1, 1);
            }
        }
    }
}
