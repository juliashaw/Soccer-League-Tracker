package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {
    private Scoreboard testScoreboard;
    private List<Team> teams;

    @BeforeEach
    void runBefore() {
        testScoreboard = new Scoreboard("HandleyCupTest");
        teams = new ArrayList<>();
    }

    @Test
    void scoreboardTest() {
        assertEquals("HandleyCupTest", testScoreboard.getLeagueName());
        assertEquals(0, testScoreboard.getTeams().size());
    }

    @Test
    void addTeamOnceTest() {
        Team teamTest1 = new Team("teamTest1");
        boolean success = testScoreboard.addTeam(teamTest1);

        teams = testScoreboard.getTeams();
        assertEquals(1, teams.size());
        assertEquals(teamTest1, teams.get(0));
        assertTrue(success);
    }

    @Test
    void addTeamTwiceTest() {
        Team teamTest1 = new Team("teamTest1");

        boolean successA = testScoreboard.addTeam(teamTest1);
        boolean successB = testScoreboard.addTeam(teamTest1);

        teams = testScoreboard.getTeams();
        assertEquals(1, teams.size());
        assertEquals(teamTest1, teams.get(0));
        assertTrue(successA);
        assertFalse(successB);
    }

    @Test
    void addMultipleTeamTest() {
        Team teamTest1 = new Team("teamTest1");
        Team teamTest2 = new Team("teamTest2");

        boolean successA = testScoreboard.addTeam(teamTest1);
        boolean successB = testScoreboard.addTeam(teamTest2);

        teams = testScoreboard.getTeams();
        assertEquals(2, teams.size());
        assertEquals(teamTest1, teams.get(0));
        assertEquals(teamTest2, teams.get(1));
        assertTrue(successA);
        assertTrue(successB);
    }

    @Test
    void addMultipleTeamFailTest() {
        Team teamTest1 = new Team("teamTest1");
        Team teamTest2 = new Team("teamTest1");

        boolean successA = testScoreboard.addTeam(teamTest1);
        boolean successB = testScoreboard.addTeam(teamTest2);

        teams = testScoreboard.getTeams();
        assertEquals(1, teams.size());
        assertEquals(teamTest1, teams.get(0));
        assertTrue(successA);
        assertFalse(successB);
    }

    @Test
    void addGameOnceTieTest() {
        Team teamTest1 = new Team("teamTest1");
        Team teamTest2 = new Team("teamTest2");
        Game gameTest1 = new Game(teamTest1, teamTest2, 3, 3);

        testScoreboard.addTeam(teamTest1);
        testScoreboard.addTeam(teamTest2);
        testScoreboard.addGame(gameTest1);

        assertEquals(1, teamTest1.getGameHistory().size());
        assertEquals(1, teamTest2.getGameHistory().size());
        assertEquals(gameTest1, teamTest1.getGameHistory().get(0));
        assertEquals(gameTest1, teamTest2.getGameHistory().get(0));
        assertEquals(1, teamTest1.getTies());
        assertEquals(1, teamTest2.getTies());
        assertEquals(3, teamTest1.getGoalsFor());
        assertEquals(3, teamTest2.getGoalsFor());
        assertEquals(3, teamTest1.getGoalsAgainst());
        assertEquals(3, teamTest2.getGoalsAgainst());
        assertEquals(1, teamTest1.getPoints());
        assertEquals(1, teamTest2.getPoints());
    }

    @Test
    void addGameOnceWinTest() {
        Team teamTest1 = new Team("teamTest1");
        Team teamTest2 = new Team("teamTest2");
        Game gameTest1 = new Game(teamTest1, teamTest2, 3, 4);

        testScoreboard.addTeam(teamTest1);
        testScoreboard.addTeam(teamTest2);
        testScoreboard.addGame(gameTest1);

        assertEquals(1, teamTest1.getGameHistory().size());
        assertEquals(1, teamTest2.getGameHistory().size());
        assertEquals(gameTest1, teamTest1.getGameHistory().get(0));
        assertEquals(gameTest1, teamTest2.getGameHistory().get(0));
        assertEquals(1, teamTest1.getLosses());
        assertEquals(1, teamTest2.getWins());
        assertEquals(3, teamTest1.getGoalsFor());
        assertEquals(4, teamTest2.getGoalsFor());
        assertEquals(4, teamTest1.getGoalsAgainst());
        assertEquals(3, teamTest2.getGoalsAgainst());
        assertEquals(0, teamTest1.getPoints());
        assertEquals(3, teamTest2.getPoints());
    }

    @Test
    void addGameMultipleTest() {
        Team teamTest1 = new Team("teamTest1");
        Team teamTest2 = new Team("teamTest2");
        Game gameTest1 = new Game(teamTest1, teamTest2, 3, 3);
        Game gameTest2 = new Game(teamTest1, teamTest2, 3, 4);

        testScoreboard.addTeam(teamTest1);
        testScoreboard.addTeam(teamTest2);
        testScoreboard.addGame(gameTest1);
        testScoreboard.addGame(gameTest2);

        assertEquals(2, teamTest1.getGameHistory().size());
        assertEquals(2, teamTest2.getGameHistory().size());
        assertEquals(gameTest1, teamTest1.getGameHistory().get(0));
        assertEquals(gameTest1, teamTest2.getGameHistory().get(0));
        assertEquals(gameTest2, teamTest1.getGameHistory().get(1));
        assertEquals(gameTest2, teamTest2.getGameHistory().get(1));
        assertEquals(1, teamTest1.getTies());
        assertEquals(1, teamTest2.getTies());
        assertEquals(1, teamTest1.getLosses());
        assertEquals(1, teamTest2.getWins());
        assertEquals(6, teamTest1.getGoalsFor());
        assertEquals(7, teamTest2.getGoalsFor());
        assertEquals(7, teamTest1.getGoalsAgainst());
        assertEquals(6, teamTest2.getGoalsAgainst());
        assertEquals(1, teamTest1.getPoints());
        assertEquals(4, teamTest2.getPoints());
    }

    @Test
    void findTeamByNameNullTest() {
        assertNull(testScoreboard.findTeamByName("Test"));
    }

    @Test
    void findTeamByNameFoundTest() {
        Team teamTest1 = new Team("teamTest1");
        Team teamTest2 = new Team("teamTest2");
        testScoreboard.addTeam(teamTest1);
        testScoreboard.addTeam(teamTest2);

        assertEquals(teamTest2, testScoreboard.findTeamByName("teamTest2"));
    }

}