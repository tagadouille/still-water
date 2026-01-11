package com.app.main.controller.playercontroller;

import java.util.HashSet;
import java.util.Set;

import com.app.main.model.core.Team;
import com.app.main.util.Controller;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Contrôleur basé sur le clavier pour déplacer une cible de {@link Team}.
 * <p>
 * Maintient un ensemble de touches actuellement pressées et met à jour une
 * position interne qui est ensuite transmise à la {@code Team} via
 * {@code team.setTarget(x,y)}.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public final class KeyboardController implements Controller{

    /** Équipe contrôlée. */
    public Team team;

    /** Position courante du contrôleur (coordonnées flottantes). */
    public double Posx, Posy;

    /** Limites de la carte (largeur/hauteur en unités de grille). */
    public final int mapwidth, mapheight;
    
    /** Touches de direction configurées. */
    public final KeyCode UP, DOWN, RIGHT, LEFT;
    /** Ensemble des touches actuellement pressées. */
    public final Set<KeyCode> keylist = new HashSet<>();

    /** Vitesse de déplacement appliquée à la position (unités par update). */
    public final double speed = 5;

    private KeyboardController(Team team, int Mapwidth, int Mapheight, KeyCode up, KeyCode down, KeyCode right, KeyCode left){
        this.team = team;

        this.mapwidth = Mapwidth;
        this.mapheight = Mapheight;

        this.Posx = Mapwidth/2.0;
        this.Posy = Mapheight/2.0;

        this.UP = up;
        this.DOWN = down;
        this.RIGHT = right;
        this.LEFT = left;
    }

    /**
     * Crée une instance de {@code KeyboardController} avec les touches et
     * dimensions fournies.
     */
    public static KeyboardController createKeyboardController(Team team, int Mapwidth, int Mapheight, KeyCode up, KeyCode down, KeyCode right, KeyCode left){
        return new KeyboardController(team, Mapwidth, Mapheight, up, down, right, left);
    }

    /**
     * Attache des handlers clavier à la scène pour maintenir la liste
     * {@link #keylist} des touches pressées.
     */
    @Override
    public void setupInput(Scene scene, Canvas canvas) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            keylist.add(e.getCode());
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            keylist.remove(e.getCode());
        });
    }

    /** Remplace l'équipe associée à ce contrôleur. */
    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Met à jour la position interne en fonction des touches pressées,
     * contraint la position aux limites puis notifie la {@code Team}.
     */
    @Override
    public void update() {
        if(team == null) return;

        if(keylist.contains(UP)) this.Posy -= speed;
        if(keylist.contains(DOWN)) this.Posy += speed;
        if(keylist.contains(RIGHT)) this.Posx += speed;
        if(keylist.contains(LEFT)) this.Posx -= speed;

        this.Posx = Math.max(0, Math.min(Posx, mapwidth - 1));
        this.Posy = Math.max(0, Math.min(Posy, mapheight - 1));

        team.setTarget((int) this.Posx, (int) this.Posy);
    }
    
}
