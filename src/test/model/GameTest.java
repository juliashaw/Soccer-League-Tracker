package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game testGame;
    private Team testTeam1;
    private Team testTeam2;

    @BeforeEach
    void runBefore() {
        testTeam1 = new Team("testTeam1");
        testTeam2 = new Team("testTeam2");
    }

    @Test
    void gameTest() {
        testGame = new Game(testTeam1, testTeam2, 2, 3);

        assertEquals(testTeam1, testGame.getTeam1());
        assertEquals(testTeam2, testGame.getTeam2());
        assertEquals(2, testGame.getGoals1());
        assertEquals(3, testGame.getGoals2());
    }

    @Test
    void tieTrueTest() {
        testGame = new Game(testTeam1, testTeam2, 2, 2);
        assertTrue(testGame.tie());
    }

    @Test
    void tieFalseTest() {
        testGame = new Game(testTeam1, testTeam2, 2, 3);
        assertFalse(testGame.tie());
    }

    @Test
    void winnerFirstBranchTest() {
        testGame = new Game(testTeam1, testTeam2, 3, 2);
        assertEquals(testTeam1, testGame.winner());
    }

    @Test
    void winnerSecondBranchTest() {
        testGame = new Game(testTeam1, testTeam2, 2, 3);
        assertEquals(testTeam2, testGame.winner());
    }

    @Test
    void loserFirstBranchTest() {
        testGame = new Game(testTeam1, testTeam2, 2, 3);
        assertEquals(testTeam1, testGame.loser());
    }

    @Test
    void loserSecondBranchTest() {
        testGame = new Game(testTeam1, testTeam2, 3, 2);
        assertEquals(testTeam2, testGame.loser());
    }

    @Test
    void winnerGoalsFirstBranchTest() {
        testGame = new Game(testTeam1, testTeam2, 3, 2);
        assertEquals(3, testGame.winnerGoals());
    }

    @Test
    void winnerGoalsSecondBranchTest() {
        testGame = new Game(testTeam1, testTeam2, 2, 3);
        assertEquals(3, testGame.winnerGoals());
    }

    @Test
    void loserGoalsSecondBranchTest() {
        testGame = new Game(testTeam1, testTeam2, 2, 3);
        assertEquals(2, testGame.loserGoals());
    }

    @Test
    void loserGoalsFirstBranchTest() {
        testGame = new Game(testTeam1, testTeam2, 3, 2);
        assertEquals(2, testGame.loserGoals());
    }
}
