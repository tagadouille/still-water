package com.app.main.view.levelEditor;

import java.io.File;
import java.io.IOException;

import com.app.main.controller.levelEditor.LevelEditorController;
import com.app.main.util.mapGenerator.MapGenerator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ObstaclePreview extends Canvas{
    
    private boolean[][] obstacles;
    private int[][] grayMap;

    public ObstaclePreview(){
        super(480, 480);
    }

    public void setObstacles(File obstacleImage){
        
        if(LevelEditorController.isValidImage(obstacleImage)){
            try{
                grayMap = MapGenerator.getGrayMap(obstacleImage.getAbsolutePath());
            }
            catch(IOException e){
                throw new IllegalArgumentException("The obstacle image is invalid");
            }
            
        }
        else{
            throw new IllegalArgumentException("The obstacle image is invalid");
        }
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
                gc.fillRect(i, j, 1, 1);
            }
        }
    }
}
