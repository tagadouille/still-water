package com.app.main.util;

import java.util.ArrayDeque;
import java.util.Queue;

public class GradientGrid {

    public static int height, width;

    private final int[][] distances;

    private final boolean[][] obstacle;

    public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;
    public static final int WALL = -1;

    private static final int[][] DIRECTIONS = {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}
    };

    public record Point(int x, int y) {}

    public GradientGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.distances = new int[width][height];
        this.obstacle = new boolean[width][height];
        
        reset();
    }

    public void setObstacle(int x, int y, boolean isObstacle) {
        if (isValid(x, y)) {
            obstacle[x][y] = isObstacle;
        }
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void reset(){
        for(int i = 0; i < distances.length ; i++){
            for(int j = 0; j < distances.length; j++){
                distances[i][j] = INFINITE_DISTANCE;
            }
        }
    }

    public int getDistance(int x, int y) {
        if (isValid(x, y)) {
            return distances[x][y];
        }
        return INFINITE_DISTANCE;
    }

    //Ouai c'est littéralement le BFS bluddy blud
    public void calculgradient(int targetx, int targety){

        if(!isValid(targetx, targety) || obstacle[targetx][targety]){
            throw new IllegalArgumentException("Case non valide");
        }

        Queue<Point> file = new ArrayDeque<>();

        distances[targetx][targety] = 0;
        file.add(new Point(targetx, targety));

        while(!file.isEmpty()){
            Point p = file.poll();
            int currentDist = distances[p.x][p.y];

            for(int [] dir : DIRECTIONS){
                int nx = p.x + dir[0];
                int ny = p.y + dir[1];

                if(isValid(nx, ny) && !obstacle[nx][ny]){

                    if(distances[nx][ny] == INFINITE_DISTANCE){

                        distances[nx][ny] = currentDist + 1;

                        file.add(new Point(nx, ny));
                    }
                }
            }
        }
    }

}
