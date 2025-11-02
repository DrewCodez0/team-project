package use_case.start;

/**
 * The output boundary for the Start Use Case.
 */
public interface StartOutputBoundary {
    /**
     * Prepares the game view for the Start Use Case.
     */
    void prepareGameView();

    /**
     * Prepares the options view for the Start Use Case.
     */
    void prepareOptionsView();

    /**
     * Prepares the stats view for the Start Use Case.
     */
    void prepareStatsView();
}
