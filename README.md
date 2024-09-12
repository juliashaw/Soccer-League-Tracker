# My Personal Project

## Proposal

The application will display a **scoreboard** for a soccer league, with the ability to **click** on any team and get
their **game history**. The scoreboard will include columns for the team names, wins, losses, ties, goals for, goals
against, and points(win=3pts, tie=1pt, loss=0pt), each row will contain these statistics for a specific team. Clicking
on a team's name will give you their game history, showing the date of the game, the team they played against, and the
score.

The coordinators of soccer leagues can use this application to easily **display the statistics** from the league. The
information to be displayed can be viewed by coaches, players, and the players' friends and family on the league's
website. This information is important to display to those involved in the league so that they are aware of their
standings and how many more games they need to win in order to win the league. This project is of interest to me as I
have played competitive soccer since I was seven years old and am currently participating in the Handley Cup Soccer
League here at UBC.

## User Stories

- As a user, I want to be able to create a team and add it to the league.
- As a user, I want to be able to view a list of teams in the league, with their corresponding statistics.
- As a user, I want to be able to add a game to the league.
- As a user, I want to be able to select a team and view their game history
- As a user, I want to be able to save my soccer league scoreboard to file (if I so choose)
- As a user, I want to be able to reload my soccer league scoreboard from file (if I so choose)
- As a user, I want to be able to add multiple teams to a scoreboard (UI)
- As a user, I want to be able to load and save the state of the scoreboard (UI)

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by
  typing a unique team name into the box beside the "Add team" button, then clicking the "Add team" button.
  Your new team should appear on the right side panel.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by
  first ensuring you have added at least two teams. Now enter the name of the first team, name of the second team,
  goals scored by the first team, and goals scored by the second team into the corresponding text boxes to the
  right of the "Add game" button. Now click the "Add game" button, the stats listed beside the teams on the right
  side panel should be changed accordingly.
- You can locate my visual component by looking on the right side panel after adding a new team
- You can save the state of my application by clicking the "Save" button
- You can reload the state of my application by clicking the "Load" button

# Phase 4: Task 2

Here is a representative sample of the events that occur while the Scoreboard application is run.

Tue Nov 28 13:28:39 PST 2023
Team A was added to the scoreboard.
Tue Nov 28 13:28:50 PST 2023
Team B was added to the scoreboard.
Tue Nov 28 13:29:08 PST 2023
A game was added to the scoreboard.
Tue Nov 28 13:29:11 PST 2023
A game was added to the scoreboard.

# Phase 4: Task 3

If I had more time to spend on my project, I would make a few changes and refactor to improve the design. First, I
would remove the following classes from being inside the ScoreboardUI class: AddTeamAction, AddGameAction, SaveAction,
and LoadAction. Next, I would break down large and complex methods, specifically within the ScoreboardUI class, so that they are more manageable.

Removing the (AddTeam, AddGame, Save, Load)Action classes from inside the ScoreboardUI class would make it easier for
readers to understand the design of my project. Additionally, using the Singleton design pattern to create a unique tag
for each Team instance would improve the readability of my project. Using a unique ID field to reference a specific
team(such as when we add a game), and a name field to display or pass the name of a team(when we display the teams
already added to the scoreboard) would be much easier to understand than having to look more closely in order to discern
whether the team name field is being used to reference a specific team, or display that teams name. Finally, simply
breaking down large/complex methods into smaller and easier to understand methods would improve the readability of my
code, especially if this is done within the ScoreboardUI class.
