package use_case.end;

import interface_adapter.options.OptionsState;

/**
 * The output boundary for the End Use Case
 */
public interface EndOutputBoundary {
    /**
     * Prepares the game view for the End Use Case.
     * @param options the options for a new game
     */
    void prepareGameView(OptionsState options);

    /**
     * Prepares the options view for the End Use Case.
     */
    void prepareOptionsView();

    /**
     * Prepares the start view for the End Use Case.
     */
    void prepareStartView();
}
