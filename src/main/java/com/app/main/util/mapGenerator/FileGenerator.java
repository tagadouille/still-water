package com.app.main.util.mapGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.main.util.json.JSONFileManager;

public final class FileGenerator{

    public final boolean[][] obstacles;

    public final int width;
    public final int height;

    private FileGenerator(boolean[][] obstacles, int width, int height){
        this.obstacles = obstacles;
        this.width = width;
        this.height = height;
    }

    public static FileGenerator createFileGenerator(boolean[][] obstacles, int width, int height){
        return new FileGenerator(obstacles, width, height);
    }

    public void createfile(String nameoffile) throws IOException{
        try{
            JSONFileManager writer = new JSONFileManager("levels/" + nameoffile + ".json");
            writer.create();

            List<List<Boolean>> obst = new ArrayList<>();

            for(int i = 0; i < height ; i++){

                List<Boolean> ligne = new ArrayList<>();

                for(int j = 0 ; j < width ; j++){

                    ligne.add(obstacles[i][j]);

                }
                obst.add(ligne);
            }
            writer.writeLine("map", obst);

            writer.writeLine("width", width);
            writer.writeLine("height", height);

        }catch(Exception e){
            e.printStackTrace();
            throw new IOException("writer failed" + e.getMessage());
        }
    }
    
}
