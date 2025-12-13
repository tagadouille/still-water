package com.app.main.util.mapGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.main.model.GameManager;
import com.app.main.util.json.JSONFileManager;

public final class FileGenerator{

    public final boolean[][] obstacles;

    private FileGenerator(boolean[][] obstacles){
        this.obstacles = obstacles;
    }

    public static FileGenerator createFileGenerator(boolean[][] obstacles){
        return new FileGenerator(obstacles);
    }

    public void createfile(String nameoffile) throws IOException{
        try{
            JSONFileManager writer = new JSONFileManager("levels/" + nameoffile + ".json");
            writer.create();

            List<List<Boolean>> obst = new ArrayList<>();

            for(int i = 0; i < GameManager.GRID_DIM ; i++){

                List<Boolean> ligne = new ArrayList<>();

                for(int j = 0 ; j < GameManager.GRID_DIM ; j++){

                    ligne.add(obstacles[i][j]);

                }
                obst.add(ligne);
            }
            writer.writeLine("map", obst);

        }catch(Exception e){
            e.printStackTrace();
            throw new IOException("writer failed" + e.getMessage());
        }
    }
    
}
