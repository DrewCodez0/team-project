package use_case.start;

import interface_adapter.options.OptionsState;

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
     * @param optionsState the options to use for the game
     */
    void prepareGameView(OptionsState optionsState);

    /**
     * Executes the Options use case.
     */
    void prepareOptionsView();

    /**
     * Executes the Stats use case.
     */
    void prepareStatsView();
}
