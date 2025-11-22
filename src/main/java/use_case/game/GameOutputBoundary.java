package use_case.game;

import interface_adapter.game.GameState;

/**
 * The output boundary for the Game Use Case.
 */
public interface GameOutputBoundary {
    /**
     * Prepares the game view for the Game Use Case.
     * @param state the state of the game
     */
    void updateGameView(GameState state);

    /**
     * Shakes the active word for this GameState.
     * @param state the state of the game
     */
    void shakeWord(GameState state);

    /**
     * Prepares the start view for the Game Use Case.
     */
    void prepareStartView();

    /**
     * Prepares the end view for the Game Use Case.
     * @param state the state of the game
     */
    void prepareEndView(GameState state);
}
