package com.app.main.util.mapGenerator;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is for get a grayscale map of an image of generate a level map of the game.
 * @author Dai Elias
 */
public final class MapGenerator {

    private final Image img; //The image
    private final int width, height; //Image dimension

    /**
     * Constructor : load an image from a file.
     * @param imagePath the path to the image
     */
    private MapGenerator(String imagePath) throws IOException {
        BufferedImage buff = ImageIO.read(new File(imagePath));
        img = SwingFXUtils.toFXImage(buff, null);

        width = (int) img.getWidth();
        height = (int) img.getHeight();
    }

    /**
     * Return the array in density of gray of each pixels of an image
     * @param imagepath the path to the image
     * @return An array where each cell represents a pixel of the image. The integer values ​​are
        between 0 and 255, which correspond to the grayscale intensity values. The closer the value is
        to 255, the whiter the pixel, and the closer the value is to 0, the blacker the pixel.
     * @throws IOException
     */
    public static int[][] getGrayMap(String imagepath) throws IOException{
        MapGenerator mapGenerator = new MapGenerator(imagepath);
        return mapGenerator.getGrayMap();
    }

    private int[][] getGrayMap() {
        int[][] gray = new int[height][width];
        PixelReader reader = img.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int argb = reader.getArgb(x, y);

                int r = (argb >> 16) & 255;
                int b = (argb) & 255;
                int g = (argb >> 8) & 255;

                gray[y][x] = (b + g * 10 + r * 3) / 14;
            }
        }
        return gray;
    }
    /**
     * Based on a certain tolerance threshold, generates a map for a level, specifying empty cells
     * non-empty cells
     * @param grayMap the grayscale array of each pixel of an image
     * @param threshold the tolerance thresshold, the higher is, the more likely the cell is to be empty.
     * @return the array of the level map wher false is an empty tile and true a obstacle one
     */
    public static boolean[][] getLevelMap(int[][] grayMap, int threshold){
        // Verification of the validity of the arguments :
        if (grayMap == null || grayMap.length == 0){
            throw new IllegalArgumentException("grayMap must not be null or empty");
        }
    
        if(threshold < 0 || threshold > 100){
            throw new IllegalArgumentException("The threshold must be between 0 and 100");
        }

        boolean[][] levelMap = new boolean[grayMap.length][];

        for (int i = 0; i < grayMap.length; i++) {
            levelMap[i] = new boolean[grayMap[i].length];

            for (int j = 0; j < grayMap[i].length; j++) {
                if(grayMap[i][j] < 0 || grayMap[i][j] > 255){
                    throw new IllegalArgumentException("The grayMap have illegals values");
                }
                int grayIntensity = (grayMap[i][j]*100)/255;

                if(grayIntensity < threshold){
                    levelMap[i][j] = true;
                }else{
                    levelMap[i][j] = false;
                }
            }
        }
        return levelMap;
    }
}
