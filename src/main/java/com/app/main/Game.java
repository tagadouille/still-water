package com.app.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.app.main.audio.Audio;
import com.app.main.audio.GamePlaylist;
import com.app.main.audio.Playlist;

public final class Game extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    private static String css;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("MainMenu.fxml"));
        scene = new Scene(fxmlLoader.load());

        css = getClass().getResource("Style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage = stage;

        primaryStage.setTitle("Still Water??");
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();
        GamePlaylist.getPlaylist().play(0);
    }
    public static Scene getScene() {
        return scene;
    }
    public static void setScene(Scene scene) {
        Game.scene = scene;
        Game.scene.getStylesheets().add(css);
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
