package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a game with two teams and the number of goals they each scored
public class Game implements Writable {
    private final Team team1;
    private final Team team2;
    private final int goals1;
    private final int goals2;

    // EFFECTS: constructs a game with two teams their respective number of goals
    public Game(Team team1, Team team2, int goals1, int goals2) {
        this.team1 = team1;
        this.team2 = team2;
        this.goals1 = goals1;
        this.goals2 = goals2;
    }

    // EFFECTS: returns true if the teams tied(scored the same number of goals)
    public boolean tie() {
        return (goals1 == goals2);
    }

    // REQUIRES: goals1 != goals2
    // EFFECTS: returns the team who scored the most goals
    public Team winner() {
        if (goals1 > goals2) {
            return team1;
        } else {
            return team2;
        }
    }

    // REQUIRES: goals1 != goals2
    // EFFECTS: returns the team who scored the least goals
    public Team loser() {
        if (goals1 > goals2) {
            return team2;
        } else {
            return team1;
        }
    }

    // REQUIRES: goals1 != goals2
    // EFFECTS: returns the greater number of goals
    public int winnerGoals() {
        return Math.max(goals1, goals2);
    }

    // REQUIRES: goals1 != goals2
    // EFFECTS: returns the smaller number of goals
    public int loserGoals() {
        return Math.min(goals1, goals2);
    }

    // EFFECTS: returns game as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("team1", team1.getName());
        json.put("team2", team2.getName());
        json.put("goals1", Integer.toString(goals1));
        json.put("goals2", Integer.toString(goals2));
        return json;
    }

    public Team getTeam1() {
        return this.team1;
    }

    public String getTeam1Name() {
        return this.team1.getName();
    }

    public Team getTeam2() {
        return this.team2;
    }

    public String getTeam2Name() {
        return this.team2.getName();
    }

    public int getGoals1() {
        return this.goals1;
    }

    public int getGoals2() {
        return this.goals2;
    }
}
