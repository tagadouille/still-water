package com.app.main.model;

import java.util.List;

import com.app.main.model.core.Point;
import com.app.main.model.core.Color;

public class GameLevel {

    public final boolean[][] obstacles;

    public final int width;
    public final int height;

    public final List<TeamConfig> teamsInfo;

    public GameLevel(boolean[][] obstacles, List<TeamConfig> teamsInfo) {
        this.obstacles = obstacles;
        this.width = obstacles.length;
        this.height = obstacles[0].length;
        this.teamsInfo = teamsInfo;
    }

    public int getHeight() {
        return height;
    }
    public boolean[][] getObstacles() {
        return obstacles;
    }
    public List<TeamConfig> getTeamsInfo() {
        return teamsInfo;
    }
    public int getWidth() {
        return width;
    }

    public static class TeamConfig {
        public final Color color;
        public final Point[] spawnArea;

        public TeamConfig(Color color, Point[] spawnArea) {
            this.color = color;
            this.spawnArea = spawnArea;
        }
    }
}
