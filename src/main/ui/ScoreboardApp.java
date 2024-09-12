package ui;

import model.Game;
import model.Team;
import model.Scoreboard;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Code influenced by the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class ScoreboardApp {
    private static final String JSON_STORE = "./data/scoreboard.json";
    private Scanner input;
    private Scoreboard scoreboard;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: A soccer scoreboard is created and a series of
    // options(including 1. Add team 2. Add game 3. View teams 4 View game history for a team
    // 5. Save scoreboard to file 6. Load scoreboard from file 7. Quit) is looped until they
    // enter the command '7' to quit the application.
    public ScoreboardApp() throws FileNotFoundException {
        scoreboard = new Scoreboard("My Soccer Scoreboard");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runScoreboard();
    }

    private void runScoreboard() {
        boolean keepRunning = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepRunning) {

            displayOptions();
            command = input.next();

            if (command.equals("7")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }
    }

    private void displayOptions() {
        System.out.println("Type the number corresponding to your desired action:");
        System.out.println("1. Add team");
        System.out.println("2. Add game");
        System.out.println("3. View teams");
        System.out.println("4. View game history for a team");
        System.out.println("5. Save scoreboard to file");
        System.out.println("6. Load scoreboard from file");
        System.out.println("7. Quit");
    }

    private void processCommand(String command) {
        if (command.equals("1")) {
            addTeamMain(scoreboard);
        } else if (command.equals("2")) {
            addGameMain(scoreboard);
        } else if (command.equals("3")) {
            viewTeamsMain(scoreboard);
        } else if (command.equals("4")) {
            viewGameHistoryMain(scoreboard);
        } else if (command.equals("5")) {
            saveScoreboard();
        } else if (command.equals("6")) {
            loadScoreboard();
        } else {
            System.out.println("Invalid selection");
        }
    }

    // EFFECTS: Adds a team with the given name to the league, only if a team with the same name has not
    // already been added to the league
    private static void addTeamMain(Scoreboard scoreboard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the team name");
        String teamName = scanner.nextLine();
        Team team = new Team(teamName);
        if (scoreboard.addTeam(team)) {
            System.out.println("Team successfully added!");
        } else {
            System.out.println("Team was already added, or a team with the same name already exists");
        }
    }

    // EFFECTS: Adds a game with the given teams and given score to the league, only if two different teams that exist
    // were entered by the user.
    private static void addGameMain(Scoreboard scoreboard) {
        Scanner scanner = new Scanner(System.in);

        if (scoreboard.getTeams().size() < 2) {
            System.out.println("You must add at least two teams to add a game.");
            return;
        }

        System.out.println("Enter the name of team one");
        String team1 = scanner.nextLine();
        System.out.println("Enter the name of team two");
        String team2 = scanner.nextLine();
        if ((scoreboard.findTeamByName(team2) == null) || (scoreboard.findTeamByName(team1) == null)) {
            System.out.println("At least one of these teams does not exist");
            return;
        }

        if (team1.equals(team2)) {
            System.out.println("You must select two different teams to play");
            return;
        }

        System.out.println("Enter the number of goals scored by team one");
        int goal1 = scanner.nextInt();
        System.out.println("Enter the number of goals scored by team two");
        int goal2 = scanner.nextInt();
        scoreboard.addGame(new Game(scoreboard.findTeamByName(team1), scoreboard.findTeamByName(team2), goal1, goal2));
        System.out.println("Game was successfully added!");

    }

    // EFFECTS: Displays the team name, wins, losses, ties, goals for, goals against, and points for each team in the
    // league, in the order in which they were added to the league.
    private static void viewTeamsMain(Scoreboard scoreboard) {
        ArrayList<Team> teams = scoreboard.getTeams();
        System.out.println("----------------SCOREBOARD---------------");
        System.out.println("--Team--Wins--Losses--Ties--GF--GA--Pts--");

        for (Team t: teams) {
            System.out.println(t.getName() + " " + t.getWins() + " " + t.getLosses() + " " + t.getTies() + " "
                    + t.getGoalsFor() + " " + t.getGoalsAgainst() + " " + t.getPoints());
        }

        System.out.println("-----------------------------------------");
    }

    // EFFECTS: For the given team, if it exists, displays their past games in the order in which they were added,
    // each game shows which teams played and their respective scores, informs user if they entered a team that is not
    // in the league/does not exist.
    private static void viewGameHistoryMain(Scoreboard scoreboard) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What team would you like to see the game history for?");
        String teamName = scanner.nextLine();

        if (scoreboard.findTeamByName(teamName) == null) {
            System.out.println("That team does not exist");
            return;
        }

        ArrayList<Game> games = scoreboard.findTeamByName(teamName).getGameHistory();

        System.out.println("----Team1--Goals1--Goals2--Team2----");
        for (Game g: games) {
            System.out.println(g.getTeam1().getName() + " " + g.getGoals1() + " " + g.getGoals2() + " "
                    + g.getTeam2().getName());
        }
        System.out.println("------------------------------------");
    }

    // EFFECTS: saves the scoreboard to file
    private void saveScoreboard() {
        try {
            jsonWriter.open();
            jsonWriter.write(scoreboard);
            jsonWriter.close();
            System.out.println("Saved " + scoreboard.getLeagueName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads scoreboard from file
    private void loadScoreboard() {
        try {
            scoreboard = jsonReader.read();
            System.out.println("Loaded " + scoreboard.getLeagueName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
