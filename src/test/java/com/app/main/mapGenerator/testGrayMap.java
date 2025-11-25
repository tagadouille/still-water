package com.app.main.mapGenerator;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testGrayMap {
    private static final String IMAGE_PATH = "src/test/java/com/app/test-resources/";

    @Test
    public void grayMap(){
        try{
            int[][] grayMap1 = MapGenerator.getGrayMap(IMAGE_PATH + "test-map1.png");
            int[][] grayMap2 = MapGenerator.getGrayMap(IMAGE_PATH + "test-map2.png");

            int[][] arr1 = {
                            {255,255,255,255,255,255,255,255},
                            {255,0,0,0,0,0,0,255},
                            {255,0,0,0,0,0,0,255},
                            {255,0,0,0,0,0,0,255},
                            {255,0,0,0,0,0,0,255},
                            {255,0,0,0,0,0,0,255},
                            {255,0,0,0,0,0,0,255},
                            {255,255,255,255,255,255,255,255}
                            };
            
            int[][] arr2 = {
                            {25,255,255,255,255,255,255,50},
                            {255,255,255,255,255,255,255,255},
                            {255,255,255,255,255,255,255,255},
                            {0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0},
                            {255,255,255,255,255,255,255,255},
                            {255,255,255,255,255,255,255,255},
                            {100,255,255,255,255,255,255,75}
                        };
            
            Assertions.assertArrayEquals(grayMap1, arr1);
            Assertions.assertArrayEquals(grayMap2, arr2);
            
        }catch(IOException e){
            e.getStackTrace();
        }
    }
}
