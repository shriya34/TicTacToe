package tictactoe.game;

public class Game {
    private final Board board;
    private char currentPlayer;

    public Game() {
        board = new Board();
        currentPlayer = 'X';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int row, int col) {
        if (!board.placeMark(row, col, currentPlayer)) {
            return false;
        }

        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
        return true;
    }

    // TODO: detect wins/ties
}
