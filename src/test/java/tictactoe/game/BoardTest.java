package tictactoe.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testNewBoardIsEmpty() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertEquals(' ', board.getCell(row, col));
            }
        }
    }

    @Test
    void testPlaceMark() {
        assertTrue(board.placeMark(0, 0, 'X'));
        assertEquals('X', board.getCell(0,0));
    }

    @Test
    void testOccupiedCell() {
        board.placeMark(0, 0, 'X');
        assertFalse(board.placeMark(0, 0, 'O'));
        assertEquals('X', board.getCell(0,0));
    }

    @Test
    void testOutOfBoundsMove() {
        assertFalse(board.placeMark(3, 0, 'X'));
        assertFalse(board.placeMark(-1, 0, 'X'));
        assertFalse(board.placeMark(0, 3, 'O'));
        assertFalse(board.placeMark(0, -1, 'O'));
    }
}
