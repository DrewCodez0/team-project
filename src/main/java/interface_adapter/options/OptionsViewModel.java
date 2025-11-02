package interface_adapter.options;

import interface_adapter.ViewModel;

/**
 * The View Model for the Options View
 */
public class OptionsViewModel extends ViewModel<OptionsState> {
    public OptionsViewModel() {
        super("options");
        setState(new OptionsState());
    }
}
