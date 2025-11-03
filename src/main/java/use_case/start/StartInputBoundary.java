package use_case.start;

import entity.Theme;

/**
 * Input boundary for the Start Use Case.
 */
public interface StartInputBoundary {

    /**
     * Executes the Start use case.
     */
    void execute(Theme theme);

    void prepareGameView();

    void prepareOptionsView();

    void prepareStatsView();
}
