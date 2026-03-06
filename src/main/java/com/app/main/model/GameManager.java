package com.app.main.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.app.main.model.core.Color;
import com.app.main.model.core.Point;
import com.app.main.model.core.Team;
import com.app.main.model.core.Cell;

/**
 * Class for managing the models and makes update
 * of the model
 * 
 * @author Dai Elias
 */
public final class GameManager {
    
    public static final int GRID_DIM = 480;
    public static final int NB_CELL = 8000;

    private boolean[][] obstacles;

    private volatile Cell[][] globalGrid;
    private Team[] teams;

    private double[] forces;
    private int totalCell;


    private GameManager(boolean[][] obstacles, Team[] teams, Point[][] teamsSpawn){
        this.obstacles = obstacles;
        
        this.globalGrid = new Cell[GRID_DIM][GRID_DIM];
        
        this.initTeam(teams, teamsSpawn);
    }
    
    public Cell[][] getGlobalGrid() {
        return globalGrid;
    }

    public boolean[][] getObstacles() {
        return this.obstacles;
    }
    public void setObstacles(boolean[][] obstacles) {
        this.obstacles = obstacles;
    }

    public double[] getForces() {
        return forces;
    }

    public Team[] getTeams() {
        return teams;
    }

    public int getWidth(){
        return obstacles[0].length;
    }

    public int getHeight(){
        return obstacles.length;
    }
    
    /**
     * Static factory for creating a GameManager
     * @param obstacles the array of obstacles
     * @param teams the array of team
     * @param teamsSpawn the spawns point of each teams
     * @return the GameManager
     */
    public static GameManager gameManagerFactory(boolean[][] obstacles, Team[] teams, Point[][] teamsSpawn){
        teamVerification(teams, teamsSpawn, false);

        GameManager gm = new GameManager(obstacles, teams, teamsSpawn);

        return gm;
    }

    /**
     * Static factory for test
     * @param obstacles
     * @param teams
     * @param teamsSpawn
     * @return
     */
    public static GameManager gameManagerFactoryTest(boolean[][] obstacles, Team[] teams, Point[][] teamsSpawn, int dimension){
        teamVerification(teams, teamsSpawn, true);

        GameManager gm = new GameManager(obstacles, teams, teamsSpawn);

        return gm;
    }

    /**
     * For do the verification of the validity of the arguments
     * @param teams the teams of the game
     * @param teamsSpawn the spawn point of each team
     */
    private static void teamVerification(Team[] teams, Point[][] teamsSpawn, boolean isTest){
        // Size verification
        if(teams.length != teamsSpawn.length){
            throw new IllegalArgumentException("The number of teams and the number of teams's spawn point doesn't match");
        }
        // Verification of the content of teams
        for (int i = 0; i < teams.length; i++) {
            if(teams[i] == null){
                throw new IllegalArgumentException("The team can't be null");
            }
            if(teams[i].getTeam() == null){
                throw new IllegalArgumentException("The team must have a color");
            }
        }
        // Verification of the team unicity
        for (int i = 0; i < teams.length; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                if(teams[i].getTeam() == teams[j].getTeam()){
                    throw new IllegalArgumentException("Each team must have their own color.");
                }
            }
        }
        spawnPointVerification(teamsSpawn, isTest);
    }

    private static void spawnPointVerification(Point[][] teamsSpawn, boolean isTest){
        //Verification of the content and the size of the spawns points
        for (int i = 0; i < teamsSpawn.length; i++) {
            Point[] pointArray = teamsSpawn[i];

            if(pointArray.length != 2){
                throw new IllegalArgumentException("There must be only thow points.");
            }
            for (int j = 0; j < pointArray.length; j++) {
                if(pointArray[j] == null){
                    throw new IllegalArgumentException("The points can't be null");
                }
            }
            int lengthUp = pointArray[1].x() - pointArray[0].x();
            int lengthDown = pointArray[1].y() - pointArray[0].y();

            if(lengthDown < 0){
                throw new IllegalArgumentException("The first point must be upper to the second in term of y");
            }
            if(lengthUp < 0){
                throw new IllegalArgumentException("The first point must be lower to the second in term of x");
            }
            
            if(!isTest && lengthUp * lengthDown != NB_CELL){
                throw new IllegalArgumentException("The number of cell of a team must be " + NB_CELL);
            }
        }
    }

    private void initTeam(Team[] teams, Point[][] teamsSpawn){
        
        this.teams = teams;
        int nbTeam = teams.length;

        this.forces = new double[nbTeam];
        this.totalCell = 0;

        for (int i = 0; i < nbTeam; i++) {

            // Spawning the cells : (fill globalGrid + teams)
            Point[] spawn = teamsSpawn[i];

            int startX = Math.min(spawn[0].x(), spawn[1].x());
            int endX   = Math.max(spawn[0].x(), spawn[1].x());
            int startY = Math.min(spawn[0].y(), spawn[1].y());
            int endY   = Math.max(spawn[0].y(), spawn[1].y());

            for (int x = startX; x < endX; x++) {
                for (int y = startY; y < endY; y++) {
                        Cell cell = Cell.CreateCell(x, y, teams[i].getTeam());
                        teams[i].addCell(cell);
                        globalGrid[y][x] = cell; 
                    } 
                }
                this.totalCell += teams[i].getArmy().size();
        }
        // Initialiser les rapports de forces
        updateForces();
    }

    /**
     * Update the model by calculate the gradient grid of each team
     * and apply the movements/heals/attacks of each teams
     */
    public void update(){
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        for (Team team : teams) {
            executor.submit(() -> team.updateArmy(globalGrid));
        }
        executor.shutdown();

        try{
            executor.awaitTermination(5, TimeUnit.MILLISECONDS);
        }
        catch(InterruptedException e){
            executor.shutdownNow();
        }
        clearAndRemanageTeam();
        updateForces();
    }

    /**
     * For each team, iterate in the list of cell and if a cell will switch team,
     * move it to her new team.
     */
    private void clearAndRemanageTeam(){

        @SuppressWarnings("unchecked")
        List<Cell>[] newArmies = new List[teams.length];
        
        for (int i = 0; i < newArmies.length; i++) {
            newArmies[i] = new ArrayList<Cell>();
        }

        for (int i = 0; i < teams.length; i++) {
            
            for (Cell cell : teams[i].getArmy()) {

                if(cell.getNextTeam() != null){
                    cell.setCurrentTeam(cell.getNextTeam());
                    cell.setNextTeam(null);
                }
                int teamIndex = getTeamIndex(cell.getCurrentTeam());
                if (teamIndex != -1) {
                    newArmies[teamIndex].add(cell);
                } else {
                    // Cas d'erreur (ne devrait pas arriver si tes équipes sont cohérentes)
                    throw new IllegalArgumentException("Erreur: Cellule avec couleur " + cell.getCurrentTeam() + " n'a pas d'équipe !");
                }
            }
        }
        for (int i = 0; i < teams.length; i++) {
            teams[i].setArmy(newArmies[i]);
        }
    }

    private int getTeamIndex(Color c) {
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getTeam() == c) {
                return i;
            }
        }
        return -1; // Not found
    }

    /**
     * Update of the balance of power
     */
    private void updateForces(){

        for (int i = 0; i < teams.length; i++) {

            forces[i] = teams[i].getArmy().size() / (double) totalCell;
        }
    }

    /**
     * Create an instance of GameManager from a GameLevel instance
     * @param level the GameLevel instance
     * @return an instance of GameManager
     */
    public static GameManager createFromJSON(GameLevel level) {

        if(level == null){
            throw new IllegalArgumentException("The gamelevel can't be null");
        }

        int nbTeams = level.teamsInfo.size();
        Team[] teams = new Team[nbTeams];
        Point[][] spawns = new Point[nbTeams][2];

        for (int i = 0; i < nbTeams; i++) {
            GameLevel.TeamConfig config = level.teamsInfo.get(i);

            spawns[i] = config.spawnArea;

            teams[i] = Team.CreateTeam(
                config.color, 
                level.width, 
                level.height, 
                level.obstacles
            );
        }
        return GameManager.gameManagerFactory(level.obstacles, teams, spawns);
    }
}
