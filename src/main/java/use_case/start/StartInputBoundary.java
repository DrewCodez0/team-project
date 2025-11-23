package use_case.start;

/**
 * Input boundary for the Start Use Case.
 */
public interface StartInputBoundary {

    /**
     * Executes the Start use case.
     */
    void prepareStartView();

    /**
     * Executes the Game use case.
     */
    void prepareGameView();

    /**
     * Executes the Options use case.
     */
    void prepareOptionsView();

    /**
     * Executes the Stats use case.
     */
    void prepareStatsView();
}
