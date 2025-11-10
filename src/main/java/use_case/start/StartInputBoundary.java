package use_case.start;

import entity.Theme;
import interface_adapter.options.OptionsState;

/**
 * Input boundary for the Start Use Case.
 */
public interface StartInputBoundary {

    /**
     * Executes the Start use case.
     */
    void execute(Theme theme);

    void prepareGameView(OptionsState optionsState);

    void prepareOptionsView();

    void prepareStatsView();
}
