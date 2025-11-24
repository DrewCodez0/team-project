package use_case.stats;

import entity.Theme;

/**
 * Input boundary for the stats use case.
 */
public interface StatsInputBoundary {

    /**
     * Executes the stats use case.
     * @param statsInputData the input data for the stats use case
     */
    void execute(StatsInputData statsInputData);

    /**
     * Enables Start View.
     */
    void prepareStartView();
}