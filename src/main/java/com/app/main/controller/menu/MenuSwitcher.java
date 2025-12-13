package com.app.main.controller.menu;

import java.io.IOException;

import com.app.main.Game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class MenuSwitcher {

    /**
     * For switching to a new scene.
     * It'll display an error if there's an error
     * @param fxmlFile the fxml file name to the scene to switch
     * @return the parent of the scene
     */
    public static FXMLLoader switchScene(String fxmlFile) {
    try {
        FXMLLoader loader = new FXMLLoader(Game.class.getResource(fxmlFile));
        Parent root = loader.load();

        Platform.runLater(() -> {
            Game.setScene(new Scene(root));
            Game.getPrimaryStage().setScene(Game.getScene());
        });

        return loader;
    }
    catch (IOException e) {
        Alert alert = new Alert(AlertType.ERROR, "The file " + fxmlFile + " doesn't exist 💀🙏.", ButtonType.CLOSE);
        alert.setHeaderText("Something went wrong..");
        alert.showAndWait();
        System.exit(0);
    }

    return null;
}


    public static void switchScene(Scene scene){
        if(scene != null){
            Platform.runLater(() -> {
                Game.setScene(scene);
                Game.getPrimaryStage().setScene(Game.getScene());
            });
        }else{
            throw new IllegalArgumentException("The scene can't be null");
        }
    }
}
