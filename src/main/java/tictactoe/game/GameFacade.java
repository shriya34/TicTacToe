package tictactoe.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade pattern: Provides a simplified interface for the UI (GameWindow)
 */
public class GameFacade {

    private final List<GameObserver> observers = new ArrayList<>();

    private final char[][] tempGrid = new char[3][3];
    private char currentTurn = 'X';
    private boolean vsComputer = false;
    private boolean gameOver = false;

    public GameFacade() {
        resetTempBoard();
    }

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
        this.currentTurn = 'X';
        this.gameOver = false;
        resetTempBoard();
        notifyObservers("Player X's Turn");
    }

    public void handleCellClick(int row, int col) {
        if (gameOver || row < 0 || row >= 3 || col < 0 || col >= 3) {
            return;
        }
        if (tempGrid[row][col] != ' ') {
            return; // cell already occupied
        }

        tempGrid[row][col] = currentTurn;

        currentTurn = (currentTurn == 'X') ? 'O' : 'X';
        String status = (vsComputer && currentTurn == 'O')
                ? "Computer (O)'s Turn"
                : "Player " + currentTurn + "'s Turn";

        notifyObservers(status);
    }

    protected void notifyObservers(String statusText) {
        // send a copy of the grid to observers
        char[][] gridCopy = new char[3][3];
        for (int r = 0; r < 3; r++) {
            System.arraycopy(tempGrid[r], 0, gridCopy[r], 0, 3);
        }
        for (GameObserver observer : observers) {
            observer.onGameStateChanged(gridCopy, statusText, gameOver);
        }
    }

    private void resetTempBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                tempGrid[r][c] = ' ';
            }
        }
    }
}