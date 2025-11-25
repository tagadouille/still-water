package com.app.main.mapGenerator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestLevelMap {

    @Test
    public void testThresholdZero() {
        int[][] grayMap = {
                {255, 0},
                {25, 50}
        };

        assertArrayEquals(
            MapGenerator.getLevelMap(grayMap, 0),
            new int[][]{
                    {0, 0},
                    {0, 0}
            }
        );
    }

    @Test
    public void testThreshold50() {
        int[][] grayMap = {
                {255, 0},
                {25, 50}
        };

        assertArrayEquals(
            MapGenerator.getLevelMap(grayMap, 50),
            new int[][]{
                    {0, -1},
                    {-1, -1}
            }
        );
    }

    @Test
    public void testThreshold100() {
        int[][] grayMap = {
                {255, 0},
                {25, 50}
        };
        assertArrayEquals(
            MapGenerator.getLevelMap(grayMap, 100),
            new int[][]{
                    {0, -1},
                    {-1, -1}
            }
        );
    }

    @Test
    public void testSquareMap() {
        int[][] grayMap = {
                {255, 255, 255},
                {0, 25, 50},
                {100, 200, 255}
        };

        assertArrayEquals(
            MapGenerator.getLevelMap(grayMap, 30),
            new int[][]{
                    {0, 0, 0},
                    {-1, -1, -1},
                    {0, 0, 0}
            }
        );
    }

    @Test
    public void testIllegalThresholdLow() {
        int[][] grayMap = {
                {10, 20},
                {30, 40}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            MapGenerator.getLevelMap(grayMap, -1);
        });
    }

    @Test
    public void testIllegalThresholdHigh() {
        int[][] grayMap = {
                {10, 20},
                {30, 40}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            MapGenerator.getLevelMap(grayMap, 101);
        });
    }

    @Test
    public void testIllegalGrayValue() {
        int[][] grayMap = {
                {0, 256},   // <-- valeur illégale
                {50, 100}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            MapGenerator.getLevelMap(grayMap, 50);
        });
    }
}
