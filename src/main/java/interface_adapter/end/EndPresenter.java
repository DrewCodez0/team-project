package interface_adapter.end;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;
import interface_adapter.start.StartViewModel;
import use_case.end.EndOutputBoundary;

public class EndPresenter implements EndOutputBoundary {

    public EndPresenter(ViewManagerModel viewManagerModel, EndViewModel endViewModel,
                        GameViewModel gameViewModel, StartViewModel  startViewModel,
                        OptionsViewModel optionsViewModel) {}

    @Override
    public void prepareGameView(OptionsState optionsState) {

    }

    @Override
    public void prepareOptionsView() {

    }

    @Override
    public void prepareStartView() {

    }
}
