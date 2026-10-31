# Tic-Tac-Toe (Object-Oriented Design Project)

**Team Members:** Santana Reyes and Shriya Malu  
**Language & Platform:** Java / Java Swing (IntelliJ IDEA & JUnit 6)

## Project Overview
This project is an object-oriented Tic-Tac-Toe game with an interactive 3x3 Java Swing graphical user interface. Players can compete in **Player vs. Player** or **Player vs. Computer** modes. Our project tracks player turns, validates moves, detects wins and ties, and updates the GUI reactively.

---

## Design Patterns Used

### 1. Observer Pattern (GameObserver, GameWindow, GameFacade)
* Where it is used: tictactoe.game.GameObserver (interface), implemented by tictactoe.ui.GameWindow, and managed by tictactoe.game.GameFacade.
* How it works: GameWindow registers itself as an observer via gameFacade.addObserver(this). Whenever a valid move is made, the turn changes, or the game ends/resets, the subject invokes onGameStateChanged(char[][] boardGrid, String statusText, boolean isGameOver) on all registered observers, prompting GameWindow to update its 9 buttons and status label.

### 2. Facade Pattern (GameFacade)
* Where it is used: tictactoe.game.GameFacade
* How it works: Provides an interface to game subsystem (Board, Game, Player, PlayerFactory, and MoveStrategy). GameWindow interacts strictly with GameFacade (calling handleCellClick(row, col) and startNewGame(vsComputer)) without needing to manipulate Board coordinates, instantiate Player objects, or invoke strategies.

### 3. Strategy Pattern (MoveStrategy, FirstAvailableStrategy, RandomStrategy)
* Where it is used: tictactoe.strategies.MoveStrategy (interface), tictactoe.strategies.FirstAvailableStrategy (concrete implementation), and tictactoe.strategies.RandomStrategy (concrete implementation), used by tictactoe.players.Player.
* How it works: Computer players will delegate move selection to a MoveStrategy instance, allowing different behaviors (such as selecting the first available square through FirstAvailableStrategy, choosing a random square through RandomStrategy, or future smart/blocking strategies) to be swapped without changing the Player or Game classes.

### 4. Factory Pattern (PlayerFactory)
* Where it is used: tictactoe.players.PlayerFactory
* How it works: Centralizes the creation of Player instances. Rather than having Game or GameFacade manually construct players and bind strategies, PlayerFactory encapsulates the instantiation logic for creating Human players and Computer players configured with their respective symbols ('X' or 'O') and MoveStrategy implementations.

---

## Object-Oriented Principles

* **Abstractions:**
    * GameFacade notifies listeners through the GameObserver interface.
    * Player interacts with the MoveStrategy interface.
* **Polymorphism:**
    * Any class implementing MoveStrategy can provide its own implementation of move selection, and Player invokes the strategy polymorphically at runtime.
    * Any UI or test listener implementing GameObserver receives state updates polymorphically through onGameStateChanged.
* **Dependency Injection:**
    * GameWindow receives its GameFacade instance through constructor injection allowing unit tests (GameWindowTest) to inject a fake GameFacade to test GUI interactions.
    * Player receives its MoveStrategy through constructor injection from PlayerFactory.