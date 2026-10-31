package tictactoe.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade pattern: Provides a simplified interface for the UI (GameWindow)
 */
public class GameFacade {
    private Game game = new Game();

    private final List<GameObserver> observers = new ArrayList<>();

    private boolean vsComputer = false;
    private boolean gameOver = false;

    public void addObserver(GameObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void startNewGame(boolean vsComputer) {
        this.vsComputer = vsComputer;
        this.game = new Game();
        this.gameOver = false;
        notifyObservers("Player X's Turn");
    }

    public void handleCellClick(int row, int col) {
        if (gameOver || !game.makeMove(row, col)) {
            return;
        }

        char currentTurn = game.getCurrentPlayer();

        String status = (vsComputer && currentTurn == 'O')
                ? "Computer (O)'s Turn"
                : "Player " + currentTurn + "'s Turn";

        notifyObservers(status);
    }

    protected void notifyObservers(String statusText) {
        // send a copy of the grid to observers
        char[][] gridCopy = new char[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gridCopy[r][c] = game.getBoard().getCell(r, c);
            }
        }

        for (GameObserver observer : observers) {
            observer.onGameStateChanged(gridCopy, statusText, gameOver);
        }
    }
}