package com.app.main.model;

import com.app.main.model.Team.Cell;

//TODO tab de gradient créer pendant la lecture de niveau
public final class GameManager {
    public static final int GRID_DIM = 480;
    public static final int NB_CELL = 14400;

    private Cell[][] globalGrid;
    private final boolean[][] obstacles; //! mettre dans la lecture de niveau ?
    private Team[] teams;

    private GameManager(boolean[][] obstacles, Team[] teams, Point[][] teamsSpawn){
        this.obstacles = obstacles;
        this.initTeam(teams, teamsSpawn);
    }
    
    public Cell[][] getGlobalGrid() {
        return globalGrid;
    }
    
    /**
     * Static factory
     * @param obstacles
     * @param teams
     * @param teamsSpawn
     * @return
     */
    public static GameManager gameManagerFactory(boolean[][] obstacles, Team[] teams, Point[][] teamsSpawn){
        teamVerification(teams, teamsSpawn);

        GameManager gm = new GameManager(obstacles, teams, teamsSpawn);
        gm.globalGrid = new Cell[GRID_DIM][GRID_DIM];
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
        teamVerification(teams, teamsSpawn);

        GameManager gm = new GameManager(obstacles, teams, teamsSpawn);
        gm.globalGrid = new Cell[dimension][dimension];
        return gm;
    }

    //! Package private pour l'utiliser pour la lecture de fichier
    // TODO créer ses propres exeptions ???
    /**
     * For do the verification of the validity of the arguments
     * @param teams the teams of the game
     * @param teamsSpawn the spawn point of each team
     */
    static void teamVerification(Team[] teams, Point[][] teamsSpawn){
        // Vérification de taille
        if(teams.length != teamsSpawn.length){
            throw new IllegalArgumentException("The number of teams and the number of teams's spawn point doesn't match");
        }
        //Vérification de contenu de teams
        for (int i = 0; i < teams.length; i++) {
            if(teams[i] == null){
                throw new IllegalArgumentException("The team can't be null");
            }
            if(teams[i].getTeam() == null){
                throw new IllegalArgumentException("The team must have a color");
            }
        }
        //Vérification d'unicité des équipes
        for (int i = 0; i < teams.length; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                if(teams[i].getTeam() == teams[i].getTeam()){
                    throw new IllegalArgumentException("Each team must have their own color.");
                }
            }
        }
        spawnPointVerification(teamsSpawn);
    }

    private static void spawnPointVerification(Point[][] teamsSpawn){
        //Vérification de contenu et de taille des points d'apparitions
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
            /* (Pour les tests c'est peut être pas une bonne idée)
            if(lengthUp * lengthDown != NB_CELL){
                throw new IllegalArgumentException("The number of cell of a team must be " + NB_CELL);
            }*/
        }
    }

    private void initTeam(Team[] teams, Point[][] teamsSpawn){
        
        this.teams = teams;
        int nbTeam = teams.length;

        for (int i = 0; i < nbTeam; i++) {

            //Faire apparaître les cellules : (remplir globalGrid + teams)
            Point[] spawn = teamsSpawn[i];

            for (int j = 0; j < spawn.length; j++) {
                int x = spawn[i].x();
                int y = spawn[i].y();

                Cell cell = new Cell(x, y, Color.values()[i]);
                teams[i].addCell(cell);
                globalGrid[y][x] = cell;
            }
        }
    }

    /**
     * Update the model by calculate the gradient grid of each team
     * and apply the movements/heals/attacks of each teams
     */
    public void update(){
        //! Provisoire, mettre des threads
        for (int i = 0; i < teams.length; i++) {
            Team currTeam = this.teams[i];

            currTeam.gradient.calculgradient(currTeam.getTargetX(), currTeam.getTargetY());
            currTeam.updateArmy(globalGrid);
        }
    }
}
