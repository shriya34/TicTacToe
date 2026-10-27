package tictactoe.ui;

import tictactoe.game.GameFacade;
import tictactoe.game.GameObserver;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame implements GameObserver {

    private final GameFacade gameFacade;
    private final JButton[][] boardButtons;
    private final JLabel statusLabel;
    private final JRadioButton pvpRadioButton;
    private final JRadioButton pvcRadioButton;
    private final JButton restartButton;

    // dependency injection - GameFacade is passed into GameWindow
    public GameWindow(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
        this.boardButtons = new JButton[3][3];
        this.statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        this.pvpRadioButton = new JRadioButton("Player vs. Player", true);
        this.pvcRadioButton = new JRadioButton("Player vs. Computer", false);
        this.restartButton = new JButton("Restart Game");

        initializeUI();
        this.gameFacade.addObserver(this);
    }

    private void initializeUI() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // top panel - status and mode selection
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(statusLabel);

        JPanel modePanel = new JPanel(new FlowLayout());
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(pvpRadioButton);
        modeGroup.add(pvcRadioButton);
        modePanel.add(pvpRadioButton);
        modePanel.add(pvcRadioButton);
        topPanel.add(modePanel);

        // switching mode starts a new game in that mode
        pvpRadioButton.addActionListener(e -> gameFacade.startNewGame(false));
        pvcRadioButton.addActionListener(e -> gameFacade.startNewGame(true));

        add(topPanel, BorderLayout.NORTH);

        // center panel - 3x3 board
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        Font buttonFont = new Font("Arial", Font.BOLD, 48);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton cellButton = new JButton("");
                cellButton.setFont(buttonFont);
                cellButton.setFocusPainted(false);

                final int r = row;
                final int c = col;
                cellButton.addActionListener(e -> gameFacade.handleCellClick(r, c));

                boardButtons[row][col] = cellButton;
                boardPanel.add(cellButton);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // bottom panel - restart button
        JPanel bottomPanel = new JPanel(new FlowLayout());
        restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
        restartButton.addActionListener(e -> {
            boolean vsComputer = pvcRadioButton.isSelected();
            gameFacade.startNewGame(vsComputer);
        });
        bottomPanel.add(restartButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void onGameStateChanged(char[][] boardGrid, String statusText, boolean isGameOver) {
        statusLabel.setText(statusText);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char mark = boardGrid[row][col];
                String text = (mark == 'X' || mark == 'O') ? String.valueOf(mark) : "";
                boardButtons[row][col].setText(text);
                // disable clicked cells or all cells if the game is over
                boardButtons[row][col].setEnabled(!isGameOver && text.isEmpty());
            }
        }

    }

    // run this for ui to pop-up :)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFacade facade = new GameFacade();
            GameWindow window = new GameWindow(facade);
            window.setLocationRelativeTo(null); // Center on screen
            window.setVisible(true);
            facade.startNewGame(false);
        });
    }

    public JButton getBoardButton(int row, int col) { return boardButtons[row][col]; }

    public JLabel getStatusLabel() { return statusLabel; }

    public JButton getRestartButton() { return restartButton; }

    public JRadioButton getPvpRadioButton() { return pvpRadioButton; }

    public JRadioButton getPvcRadioButton() { return pvcRadioButton; }
}