package use_case.end;

/**
 * The output boundary for the End Use Case.
 */
public interface EndOutputBoundary {

    /**
     * Prepares the success view for the End Use Case.
     * @param outputData the output data containing game results
     */
    void prepareSuccessView(EndOutputData outputData);

    /**
     * Prepares the fail view for the End Use Case.
     * @param outputData the output data containing game results
     */
    void prepareFailView(EndOutputData outputData);

    /**
     * Prepares the game view for a new game.
     */
    void prepareGameView();

    /**
     * Prepares the start view for the End Use Case.
     */
    void prepareStartView();
}
