package com.app.main.controller.playercontroller;

import com.app.main.model.GameManager;
import com.app.main.model.core.Team;
import com.app.main.util.Controller;
import com.app.main.view.GameScene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public final class MouseController implements Controller{

    public Team team;
    public double mousex, mousey;

    private MouseController(Team team){
        this.team = team;
    }

    public static MouseController createMouseController(Team team){
        if(team == null){
            throw new IllegalArgumentException("The team can't be null");
        }
        return new MouseController(team);
    }
    
    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public void update() {}

    @Override
    public void setupInput(Scene scene, Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            this.mousex = e.getX();
            this.mousey = e.getY();

            if (team != null) {
                int targetX = (int) (this.mousex / GameScene.getxFactor());
                int targetY = (int) (this.mousey / GameScene.getyFactor());

                team.setTarget(targetX, targetY);
            }
        });
    }
}
