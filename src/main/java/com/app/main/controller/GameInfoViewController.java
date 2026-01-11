package com.app.main.controller;

import com.app.main.Game;
import com.app.main.audio.GamePlaylist;
import com.app.main.controller.menu.MenuSwitcher;
import com.app.main.controller.menu.WinnerController;
import com.app.main.util.Observable;
import com.app.main.util.Observer;
import com.app.main.view.GameInfoView;
import com.app.main.view.GridView;
import com.app.main.view.ParticleView;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The GameInfoViewController is a class that implements Observer
 * and is the controller for the GameInfoView.
 * 
 * @author Dai Elias
 * @see GameInfoView
 * @see Observer 
 */
public class GameInfoViewController implements Observer{
    
    private GameInfoView gameInfoView;

    private GameInfoViewController(GameInfoView gameinfo){
        this.gameInfoView = gameinfo;

        // Quit button behavior :
        gameinfo.getQuitButton().setOnAction((e) -> {
            System.exit(0);
        });
    }

    /**
     * The static factory of the GameInfoViewController class that create a
     * new instance of the class
     * 
     * @param gameInfoView the GameInfoView that the instance will handle
     * @return a new instance of the class
     */
    public static GameInfoViewController creaInfoViewController(GameInfoView gameInfoView){

        if(gameInfoView == null){
            throw new IllegalArgumentException("The GameInfoView can't be null");
        }

        return new GameInfoViewController(gameInfoView);
    }

    @Override
    public void update(Observable o, Object arg, String action) {

        if(o instanceof GridView){
            GridView gridView = (GridView) o;

            // Update the informations that are displayed
            if(action.equals("info")){

                double[] forces = gridView.getGameManager().getForces();
                Color[] teamColors = new Color[forces.length];

                for (int i = 0; i < teamColors.length; i++) {
                    teamColors[i] = ParticleView.getTeamColor(gridView.getGameManager().getTeams()[i]);
                }

                // Updates :
                updateForcesRepartition(forces, teamColors);
                updateFps(gridView.getCurrentFps());
                updateTimer(gridView.getTimer().getTimeRemaining());
            }

            // Go to the winner pannel
            if(action.equals("winner")){
            
                // Displaying the screen of victory when a winner is choosen
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

    /* Helpers */

    private void updateFps(int fps){
        this.gameInfoView.getFps().setText("FPS : " + fps);
    }

    private void updateTimer(String time){
        this.gameInfoView.getTimer().setText(time);
    }

    private void updateForcesRepartition(double[] forces, Color[] teamColor){

        // If no repartition are displayed, create them
        if(gameInfoView.getForcesRepartiton().getChildren().size() == 0){

            for (int i = 0; i < forces.length; i++) {
                ProgressBar progressBar = new ProgressBar(forces[i]);
                progressBar.setMaxWidth(Double.MAX_VALUE);
                progressBar.setStyle("-fx-accent: " + teamColor[i].toString().replace("0x", "#") + ";");

                gameInfoView.getForcesRepartiton().getChildren().add(progressBar);
                VBox.setVgrow(progressBar, Priority.ALWAYS);
            }
        }
        //Update them
        for (int i = 0; i < forces.length; i++) {
            ProgressBar progressBar = (ProgressBar) gameInfoView.getForcesRepartiton().getChildren().get(i);
            progressBar.setProgress(forces[i]);
        }
    }
}
