package use_case.options;

import interface_adapter.options.OptionsState;

public class OptionsInteractor implements OptionsInputBoundary {
    public OptionsInteractor(OptionsDataAccessInterface optionsDataAccessInterface,
                             OptionsOutputBoundary optionsOutputBoundary) {}

    @Override
    public void execute(OptionsState inputData) {}
}
