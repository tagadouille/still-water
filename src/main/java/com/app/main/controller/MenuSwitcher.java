package com.app.main.controller;

import java.io.IOException;

import com.app.main.Game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class MenuSwitcher {

    public static void switchScene(String fxmlFile){
        try{
            Game.setScene(new Scene(FXMLLoader.load(Game.class.getResource(fxmlFile))));
            Game.getPrimaryStage().setScene(Game.getScene());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            //Affichage d'une alerte pour avertir l'utilisateur
            Alert alert = new Alert(AlertType.ERROR, "The file " + fxmlFile + " doesn't exist 💀🙏.", ButtonType.CLOSE);
            alert.setHeaderText("Something wen't wrong..");
            alert.showAndWait();
            System.exit(0);
        }
    }

    public static void switchScene(Scene scene){
        if(scene != null){
            Game.setScene(scene);
            Game.getPrimaryStage().setScene(Game.getScene());
        }else{
            throw new IllegalArgumentException("The scene can't be null");
        }
    }
}
