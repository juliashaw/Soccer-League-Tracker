package persistence;

import model.Game;
import model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code influenced by the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkTeam(String name, int wins, int losses, int ties,
                             int goalsFor, int goalsAgainst, int points, Team team) {
        assertEquals(name, team.getName());
        assertEquals(wins, team.getWins());
        assertEquals(losses, team.getLosses());
        assertEquals(ties, team.getTies());
        assertEquals(goalsFor, team.getGoalsFor());
        assertEquals(goalsAgainst, team.getGoalsAgainst());
        assertEquals(points, team.getPoints());
    }

    protected void checkGame(Team team1, Team team2, int goals1, int goals2, Game game) {
        assertEquals(team1, game.getTeam1());
        assertEquals(team2, game.getTeam2());
        assertEquals(goals1, game.getGoals1());
        assertEquals(goals2, game.getGoals2());
    }
}
