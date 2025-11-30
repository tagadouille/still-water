package com.app.main.controller;

import java.util.HashSet;
import java.util.Set;

import com.app.main.model.core.Team;
import com.app.main.util.Controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

//! Pas encore sur sur de l'implémentation, peut être qu'on peut faire plus simple avec moins d'attribus..
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
    public final double speed = 1.5;

    private KeyboardController(Canvas canva, Team team, int Mapwidth, int Mapheight, double Posx, double Posy, KeyCode up, KeyCode down, KeyCode right, KeyCode left){
        this.team = team;

        this.Posx = Posx;
        this.Posy = Posy;

        this.mapwidth = Mapwidth;
        this.mapheight = Mapheight;

        this.UP = up;
        this.DOWN = down;
        this.RIGHT = right;
        this.LEFT = left;

        canva.setOnKeyPressed(e -> keylist.add(e.getCode()));
        canva.setOnKeyReleased(e -> keylist.remove(e.getCode()));
    }

    public static KeyboardController createKeyboardController(Canvas canva, Team team, int mapwidth, int mapheight, int posx, int posy, KeyCode up, KeyCode down, KeyCode right, KeyCode left){
        return new KeyboardController(canva, team, mapwidth, mapheight, posx, posy, up, down, right, left);
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
        if(keylist.contains(UP)) this.Posx -= speed;

        this.Posx = Math.max(0, Math.min(Posx, mapwidth - 1));
        this.Posy = Math.max(0, Math.min(Posy, mapheight - 1));

        team.setTarget((int) this.Posx, (int) this.Posy);
    }
    
}
