package use_case.stats;

public interface StatsInputBoundary {
    /**
     * Executes the primary action to load and display statistics.
     * @param inputData the input data
     */
    void execute(StatsInputData inputData);

    /**
     * Prepares the start view.
     */
    void prepareStartView();

    /**
     * Executes the export stats action.
     */
    void exportStats();

    /**
     * Executes the import stats action.
     * @param importInputData The file to import from.
     */
    void importStats(StatsImportInputData importInputData);
}
