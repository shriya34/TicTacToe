package tictactoe.game;


public interface GameObserver {
    /**
     * Called whenever a move is made, the turn switches, or the game resets/ends.
     *
     * @param boardGrid   3x3 char array
     * @param statusText  Message to display
     * @param isGameOver  True if the game has ended (win or tie)
     */
    void onGameStateChanged(char[][] boardGrid, String statusText, boolean isGameOver);
}