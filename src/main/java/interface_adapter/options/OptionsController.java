package interface_adapter.options;

import use_case.options.OptionsInputBoundary;

public class OptionsController {
    private final OptionsInputBoundary interactor;

    public OptionsController(OptionsInputBoundary optionsInputBoundary) {
        this.interactor = optionsInputBoundary;

    }

    public void execute(OptionsState newState) {
        interactor.execute(newState);
    }
}
