package tictactoe.players;

import tictactoe.strategies.MoveStrategy;

public class Player {
    private final MoveStrategy strategy;

    public Player(MoveStrategy strategy) {
        this.strategy = strategy;
    }

    public int[] chooseMove(char[][] board) {
        return strategy.chooseMove(board);
    }
}
