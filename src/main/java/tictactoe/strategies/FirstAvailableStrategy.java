package tictactoe.strategies;

public class FirstAvailableStrategy implements MoveStrategy {

    @Override
    public int[] chooseMove(char[][] board) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    return new int[]{row, col};
                }
            }
        }
        return new int[0];
    }
}
