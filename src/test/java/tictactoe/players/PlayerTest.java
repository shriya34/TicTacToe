package tictactoe.players;

import org.junit.jupiter.api.Test;
import tictactoe.strategies.FirstAvailableStrategy;
import tictactoe.strategies.MoveStrategy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PlayerTest {

    @Test
    public void testPlayerUsesInjectedStrategy() {
        MoveStrategy strategy = new FirstAvailableStrategy();
        Player player = new Player(strategy);

        char[][] board = {
                {'X', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        int[] move = player.chooseMove(board);
        assertArrayEquals(new int[]{0, 1}, move);
    }
}
