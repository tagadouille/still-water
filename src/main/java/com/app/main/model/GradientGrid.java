package com.app.main.model;

import java.util.ArrayDeque;
import java.util.Queue;

public final class GradientGrid {

    public int height, width;

    private final int[][] distances;

    private final boolean[][] obstacle;

    public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;
    public static final int WALL = -1;

    private static final int[][] DIRECTIONS = {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}
    };

    public GradientGrid(int width, int height, boolean[][] obstacle) {
        this.width = width;
        this.height = height;
        this.distances = new int[width][height];
        this.obstacle = obstacle;
        
        reset();
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void reset(){
        for(int i = 0; i < width ; i++){
            for(int j = 0; j < height; j++){
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

    public Point BestNeighbors(int targetx, int targety){
        Point p = null;

        int minDistance = distances[targetx][targety];

        for(int [] dir :  DIRECTIONS){
            int nx = targetx + dir[0];
            int ny = targety + dir[1];

            if(isValid(nx, ny) && !obstacle[nx][ny]){

                int distneighbors = distances[nx][ny];

                if(minDistance > distneighbors){
                    minDistance = distneighbors;
                    p = new Point(nx, ny);
                }
            }
        }
        
        return p;
    }

    public void calculgradient(int targetx, int targety){

        reset();

        if(!isValid(targetx, targety) || obstacle[targetx][targety]){
            return;
        }

        Queue<Point> file = new ArrayDeque<>();

        distances[targetx][targety] = 0;
        file.add(new Point(targetx, targety));

        while(!file.isEmpty()){
            Point p = file.poll();
            int currentDist = distances[p.x()][p.y()];

            for(int [] dir : DIRECTIONS){
                int nx = p.x() + dir[0];
                int ny = p.y() + dir[1];

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
