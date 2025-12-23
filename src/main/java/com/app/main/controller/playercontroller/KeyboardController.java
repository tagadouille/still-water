package com.app.main.controller.playercontroller;

import java.util.HashSet;
import java.util.Set;

import com.app.main.model.core.Team;
import com.app.main.util.Controller;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public final class KeyboardController implements Controller{

    public Team team;

    //Position du controller;
    public double Posx, Posy;

    //Limite de la map
    public final int mapwidth, mapheight;
    
    //Touche sur le clavier
    public final KeyCode UP, DOWN, RIGHT, LEFT;
    public final Set<KeyCode> keylist = new HashSet<>();

    //vitesse de mouvement
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

    public static KeyboardController createKeyboardController(Team team, int Mapwidth, int Mapheight, KeyCode up, KeyCode down, KeyCode right, KeyCode left){
        return new KeyboardController(team, Mapwidth, Mapheight, up, down, right, left);
    }

    @Override
    public void setupInput(Scene scene, Canvas canvas) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            keylist.add(e.getCode());
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            keylist.remove(e.getCode());
        });
    }

    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

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
