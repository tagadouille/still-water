package com.app.main.util.mapGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.main.model.GameLevel;
import com.app.main.model.GameManager;
import com.app.main.model.GameLevel.TeamConfig;
import com.app.main.util.json.JSONFileManager;
import com.app.main.model.core.Point;

/**
 * Utilitaire de création de fichier JSON représentant un niveau édité.
 * <p>
 * Transforme un {@link GameLevel} en structure sérialisable et écrit le
 * résultat dans le dossier <code>editorlevels</code> via
 * {@link com.app.main.util.json.JSONFileManager}.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public final class FileGenerator{

    private final GameLevel gamelevel;

    private FileGenerator(GameLevel gameLevel){
        this.gamelevel = gameLevel;
    }

    /**
     * Fabrique un FileGenerator pour un {@link GameLevel} donné.
     *
     * @param gameLevel le niveau source (non null)
     * @return instance de FileGenerator
     * @throws IllegalArgumentException si {@code gameLevel} est {@code null}
     */
    public static FileGenerator createFileGenerator(GameLevel gameLevel){

        if(gameLevel == null){
            throw new IllegalArgumentException("The GameLevel parameter can't be null");
        }

        return new FileGenerator(gameLevel);
    }

    /**
     * Écrit le niveau dans un fichier JSON nommé <code>editorlevels/{nameoffile}.json</code>.
     * <p>
     * Le fichier contient la carte d'obstacles, la liste des équipes (couleur,
     * positions de spawn, type) et le nom du fond.
     * </p>
     *
     * @param nameoffile nom du fichier (sans extension)
     * @param backgroundFilename nom du fichier d'image de fond
     * @throws IOException si l'écriture échoue
     */
    public void createfile(String nameoffile, String backgroundFilename) throws IOException{
        try{
            JSONFileManager writer = new JSONFileManager("editorlevels/" + nameoffile + ".json");
            writer.create();

            List<List<Boolean>> obst = new ArrayList<>();

            for(int i = 0; i < GameManager.GRID_DIM ; i++){

                List<Boolean> ligne = new ArrayList<>();

                for(int j = 0 ; j < GameManager.GRID_DIM ; j++){

                    ligne.add(gamelevel.getObstacles()[i][j]);

                }
                obst.add(ligne);
            }
            writer.writeLine("map", obst);

            // Add all the teams info to the file
            List<Map<String, Object>> teams = new ArrayList<>();

            for (TeamConfig config : gamelevel.getTeamsInfo()) {

                // The color : 
                Map<String, Object> team = new HashMap<>();
                team.put("color", config.color.toString());

                // The position :
                Point pos1 = config.spawnArea[0];
                Point pos2 = config.spawnArea[1];

                List<List<Integer>> pos = new ArrayList<>();
                pos.add(Arrays.asList(pos1.x(), pos1.y()));
                pos.add(Arrays.asList(pos2.x(), pos2.y()));

                team.put("pos", pos);

                // The type of the team :
                team.put("isPlayer", config.isPlayer);

                teams.add(team);
            }
            writer.writeLine("team", teams);

            writer.writeLine("background", backgroundFilename);

        }catch(Exception e){
            e.printStackTrace();
            throw new IOException("writer failed" + e.getMessage());
        }
    }
    
}
