package com.app.main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The ImageUtil is an utilitary class that has some
 * methods for images.
 * 
 * @author Dai Elias
 */
public final class ImageUtil {
    
    /**
     * Inform if the file is a valid javafx Image
     * @param file the file to test
     * @return true if it's the case, false if not
     */
    public static boolean isValidImage(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            Image image = new Image(fis);

            return !image.isError() && image.getWidth() > 0 && image.getHeight() > 0;

        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Method that can resize the given image
     * @param image the image to resize
     * @param width the new width
     * @param height the new heigth
     * @return the same image that was gived in parameter but with another size
     * @throws FileNotFoundException
     */
    public static Image resizeImage(Image image, double width, double height) throws FileNotFoundException {

        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be non-negative");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);

        return imageView.snapshot(null, null);
    }

    /**
     * The method make a copy of a specified file and put it at the specified folder
     * @param file the file to copy
     * @param dstFolder the folder to put the copy
     * @throws IOException
     */
    public static void copyFile(File file, Path dstFolder) throws IOException {

        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("The source file doesn't exit");
        }

        // Creation if the folder if it doesn't exist
        Files.createDirectories(dstFolder);

        Path destination = dstFolder.resolve(file.getName());

        // Copy of the file
        Files.copy(
            file.toPath(),
            destination,
            StandardCopyOption.REPLACE_EXISTING
        );
    }
}
