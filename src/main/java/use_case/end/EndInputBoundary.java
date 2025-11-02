package use_case.end;

import interface_adapter.game.GameState;

/**
 * The End Use Case
 */
public interface EndInputBoundary {

    /**
     * Execute the End Use Case
     * @param endInputData the input data for this use case
     */
    void execute(GameState endInputData);
}
