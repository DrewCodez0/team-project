package use_case.game;

import interface_adapter.game.GameState;

/**
 * Input Boundary for playing the game
 */
public interface GameInputBoundary {

    /**
     * Executes the Game Use Case
     * @param gameInputData the input data
     */
    void executeLetter(GameState gameInputData, char letter);

    void executeSubmit(GameState gameInputData);

    void executeBackspace(GameState gameInputData);

    /**
     * Returns to the start menu.
     */
    void prepareStartView();

    /**
     * Prepares the end view.
     */
    void prepareEndView(GameState gameState);
}
