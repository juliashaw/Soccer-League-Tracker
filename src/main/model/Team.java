package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a team with a name, game history, wins, losses, ties, goals for, goals against, and points
public class Team implements Writable {
    private final String name;
    private final ArrayList<Game> gameHistory;
    private int wins;
    private int losses;
    private int ties;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    // EFFECTS: constructs a team with given name and no game history,
    // zero wins, losses, ties, goalsFor, goalsAgainst, and points
    public Team(String name) {
        this.name = name;
        gameHistory = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if game isn't already recorded,
    // adds the given game to the teams game history, increases wins by one, increases points by three,
    // increases goalsFor and goalsAgainst appropriately
    public void addGameWon(Game game) {
        if (!gameHistory.contains(game)) {
            this.gameHistory.add(game);
            this.wins++;
            this.goalsFor += game.winnerGoals();
            this.goalsAgainst += game.loserGoals();
            this.points += 3;
        }
    }

    // MODIFIES: this
    // EFFECTS: if game isn't already recorded,
    // adds the given game to the teams game history, increases losses by one, increases
    // goalsFor and goalsAgainst appropriately
    public void addGameLoss(Game game) {
        if (!gameHistory.contains(game)) {
            this.gameHistory.add(game);
            this.losses++;
            this.goalsFor += game.loserGoals();
            this.goalsAgainst += game.winnerGoals();
        }
    }

    // MODIFIES: this
    // EFFECTS: if game isn't already recorded,
    // adds the given game to the teams game history, increases ties by one, increases points by one,
    // increases goalsFor and goalsAgainst appropriately
    public void addGameTie(Game game) {
        if (!gameHistory.contains(game)) {
            this.gameHistory.add(game);
            this.ties++;
            this.goalsFor += game.getGoals1();
            this.goalsAgainst += game.getGoals1();
            this.points++;
        }
    }

    // EFFECTS: returns team as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("gameHistory", gameHistoryToJson());
        json.put("wins", Integer.toString(wins));
        json.put("losses", Integer.toString(losses));
        json.put("ties", Integer.toString(ties));
        json.put("goalsFor", Integer.toString(goalsFor));
        json.put("goalsAgainst", Integer.toString(goalsAgainst));
        json.put("points", Integer.toString(points));
        return json;
    }

    // EFFECTS: returns games in this gameHistory as a JSON array
    private JSONArray gameHistoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game g : gameHistory) {
            JSONObject json = new JSONObject();
            json.put("team1", g.getTeam1Name());
            json.put("team2", g.getTeam2Name());
            json.put("goals1", Integer.toString(g.getGoals1()));
            json.put("goals2", Integer.toString(g.getGoals2()));
            jsonArray.put(json);
        }

        return jsonArray;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Game> getGameHistory() {
        return this.gameHistory;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLosses() {
        return this.losses;
    }

    public int getTies() {
        return this.ties;
    }

    public int getGoalsFor() {
        return this.goalsFor;
    }

    public int getGoalsAgainst() {
        return this.goalsAgainst;
    }

    public int getPoints() {
        return this.points;
    }
}
