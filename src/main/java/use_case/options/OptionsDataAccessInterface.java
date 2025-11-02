package use_case.options;

import interface_adapter.options.OptionsState;

/**
 * The DAO interface for the Options Use Case.
 */
public interface OptionsDataAccessInterface {
    /**
     * Gets the user's saved options.
     * @return the OptionsState representing the saved options
     */
    OptionsState getOptions();

    /**
     * Saves the user's options.
     * @param options the OptionsState representing the options to save
     */
    void saveOptions(OptionsState options);
}
