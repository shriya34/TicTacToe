package tictactoe.game;

public class Board {
    private final char[][] cells = new char[3][3];

    public Board() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cells[row][col] = ' ';
            }
        }
    }

    public char getCell(int row, int col) {
        return cells[row][col];
    }

    public boolean placeMark(int row, int col, char mark) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return false;
        }

        if (cells[row][col] != ' ') {
            return false;
        }

        cells[row][col] = mark;
        return true;
    }

    // TODO: reject other marks, only accept 'X'/'O'
    // TODO: too many magic numbers, could define row/col size with public static final int
}
