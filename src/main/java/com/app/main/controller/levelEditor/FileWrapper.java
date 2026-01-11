package com.app.main.controller.levelEditor;

import java.io.File;

import com.app.main.util.ImageUtil;

import javafx.scene.image.Image;

/**
 * A wrapper for the file that has been choosed for 
 * the background image and the obstacle image
 * 
 * @author Dai Elias
 */
class FileWrapper{

    private File backgroundImage;
    private Image obstacleImage;

    /* Getters and setters */
    File getBackgroundImage() {
        return backgroundImage;
    }

    Image getObstacleImage() {
        return obstacleImage;
    }

    void setBackgroundImage(File backgroundImage) {

        if(!ImageUtil.isValidImage(backgroundImage)){
            throw new IllegalArgumentException("The background image is not a valid file");
        }
        this.backgroundImage = backgroundImage;
    }

    void setObstacleImage(Image obstacleImage) {

        if(obstacleImage == null){
            throw new IllegalArgumentException("The obstacle image of the file wrapper is null");
        }
        this.obstacleImage = obstacleImage;
    }

    /**
     * Inform if backgroundImage and obstacleImage are
     * correctly initialize
     * @return
     */
    boolean allGood(){ 
        return ImageUtil.isValidImage(backgroundImage) && obstacleImage != null;
    }

    /**
     * Verify is the filewrapper is correct. So if the images are really
     * a correct image
     * @param fileWrapper the filewrapper
     */
    static void verifyFileWrapper(FileWrapper fileWrapper){

        if(fileWrapper == null){
            throw new IllegalArgumentException("The FileWrapper parameter can't be null");
        }

        if(!ImageUtil.isValidImage(fileWrapper.backgroundImage)){
            throw new IllegalArgumentException("The background image of the file wrapper is not a valid file");
        }

        if(fileWrapper.obstacleImage == null){
            throw new IllegalArgumentException("The obstacle image of the file wrapper is null");
        }
    }
}