package com.app.main.model;

import com.app.main.model.core.Color;
import com.app.main.model.core.Point;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameLevelTest {

    @Test
    void testGameLevelConstruction() {
        boolean[][] obs = new boolean[10][20];
        List<GameLevel.TeamConfig> configs = new ArrayList<>();
        
        Point[] spawn = {new Point(0,0), new Point(5,5)};
        configs.add(new GameLevel.TeamConfig(Color.RED, spawn, true));

        GameLevel level = new GameLevel(obs, configs, "background.png");

        assertEquals(10, level.getWidth());
        assertEquals(20, level.getHeight());
        assertEquals("background.png", level.backgroundImageFilename);
        assertEquals(1, level.getTeamsInfo().size());
        assertEquals(Color.RED, level.getTeamsInfo().get(0).color);
    }
}