package use_case.start;

import interface_adapter.options.OptionsState;

/**
 * The output boundary for the Start Use Case.
 */
public interface StartOutputBoundary {
    /**
     * Prepares the game view for the Start Use Case.
     */
    void prepareGameView(OptionsState optionsState);

    /**
     * Prepares the options view for the Start Use Case.
     */
    void prepareOptionsView();

    /**
     * Prepares the stats view for the Start Use Case.
     */
    void prepareStatsView();
}
