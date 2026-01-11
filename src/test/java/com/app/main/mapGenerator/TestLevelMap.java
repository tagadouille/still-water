package com.app.main.mapGenerator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.app.main.util.mapGenerator.MapGenerator;

public class TestLevelMap {

    @Test
    void testThresholdZero() {
        int[][] grayMap = {
                {255, 0},
                {25, 50}
        };

        assertArrayEquals(
            MapGenerator.getLevelMap(grayMap, 0),
            new boolean[][]{
                    {false, false},
                    {false, false}
            }
        );
    }

    @Test
    void testThreshold50() {
        int[][] grayMap = {
                {255, 0},
                {25, 50}
        };

        assertArrayEquals(
            MapGenerator.getLevelMap(grayMap, 50),
            new boolean[][]{
                    {false, true},
                    {true, true}
            }
        );
    }

    @Test
    void testThreshold100() {
        int[][] grayMap = {
                {255, 0},
                {25, 50}
        };
        assertArrayEquals(
            MapGenerator.getLevelMap(grayMap, 100),
            new boolean[][]{
                    {false, true},
                    {true, true}
            }
        );
    }

    @Test
    void testSquareMap() {
        int[][] grayMap = {
                {255, 255, 255},
                {0, 25, 50},
                {100, 200, 255}
        };

        assertArrayEquals(
            MapGenerator.getLevelMap(grayMap, 30),
            new boolean[][]{
                    {false, false, false},
                    {true, true, true},
                    {false, false, false}
            }
        );
    }

    @Test
    void testIllegalThresholdLow() {
        int[][] grayMap = {
                {10, 20},
                {30, 40}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            MapGenerator.getLevelMap(grayMap, -1);
        });
    }

    @Test
    void testIllegalThresholdHigh() {
        int[][] grayMap = {
                {10, 20},
                {30, 40}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            MapGenerator.getLevelMap(grayMap, 101);
        });
    }

    @Test
    void testIllegalGrayValue() {
        int[][] grayMap = {
                {0, 256},   // <-- valeur illégale
                {50, 100}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            MapGenerator.getLevelMap(grayMap, 50);
        });
    }
}
