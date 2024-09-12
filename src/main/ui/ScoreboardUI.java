package ui;

import model.Event;
import model.EventLog;
import model.Game;
import model.Scoreboard;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.Integer.parseInt;

// Code influenced by
// https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html

// Represents the GUI for a Scoreboard application
public class ScoreboardUI {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Scoreboard sb;
    private JFrame frame;
    private JPanel teamsPanel;
    private JPanel buttonsPanel;
    private JTextField addTeamField;
    private final JLabel soccerBallLabel;
    private JTextField team1;
    private JTextField team2;
    private JTextField goals1;
    private JTextField goals2;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/UI_data.json";

    // EFFECTS: creates a GUI with the appropriate buttons and text boxes to add teams/games
    // and save/load the state to/from file.
    public ScoreboardUI() {
        sb = new Scoreboard("My Soccer Scoreboard");

        setupFrame();
        setupTeamsPanel();
        setupButtonsPanel();

        ImageIcon soccerBallImage = new ImageIcon("./data/soccerBall.png");
        soccerBallLabel = new JLabel(soccerBallImage);
        soccerBallLabel.setVisible(false);
        teamsPanel.add(soccerBallLabel);

        frame.add(buttonsPanel);
        frame.add(teamsPanel);
        frame.setVisible(true);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: creates a new JFrame object with a width and height
    private void setupFrame() {
        frame = new JFrame("My Soccer Scoreboard");
        frame.setLayout(new GridLayout(1, 2));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog eventLog = EventLog.getInstance();
                for (Event event : eventLog) {
                    System.out.println(event.toString());
                }
                System.exit(0);
            }
        });
    }



    // EFFECTS: creates a panel that vertically displays the teams that have been added to the scoreboard
    // with some basic stats listed beside each team
    private void setupTeamsPanel() {
        teamsPanel = new JPanel();
        teamsPanel.setBackground(Color.GRAY);
        teamsPanel.setLayout(new BoxLayout(teamsPanel, BoxLayout.Y_AXIS));

        JLabel teamsLabel = new JLabel("Teams---Wins---Losses---Ties");
        teamsPanel.add(teamsLabel);
    }

    // EFFECTS: Adds two sub-panels and 2 buttons to the buttonsPanel on the left side
    private void setupButtonsPanel() {
        JPanel addTeamPanel = new JPanel();
        JButton saveButton = new JButton(new SaveAction());
        JButton loadButton = new JButton(new LoadAction());
        JPanel addGamePanel = new JPanel();

        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.LIGHT_GRAY);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));


        setupAddTeamPanel(addTeamPanel);
        setupAddGamePanel(addGamePanel);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(loadButton);
    }

    // EFFECTS: Adds appropriate button and text box to the AddTeam panel displayed within the buttonsPanel
    private void setupAddTeamPanel(JPanel panel) {
        JButton addTeamButton = new JButton(new AddTeamAction());
        addTeamField = new JTextField("Name");

        panel.add(addTeamButton);
        panel.add(addTeamField);
        buttonsPanel.add(panel);
    }

    // EFFECTS: Adds appropriate button and text boxes to the AddGame panel displayed within the buttonsPanel
    private void setupAddGamePanel(JPanel panel) {
        JButton addGameButton = new JButton(new AddGameAction());
        team1 = new JTextField("T1 Name");
        team2 = new JTextField("T2 Name");
        goals1 = new JTextField("T1 Goals");
        goals2 = new JTextField("T2 Goals");

        panel.add(addGameButton);
        panel.add(team1);
        panel.add(team2);
        panel.add(goals1);
        panel.add(goals2);
        buttonsPanel.add(panel);
    }

    // Represents the action to be taken when the user presses the AddTeam button
    private class AddTeamAction extends AbstractAction {
        AddTeamAction() {
            super("Add Team");
        }

        @Override
        // EFFECTS: adds the new team to the scoreboard, updates the teamsPanel accordingly
        public void actionPerformed(ActionEvent evt) {
            String teamName = addTeamField.getText();
            if (sb.addTeam(new Team(teamName))) {
                int teamWins = sb.findTeamByName(teamName).getWins();
                int teamLosses = sb.findTeamByName(teamName).getLosses();
                int teamTies = sb.findTeamByName(teamName).getTies();
                JLabel teamsPanelTitle = new JLabel(teamName
                        + "     " + teamWins + "     " + teamLosses  + "     " + teamTies);
                teamsPanel.add(teamsPanelTitle);
                teamsPanel.revalidate();
                teamsPanel.repaint();

                soccerBallLabel.setVisible(true);
                Timer timer = new Timer(2000, e -> soccerBallLabel.setVisible(false));
                timer.setRepeats(false);
                timer.start();
            }

        }
    }

    // Represents the action to be taken when the user presses the AddGame button
    private class AddGameAction extends AbstractAction {
        AddGameAction() {
            super("Add Game");
        }

        @Override
        // EFFECTS: adds the new game to the scoreboard, updates the teamsPanel accordingly
        public void actionPerformed(ActionEvent evt) {
            Game newGame = new Game(sb.findTeamByName(team1.getText()), sb.findTeamByName(team2.getText()),
                    parseInt(goals1.getText()), parseInt(goals2.getText()));
            sb.addGame(newGame);
            teamsPanel.removeAll();
            JLabel teamsLabel = new JLabel("Teams---Wins---Losses---Ties");
            teamsPanel.add(teamsLabel);

            for (Team t : sb.getTeams()) {
                int teamWins = sb.findTeamByName(t.getName()).getWins();
                int teamLosses = sb.findTeamByName(t.getName()).getLosses();
                int teamTies = sb.findTeamByName(t.getName()).getTies();
                JLabel teamsPanelTitle = new JLabel(t.getName()
                        + "     " + teamWins + "     " + teamLosses  + "     " + teamTies);
                teamsPanel.add(teamsPanelTitle);
            }
            teamsPanel.revalidate();
            teamsPanel.repaint();
        }
    }

    // Represents the action to be taken when the user presses the Save button
    private class SaveAction extends AbstractAction {
        SaveAction() {
            super("Save");
        }

        @Override
        // EFFECTS: saves the application at its current state to file
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(sb);
                jsonWriter.close();
                System.out.println("Saved " + sb.getLeagueName() + " to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // Represents the action to be taken when the user presses the Load button
    private class LoadAction extends AbstractAction {
        LoadAction() {
            super("Load");
        }

        @Override
        // EFFECTS: loads the state of the application from file
        public void actionPerformed(ActionEvent evt) {
            try {
                sb = jsonReader.read();

                for (Team t : sb.getTeams()) {
                    int teamWins = sb.findTeamByName(t.getName()).getWins();
                    int teamLosses = sb.findTeamByName(t.getName()).getLosses();
                    int teamTies = sb.findTeamByName(t.getName()).getTies();
                    JLabel teamsPanelTitle = new JLabel(t.getName()
                            + "     " + teamWins + "     " + teamLosses + "     "
                            + teamTies);
                    teamsPanel.add(teamsPanelTitle);
                }

                System.out.println("Loaded " + sb.getLeagueName() + " from " + JSON_STORE);
                teamsPanel.revalidate();
                teamsPanel.repaint();
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // Runs the Scoreboard application with UI
    public static void main(String[] args) {
        new ScoreboardUI();
    }
}
