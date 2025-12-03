package com.app.main.model.core;

import java.util.List;

public enum Direction {
    HAUT(-1, 0),
    BAS(1, 0),
    GAUCHE(0, -1),
    DROITE(0, 1),
    BASGAUCHE(1, -1),
    BASDROITE(1, 1),
    HAUTGAUCHE(-1, -1),
    HAUTDROITE(-1, 1);

    public final int x, y;
    
    private Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static final List<Direction> ALL = List.of(values());

    public Point applyTo(Point p) {
        return new Point(p.x() + this.x, p.y() + this.y);
    }
}
