package use_case.options;

import interface_adapter.options.OptionsState;

/**
 * The Options Use Case.
 */
public interface OptionsInputBoundary {

    /**
     * Execute the Options Use Case.
     * @param optionsInputData the input data for this use case
     */
    void execute(OptionsState optionsInputData);
}
