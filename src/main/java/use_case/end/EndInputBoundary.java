package use_case.end;

/**
 * The End Use Case
 */
public interface EndInputBoundary {
    /**
     * Execute the End Use Case
     * @param endInputData the input data for this use case
     */
    void execute(EndInputData endInputData);

    void prepareStartView();

    void prepareNewGame(EndInputData endInputData);

}
