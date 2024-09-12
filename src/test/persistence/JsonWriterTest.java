package persistence;

import model.Game;
import model.Scoreboard;
import model.Team;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Code influenced by the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Scoreboard sb = new Scoreboard("My scoreboard");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyScoreboard() {
        try {
            Scoreboard sb = new Scoreboard("My scoreboard");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyScoreboard.json");
            writer.open();
            writer.write(sb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyScoreboard.json");
            sb = reader.read();
            assertEquals("My scoreboard", sb.getLeagueName());
            assertEquals(0, sb.getTeams().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralScoreboard() {
        try {
            Scoreboard sb = new Scoreboard("My scoreboard");
            sb.addTeam(new Team("A"));
            sb.addTeam(new Team("B"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralScoreboard.json");
            writer.open();
            writer.write(sb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralScoreboard.json");
            sb = reader.read();
            assertEquals("My scoreboard", sb.getLeagueName());
            List<Team> teams = sb.getTeams();
            assertEquals(2, teams.size());
            checkTeam("A", 0, 0, 0, 0, 0, 0,
                    teams.get(0));
            checkTeam("B", 0, 0, 0, 0, 0, 0,
                    teams.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterAdvancedScoreboard() {
        try {
            Scoreboard sb = new Scoreboard("My Soccer Scoreboard");
            sb.addTeam(new Team("A"));
            sb.addTeam(new Team("B"));
            sb.addTeam(new Team("C"));
            sb.addGame(new Game(sb.findTeamByName("A"), sb.findTeamByName("B"), 1, 2));
            sb.addGame(new Game(sb.findTeamByName("B"), sb.findTeamByName("C"), 2, 3));
            sb.addGame(new Game(sb.findTeamByName("A"), sb.findTeamByName("C"), 1, 3));

            JsonWriter writer = new JsonWriter("./data/testWriterAdvancedScoreboard.json");
            writer.open();
            writer.write(sb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterAdvancedScoreboard.json");
            sb = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterTieScoreboard() {
        try {
            Scoreboard sb = new Scoreboard("My Soccer Scoreboard");
            sb.addTeam(new Team("A"));
            sb.addTeam(new Team("B"));
            sb.addGame(new Game(sb.findTeamByName("A"), sb.findTeamByName("B"), 1, 1));

            JsonWriter writer = new JsonWriter("./data/testWriterAdvancedScoreboard.json");
            writer.open();
            writer.write(sb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterAdvancedScoreboard.json");
            sb = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGameToJson() {
        Scoreboard sb = new Scoreboard("My Soccer Scoreboard");
        sb.addTeam(new Team("A"));
        sb.addTeam(new Team("B"));
        Game testGame = new Game(sb.findTeamByName("A"), sb.findTeamByName("B"), 1, 1);
        sb.addGame(testGame);
        JSONObject jsonGame = testGame.toJson();

        assertEquals("A", jsonGame.getString("team1"));
        assertEquals("B", jsonGame.getString("team2"));
        assertEquals("1", jsonGame.getString("goals1"));
        assertEquals("1", jsonGame.getString("goals2"));
    }

}