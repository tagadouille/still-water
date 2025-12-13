package com.app.main.controller;

import com.app.main.Game;
import com.app.main.audio.GamePlaylist;
import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.controller.menu.WinnerController;
import com.app.main.util.Observable;
import com.app.main.util.Observer;
import com.app.main.view.GameInfoView;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

public class GameInfoViewController implements Observer{
    
    GameInfoView gameInfoView;

    private GameInfoViewController(GameInfoView gameinfo){
        this.gameInfoView = gameinfo;

        gameinfo.getQuitButton().setOnAction((e) -> System.exit(0));
    }

    public static GameInfoViewController creaInfoViewController(GameInfoView gameInfoView){

        if(gameInfoView == null){
            throw new IllegalArgumentException("The GameInfoView can't be null");
        }

        return new GameInfoViewController(gameInfoView);
    }

    @Override
    public void update(Observable o, Object arg, String action) {
        if(action.equals("winner")){
            
            // Displaying the screen of victory
            if(arg instanceof com.app.main.model.core.Color){

                com.app.main.model.core.Color c = (com.app.main.model.core.Color) arg;

                GamePlaylist.getPlaylist().playOnlyOne(0, true);

                FXMLLoader loader = MenuSwitcher.switchScene("Winner.fxml");

                Object controller = loader.getController();

                if (controller instanceof WinnerController wc) {
                    Platform.runLater(() -> Game.getScene().getRoot().setStyle("-fx-background-color: " + c.toString().replace("0x", "#") + " !important;"));
                    Game.getScene().getRoot().requestFocus();
                    wc.updateWinner(c.toString());
                }
            }
        }
    }
}
