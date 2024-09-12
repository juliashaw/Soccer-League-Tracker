package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team testTeam1;
    private Team testTeam2;

    @BeforeEach
    void runBefore() {
        testTeam1 = new Team("testTeam1");
        testTeam2 = new Team("testTeam2");
    }

    @Test
    void teamTest() {
        assertEquals("testTeam1", testTeam1.getName());
        assertEquals(0, testTeam1.getGameHistory().size());
        assertEquals(0, testTeam1.getWins());
        assertEquals(0, testTeam1.getLosses());
        assertEquals(0, testTeam1.getTies());
        assertEquals(0, testTeam1.getGoalsFor());
        assertEquals(0, testTeam1.getGoalsAgainst());
        assertEquals(0, testTeam1.getPoints());
    }

    @Test
    void addGameWonTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 5, 4);

        testTeam1.addGameWon(testGame1);

        assertEquals(1, testTeam1.getGameHistory().size());
        assertEquals(testGame1, testTeam1.getGameHistory().get(0));
        assertEquals(1, testTeam1.getWins());
        assertEquals(3, testTeam1.getPoints());
        assertEquals(5, testTeam1.getGoalsFor());
        assertEquals(4, testTeam1.getGoalsAgainst());
    }

    @Test
    void addGameWonSameTwiceTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 5, 4);

        testTeam1.addGameWon(testGame1);
        testTeam1.addGameWon(testGame1);

        assertEquals(1, testTeam1.getGameHistory().size());
        assertEquals(testGame1, testTeam1.getGameHistory().get(0));
        assertEquals(1, testTeam1.getWins());
        assertEquals(3, testTeam1.getPoints());
        assertEquals(5, testTeam1.getGoalsFor());
        assertEquals(4, testTeam1.getGoalsAgainst());
    }

    @Test
    void addGameWonMultipleTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 4, 5);
        Game testGame2 = new Game(testTeam1, testTeam2, 3, 4);

        testTeam2.addGameWon(testGame1);
        testTeam2.addGameWon(testGame2);

        assertEquals(2, testTeam2.getGameHistory().size());
        assertEquals(testGame1, testTeam2.getGameHistory().get(0));
        assertEquals(testGame2, testTeam2.getGameHistory().get(1));
        assertEquals(2, testTeam2.getWins());
        assertEquals(6, testTeam2.getPoints());
        assertEquals(9, testTeam2.getGoalsFor());
        assertEquals(7, testTeam2.getGoalsAgainst());
    }

    @Test
    void addGameLossTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 4, 5);

        testTeam1.addGameLoss(testGame1);

        assertEquals(1, testTeam1.getGameHistory().size());
        assertEquals(testGame1, testTeam1.getGameHistory().get(0));
        assertEquals(1, testTeam1.getLosses());
        assertEquals(4, testTeam1.getGoalsFor());
        assertEquals(5, testTeam1.getGoalsAgainst());
    }

    @Test
    void addGameLossSameTwiceTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 4, 5);

        testTeam1.addGameLoss(testGame1);
        testTeam1.addGameLoss(testGame1);


        assertEquals(1, testTeam1.getGameHistory().size());
        assertEquals(testGame1, testTeam1.getGameHistory().get(0));
        assertEquals(1, testTeam1.getLosses());
        assertEquals(4, testTeam1.getGoalsFor());
        assertEquals(5, testTeam1.getGoalsAgainst());
    }

    @Test
    void addGameLossMultipleTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 5, 4);
        Game testGame2 = new Game(testTeam1, testTeam2, 3, 2);

        testTeam2.addGameLoss(testGame1);
        testTeam2.addGameLoss(testGame2);


        assertEquals(2, testTeam2.getGameHistory().size());
        assertEquals(testGame1, testTeam2.getGameHistory().get(0));
        assertEquals(testGame2, testTeam2.getGameHistory().get(1));
        assertEquals(2, testTeam2.getLosses());
        assertEquals(6, testTeam2.getGoalsFor());
        assertEquals(8, testTeam2.getGoalsAgainst());
    }

    @Test
    void addGameTieTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 4, 4);

        testTeam2.addGameTie(testGame1);


        assertEquals(1, testTeam2.getGameHistory().size());
        assertEquals(testGame1, testTeam2.getGameHistory().get(0));
        assertEquals(1, testTeam2.getTies());
        assertEquals(1, testTeam2.getPoints());
        assertEquals(4, testTeam2.getGoalsFor());
        assertEquals(4, testTeam2.getGoalsAgainst());
    }

    @Test
    void addGameTieSameTwiceTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 4, 4);

        testTeam1.addGameTie(testGame1);
        testTeam1.addGameTie(testGame1);


        assertEquals(1, testTeam1.getGameHistory().size());
        assertEquals(testGame1, testTeam1.getGameHistory().get(0));
        assertEquals(1, testTeam1.getTies());
        assertEquals(1, testTeam1.getPoints());
        assertEquals(4, testTeam1.getGoalsFor());
        assertEquals(4, testTeam1.getGoalsAgainst());
    }

    @Test
    void addGameTieMutlipleTest() {
        Game testGame1 = new Game(testTeam1, testTeam2, 4, 4);
        Game testGame2 = new Game(testTeam1, testTeam2, 2, 2);

        testTeam1.addGameTie(testGame1);
        testTeam1.addGameTie(testGame2);


        assertEquals(2, testTeam1.getGameHistory().size());
        assertEquals(testGame1, testTeam1.getGameHistory().get(0));
        assertEquals(testGame2, testTeam1.getGameHistory().get(1));
        assertEquals(2, testTeam1.getTies());
        assertEquals(2, testTeam1.getPoints());
        assertEquals(6, testTeam1.getGoalsFor());
        assertEquals(6, testTeam1.getGoalsAgainst());
    }
}
