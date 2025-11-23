package use_case.game;

import interface_adapter.game.GameState;

/**
 * Input Boundary for playing the game.
 */
public interface GameInputBoundary {
    /**
     * Sends a letter to the game use case.
     * @param gameInputData the input data
     * @param letter the letter to add to the data
     */
    void executeLetter(GameState gameInputData, char letter);

    /**
     * Submits a word in the game use case.
     * @param gameInputData the input data to submit
     */
    void executeSubmit(GameState gameInputData);

    /**
     * Removes a letter in the game use case.
     * @param gameInputData the input data to remove the letter from
     */
    void executeBackspace(GameState gameInputData);

    /**
     * Prepares a new game.
     */
    void prepareNewGame();

    /**
     * Returns to the start menu.
     */
    void prepareStartView();

    /**
     * Prepares the end view.
     * @param gameState the state of the game
     */
    void prepareEndView(GameState gameState);
}
