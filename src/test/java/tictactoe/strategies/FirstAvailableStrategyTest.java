package tictactoe.strategies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FirstAvailableStrategyTest {
    @Test
    void testFirstAvailableMove() {
        MoveStrategy strategy = new FirstAvailableStrategy();

        char[][] board = {
                {'X', 'O', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        int[] move = strategy.chooseMove(board);
        assertArrayEquals(new int[]{0,2}, move);
    }
}
