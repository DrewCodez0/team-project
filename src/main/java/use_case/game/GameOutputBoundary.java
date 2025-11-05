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
    void prepareGameView(GameState state);

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
