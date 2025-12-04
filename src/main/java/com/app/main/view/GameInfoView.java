package com.app.main.view;

import com.app.main.util.Observable;
import com.app.main.util.Observer;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Classe étendant VBox permettant de représenter
 * la partie de la vue du jeu où sont affichés les
 * éléments relatif au déroulement de la partie 
 * (nombre de FPS, répartition des équipes, points gagnés, etc..)
 * @author Dai Elias
 */
public final class GameInfoView extends VBox implements Observer{

    /**
     * Pane that display the repartition of the teams forces
     */
    private VBox forcesRepartiton = new VBox();

    /**
     * Pane that display the number of fps, the timer
     * and the quit button
     */
    private HBox infoBox = new HBox();

    private Text fps = new Text("FPS : 0");
    private Text timer = new Text("0:00");

    private Button quitButton = new Button("Quit");

    /**
     * Constructeur de la classe permettant d'initialiser ses
     * principaux composants
     */
    public GameInfoView(int width){
        super();
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, getInsets())));
        this.setSpacing(10);
        HBox.setHgrow(this, Priority.ALWAYS);

        this.setPrefWidth(width);
        this.setMinWidth(width);
        this.setMaxWidth(width);
        
        this.initialiseInfoBox();

        this.getChildren().addAll(infoBox, forcesRepartiton);
    }

    public Button getQuitButton() {
        return quitButton;
    }

    private void initialiseInfoBox(){

        VBox textBox = new VBox(10);

        fps.setFont(new Font("comic sans ms", 30));
        fps.setFill(Color.WHITE);
        timer.setFont(fps.getFont());
        timer.setFill(Color.WHITE);

        textBox.getChildren().addAll(fps, timer);

        
        infoBox.getChildren().addAll(textBox, quitButton);
        
        quitButton.setMinSize(this.getWidth()/4, quitButton.getHeight());
        
        HBox.setHgrow(quitButton, Priority.ALWAYS);
    }

    private void updateForcesRepartition(double[] forces, Color[] teamColor){

        // If no repartition are displayed, create them
        if(forcesRepartiton.getChildren().size() == 0){

            for (int i = 0; i < forces.length; i++) {
                ProgressBar progressBar = new ProgressBar(forces[i]);
                progressBar.setStyle("-fx-accent: " + teamColor[i].toString().replace("0x", "#") + ";");

                forcesRepartiton.getChildren().add(progressBar);
                progressBar.setMaxWidth(this.getWidth());
                VBox.setVgrow(progressBar, Priority.ALWAYS);
            }
        }
        //Update them
        for (int i = 0; i < forces.length; i++) {
            ProgressBar progressBar = (ProgressBar) forcesRepartiton.getChildren().get(i);
            progressBar.setProgress(forces[i]);
        }
    }

    private void updateFps(int fps){
        this.fps.setText("FPS : " + fps);
    }

    private void updateTimer(String time){
        this.timer.setText(time);
    }

    @Override
    public void update(Observable o, Object arg, String action) {

        if(o instanceof GridView && action.equals("info")){
            GridView gridView = (GridView) o;

            double[] forces = gridView.getGameManager().getForces();
            Color[] teamColors = new Color[forces.length];

            for (int i = 0; i < teamColors.length; i++) {
                teamColors[i] = ParticleView.getTeamColor(gridView.getGameManager().getTeams()[i]);
            }
            updateForcesRepartition(forces, teamColors);
            updateFps(gridView.getCurrentFps());
            updateTimer(gridView.getTimer().getTimeRemaining());
        }
    }
}
