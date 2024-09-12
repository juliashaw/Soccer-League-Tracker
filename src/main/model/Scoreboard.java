package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents the teams and their respective stats within a soccer league
public class Scoreboard implements Writable {
    private final String leagueName;
    private final ArrayList<Team> teams;

    // MODIFIES: this
    // EFFECTS: constructs a scoreboard with given name and an empty list of teams
    public Scoreboard(String leagueName) {
        this.leagueName = leagueName;
        this.teams = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given team to the list of teams for this scoreboard if it is not already contained,
    // and if there is not another team with the same name; returns true if the team was successfully added, false
    // otherwise. Adds the event to the EventLog
    public boolean addTeam(Team team) {
        for (Team t: teams) {
            if (t.getName().equals(team.getName())) {
                return false;
            }
        }
        this.teams.add(team);
        EventLog.getInstance().logEvent(new Event("Team " + team.getName() + " was added to the scoreboard."));
        return true;
    }


    // EFFECTS: adds stats to each team who played in the game, based on the results
    public void addGame(Game game) {
        if (game.tie()) {
            game.getTeam1().addGameTie(game);
            game.getTeam2().addGameTie(game);
        } else {
            game.winner().addGameWon(game);
            game.loser().addGameLoss(game);
        }
        EventLog.getInstance().logEvent(new Event("A game was added to the scoreboard."));
    }

    // EFFECTS: finds the Team object in teams with the same name as the given string; returns null if
    // no team with that name was found.
    public Team findTeamByName(String name) {
        for (Team t: teams) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public String getLeagueName() {
        return this.leagueName;
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }

    // EFFECTS: returns scoreboard as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("leagueName", leagueName);
        json.put("teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns teams in this scoreboard as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teams) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
