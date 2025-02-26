# Knight's Escape

## Description

Knight's Escape is a 2D JavaFX game built using the MVC architectural pattern. The game follows a knight trapped in a skeleton-infested castle, and the player's objective is to guide the knight to freedom. The game features combat against skeletons, a save/load system, and an inventory system for managing items.

## Screenshots

![Screenshot 1](images/bait_png.png)  
*A knight battling a skeleton.*

![Screenshot 2](images/inventory.png)
*The game's inventory system.*

![Screenshot 3](diagram/java.png)
*Base project uml Structure ( its simplified for better understanding) *

[//]: # (Optional: Add more screenshots with captions.)



## Features

* **2D Graphics:** Experience the game world through engaging 2D visuals powered by JavaFX.
* **MVC Architecture:** Cleanly separated Model, View, and Controller components for maintainability and scalability.
* **Combat System:** Engage in thrilling battles against hordes of skeletons using various attacks.
* **Save/Load System:** Save your progress and resume your adventure at any time.
* **Inventory System:** Manage collected items and utilize them strategically.



## Getting Started

### Prerequisites

* **Java JDK 11 or higher**
* JavaFX libraries (if not included with your JDK distribution)



## Running the Game

1. **Clone the Repository:** `git clone https://gitlab.fel.cvut.cz/nabokval/javaFX_Game.git`
2. **Navigate to Project Directory:** `cd game`
3. **Run:** `java -jar Game.jar`  (Make sure `Game.jar` is in the `game` directory)




## Project Structure

The project follows the MVC pattern:

* **Model:** Contains the game logic, data structures, and game state.
* **View:** Handles the graphical representation of the game, including the user interface.
* **Controller:** Mediates between the model and view, handling user input and updating the game state.


## Controls

* **Movement:** Arrow keys or WASD
* **Attack:** Spacebar
* **Inventory:** 'I' key
* **Menu:** Escape


## Contributing

Contributions are welcome! Please fork the repository and submit pull requests for bug fixes, feature enhancements, or other improvements.


## Authors and Acknowledgment

 **Valera Nabok**


## Project Status

Actively under development.