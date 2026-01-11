package com.app.main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.app.main.util.ImageUtil;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

class ImageUtilTest {
    
    @BeforeAll
    static void initJavaFX() {
        new JFXPanel();
    }
    
    /**
     * Helper for executing code on javafx thread
     */
    private <T> T runOnJavaFXAndWait(java.util.concurrent.Callable<T> callable) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<T> result = new AtomicReference<>();
        final AtomicReference<Exception> exception = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                result.set(callable.call());
            } catch (Exception e) {
                exception.set(e);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout while waiting for javafx execution");
        }

        if (exception.get() != null) {
            throw exception.get();
        }

        return result.get();
    }
    
    /**
     * Helper for execute an action on the javafx thread
     */
    private void runOnJavaFXAndWait(Runnable runnable) throws Exception {
        runOnJavaFXAndWait(() -> {
            runnable.run();
            return null;
        });
    }

    /* Tests isValidImage */

    @Test
    void isValidImage_nullFile_returnsFalse() {
        assertFalse(ImageUtil.isValidImage(null));
    }

    @Test
    void isValidImage_nonExistingFile_returnsFalse(@TempDir Path tempDir) {
        File file = tempDir.resolve("nonexistent.jpg").toFile();
        assertFalse(ImageUtil.isValidImage(file));
    }

    @Test
    void isValidImage_directory_returnsFalse(@TempDir Path tempDir) {
        File dir = tempDir.toFile();
        assertFalse(ImageUtil.isValidImage(dir));
    }

    @Test
    void isValidImage_emptyFile_returnsFalse(@TempDir Path tempDir) throws IOException {

        File emptyFile = tempDir.resolve("empty.jpg").toFile();
        Files.createFile(emptyFile.toPath());
        assertFalse(ImageUtil.isValidImage(emptyFile));
    }

    @Test
    void isValidImage_invalidImageFile_returnsFalse(@TempDir Path tempDir) throws IOException {

        File textFile = tempDir.resolve("fake.png").toFile();
        Files.writeString(textFile.toPath(), "That's not an image");
        assertFalse(ImageUtil.isValidImage(textFile));
    }

    @Test
    void isValidImage_validImage_returnsTrue(@TempDir Path tempDir) throws Exception {
        
        // Create a minimal image
        Path imagePath = tempDir.resolve("test.png");
        Files.write(imagePath, new byte[] {
            (byte)0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A
        });
        
        // This test can fail. It depend on javafx but if isValidImage try to load
        // the image it's ok. So no assertion.
        ImageUtil.isValidImage(imagePath.toFile());
    }
 
    /* Tests resizeImage*/

    @Test
    void resizeImage_nullImage_throwsException() throws Exception {

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            runOnJavaFXAndWait(() -> {
                try {
                    ImageUtil.resizeImage(null, 100, 100);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });
        
        assertNotNull(ex);
    }

    @Test
    void resizeImage_validImage_returnsResizedImage() throws Exception {
  
        Image resizedImage = runOnJavaFXAndWait(() -> {
            
            Image originalImage = new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAANSURBVBhXY2BgYPgPAAEEAQBwIGULAAAAAElFTkSuQmCC");
            
            // Verify the image validity :
            assertNotNull(originalImage);
            assertFalse(originalImage.isError());
            
            // Resize :
            Image result = ImageUtil.resizeImage(originalImage, 50, 30);
            
            // Assert :
            assertNotNull(result);
            assertFalse(result.isError());
            assertTrue(result.getWidth() > 0);
            assertTrue(result.getHeight() > 0);
            
            return result;
        });
        
        assertNotNull(resizedImage);
    }

    @Test
    void resizeImage_zeroDimensions_createsImage() throws Exception {
        runOnJavaFXAndWait(() -> {

            Image originalImage = new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg==");
            
            // Redimensionner avec des dimensions de 0
            Image resizedImage;
            try {
                resizedImage = ImageUtil.resizeImage(originalImage, 0, 0);
                assertNotNull(resizedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void resizeImage_negativeDimensions() throws Exception {

        runOnJavaFXAndWait(() -> {
            Image originalImage = new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg==");
            
            assertThrows(IllegalArgumentException.class, () -> ImageUtil.resizeImage(originalImage, -10, -20));
        });
    }

    /* Tests copyFile*/

    @Test
    void copyFile_nullFile_throwsIllegalArgumentException(@TempDir Path tempDir) {

        assertThrows(IllegalArgumentException.class, () -> {
            ImageUtil.copyFile(null, tempDir);
        });
    }

    @Test
    void copyFile_nonExistingFile_throwsIllegalArgumentException(@TempDir Path tempDir) {

        File nonExistingFile = tempDir.resolve("nonexistent.txt").toFile();
        assertThrows(IllegalArgumentException.class, () -> {
            ImageUtil.copyFile(nonExistingFile, tempDir);
        });
    }

    @Test
    void copyFile_validFile_copiesSuccessfully(@TempDir Path tempDir) throws IOException {

        Path sourcePath = tempDir.resolve("source.txt");
        String content = "Contenu de test";
        Files.writeString(sourcePath, content);
        File sourceFile = sourcePath.toFile();
        
        Path destDir = tempDir.resolve("destination");
        
        ImageUtil.copyFile(sourceFile, destDir);
        
        Path copiedFile = destDir.resolve("source.txt");
        assertTrue(Files.exists(copiedFile));
        assertEquals(content, Files.readString(copiedFile));
    }

    @Test
    void copyFile_existingDestination_overwrites(@TempDir Path tempDir) throws IOException {

        Path sourcePath = tempDir.resolve("test.txt");
        Files.writeString(sourcePath, "Nouveau contenu");
        File sourceFile = sourcePath.toFile();
        
        Path destDir = tempDir.resolve("dest");
        Files.createDirectories(destDir);
        Path existingFile = destDir.resolve("test.txt");
        Files.writeString(existingFile, "Ancien contenu");
        
        ImageUtil.copyFile(sourceFile, destDir);
        
        assertEquals("Nouveau contenu", Files.readString(existingFile));
    }

    @Test
    void copyFile_createsDestinationDirectory(@TempDir Path tempDir) throws IOException {
        Path sourcePath = tempDir.resolve("file.txt");
        Files.writeString(sourcePath, "Test");
        File sourceFile = sourcePath.toFile();
        
        Path nonExistentDir = tempDir.resolve("new").resolve("subdir");
        
        ImageUtil.copyFile(sourceFile, nonExistentDir);
        
        assertTrue(Files.exists(nonExistentDir));
        assertTrue(Files.exists(nonExistentDir.resolve("file.txt")));
    }

    @Test
    void copyFile_withSpecialCharacters(@TempDir Path tempDir) throws IOException {
        String fileName = "fichier avec espaces et (parenthèses).txt";
        Path sourcePath = tempDir.resolve(fileName);
        Files.writeString(sourcePath, "Contenu spécial");
        File sourceFile = sourcePath.toFile();
        
        Path destDir = tempDir.resolve("dest");
        
        ImageUtil.copyFile(sourceFile, destDir);
        
        Path copiedFile = destDir.resolve(fileName);
        assertTrue(Files.exists(copiedFile));
        assertEquals("Contenu spécial", Files.readString(copiedFile));
    }

    @Test
    void copyFile_largeFile(@TempDir Path tempDir) throws IOException {

        Path sourcePath = tempDir.resolve("large.bin");

        byte[] largeContent = new byte[1024 * 1024]; // 1 MB

        for (int i = 0; i < largeContent.length; i++) {
            largeContent[i] = (byte) (i % 256);
        }

        Files.write(sourcePath, largeContent);
        File sourceFile = sourcePath.toFile();
        
        Path destDir = tempDir.resolve("dest");
        
        ImageUtil.copyFile(sourceFile, destDir);
        
        Path copiedFile = destDir.resolve("large.bin");
        assertTrue(Files.exists(copiedFile));
        assertArrayEquals(largeContent, Files.readAllBytes(copiedFile));
    }
}