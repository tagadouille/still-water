package com.app.main.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.main.model.GameManager;
import com.app.main.model.core.Color;
import com.app.main.model.core.Team;

public class VictoryHandlerTest {

    private Team redTeam = mock(Team.class);
    private Team blueTeam = mock(Team.class);

    @BeforeEach
    void renit(){
        redTeam = mock(Team.class);
        blueTeam = mock(Team.class);
    }

    @Test
    void determineWinnerByDominance_oneTeamStanding_returnsWinnerColor() {
        // Arrange
        GameManager gameManager = mock(GameManager.class);

        when(redTeam.getTeam()).thenReturn(Color.RED);
        when(blueTeam.getTeam()).thenReturn(Color.BLUE);

        when(gameManager.getForces()).thenReturn(new double[]{10.0, 0.0});
        when(gameManager.getTeams()).thenReturn(new Team[]{redTeam, blueTeam});

        Color winner = VictoryHandler.determineWinnerByDominance(gameManager);

        assertEquals(Color.RED, winner);
    }

    @Test
    void determineWinnerByDominance_noClearWinner_returnsNoColor() {
        // Arrange
        GameManager gameManager = mock(GameManager.class);

        when(gameManager.getForces()).thenReturn(new double[]{5.0, 3.0});
        when(gameManager.getTeams()).thenReturn(new Team[]{redTeam, blueTeam});

        Color winner = VictoryHandler.determineWinnerByDominance(gameManager);

        assertEquals(Color.NO_COLOR, winner);
    }

    @Test
    void determineWinnerByDominance_allForcesZero_returnsNoColor() {
        // Arrange
        GameManager gameManager = mock(GameManager.class);

        when(gameManager.getForces()).thenReturn(new double[]{0.0, 0.0});
        when(gameManager.getTeams()).thenReturn(new Team[]{redTeam, blueTeam});

        Color winner = VictoryHandler.determineWinnerByDominance(gameManager);

        assertEquals(Color.NO_COLOR, winner);
    }

    @Test
    void determineWinnerByPower_highestForceWins() {
        // Arrange
        GameManager gameManager = mock(GameManager.class);

        when(redTeam.getTeam()).thenReturn(Color.RED);
        when(blueTeam.getTeam()).thenReturn(Color.BLUE);

        when(gameManager.getForces()).thenReturn(new double[]{12.5, 20.0});
        when(gameManager.getTeams()).thenReturn(new Team[]{redTeam, blueTeam});

        Color winner = VictoryHandler.determineWinnerByPower(gameManager);

        assertEquals(Color.BLUE, winner);
    }

    @Test
    void determineWinnerByPower_allForcesZero_returnsNoColor() {
        // Arrange
        GameManager gameManager = mock(GameManager.class);

        when(gameManager.getForces()).thenReturn(new double[]{0.0, 0.0});
        when(gameManager.getTeams()).thenReturn(new Team[]{redTeam, blueTeam});

        Color winner = VictoryHandler.determineWinnerByPower(gameManager);

        assertEquals(Color.NO_COLOR, winner);
    }
}

