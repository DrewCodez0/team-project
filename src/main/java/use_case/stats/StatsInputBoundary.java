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
     * @param exportInputData The input data for exporting stats, including the destination path.
     */
    void exportStats(ExportStatsInputData exportInputData);

    /**
     * Executes the import stats action.
     * @param importInputData The file to import from.
     */
    void importStats(StatsImportInputData importInputData);
}