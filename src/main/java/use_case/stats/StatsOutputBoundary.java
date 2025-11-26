package use_case.stats;

public interface StatsOutputBoundary {
    /**
     * Prepares the success view for the Stats Use Case.
     * This displays the user's stats
     * @param outputData the output data
     */
    void prepareSuccessView(StatsOutputData outputData);

    /**
     * Prepares the failure view for the Stats Use Case.
     * This executes when the DAO fails to get the stats data.
     */
    void prepareFailView(String message);

    /**
     * Prepares the start view for the Stats Use Case.
     * This returns the user to the main menu.
     */
    void prepareStartView();

    /**
     * Prepares the success view for the export stats action.
     * @param message the success message
     */
    void prepareExportSuccessView(String message);

    /**
     * Prepares the failure view for the export stats action.
     * @param message the error message
     */
    void prepareExportFailView(String message);
}
