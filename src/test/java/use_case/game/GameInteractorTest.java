package use_case.game;

import data_access.InMemoryWordDataAccessObject;
import data_access.WordNotFoundException;
import entity.WordFactory;
import interface_adapter.game.GameState;
import interface_adapter.options.OptionsViewModel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.end.EndInputBoundary;
import use_case.end.EndInputData;

import static org.junit.jupiter.api.Assertions.*;

class GameInteractorTest {
    private static void fillState(GameState state, String word) {
        for (char c : word.toCharArray()) {
            state.nextLetter(c);
        }
    }

    @Nested
    class ExecuteLetter {
        GameOutputBoundary gamePresenter = new GameOutputBoundary() {
            @Override
            public void updateGameView(GameState gameState) {
                assertTrue(true);
            }

            @Override
            public void shakeWord(GameState gameState) {
                fail("shakeWord should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareEndView(GameState gameState) {
                fail("prepareEndView should not be called.");
            }
        };

        @Test
        void testExecuteLetterEmpty() {
            final GameState testState = new GameState();
            final GameState expectedState = new GameState();
            expectedState.nextLetter('A');

            final GameInteractor interactor = new GameInteractor(null, gamePresenter,null,null);
            interactor.executeLetter(testState, 'A');

            assertEquals(testState, expectedState, "States do not match");
        }

        @Test
        void testExecuteLetterPartial() {
            final GameState testState = new GameState();
            fillState(testState, "AB");
            final GameState expectedState = new GameState();
            fillState(expectedState, "ABC");

            final GameInteractor interactor = new GameInteractor(null, gamePresenter,null,null);
            interactor.executeLetter(testState, 'C');

            assertEquals(testState, expectedState, "States do not match");
        }

        @Test
        void testExecuteLetterFull() {
            final GameState testState = new GameState();
            fillState(testState, "ABCDE");
            final GameState expectedState = new GameState();
            fillState(expectedState, "ABCDE");

            final GameInteractor interactor = new GameInteractor(null, gamePresenter,null,null);
            interactor.executeLetter(testState, 'F');

            assertEquals(testState, expectedState, "State should not change when trying to add letter to full word.");
        }
    }

    @Nested
    class ExecuteSubmit {
        private static final int LENGTH = 4;

        class TestGamePresenter implements GameOutputBoundary {
            @Override
            public void updateGameView(GameState gameState) {
                fail("updateGameView should not be called.");
            }

            @Override
            public void shakeWord(GameState gameState) {
                fail("shakeWord should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareEndView(GameState gameState) {
                fail("prepareEndView should not be called.");
            }
        }

        class TestEndInteractor implements EndInputBoundary {
            @Override
            public void execute(EndInputData inputData) {
                fail("execute should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareNewGame(EndInputData inputData) {
                fail("prepareNewGame should not be called.");
            }
        }
        final InMemoryWordDataAccessObject dao = new InMemoryWordDataAccessObject();

        @Test
        void testExecuteSubmitValidWord() {
            final GameOutputBoundary gamePresenter = new TestGamePresenter() {
                @Override
                public void updateGameView(GameState gameState) {
                    assertTrue(true);
                }
            };
            final EndInputBoundary endInteractor = new TestEndInteractor();
            final GameInteractor gameInteractor = new GameInteractor(dao, gamePresenter,endInteractor,null);

            final GameState testState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final GameState expectedState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);

            final String word = InMemoryWordDataAccessObject.getWrongWord(LENGTH);
            fillState(testState, word);
            fillState(expectedState, word);
            expectedState.submit();
            expectedState.nextWord();

            gameInteractor.executeSubmit(testState);
            assertEquals(testState, expectedState, "States do not match");
        }

        @Test
        void testExecuteSubmitInvalidWord() {
            final GameOutputBoundary gamePresenter = new TestGamePresenter() {
                @Override
                public void shakeWord(GameState gameState) {
                    assertTrue(true);
                }
            };
            final EndInputBoundary endInteractor = new TestEndInteractor();
            final GameInteractor gameInteractor = new GameInteractor(dao, gamePresenter,endInteractor,null);

            final GameState testState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final GameState expectedState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final String word = InMemoryWordDataAccessObject.getInvalidWord(LENGTH);
            fillState(testState, word);
            fillState(expectedState, word);

            gameInteractor.executeSubmit(testState);
            assertEquals(testState, expectedState, "States do not match");
        }

        @Test
        void testExecuteSubmitPartial() {
            final GameOutputBoundary gamePresenter = new TestGamePresenter();
            final EndInputBoundary endInteractor = new TestEndInteractor();
            final GameInteractor interactor = new GameInteractor(dao, gamePresenter, endInteractor,null);

            final GameState testState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final GameState expectedState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            testState.nextLetter(InMemoryWordDataAccessObject.WRONG_CHAR.charAt(0));
            expectedState.nextLetter(InMemoryWordDataAccessObject.WRONG_CHAR.charAt(0));

            interactor.executeSubmit(testState);
            assertEquals(testState, expectedState, "States do not match");
        }

        @Test
        void testExecuteSubmitFinishedWin() {
            final GameOutputBoundary gamePresenter = new TestGamePresenter() {
                @Override
                public void updateGameView(GameState gameState) {
                    assertTrue(true);
                }
            };
            final EndInputBoundary endInteractor = new TestEndInteractor() {
                @Override
                public void execute(EndInputData inputData) {
                    assertTrue(true);
                }
            };
            final GameInteractor interactor = new GameInteractor(dao, gamePresenter,endInteractor,null);

            final GameState testState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final GameState expectedState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final String word = InMemoryWordDataAccessObject.getCorrectWord(LENGTH);
            fillState(testState, word);
            fillState(expectedState, word);
            expectedState.submit();

            interactor.executeSubmit(testState);
            assertEquals(testState, expectedState, "States do not match");
        }

        @Test
        void testExecuteSubmitFinishedLoss() {
            final GameOutputBoundary gamePresenter = new TestGamePresenter() {
                @Override
                public void updateGameView(GameState gameState) {
                    assertTrue(true);
                }
            };
            final EndInputBoundary endInteractor = new TestEndInteractor() {
                @Override
                public void execute(EndInputData inputData) {
                    assertTrue(true);
                }
            };
            final GameInteractor interactor = new GameInteractor(dao, gamePresenter,endInteractor,null);

            final GameState testState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final GameState expectedState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final String word = InMemoryWordDataAccessObject.getWrongWord(LENGTH);
            fillState(testState, word);
            fillState(expectedState, word);
            testState.submit();
            testState.nextWord();
            expectedState.submit();
            expectedState.nextWord();
            fillState(testState, word);
            fillState(expectedState, word);
            expectedState.submit();

            interactor.executeSubmit(testState);
            assertEquals(testState, expectedState, "States do not match");
        }
    }

    @Nested
    class ExecuteBackspace {
        GameOutputBoundary gamePresenter = new GameOutputBoundary() {
            @Override
            public void updateGameView(GameState gameState) {
                assertTrue(true);
            }

            @Override
            public void shakeWord(GameState gameState) {
                fail("shakeWord should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareEndView(GameState gameState) {
                fail("prepareEndView should not be called.");
            }
        };

        @Test
        void testExecuteBackspaceEmpty() {
            final GameState testState = new GameState();
            final GameState expectedState = new GameState();

            final GameInteractor interactor = new GameInteractor(null, gamePresenter,null,null);
            interactor.executeBackspace(testState);

            assertEquals(testState, expectedState, "States do not match");
        }

        @Test
        void testExecuteBackspacePartial() {
            final GameState testState = new GameState();
            testState.nextLetter('A');
            testState.nextLetter('B');
            final GameState expectedState = new GameState();
            expectedState.nextLetter('A');

            final GameInteractor interactor = new GameInteractor(null, gamePresenter,null,null);
            interactor.executeBackspace(testState);

            assertEquals(testState, expectedState, "States do not match");
        }

        @Test
        void testExecuteBackspaceFull() {
            final GameState testState = new GameState();
            fillState(testState, "ABCDE");
            final GameState expectedState = new GameState();
            fillState(expectedState, "ABCD");

            final GameInteractor interactor = new GameInteractor(null, gamePresenter,null,null);
            interactor.executeBackspace(testState);
            assertEquals(testState, expectedState, "States do not match");
        }
    }

    @Nested
    class TestPrepareNewGame {
        private final InMemoryWordDataAccessObject dao = new InMemoryWordDataAccessObject();
        private GameState expected;
        class TestGamePresenter implements GameOutputBoundary {
            @Override
            public void updateGameView(GameState gameState) {
                assertEquals(gameState, expected, "GameState do not match");
            }

            @Override
            public void shakeWord(GameState gameState) {
                fail("shakeWord should not be called.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareEndView(GameState gameState) {
                fail("prepareEndView should not be called.");
            }
        }

        @Test
        void testPrepareNewGameSuccess() {
            final GameOutputBoundary gamePresenter = new TestGamePresenter();
            final OptionsViewModel optionsViewModel = new OptionsViewModel();

            final GameInteractor interactor = new GameInteractor(dao, gamePresenter,null, optionsViewModel);

            expected = new GameState(WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(
                    optionsViewModel.getState().getLength())), optionsViewModel.getState().getMaxGuesses());

            interactor.prepareNewGame();
        }

        @Test
        void testPrepareNewGameFail() {
            final GameOutputBoundary gamePresenter = new TestGamePresenter() {
                @Override
                public void updateGameView(GameState gameState) {
                    fail("updateGameView should not be called.");
                }
            };
            final OptionsViewModel optionsViewModel = new OptionsViewModel();
            optionsViewModel.getState().setLength(-1);

            final GameInteractor interactor = new GameInteractor(dao, gamePresenter,null, optionsViewModel);

            assertThrows(WordNotFoundException.class, interactor::prepareNewGame,
                    "Expected WordNotFoundException to be thrown.");
        }
    }

    @Test
    void testPrepareStartView() {
        final GameOutputBoundary gamePresenter = new GameOutputBoundary() {
            @Override
            public void updateGameView(GameState gameState) {
                fail("updateGameView should not be called.");
            }

            @Override
            public void shakeWord(GameState gameState) {
                fail("shakeWord should not be called.");
            }

            @Override
            public void prepareStartView() {
                assertTrue(true);
            }

            @Override
            public void prepareEndView(GameState gameState) {
                fail("prepareEndView should not be called.");
            }
        };

        final GameInteractor interactor = new GameInteractor(null, gamePresenter,null,null);
        interactor.prepareStartView();
    }

    @Nested
    class TestPrepareEndView {
        private static final int LENGTH = 4;
        private EndInputData expected;
        final EndInputBoundary endInteractor = new EndInputBoundary() {
            @Override
            public void execute(EndInputData inputData) {
                assertEquals(expected, inputData, "EndInputData do not match.");
            }

            @Override
            public void prepareStartView() {
                fail("prepareStartView should not be called.");
            }

            @Override
            public void prepareNewGame(EndInputData inputData) {
                fail("prepareNewGame should not be called.");
            }
        };

        @Test
        void testPrepareEndViewWin() {
            final GameInteractor interactor = new GameInteractor(null, null,endInteractor,null);

            final GameState testState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final String word = InMemoryWordDataAccessObject.getCorrectWord(LENGTH);
            fillState(testState, word);
            testState.submit();
            expected = new EndInputData(word, true, 1, 2, testState.getWords());

            interactor.prepareEndView(testState);
        }

        @Test
        void testPrepareEndViewLoss() {
            final GameInteractor interactor = new GameInteractor(null, null,endInteractor,null);

            final GameState testState = new GameState(
                    WordFactory.createWordToGuess(InMemoryWordDataAccessObject.getCorrectWord(LENGTH)), 2);
            final String word = InMemoryWordDataAccessObject.getWrongWord(LENGTH);
            fillState(testState, word);
            testState.submit();
            testState.nextWord();
            fillState(testState, word);
            testState.submit();
            expected = new EndInputData(InMemoryWordDataAccessObject.getCorrectWord(LENGTH),
                    false, 2, 2, testState.getWords());

            interactor.prepareEndView(testState);
        }
    }
}
