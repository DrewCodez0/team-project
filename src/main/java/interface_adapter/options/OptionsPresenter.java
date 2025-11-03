package interface_adapter.options;

import interface_adapter.ViewManagerModel;
import interface_adapter.start.StartViewModel;
import use_case.options.OptionsOutputBoundary;

public class OptionsPresenter implements OptionsOutputBoundary {

    public OptionsPresenter(ViewManagerModel viewManagerModel,
                            OptionsViewModel optionsViewModel,
                            StartViewModel startViewModel) {}

    @Override
    public void prepareStartView() {

    }
}
