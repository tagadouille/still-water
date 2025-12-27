package com.app.main.controller.levelEditor;

import java.io.File;

import javafx.scene.image.Image;

/**
 * A wrapper for the file that has been choosed for 
 * the background image and the obstacle image
 * 
 * @author Dai Elias
 */
class FileWrapper{
    public File backgroundImage;
    public File obstacleImageFile;
    public Image obstacleImage;
    private int nbDef = 0;

    public int getNbDef() {
        return nbDef;
    }

    /**
     * Increment nbDef by 1
     */
    public void incrementNbDef() {
        this.nbDef++;
    }

    /**
     * Decrement nbDef by 1. The minumum is 0
     */
    public void decrementNbDef() {
        this.nbDef = Math.max(0, nbDef - 1);
    }

    /**
     * Inform if nbDef = 2. This mean that backgroundImage an obstacleImage are
     * correctly initialise
     * @return
     */
    public boolean allGood(){
        return nbDef == 2;
    }
}