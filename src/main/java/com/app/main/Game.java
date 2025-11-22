package com.app.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {

    public static void main(String[] args) {
        //launch(args);
        System.out.println("Hello World!");
        System.exit(0);
    }
    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Game");
        stage.setScene(scene);
        stage.show();*/
    }
}
