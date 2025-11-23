package com.app.main.mapGenerator;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Dai Elias
 */
public class MapGenerator {

    private final Image img; //The image
    private final int width, height; //Dimension of the image

    /**
     * Constructeur : charge une image depuis un fichier.
     * @param imagePath le chemin vers l'image
     */
    private MapGenerator(String imagePath) throws IOException {
        BufferedImage buff = ImageIO.read(new File(imagePath));
        img = SwingFXUtils.toFXImage(buff, null);

        width = (int) img.getWidth();
        height = (int) img.getHeight();
    }

    /**
     * Fonction renvoie le tableau en densité de gris de chaque pixel d'une image
     * @param imagepath le chemin vers l'image
     * @return un tableau où chaque case représente un pixel de l'image. Les valeurs des entiers sont
        comprises entre 0 et 255, qui correspondent aux valeurs d’intensité du gris. Plus la valeur est 
        proche de 255 et plus le pixel est blanc, et plus la valeur la valeur est proche de 0 et plus le pixel est noir.
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
     * En fonction d'un certain seuil de tolérance, génère la carte d'un niveau en spécifiant les cases vides
     * des cases non-vides
     * @param grayMap le tableau de densité de gris de chaque pixel d'une image
     * @param threshold le seuil de tolérance, plus il est haut et plus la case est suceptible d'être une case vide
     * @return le tableau de la carte du niveau où 0 est une case vide et -1 une case obstacle
     */
    public static int[][] getLevelMap(int[][] grayMap, int threshold){
        // Vérifications de validités des arguments : 
        if (grayMap == null || grayMap.length == 0){
            throw new IllegalArgumentException("grayMap must not be null or empty");
        }
    
        if(threshold < 0 || threshold > 100){
            throw new IllegalArgumentException("The threshold must be between 0 and 100");
        }

        int[][] levelMap = new int[grayMap.length][];

        for (int i = 0; i < grayMap.length; i++) {
            levelMap[i] = new int[grayMap[i].length];

            for (int j = 0; j < grayMap[i].length; j++) {
                if(grayMap[i][j] < 0 || grayMap[i][j] > 255){
                    throw new IllegalArgumentException("The grayMap have illegals values");
                }
                int grayIntensity = (grayMap[i][j]*100)/255;

                if(grayIntensity < threshold){
                    levelMap[i][j] = -1;
                }else{
                    levelMap[i][j] = 0;
                }
            }
        }
        return levelMap;
    }
}
