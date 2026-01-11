package com.app.main.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.app.main.model.core.Color;
import com.app.main.model.core.Point;
import com.app.main.util.json.JSONFileManager;
import com.app.main.model.GameLevel;

/**
 * Chargeur de niveaux depuis des fichiers JSON.
 * <p>
 * Lit la structure attendue (carte d'obstacles, équipes, image de fond)
 * et construit un {@link GameLevel} utilisable par le jeu.
 * </p>
 * 
 * @author Mohamed Ibrir
 */
public class GameLevelLoader {

    /**
     * Charge un niveau à partir du chemin JSON fourni.
     *
     * @param path chemin vers le fichier JSON (ex: "levels/lvl1.json")
     * @return instance de {@link GameLevel} construite depuis le fichier
     * @throws IllegalArgumentException si la section "map" est absente ou invalide
     */
    @SuppressWarnings("unchecked")
    public static GameLevel load(String path) {
        JSONFileManager json = new JSONFileManager(path);
        
        List<List<Boolean>> rawMap = (List<List<Boolean>>) json.read("map");
        
        if (rawMap == null || rawMap.isEmpty()) {
            throw new IllegalArgumentException("La map JSON est vide ou invalide.");
        }

        int h = rawMap.size();
        int w = rawMap.get(0).size();
        
        boolean[][] obstacles = new boolean[w][h];

        for (int y = 0; y < h; y++) {
            List<Boolean> row = rawMap.get(y);
            for (int x = 0; x < w; x++) {

                obstacles[x][y] = row.get(x);
            }
        }

        List<GameLevel.TeamConfig> teamConfigs = new ArrayList<>();
        List<Map<String, Object>> rawTeams = (List<Map<String, Object>>) json.read("team");

        if (rawTeams != null) {
            for (Map<String, Object> tData : rawTeams) {

                String colorStr = (String) tData.get("color");
                Color color = parseColor(colorStr);

                List<List<Integer>> rawPos = (List<List<Integer>>) tData.get("pos");
                Point[] spawnPoints = new Point[2];

                spawnPoints[0] = new Point(
                    rawPos.get(0).get(0),
                    rawPos.get(0).get(1)
                );

                spawnPoints[1] = new Point(
                    rawPos.get(1).get(0),
                    rawPos.get(1).get(1)
                );

                Boolean isPlayer = (Boolean) tData.get("isPlayer");

                teamConfigs.add(new GameLevel.TeamConfig(color, spawnPoints, isPlayer));
            }
        }

        String bgFilename = (String) json.read("background");
       
        return new GameLevel(obstacles, teamConfigs, bgFilename);
    }

    /**
     * Convertit une chaîne en valeur {@link Color}. Accepte des abréviations
     * basées sur la première lettre et retourne RED par défaut si la couleur
     * est inconnue.
     *
     * @param s chaîne décrivant la couleur (peut être {@code null})
     * @return valeur {@link Color}
     */
    private static Color parseColor(String s) {
        if (s == null) return Color.RED;
        s = s.toUpperCase();
        if (s.startsWith("R")) return Color.RED;
        if (s.startsWith("B")) return Color.BLUE;
        if (s.startsWith("G")) return Color.GREEN;
        if (s.startsWith("P")) return Color.PURPLE;
        try {
            return Color.valueOf(s);
        } catch (Exception e) {
            System.err.println("Couleur inconnue: " + s + ", par défaut RED.");
            return Color.RED;
        }
    }
}