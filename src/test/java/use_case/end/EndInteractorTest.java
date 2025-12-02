package use_case.end;

import entity.AbstractWord;
import entity.Stats;
import entity.WordFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndInteractorTest {

    @Test
    void successExecuteWinTest() {
        final EndDataAccessInterface endRepository = new EndDataAccessInterface() {
            private Stats stats = new Stats(5, 3, 2, 3);

            @Override
            public Stats getStats() {
                return stats;
            }

            @Override
            public void saveStats(Stats stats) {
                this.stats = stats;
            }
        };

        final EndOutputBoundary successPresenter = new EndOutputBoundary() {
            @Override
            public void prepareSuccessView(EndOutputData outputData) {
                assertEquals("HELLO", outputData.getWord());
                assertTrue(outputData.isWon());
                assertEquals(3, outputData.getGuessesUsed());
                assertEquals(6, outputData.getMaxGuesses());
                assertNotNull(outputData.getMessage());
                assertTrue(outputData.getMessage().contains("HELLO"));
                assertNotNull(outputData.getGuessHistory());
                assertEquals(6, outputData.getGuessHistory().length);
            }

            @Override
            public void prepareFailView(EndOutputData outputData) {
                fail("prepareFailView should not be called for a win.");
            }

            @Override
            public void prepareGameView() {
                fail("prepareGameView should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }
        };

        final EndInputBoundary interactor = new EndInteractor(endRepository, successPresenter);

        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 3, 6, guessHistory);
        interactor.execute(inputData);

        // Verify stats were updated correctly for a win
        final Stats updatedStats = endRepository.getStats();
        assertEquals(6, updatedStats.getGamesPlayed());
        assertEquals(4, updatedStats.getWins());
        assertEquals(3, updatedStats.getCurrentStreak());
        assertEquals(3, updatedStats.getMaxStreak());
    }

    @Test
    void successExecuteLossTest() {
        final EndDataAccessInterface endRepository = new EndDataAccessInterface() {
            private Stats stats = new Stats(5, 3, 2, 3);

            @Override
            public Stats getStats() {
                return stats;
            }

            @Override
            public void saveStats(Stats stats) {
                this.stats = stats;
            }
        };

        final EndOutputBoundary successPresenter = new EndOutputBoundary() {
            @Override
            public void prepareSuccessView(EndOutputData outputData) {
                fail("prepareSuccessView should not be called for a loss.");
            }

            @Override
            public void prepareFailView(EndOutputData outputData) {
                assertEquals("WORLD", outputData.getWord());
                assertFalse(outputData.isWon());
                assertEquals(6, outputData.getGuessesUsed());
                assertEquals(6, outputData.getMaxGuesses());
                assertNotNull(outputData.getMessage());
                assertTrue(outputData.getMessage().contains("WORLD"));
                assertNotNull(outputData.getGuessHistory());
            }

            @Override
            public void prepareGameView() {
                fail("prepareGameView should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }
        };

        final EndInputBoundary interactor = new EndInteractor(endRepository, successPresenter);

        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("WORLD", false, 6, 6, guessHistory);
        interactor.execute(inputData);

        // Verify stats were updated correctly for a loss
        final Stats updatedStats = endRepository.getStats();
        assertEquals(6, updatedStats.getGamesPlayed());
        assertEquals(3, updatedStats.getWins());
        assertEquals(0, updatedStats.getCurrentStreak());
        assertEquals(3, updatedStats.getMaxStreak());
    }

    @Test
    void successPrepareStartViewTest() {
        final EndDataAccessInterface endRepository = new EndDataAccessInterface() {
            @Override
            public Stats getStats() {
                return new Stats();
            }

            @Override
            public void saveStats(Stats stats) {
            }
        };

        final EndOutputBoundary successPresenter = new EndOutputBoundary() {
            @Override
            public void prepareSuccessView(EndOutputData outputData) {
                fail("prepareSuccessView should not be called.");
            }

            @Override
            public void prepareFailView(EndOutputData outputData) {
                fail("prepareFailView should not be called.");
            }

            @Override
            public void prepareGameView() {
                fail("prepareGameView should not be called.");
            }

            @Override
            public void prepareStartView() {
                assertTrue(true);
            }
        };

        final EndInputBoundary interactor = new EndInteractor(endRepository, successPresenter);
        interactor.prepareStartView();
    }

    @Test
    void successPrepareNewGameTest() {
        final EndDataAccessInterface endRepository = new EndDataAccessInterface() {
            @Override
            public Stats getStats() {
                return new Stats();
            }

            @Override
            public void saveStats(Stats stats) {
            }
        };

        final EndOutputBoundary successPresenter = new EndOutputBoundary() {
            @Override
            public void prepareSuccessView(EndOutputData outputData) {
                fail("prepareSuccessView should not be called.");
            }

            @Override
            public void prepareFailView(EndOutputData outputData) {
                fail("prepareFailView should not be called.");
            }

            @Override
            public void prepareGameView() {
                assertTrue(true);
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }
        };

        final EndInputBoundary interactor = new EndInteractor(endRepository, successPresenter);

        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 3, 6, guessHistory);
        interactor.prepareNewGame(inputData);
    }

    @Test
    void successExecuteFirstTryWinTest() {
        final EndDataAccessInterface endRepository = new EndDataAccessInterface() {
            private Stats stats = new Stats();

            @Override
            public Stats getStats() {
                return stats;
            }

            @Override
            public void saveStats(Stats stats) {
                this.stats = stats;
            }
        };

        final EndOutputBoundary successPresenter = new EndOutputBoundary() {
            @Override
            public void prepareSuccessView(EndOutputData outputData) {
                assertEquals(1, outputData.getGuessesUsed());
                assertTrue(outputData.getMessage().contains("First try!"));
            }

            @Override
            public void prepareFailView(EndOutputData outputData) {
                fail("prepareFailView should not be called.");
            }

            @Override
            public void prepareGameView() {
                fail("prepareGameView should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }
        };

        final EndInputBoundary interactor = new EndInteractor(endRepository, successPresenter);

        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 1, 6, guessHistory);
        interactor.execute(inputData);
    }

    @Test
    void successExecuteLastGuessWinTest() {
        final EndDataAccessInterface endRepository = new EndDataAccessInterface() {
            private Stats stats = new Stats();

            @Override
            public Stats getStats() {
                return stats;
            }

            @Override
            public void saveStats(Stats stats) {
                this.stats = stats;
            }
        };

        final EndOutputBoundary successPresenter = new EndOutputBoundary() {
            @Override
            public void prepareSuccessView(EndOutputData outputData) {
                assertEquals(6, outputData.getGuessesUsed());
                assertEquals(6, outputData.getMaxGuesses());
                assertTrue(outputData.getMessage().contains("That was close!"));
            }

            @Override
            public void prepareFailView(EndOutputData outputData) {
                fail("prepareFailView should not be called.");
            }

            @Override
            public void prepareGameView() {
                fail("prepareGameView should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }
        };

        final EndInputBoundary interactor = new EndInteractor(endRepository, successPresenter);

        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 6, 6, guessHistory);
        interactor.execute(inputData);
    }

    @Test
    void successExecuteWinUpdatesMaxStreakTest() {
        final EndDataAccessInterface endRepository = new EndDataAccessInterface() {
            private Stats stats = new Stats(5, 3, 4, 4);

            @Override
            public Stats getStats() {
                return stats;
            }

            @Override
            public void saveStats(Stats stats) {
                this.stats = stats;
            }
        };

        final EndOutputBoundary successPresenter = new EndOutputBoundary() {
            @Override
            public void prepareSuccessView(EndOutputData outputData) {
                assertTrue(outputData.isWon());
            }

            @Override
            public void prepareFailView(EndOutputData outputData) {
                fail("prepareFailView should not be called.");
            }

            @Override
            public void prepareGameView() {
                fail("prepareGameView should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }
        };

        final EndInputBoundary interactor = new EndInteractor(endRepository, successPresenter);

        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 3, 6, guessHistory);
        interactor.execute(inputData);

        // Verify max streak was updated
        final Stats updatedStats = endRepository.getStats();
        assertEquals(5, updatedStats.getCurrentStreak());
        assertEquals(5, updatedStats.getMaxStreak());
    }

    @Test
    void successExecuteWithEmptyInitialStatsTest() {
        final EndDataAccessInterface endRepository = new EndDataAccessInterface() {
            private Stats stats = new Stats();

            @Override
            public Stats getStats() {
                return stats;
            }

            @Override
            public void saveStats(Stats stats) {
                this.stats = stats;
            }
        };

        final EndOutputBoundary successPresenter = new EndOutputBoundary() {
            @Override
            public void prepareSuccessView(EndOutputData outputData) {
                assertTrue(outputData.isWon());
            }

            @Override
            public void prepareFailView(EndOutputData outputData) {
                fail("prepareFailView should not be called.");
            }

            @Override
            public void prepareGameView() {
                fail("prepareGameView should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }
        };

        final EndInputBoundary interactor = new EndInteractor(endRepository, successPresenter);

        final AbstractWord[] guessHistory = new AbstractWord[6];
        for (int i = 0; i < 6; i++) {
            guessHistory[i] = WordFactory.createEmptyWord(5);
        }

        final EndInputData inputData = new EndInputData("HELLO", true, 3, 6, guessHistory);
        interactor.execute(inputData);

        // Verify stats were created and updated
        final Stats updatedStats = endRepository.getStats();
        assertEquals(1, updatedStats.getGamesPlayed());
        assertEquals(1, updatedStats.getWins());
        assertEquals(1, updatedStats.getCurrentStreak());
        assertEquals(1, updatedStats.getMaxStreak());
    }
}