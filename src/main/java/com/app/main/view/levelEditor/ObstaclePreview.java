package com.app.main.view.levelEditor;

import com.app.main.model.GameManager;
import com.app.main.util.mapGenerator.MapGenerator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * The ObstaclePreview is the preview of the obstacle map for
 * allow the user to see and modify the obstacle map.
 * 
 * @see ObstacleEditorView
 * @author Dai Elias
 */
public final class ObstaclePreview extends Canvas{
    
    private boolean[][] obstacles;
    private int[][] grayMap;

    /**
     * The constructor of the class for initialize
     * the Canvas.
     */
    public ObstaclePreview(){
        super(GameManager.GRID_DIM, GameManager.GRID_DIM);
    }

    /**
     * A setter to change the obstacles map
     * @param obstacleImage an image that represents the obstacle map
     */
    public void setObstacles(Image obstacleImage){
        
        if(obstacleImage == null){
            throw new IllegalArgumentException("The obstacle image can't be null");
        }
        grayMap = MapGenerator.getGrayMap(obstacleImage);
    }

    public boolean[][] getObstacles() {
        return obstacles;
    }

    /**
     * This method will update the obstacles map according a threshold.
     * @param threshold the threshold that determine wich pixel is an obstacle or not
     * @see MapGenerator
     */
    public void updateObstacles(int threshold){
        obstacles = MapGenerator.getLevelMap(grayMap, threshold);
        render(this.getGraphicsContext2D());
    }

    /**
     * A Method for rendering the obstacles
     * @param gc the GraphicsContext of the class instance
     */
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
