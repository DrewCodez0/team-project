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
    void execute(GameState gameInputData);
}
