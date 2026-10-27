package tictactoe.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tictactoe.game.GameFacade;
import tictactoe.game.GameObserver;

import static org.junit.jupiter.api.Assertions.*;

class GameWindowTest {

    private static class FakeGameFacade extends GameFacade {
        int lastClickedRow = -1;
        int lastClickedCol = -1;
        boolean startNewGameCalled = false;
        boolean lastVsComputerMode = false;
        GameObserver registeredObserver = null;

        @Override
        public void addObserver(GameObserver observer) {
            this.registeredObserver = observer;
        }

        @Override
        public void handleCellClick(int row, int col) {
            this.lastClickedRow = row;
            this.lastClickedCol = col;
        }

        @Override
        public void startNewGame(boolean vsComputer) {
            this.startNewGameCalled = true;
            this.lastVsComputerMode = vsComputer;
        }
    }

    private FakeGameFacade fakeFacade;
    private GameWindow window;

    @BeforeEach
    void setUp() {
        fakeFacade = new FakeGameFacade();
        window = new GameWindow(fakeFacade);
    }

    @Test
    void testWindowRegistersAsObserverOnCreation() {
        assertSame(window, fakeFacade.registeredObserver,
                "GameWindow should register itself as a GameObserver via Dependency Injection.");
    }

    @Test
    void testBoardButtonClickTriggersFacadeWithCorrectCoordinates() {
        // clicking row 1, col 2
        window.getBoardButton(1, 2).doClick();

        assertEquals(1, fakeFacade.lastClickedRow);
        assertEquals(2, fakeFacade.lastClickedCol);
    }

    @Test
    void testObserverUpdateRendersMarksAndDisablesOccupiedButtons() {
        char[][] grid = {
                {'X', ' ', ' '},
                {' ', 'O', ' '},
                {' ', ' ', ' '}
        };

        window.onGameStateChanged(grid, "Player X's Turn", false);

        assertEquals("X", window.getBoardButton(0, 0).getText());
        assertFalse(window.getBoardButton(0, 0).isEnabled(), "Occupied cell (0,0) should be disabled");

        assertEquals("O", window.getBoardButton(1, 1).getText());
        assertFalse(window.getBoardButton(1, 1).isEnabled(), "Occupied cell (1,1) should be disabled");

        assertEquals("", window.getBoardButton(0, 1).getText());
        assertTrue(window.getBoardButton(0, 1).isEnabled(), "Empty cell (0,1) should remain enabled");
        assertEquals("Player X's Turn", window.getStatusLabel().getText());
    }

    @Test
    void testObserverGameOverDisablesAllButtons() {
        char[][] grid = {
                {'X', 'X', 'X'},
                {'O', 'O', ' '},
                {' ', ' ', ' '}
        };

        window.onGameStateChanged(grid, "Player X Wins!", true);

        assertEquals("Player X Wins!", window.getStatusLabel().getText());
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertFalse(window.getBoardButton(r, c).isEnabled(),
                        "All buttons should be disabled when isGameOver is true");
            }
        }
    }

    @Test
    void testModeSelectionAndRestartButtonTriggerNewGame() {
        // switch to player vs computer mode
        window.getPvcRadioButton().doClick();
        assertTrue(fakeFacade.startNewGameCalled);
        assertTrue(fakeFacade.lastVsComputerMode, "Should start a PvC game when PvC radio is clicked");

        // reset spy flag and test restart button
        fakeFacade.startNewGameCalled = false;
        window.getRestartButton().doClick();
        assertTrue(fakeFacade.startNewGameCalled, "Clicking Restart should trigger startNewGame");
        assertTrue(fakeFacade.lastVsComputerMode, "Restart should preserve currently selected PvC mode");
    }
}