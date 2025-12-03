package com.app.main.controller.playercontroller;

import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

//! Pas encore sur sur de l'implémentations..
public final class MouseController implements Controller{

    public Team team;
    public double mousex, mousey;

    private MouseController(Canvas canva, Team team){
        this.team = team;
        this.mousex = canva.getWidth() / GameManager.GRID_DIM;
        this.mousey = canva.getHeight() / GameManager.GRID_DIM;

        canva.setOnMouseMoved(this::move);
    }

    public static MouseController createMouseController(Canvas canva, Team team){
        return new MouseController(canva, team);
    }
    
    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public void update() {}

    public void move(MouseEvent e){
        if(team == null) return;

        int gx = (int) (e.getX() / mousex); 
        int gy = (int) (e.getY() / mousey);

        team.setTarget(gx, gy);
    }

}
