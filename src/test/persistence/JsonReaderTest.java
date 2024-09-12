package persistence;

import model.Game;
import model.Scoreboard;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Code influenced by the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Scoreboard sb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyScoreboard() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyScoreboard.json");
        try {
            Scoreboard sb = reader.read();
            assertEquals("My scoreboard", sb.getLeagueName());
            assertEquals(0, sb.getTeams().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralScoreboard() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralScoreboard.json");
        try {
            Scoreboard sb = reader.read();
            assertEquals("My scoreboard", sb.getLeagueName());
            List<Team> teams = sb.getTeams();
            assertEquals(2, teams.size());
            checkTeam("A", 0, 0, 0, 0, 0, 0,
                    teams.get(0));
            checkTeam("B", 0, 0, 0, 0, 0, 0,
                    teams.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderAdvancedScoreboard() {
        JsonReader reader = new JsonReader("./data/testReaderAdvancedScoreboard.json");
        try {
            Scoreboard sb = reader.read();
            assertEquals("My Soccer Scoreboard", sb.getLeagueName());
            List<Team> teams = sb.getTeams();
            assertEquals(3, teams.size());

            checkTeam("A", 0, 2, 0, 2, 5, 0,
                    teams.get(0));
            checkTeam("B", 1, 1, 0, 4, 4, 3,
                    teams.get(1));
            checkTeam("C", 2, 0, 0, 6, 3, 6,
                    teams.get(2));

            Game game1a = sb.findTeamByName("A").getGameHistory().get(0);
            Game game1b = sb.findTeamByName("B").getGameHistory().get(0);
            Game game2b = sb.findTeamByName("B").getGameHistory().get(1);
            Game game2c = sb.findTeamByName("C").getGameHistory().get(0);
            Game game3a = sb.findTeamByName("A").getGameHistory().get(1);
            Game game3c = sb.findTeamByName("C").getGameHistory().get(1);

            checkGame(sb.findTeamByName("A"), sb.findTeamByName("B"), 1, 2, game1a);
            checkGame(sb.findTeamByName("A"), sb.findTeamByName("B"), 1, 2, game1b);
            checkGame(sb.findTeamByName("B"), sb.findTeamByName("C"), 2, 3, game2b);
            checkGame(sb.findTeamByName("B"), sb.findTeamByName("C"), 2, 3, game2c);
            checkGame(sb.findTeamByName("A"), sb.findTeamByName("C"), 1, 3, game3a);
            checkGame(sb.findTeamByName("A"), sb.findTeamByName("C"), 1, 3, game3c);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTieScoreboard() {
        JsonReader reader = new JsonReader("./data/testReaderTieScoreboard.json");
        try {
            Scoreboard sb = reader.read();
            assertEquals("My Soccer Scoreboard", sb.getLeagueName());
            List<Team> teams = sb.getTeams();
            assertEquals(2, teams.size());

            checkTeam("A", 0, 0, 1, 1, 1, 1,
                    teams.get(0));
            checkTeam("B", 0, 0, 1, 1, 1, 1,
                    teams.get(1));

            Game game1a = sb.findTeamByName("A").getGameHistory().get(0);
            Game game1b = sb.findTeamByName("B").getGameHistory().get(0);

            checkGame(sb.findTeamByName("A"), sb.findTeamByName("B"), 1, 1, game1a);
            checkGame(sb.findTeamByName("A"), sb.findTeamByName("B"), 1, 1, game1b);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}