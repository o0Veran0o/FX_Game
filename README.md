Knight Escape - A 2D JavaFX Adventure

Overview

Knight Escape is a 2D JavaFX game built using the MVC architecture and JavaFX libraries. The game immerses players in the role of a brave knight who must escape a treacherous castle filled with skeletons. Armed with a sword and wits, players will fight enemies, manage resources, and navigate dangerous corridors.

Features

Attack System: Engage in melee combat with skeleton enemies.

Save/Load System: Save your progress and pick up right where you left off.

Inventory Management: Collect, view, and use items to aid your escape.

Getting Started

Prerequisites

Java 17 or later

JavaFX SDK (compatible with your Java version)

Installation

Clone the repository:

git clone https://gitlab.fel.cvut.cz/nabokval/game.git

Navigate to the project directory:

cd game

Import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

Ensure JavaFX libraries are added to the project module settings.

Build and run the application from the main class KnightEscapeApp.

How to Play

Objective: Escape the castle while fighting skeletons and managing your resources.

Controls:

Movement: Arrow keys or WASD

Attack: Spacebar

Open Inventory: I

Save Game: F5

Load Game: F9

Collect items and use them strategically to survive.

Gameplay Images

Main Menu



Combat Scene



Inventory System



Development

Project Structure

The game is developed using the Model-View-Controller (MVC) pattern:

Model: Handles game data such as player stats, enemies, and items.

View: Manages the graphical interface using JavaFX scenes and nodes.

Controller: Connects user input and updates the game state.

Key Files

MainApp.java: Entry point for the application.

GameController.java: Manages game logic and user interactions.

Player.java: Represents the knight character.

Skeleton.java: Represents enemy skeletons.

Inventory.java: Handles item collection and usage.

Development Images

Class Diagram



Game Loop Structure



Contributing

Contributions are welcome! To get started:

Fork the repository.

Create a feature branch:

git checkout -b feature-name

Commit your changes:

git commit -m "Add feature description"

Push to the branch:

git push origin feature-name

Create a merge request.

License

This project is licensed under the MIT License. See the LICENSE file for details.

Acknowledgments

Special thanks to all contributors and the JavaFX community for making this project possible.

Project Status

The game is under active development. New features and improvements are planned, including:

Additional enemy types.

Enhanced inventory system.

More levels and puzzles.

Stay tuned for updates!

