package persistence;

import model.Scoreboard;
import model.Game;
import model.Team;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Code influenced by the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//
// Represents a reader that reads scoreboard from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads scoreboard from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Scoreboard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseScoreboard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses scoreboard from JSON object and returns it
    private Scoreboard parseScoreboard(JSONObject jsonObject) {
        String name = jsonObject.getString("leagueName");
        Scoreboard sb = new Scoreboard(name);
        addTeams(sb, jsonObject);
        addGamesToTeams(sb, jsonObject);
        return sb;
    }

    // MODIFIES: sb
    // EFFECTS: parses teams from JSON object and adds them to scoreboard
    private void addTeams(Scoreboard sb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(sb, nextTeam);
        }
    }

    // MODIFIES: sb
    // EFFECTS: parses team from JSON object and adds it to scoreboard
    private void addTeam(Scoreboard sb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Team team = new Team(name);
        sb.addTeam(team);
    }

    // MODIFIES: team
    // EFFECTS: parses games from JSON object and adds them to team
    private void addGamesToTeams(Scoreboard sb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addGamesToTeam(sb, nextTeam);
        }
    }

    // MODIFIES: team
    // EFFECTS: parses game from JSON object and adds it to team
    private void addGamesToTeam(Scoreboard sb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gameHistory");
        for (Object json : jsonArray) {
            JSONObject game = (JSONObject) json;
            String team1 = game.getString("team1");
            String team2 = game.getString("team2");
            String goals1 = game.getString("goals1");
            String goals2 = game.getString("goals2");

            Team givenTeam = sb.findTeamByName(jsonObject.getString("name"));

            assignGame(givenTeam, sb.findTeamByName(team1), sb.findTeamByName(team2),
                    Integer.parseInt(goals1), Integer.parseInt(goals2));
        }
    }

    // EFFECTS: Assigns the game to the given team, based on whether they won, lost, or tied
    private void assignGame(Team givenTeam, Team team1, Team team2, int goals1, int goals2) {

        Game game = new Game(team1, team2, goals1, goals2);

        if (goals1 == goals2) {
            givenTeam.addGameTie(game);
        } else if (givenTeam.equals(game.winner())) {
            givenTeam.addGameWon(game);
        } else {
            givenTeam.addGameLoss(game);
        }
    }

}
