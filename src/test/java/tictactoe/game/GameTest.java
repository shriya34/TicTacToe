package tictactoe.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testXGoesFirst() {
        assertEquals('X', game.getCurrentPlayer());
    }

    @Test
    void testTurnSwitching() {
        assertTrue(game.makeMove(0, 0));
        assertEquals('O', game.getCurrentPlayer());

        assertTrue(game.makeMove(0, 1));
        assertEquals('X', game.getCurrentPlayer());
    }

    @Test
    void testInvalidMoveDoesNotSwitchTurn() {
        game.makeMove(0, 0);
        assertFalse(game.makeMove(0, 0));
        assertEquals('O', game.getCurrentPlayer());
    }
}
