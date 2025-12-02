package use_case.options;

import interface_adapter.options.OptionsState;
import data_access.SettingsStore;
import use_case.options.OptionsOutputBoundary;

public class OptionsInteractor implements OptionsInputBoundary {
    private final OptionsDataAccessInterface dao;
    private final OptionsOutputBoundary presenter;
    public OptionsInteractor(OptionsDataAccessInterface dao,
                             OptionsOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;

    }

    @Override
    public void execute(OptionsState inputData) {
        dao.saveOptions(inputData);
        SettingsStore.save(inputData);
    }
}
